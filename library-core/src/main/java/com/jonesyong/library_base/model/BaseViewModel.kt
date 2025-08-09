package com.jonesyong.library_base.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Boolean>()

    fun beforeRequest() {
        loading.value = true
        error.value = false
    }

    fun afterRequest(success: Boolean = true, notify: () -> Unit) {
        loading.value = false
        if (success) {
            error.value = false
            notify.invoke()
        } else {
            error.value = true
        }
    }
}