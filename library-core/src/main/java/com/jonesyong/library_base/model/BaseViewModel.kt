package com.jonesyong.library_base.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Boolean>()
}