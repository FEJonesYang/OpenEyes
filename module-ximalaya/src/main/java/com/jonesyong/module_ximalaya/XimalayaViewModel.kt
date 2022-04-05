package com.jonesyong.module_ximalaya

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class XimalayaViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is ximalaya Fragment"
    }
    val text: LiveData<String> = _text
}