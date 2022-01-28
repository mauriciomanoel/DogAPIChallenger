package com.mauricio.dogbreedapi.breeds.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mauricio.dogbreedapi.breeds.models.Breed
import com.mauricio.dogbreedapi.breeds.models.BreedResultElement
import com.mauricio.dogbreedapi.breeds.models.BreedsByIdResult
import com.mauricio.dogbreedapi.breeds.models.BreedsResult
import com.mauricio.dogbreedapi.breeds.repository.BreedsRepository
import com.mauricio.dogbreedapi.utils.Constant.ORDER_BY_ASCENDING
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DogBreedsViewModel @Inject constructor(val repository: BreedsRepository) : ViewModel() {

    private val _messageError = MutableLiveData<String>()
    val messageError: LiveData<String> = _messageError

    private val _breeds = MutableLiveData<List<Breed>>()
    val breeds: LiveData<List<Breed>> = _breeds

    private val _breedsBySearch = MutableLiveData<List<BreedResultElement>>()
    val breedsBySearch: LiveData<List<BreedResultElement>> = _breedsBySearch

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> = _showLoading

    override fun onCleared() {
        repository.cancelAllJobs()
        super.onCleared()
    }

    fun getBreeds() {
        showLoading()
        repository.getBreeds(::processBreeds)
    }

    fun orderByBreeds(sortBy: String) {
        print("orderByBreeds: $sortBy")

        _breeds.value?.let { values ->
            Log.v("orderByBreeds", "$values")
            val valuesSorted = when(sortBy) {
                ORDER_BY_ASCENDING -> values.sortedBy { it.name }
                else -> values.sortedByDescending { it.name }
            }
            Log.v("orderByBreeds", "$sortBy | $valuesSorted")
            _breeds.apply {
                postValue(valuesSorted)
            }
        }

//        repository.getBreeds { values ->
//            Log.v("orderByBreeds", "$values")
//            val valuesSorted = when(sortBy) {
//                ORDER_BY_ASCENDING -> values.sortedBy { it.name }
//                else -> values.sortedByDescending { it.name }
//            }
//            Log.v("orderByBreeds", "$sortBy | $valuesSorted")
//            _breeds.apply {
//                postValue(valuesSorted)
//            }
//        }
    }

    fun getBreedsName() = repository.getBreedsName()

    fun searchBreedByPosition(position: Int) {
        repository.getBreeds { values ->
            values[position].id.run {
                showLoading()
                repository.getBreedsById(this, ::processBreedsBySearch)
            }
        }
    }

    fun searchBreedById(breedId: Long) {
        showLoading()
        repository.getBreedsById(breedId, ::processBreedsBySearch)
    }

    private fun processBreeds(result: BreedsResult?, e: Throwable?) {
        hideLoading()
        result?.let {
            _breeds.apply {
                postValue(it)
            }
        } ?: run {
            _messageError.apply {
                postValue(e?.message)
            }
        }
        Log.v("TAG", "$result")
    }

    private fun processBreedsBySearch(result: BreedsByIdResult?, e: Throwable?) {
        hideLoading()
        result?.let {
            _breedsBySearch.apply {
                postValue(it)
            }
        } ?: run {
            _messageError.apply {
                postValue(e?.message)
            }
        }
        Log.v("TAG", "$result")
    }

    private fun showLoading() {
        _showLoading.apply {
            postValue(true)
        }
    }

    private fun hideLoading() {
        _showLoading.apply {
            postValue(false)
        }
    }

}