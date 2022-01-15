package com.mauricio.dogapichallenger.breeds.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mauricio.dogapichallenger.breeds.Breed
import com.mauricio.dogapichallenger.databinding.ItemDogBreedsBinding

class DogBreedsRecyclerViewAdapter(
    private val values: List<Breed>
) : RecyclerView.Adapter<DogBreedsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DogBreedsRecyclerViewAdapter.ViewHolder {

        return ViewHolder(
            ItemDogBreedsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DogBreedsRecyclerViewAdapter.ViewHolder, position: Int) {

    }

    override fun getItemCount() = values.size

    inner class ViewHolder(binding: ItemDogBreedsBinding) : RecyclerView.ViewHolder(binding.root) {
//        val idView: TextView = binding.itemNumber
//        val contentView: TextView = binding.content
//        val imageView: ImageView = binding.imageView
    }
}