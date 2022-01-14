package com.mauricio.dogapichallenger.ui.dogbreeds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DogBreedsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Dog Breeds Fragment"
    }
    val text: LiveData<String> = _text
}