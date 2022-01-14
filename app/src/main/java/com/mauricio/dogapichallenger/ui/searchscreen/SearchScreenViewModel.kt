package com.mauricio.dogapichallenger.ui.searchscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchScreenViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Search Screen Fragment"
    }
    val text: LiveData<String> = _text
}