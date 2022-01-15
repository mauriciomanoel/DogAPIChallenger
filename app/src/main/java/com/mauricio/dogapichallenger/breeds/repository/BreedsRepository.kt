package com.mauricio.dogapichallenger.breeds.repository

import com.mauricio.dogapichallenger.breeds.BreedsResult
import com.mauricio.dogapichallenger.network.RetrofitApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class BreedsRepository @Inject constructor(private val apiService: RetrofitApiService)  {

    private val aplScope = CoroutineScope(Dispatchers.Main)

    fun getBreeds(process: (value: BreedsResult?, e: Throwable?) -> Unit) {
        aplScope.launch(Dispatchers.IO) {
            val breeds = apiService.getBreeds()
            process(breeds, null)
        }
    }
}