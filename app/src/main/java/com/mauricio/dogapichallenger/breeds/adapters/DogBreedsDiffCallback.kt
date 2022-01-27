package com.mauricio.dogapichallenger.breeds.adapters

import androidx.recyclerview.widget.DiffUtil
import com.mauricio.dogapichallenger.breeds.models.Breed
import com.mauricio.dogapichallenger.breeds.models.BreedResultElement

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