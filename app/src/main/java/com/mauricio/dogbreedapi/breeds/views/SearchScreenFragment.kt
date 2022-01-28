package com.mauricio.dogbreedapi.breeds.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.mauricio.dogbreedapi.breeds.adapters.DogBreedsRecyclerViewAdapter
import com.mauricio.dogbreedapi.breeds.models.IOnClickEvent
import com.mauricio.dogbreedapi.breeds.viewmodel.DogBreedsViewModel
import com.mauricio.dogbreedapi.databinding.FragmentSearchScreenBinding
import com.mauricio.dogbreedapi.utils.Constant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchScreenFragment : Fragment(), IOnClickEvent  {

    private val viewModel by viewModels<DogBreedsViewModel>()
    private var _binding: FragmentSearchScreenBinding? = null
    private lateinit var mContext: Context
    private lateinit var breedsAdapter: DogBreedsRecyclerViewAdapter
    private var callback: IOnClickEvent? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        callback = (activity as? IOnClickEvent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchScreenBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initObservers()
        initAdapters()
        initializeSpinnerData()
        initializeParameters()

        return root
    }

    private fun initializeParameters() {
        with(binding) {
            layoutManager = Constant.LIST_VIEW_FORMAT
        }
    }

    private fun initObservers() {
        with(viewModel) {
            breedsBySearch.observe(viewLifecycleOwner, {
                breedsAdapter.differ.submitList(it)
            })
            showLoading.observe(viewLifecycleOwner, { showLoading ->
                binding.showLoading = showLoading
            })
            messageError.observe(viewLifecycleOwner, { message ->
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun initAdapters() {
        breedsAdapter = DogBreedsRecyclerViewAdapter(this)
        binding.breedsAdapter = breedsAdapter
    }

    private fun initializeSpinnerData() {

        viewModel.getBreedsName().observe(viewLifecycleOwner, { values ->
            if (values.size > 0) {
                ArrayAdapter(mContext, android.R.layout.simple_spinner_item, values).also { adapter ->
                    // Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    // Apply the adapter to the spinner
                    binding.spinner.adapter = adapter
                    binding.spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                            viewModel.searchBreedByPosition(position)
                        }
                        override fun onNothingSelected(parent: AdapterView<*>?) {}
                    })
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        callback = null
    }

    override fun onItemClicked(value: Any) {
        callback?.onItemClicked(value)
    }
}