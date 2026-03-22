package com.jonesyong.library_base.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    internal val loading = MutableLiveData<Boolean>()
    internal val error = MutableLiveData<Boolean>()

    protected fun beforeRequest() {
        loading.value = true
        error.value = false
    }

    protected fun afterRequest(success: Boolean = true, notify: () -> Unit) {
        loading.value = false
        if (success) {
            error.value = false
            notify.invoke()
        } else {
            error.value = true
        }
    }

    abstract fun loadData()
}