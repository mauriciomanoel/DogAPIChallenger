package com.mauricio.dogapichallenger.breeds.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.reflect.TypeToken
import com.mauricio.dogapichallenger.breeds.models.Breed
import com.mauricio.dogapichallenger.breeds.models.BreedsByIdResult
import com.mauricio.dogapichallenger.breeds.models.BreedsResult
import com.mauricio.dogapichallenger.network.RetrofitApiService
import com.mauricio.dogapichallenger.utils.SharedPreferencesUtils
import kotlinx.coroutines.*
import java.lang.Exception
import javax.inject.Inject

class BreedsRepository @Inject constructor(private val apiService: RetrofitApiService,  private val breedDao: BreedDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val breeds = ArrayList<Breed>()
    private val jobs = mutableListOf<Job>()

    fun getBreeds(process: (value: BreedsResult?, e: Throwable?) -> Unit) {

        val handler = CoroutineExceptionHandler { _, exception ->
            Log.e(TAG, "CoroutineExceptionHandler got $exception")
            reportErros(exception)
            getBreedsFromDatabase { values ->
                process(values, null)
            }
        }

        val job = coroutineScope.launch(handler) {
            val breeds = apiService.getBreeds()
            addAllBreeds(breeds)
            process(breeds, null)
        }

        jobs.add(job)

        job.invokeOnCompletion { exception: Throwable? ->
            exception?.let {
                Log.e(TAG, "JobCancellationException got $exception")
                reportErros(exception)
                getBreedsFromDatabase { values ->
                    process(values, null)
                }
            }
        }
    }

    fun getBreedsById(breedId: Long, process: (value: BreedsByIdResult?, e: Throwable?) -> Unit) {

        val handler = CoroutineExceptionHandler { _, exception ->
            Log.e(TAG, "CoroutineExceptionHandler got $exception")
            reportErros(exception)
            process(null, exception)
        }

        val job = coroutineScope.launch(handler) {
            val breeds = apiService.getBreedsById(breedId.toString())
            process(breeds, null)
        }

        jobs.add(job)

        job.invokeOnCompletion { exception: Throwable? ->
            exception?.let {
                Log.e(TAG, "JobCancellationException got $exception")
                reportErros(exception)
                process(null, exception)
            }
        }
    }

    fun getBreeds(process: (values: ArrayList<Breed>) -> Unit) {
        getBreedsFromDatabase { breeds->
            process(breeds)
        }
    }

    fun getBreedsName(): LiveData<List<String>> {
        val _breeds = MutableLiveData<List<String>>()

        getBreedsFromDatabase { values->
            values.map { it.name }.let {
                _breeds.postValue(it)
            }
        }
        return _breeds
    }

    fun cancelAllJobs() {
        for (job in jobs) {
            if (job.isActive) {
                job.cancel()
            }
        }
    }

    private fun addAllBreeds(values: ArrayList<Breed>) {
        coroutineScope.launch {
            values.forEach { breed ->
                breedDao.insert(breed)
            }
        }
    }

    private fun getBreedsFromDatabase(process: (values: ArrayList<Breed>) -> Unit) {
        CoroutineScope(Dispatchers.IO).async {
            val value = breedDao.getBreeds()
            process(ArrayList(value))
        }
    }

    // Send information to backend
    private fun reportErros(exception: Throwable) {}


    companion object {
        val TAG = BreedsRepository::class.java.simpleName
        private val KEY_STORE_BREEDS = "db38559e35e10446884877da28b4580773286295"
    }
}