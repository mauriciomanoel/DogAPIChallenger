package com.mauricio.dogapichallenger.breeds.repository

import com.mauricio.dogapichallenger.breeds.Breed
import com.mauricio.dogapichallenger.breeds.BreedsResult
import com.mauricio.dogapichallenger.network.RetrofitApiService
import kotlinx.coroutines.*
import javax.inject.Inject

class BreedsRepository @Inject constructor(private val apiService: RetrofitApiService)  {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val breeds = ArrayList<Breed>()

    fun getBreeds(process: (value: BreedsResult?, e: Throwable?) -> Unit) {

        val handler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")
            process(null, exception)
        }

        coroutineScope.launch(handler) {
            val breeds = apiService.getBreeds()
            updateLocalBreeds(breeds)
            process(breeds, null)
        }
    }

    private fun updateLocalBreeds(values: ArrayList<Breed>) {
        breeds.clear()
        breeds.addAll(values)
    }

    fun getBreeds() = breeds
}