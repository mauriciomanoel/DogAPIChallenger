package com.mauricio.dogapichallenger.breeds.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mauricio.dogapichallenger.breeds.Breed
import com.mauricio.dogapichallenger.databinding.ItemDogBreedsBinding
import com.mauricio.dogapichallenger.BR
import com.mauricio.dogapichallenger.breeds.models.IOnClickEvent

class DogBreedsRecyclerViewAdapter(
    private val values: List<Breed>, private val callback: IOnClickEvent
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
        values[position]?.let { breed ->
            holder.binding.itemDogBreed.setOnClickListener {
                callback.onItemClicked(breed)
            }
            holder.bind(breed)
        }
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(var binding: ItemDogBreedsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(breed: Breed) {
            binding.setVariable(BR.urlPhoto, breed.image.url)
            binding.setVariable(BR.name, breed.name)
            binding.executePendingBindings()
        }
    }
}

