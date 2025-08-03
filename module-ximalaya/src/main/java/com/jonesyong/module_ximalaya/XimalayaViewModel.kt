package com.jonesyong.module_ximalaya

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jonesyong.library_base.model.BaseViewModel
import com.jonesyong.module_ximalaya.api.data.Categories
import com.jonesyong.module_ximalaya.api.request.XimalayaRequest
import kotlinx.coroutines.launch

class XimalayaViewModel : BaseViewModel() {

    private val ximalayaRequest = XimalayaRequest(this)

    val tabList = MutableLiveData<MutableList<Categories>>()

    fun loadCategories() {
        viewModelScope.launch {
            loading.value = true
            val data = ximalayaRequest.loadCategories()
            tabList.value = data
            loading.value = false
        }
    }

}