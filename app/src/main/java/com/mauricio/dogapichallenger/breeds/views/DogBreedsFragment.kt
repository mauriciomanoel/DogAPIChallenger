package com.mauricio.dogapichallenger.breeds.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.mauricio.dogapichallenger.AndroidDogApiApplication
import com.mauricio.dogapichallenger.breeds.viewmodel.DogBreedsViewModel
import com.mauricio.dogapichallenger.databinding.FragmentDogBreedsBinding
import javax.inject.Inject

class DogBreedsFragment : Fragment() {

    @Inject
    lateinit var dogBreedsViewModel: DogBreedsViewModel
    private var _binding: FragmentDogBreedsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as AndroidDogApiApplication).androidInjector.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        dogBreedsViewModel =
//            ViewModelProvider(this).get(DogBreedsViewModel::class.java)

        _binding = FragmentDogBreedsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        dogBreedsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        dogBreedsViewModel.getBreeds()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}