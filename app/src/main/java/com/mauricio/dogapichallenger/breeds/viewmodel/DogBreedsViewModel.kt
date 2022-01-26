package com.mauricio.dogapichallenger.breeds.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mauricio.dogapichallenger.breeds.models.Breed
import com.mauricio.dogapichallenger.breeds.models.BreedResultElement
import com.mauricio.dogapichallenger.breeds.models.BreedsByIdResult
import com.mauricio.dogapichallenger.breeds.models.BreedsResult
import com.mauricio.dogapichallenger.breeds.repository.BreedsRepository
import com.mauricio.dogapichallenger.utils.Constant.ORDER_BY_ASCENDING
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DogBreedsViewModel @Inject constructor(val repository: BreedsRepository) : ViewModel() {

    private val _messageError = MutableLiveData<String>()
    val messageError: LiveData<String> = _messageError

    private val _breeds = MutableLiveData<ArrayList<Breed>>()
    val breeds: LiveData<ArrayList<Breed>> = _breeds

    private val _breedsBySearch = MutableLiveData<ArrayList<BreedResultElement>>()
    val breedsBySearch: LiveData<ArrayList<BreedResultElement>> = _breedsBySearch

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
        val values = repository.getBreeds()
        val valuesSorted = when(sortBy) {
            ORDER_BY_ASCENDING ->  values?.sortedBy { it.name }
            else -> values?.sortedByDescending { it.name }
        }
        _breeds.apply {
            val value = ArrayList<Breed>()
            valuesSorted?.let {
                value.addAll(valuesSorted)
            }
            postValue(value)
        }
    }

    fun getBreedsName(): ArrayList<String> {
        return repository.getBreedsName()
    }

    fun searchBreedByPosition(position: Int) {
        val values = repository.getBreeds()
        val breed = values?.get(position)
        breed?.id?.let { breedId ->
            showLoading()
            repository.getBreedsById(breedId, ::processBreedsBySearch)
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