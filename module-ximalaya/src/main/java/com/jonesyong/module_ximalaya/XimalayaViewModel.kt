package com.jonesyong.module_ximalaya

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jonesyong.library_base.ktx.safeLaunch
import com.jonesyong.library_base.model.BaseViewModel
import com.jonesyong.module_ximalaya.api.data.Categories
import com.jonesyong.module_ximalaya.api.request.XimalayaRequest
import kotlinx.coroutines.launch

class XimalayaViewModel : BaseViewModel() {

    private val ximalayaRequest = XimalayaRequest(this)

    val tabList = MutableLiveData<MutableList<Categories>?>()

    fun loadCategories() {
        viewModelScope.launch {
            beforeRequest()
            val data = safeLaunch {
                ximalayaRequest.loadCategories()
            }
            afterRequest(data != null) {
                tabList.value = data
            }
        }
    }

}