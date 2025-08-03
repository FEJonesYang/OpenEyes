package com.jonesyong.module_ximalaya.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jonesyong.library_base.ktx.safeLaunch
import com.jonesyong.library_base.model.BaseViewModel
import com.jonesyong.module_ximalaya.api.data.Albums
import com.jonesyong.module_ximalaya.api.request.RecommendRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * @author: Jony
 * @date: 2022/5/11
 * @description:
 */
class RecommendViewModel : BaseViewModel() {

    // 推荐数据的集合
    var recommendLiveData: MutableLiveData<MutableList<Albums>> = MutableLiveData()

    private val recommendRequest = RecommendRequest(this)

    // 请求推荐数据
    fun requestRecommendAlbumList(categoryId: Int) {
        viewModelScope.launch {
            beforeRequest()
            val data = safeLaunch {
                recommendRequest.requestRecommendAlbumList(categoryId)
            }
            afterRequest(data != null) {
                recommendLiveData.value = data?.albums
            }
        }
    }
}

