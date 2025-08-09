package com.jonesyong.module_ximalaya.api.request

import com.jonesyong.module_ximalaya.api.XimalayaService
import com.jonesyong.module_ximalaya.ui.vm.AlbumsDetailViewModel

class AlbumsDetailRequest(viewModel: AlbumsDetailViewModel) : XimalayaCommonRequest(viewModel) {

    suspend fun fetchAlbumsBrowse(albumId: Int?) =
        request<XimalayaService>().fetchAlbumsBrowse(albumId)
}