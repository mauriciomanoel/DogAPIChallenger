package com.mauricio.dogapichallenger.breeds.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mauricio.dogapichallenger.breeds.models.Breed
import com.mauricio.dogapichallenger.databinding.ItemDogBreedsBinding
import com.mauricio.dogapichallenger.BR
import com.mauricio.dogapichallenger.R
import com.mauricio.dogapichallenger.breeds.models.BreedResultElement
import com.mauricio.dogapichallenger.breeds.models.IOnClickEvent
import com.mauricio.dogapichallenger.utils.TextUtils

class DogBreedsRecyclerViewAdapter(
    private val values: List<Any>, private val callback: IOnClickEvent
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
        values[position].run {
            holder.binding.itemDogBreed.setOnClickListener {
                callback.onItemClicked(this)
            }
            when(this) {
                is Breed -> holder.bind(this)
                is BreedResultElement -> holder.bind(this)
            }
        }
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(var binding: ItemDogBreedsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(breed: Breed) {
            binding.setVariable(BR.urlPhoto, breed.image?.url)
            binding.setVariable(BR.name, breed.name)
            binding.setVariable(BR.showDetails, false)
            binding.executePendingBindings()
        }
        fun bind(breed: BreedResultElement) {
            binding.setVariable(BR.urlPhoto, breed.url)
            binding.setVariable(BR.name, "${binding.root.context.getString(R.string.title_breed_name)}: ${breed.breeds[0].name}")
            binding.setVariable(BR.breedGroup, "${binding.root.context.getString(R.string.title_breed_category)}: ${TextUtils.checkIsEmpty(binding.root.context, breed.breeds[0].breedGroup)}")
            binding.setVariable(BR.origin, "${binding.root.context.getString(R.string.title_origin)}: ${TextUtils.checkIsEmpty(binding.root.context, breed.breeds[0].origin)}")
            binding.setVariable(BR.showDetails, true)
            binding.executePendingBindings()
        }
    }
}

