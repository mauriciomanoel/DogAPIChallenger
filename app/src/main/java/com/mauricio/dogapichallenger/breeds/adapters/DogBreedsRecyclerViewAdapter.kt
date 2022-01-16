package com.mauricio.dogapichallenger.breeds.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mauricio.dogapichallenger.breeds.Breed
import com.mauricio.dogapichallenger.databinding.ItemDogBreedsBinding
import com.mauricio.dogapichallenger.BR
import com.mauricio.dogapichallenger.R
import com.mauricio.dogapichallenger.breeds.BreedResultElement
import com.mauricio.dogapichallenger.breeds.models.IOnClickEvent

class DogBreedsRecyclerViewAdapter(
    private val values: List<Any>, private val callback: IOnClickEvent
) : RecyclerView.Adapter<DogBreedsRecyclerViewAdapter.ViewHolder>() {

    private lateinit var context: Context
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DogBreedsRecyclerViewAdapter.ViewHolder {
        context = parent.context
        return ViewHolder(
            ItemDogBreedsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DogBreedsRecyclerViewAdapter.ViewHolder, position: Int) {
        values[position]?.let { element ->
            holder.binding.itemDogBreed.setOnClickListener {
                callback.onItemClicked(element)
            }
            when(element) {
                is Breed -> holder.bind(element)
                is BreedResultElement -> holder.bind(element)
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
            binding.setVariable(BR.name, "${context.getString(R.string.title_breed_name)}: ${breed.breeds[0].name}")
            binding.setVariable(BR.breedGroup, "${context.getString(R.string.title_breed_category)}: ${breed.breeds[0].breedGroup}")
            binding.setVariable(BR.origin, "${context.getString(R.string.title_origin)}: ${checkIsEmpty(breed.breeds[0].origin)}")
            binding.setVariable(BR.showDetails, true)
            binding.executePendingBindings()
        }

        private fun checkIsEmpty(value: String?): String {
            if (value.isNullOrBlank()) return "Is not available"
            return value
        }
    }
}

