package com.mauricio.dogapichallenger.breeds.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mauricio.dogapichallenger.breeds.models.Breed
import com.mauricio.dogapichallenger.databinding.ItemDogBreedsBinding
import com.mauricio.dogapichallenger.BR
import com.mauricio.dogapichallenger.R
import com.mauricio.dogapichallenger.breeds.models.BreedResultElement
import com.mauricio.dogapichallenger.breeds.models.IOnClickEvent
import com.mauricio.dogapichallenger.utils.extensions.checkIsEmpty

class DogBreedsRecyclerViewAdapter(private val callback: IOnClickEvent
) : RecyclerView.Adapter<DogBreedsRecyclerViewAdapter.ViewHolder>() {

    val differ = AsyncListDiffer(this, DogBreedsDiffCallback())
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
        differ.currentList[position].run {
            holder.binding.itemDogBreed.setOnClickListener {
                callback.onItemClicked(this)
            }
            when(this) {
                is Breed -> holder.bind(this)
                is BreedResultElement -> holder.bind(this)
            }
        }
    }

    override fun getItemCount() = differ.currentList.size

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
            binding.setVariable(BR.breedGroup, "${binding.root.context.getString(R.string.title_breed_category)}: ${breed.breeds[0].breedGroup?.checkIsEmpty(binding.root.context)}")
            binding.setVariable(BR.origin, "${binding.root.context.getString(R.string.title_origin)}: ${breed.breeds[0].origin?.checkIsEmpty(binding.root.context)}")
            binding.setVariable(BR.showDetails, true)
            binding.executePendingBindings()
        }
    }
}

class DogBreedsDiffCallback: DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return when(oldItem) {
            is Breed ->  oldItem.id == (newItem as Breed).id
            else -> (oldItem as BreedResultElement).id == (newItem as BreedResultElement).id
        }
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return when(oldItem) {
            is Breed ->  (oldItem as Breed) == (newItem as Breed)
            else -> (oldItem as BreedResultElement) == (newItem as BreedResultElement)
        }
    }
}

