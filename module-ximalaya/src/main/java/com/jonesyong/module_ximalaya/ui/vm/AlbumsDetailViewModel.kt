package com.jonesyong.module_ximalaya.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jonesyong.library_base.ktx.safeLaunch
import com.jonesyong.library_base.model.BaseViewModel
import com.jonesyong.module_ximalaya.api.data.AlbumsDetailData
import com.jonesyong.module_ximalaya.api.request.AlbumsDetailRequest
import kotlinx.coroutines.launch

class AlbumsDetailViewModel : BaseViewModel() {

    private val albumsDetailRequest = AlbumsDetailRequest(this)

    val albumsDetail = MutableLiveData<AlbumsDetailData?>()

    fun fetchAlbumsBrowse(albumId: Int?) {
        viewModelScope.launch {
            beforeRequest()
            val data = safeLaunch {
                albumsDetailRequest.fetchAlbumsBrowse(albumId)
            }
            afterRequest(data != null) {
                albumsDetail.value = data
            }
        }
    }
}