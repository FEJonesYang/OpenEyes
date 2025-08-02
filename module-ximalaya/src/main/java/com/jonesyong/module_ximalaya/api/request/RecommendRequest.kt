package com.jonesyong.module_ximalaya.api.request

import com.jonesyong.library_base.model.BaseRequest
import com.jonesyong.library_base.model.BaseViewModel
import com.jonesyong.module_ximalaya.api.XimalayaService
import com.jonesyong.module_ximalaya.util.XiMALAYA_BASE_URL

class RecommendRequest(viewModel: BaseViewModel) : BaseRequest<BaseViewModel>(viewModel) {
    override var url: String = XiMALAYA_BASE_URL

    // 请求推荐数据
    suspend fun requestRecommendAlbumList(categoryId: Int) =
        request<XimalayaService>().fetchAlbumsList(categoryId, 1)
}