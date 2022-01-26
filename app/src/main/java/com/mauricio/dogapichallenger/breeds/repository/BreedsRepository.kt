package com.mauricio.dogapichallenger.breeds.repository

import android.app.Application
import android.util.Log
import com.google.gson.reflect.TypeToken
import com.mauricio.dogapichallenger.breeds.models.Breed
import com.mauricio.dogapichallenger.breeds.models.BreedsByIdResult
import com.mauricio.dogapichallenger.breeds.models.BreedsResult
import com.mauricio.dogapichallenger.network.RetrofitApiService
import com.mauricio.dogapichallenger.utils.SharedPreferencesUtils
import kotlinx.coroutines.*
import javax.inject.Inject

class BreedsRepository @Inject constructor(private val apiService: RetrofitApiService, private val application: Application) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val breeds = ArrayList<Breed>()

    fun getBreeds(process: (value: BreedsResult?, e: Throwable?) -> Unit) {

        val handler = CoroutineExceptionHandler { _, exception ->
            Log.e(TAG, "CoroutineExceptionHandler got $exception")
            process(null, exception)
        }

        // Melhoria
        coroutineScope.launch(handler) {
            val breeds = apiService.getBreeds()
            updateLocalBreeds(breeds)
            process(breeds, null)
        }.invokeOnCompletion { exception: Throwable? ->
            exception?.let {
                Log.e(TAG, "JobCancellationException got $exception")
                process(null, exception)
            }
        }
    }

    fun getBreedsById(breedId: Long, process: (value: BreedsByIdResult?, e: Throwable?) -> Unit) {

        val handler = CoroutineExceptionHandler { _, exception ->
            Log.e(TAG, "CoroutineExceptionHandler got $exception")
            process(null, exception)
        }

        // Melhoria
        coroutineScope.launch(handler) {
            val breeds = apiService.getBreedsById(breedId.toString())
            process(breeds, null)
        }.invokeOnCompletion { exception: Throwable? ->
            exception?.let {
                Log.e(TAG, "JobCancellationException got $exception")
                process(null, exception)
            }
        }
    }

    private fun updateLocalBreeds(values: ArrayList<Breed>) {
        breeds.clear()
        breeds.addAll(values)
        SharedPreferencesUtils.save(application, values, KEY_STORE_BREEDS)
    }

    fun getBreeds(): ArrayList<Breed>? {
        val listType = object : TypeToken<ArrayList<Breed?>?>() {}.type
        val values = SharedPreferencesUtils.get(application, listType, KEY_STORE_BREEDS) as? ArrayList<Breed>
        return values ?: breeds
    }

    // Melhoria
    // Map iteração 1 para 1  | FlapMap interação 1 para n
    fun getBreedsName(): ArrayList<String> {
        return ArrayList(getBreeds()?.map { it.name })
    }

    companion object {
        val TAG = BreedsRepository::class.java.simpleName
        private val KEY_STORE_BREEDS = "db38559e35e10446884877da28b4580773286295"
    }
}