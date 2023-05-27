package com.jonesyong.library_base

import android.util.SparseArray
import androidx.lifecycle.ViewModel

class DataBindingConfig(val layout: Int, val vmVariableId: Int, val stateViewModel: ViewModel) {

    private var _bindingParams = SparseArray<Any?>()

    val bindingParams get() = _bindingParams

    fun addBindingParam(variableId: Int, any: Any): DataBindingConfig {
        if (_bindingParams[variableId] == null) {
            _bindingParams.put(variableId, any)
        }
        return this
    }
}