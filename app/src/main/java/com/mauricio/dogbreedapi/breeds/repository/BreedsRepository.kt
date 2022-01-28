package com.mauricio.dogbreedapi.breeds.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mauricio.dogbreedapi.breeds.models.Breed
import com.mauricio.dogbreedapi.breeds.models.BreedsByIdResult
import com.mauricio.dogbreedapi.breeds.models.BreedsResult
import com.mauricio.dogbreedapi.network.RetrofitApiService
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BreedsRepository @Inject constructor(private val apiService: RetrofitApiService,  private val breedDao: BreedDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val jobs = mutableListOf<Job>()

    fun getBreeds(process: (value: BreedsResult?, e: Throwable?) -> Unit) {

        val handler = CoroutineExceptionHandler { _, exception ->
            Log.e(TAG, "CoroutineExceptionHandler got $exception")
            reportErros(exception)
            getBreedsFromDatabase { process(it, null) }
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
                getBreedsFromDatabase { process(it, null) }
            }
        }
    }

    fun getBreedsById(breedId: Long, process: (value: BreedsByIdResult?, e: Throwable?) -> Unit) {

        val handler = CoroutineExceptionHandler { _, exception ->
            Log.e(TAG, "CoroutineExceptionHandler got $exception")
            reportErros(exception)
            process(null, exception)
        }

        val job = coroutineScope.launch(handler) { process(apiService.getBreedsById(breedId.toString()), null) }
        jobs.add(job)
        job.invokeOnCompletion { exception: Throwable? ->
            exception?.let {
                Log.e(TAG, "JobCancellationException got $exception")
                reportErros(exception)
                process(null, exception)
            }
        }
    }

    fun getBreeds(process: (values: ArrayList<Breed>) -> Unit)  {
        getBreedsFromDatabase {
            process(it)
        }
    }

    fun getBreedsName(): LiveData<List<String>> {
        val _breeds = MutableLiveData<List<String>>()
        getBreedsFromDatabase { values-> _breeds.postValue(values.map { it.name }) }
        return _breeds
    }

    fun cancelAllJobs() {
        jobs.forEach {
            if (it.isActive) {
                it.cancel()
            }
        }
    }

    private fun addAllBreeds(values: ArrayList<Breed>) {
        coroutineScope.launch {
            values.forEach { breedDao.insert(it) }
        }
    }

    private fun getBreedsFromDatabase(process: (values: ArrayList<Breed>) -> Unit) {
        jobs.add(CoroutineScope(Dispatchers.IO).async { process(ArrayList(breedDao.getBreeds())) })
    }

    // Send information to backend
    private fun reportErros(exception: Throwable) {}

    companion object {
        val TAG = BreedsRepository::class.java.simpleName
    }
}