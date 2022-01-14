package com.mauricio.dogapichallenger.ui.searchscreen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mauricio.dogapichallenger.databinding.FragmentSearchScreenBinding

class SearchScreenFragment : Fragment() {

    private lateinit var searchScreenViewModel: SearchScreenViewModel
    private var _binding: FragmentSearchScreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchScreenViewModel =
            ViewModelProvider(this).get(SearchScreenViewModel::class.java)

        _binding = FragmentSearchScreenBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        searchScreenViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}