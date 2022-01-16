package com.mauricio.dogapichallenger.breeds.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.mauricio.dogapichallenger.databinding.FragmentSearchScreenBinding
import android.widget.Toast
import com.mauricio.dogapichallenger.AndroidDogApiApplication
import com.mauricio.dogapichallenger.breeds.viewmodel.DogBreedsViewModel
import javax.inject.Inject

class SearchScreenFragment : Fragment() {

    @Inject
    lateinit var viewModel: DogBreedsViewModel
    private var _binding: FragmentSearchScreenBinding? = null
    private lateinit var mContext: Context

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        (context.applicationContext as AndroidDogApiApplication).androidInjector.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchScreenBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initializeSpinnerData()
        return root
    }


    private fun initializeSpinnerData() {

        val values = viewModel.getBreedsName()
        if (values.size > 0) {
            ArrayAdapter(mContext, android.R.layout.simple_spinner_item, values).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                binding.spinner.adapter = adapter
                binding.spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                        Toast.makeText(parent.context, "Spinner item $position", Toast.LENGTH_SHORT)
                            .show()
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                })
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}