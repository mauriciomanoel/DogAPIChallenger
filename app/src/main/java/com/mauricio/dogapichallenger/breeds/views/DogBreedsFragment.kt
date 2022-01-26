package com.mauricio.dogapichallenger.breeds.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mauricio.dogapichallenger.breeds.models.Breed
import com.mauricio.dogapichallenger.breeds.adapters.DogBreedsRecyclerViewAdapter
import com.mauricio.dogapichallenger.breeds.models.IOnClickEvent
import com.mauricio.dogapichallenger.breeds.viewmodel.DogBreedsViewModel
import com.mauricio.dogapichallenger.databinding.FragmentDogBreedsBinding
import com.mauricio.dogapichallenger.utils.Constant.DEFAULT_COLUNS
import com.mauricio.dogapichallenger.utils.Constant.GRID_VIEW_FORMAT
import com.mauricio.dogapichallenger.utils.Constant.LIST_VIEW_FORMAT
import com.mauricio.dogapichallenger.utils.Constant.ORDER_BY_ASCENDING
import com.mauricio.dogapichallenger.utils.Constant.ORDER_BY_DESCENDING
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogBreedsFragment : Fragment(), IOnClickEvent {

    private val viewModel by viewModels<DogBreedsViewModel>()

    private var _binding: FragmentDogBreedsBinding? = null
    private lateinit var breedsAdapter: DogBreedsRecyclerViewAdapter
    private val listBreeds = ArrayList<Breed>()
    private lateinit var mContext: Context
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

        _binding = FragmentDogBreedsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initializeParameters()
        initListeners()
        initAdapters()
        initObservers()

        return root
    }

    // Melhorias
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getBreeds()
    }

    private fun initializeParameters() {
        // melhoria
        with(binding) {
            columns = DEFAULT_COLUNS
            layoutManager = GRID_VIEW_FORMAT
        }
    }
    private fun initListeners() {
        binding.gridViewFormat.setOnClickListener {
            binding.columns = DEFAULT_COLUNS
            binding.layoutManager = GRID_VIEW_FORMAT
            Toast.makeText(activity, "gridView", Toast.LENGTH_SHORT).show()
        }
        binding.listViewFormat.setOnClickListener {
            binding.layoutManager = LIST_VIEW_FORMAT
            Toast.makeText(activity, "listView", Toast.LENGTH_SHORT).show()
        }
        binding.orderByAscending.setOnClickListener {
            viewModel.orderByBreeds(ORDER_BY_ASCENDING)
            Toast.makeText(activity, "orderByAscending", Toast.LENGTH_SHORT).show()
        }
        binding.orderByDescending.setOnClickListener {
            viewModel.orderByBreeds(ORDER_BY_DESCENDING)
            Toast.makeText(activity, "orderByDescending", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initObservers() {
        viewModel.breeds.observe(viewLifecycleOwner, {
            // Melhoria do código com DiffUtils
            listBreeds.clear()
            listBreeds.addAll(it)
            breedsAdapter.notifyDataSetChanged()
        })
        viewModel.showLoading.observe(viewLifecycleOwner, { showLoading ->
            binding.showLoading = showLoading
        })
        viewModel.messageError.observe(viewLifecycleOwner, { message ->
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
        })
    }

    private fun initAdapters() {
        breedsAdapter = DogBreedsRecyclerViewAdapter(listBreeds, this)
        binding.breedsAdapter = breedsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        // melhoria do código
        callback = null
    }

    override fun onItemClicked(value: Any) {
        callback?.onItemClicked(value)
    }
}