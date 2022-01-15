package com.mauricio.dogapichallenger.breeds.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mauricio.dogapichallenger.breeds.BreedsResult
import com.mauricio.dogapichallenger.breeds.repository.BreedsRepository
import com.mauricio.dogapichallenger.di.component.DaggerAppComponent
import javax.inject.Inject

class DogBreedsViewModel@Inject constructor(private val application: Application) : ViewModel() {

    @Inject
    lateinit var repository: BreedsRepository

    //initializing the necessary components and classes
    init {
        DaggerAppComponent.builder().app(application).build().inject(this)
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is Dog Breeds Fragment"
    }
    val text: LiveData<String> = _text


    fun getBreeds() {
        repository.getBreeds(::processBreeds)
    }

    private fun processBreeds(result: BreedsResult?, e: Throwable?) {

        Log.v("TAG", "$result")

    }
}