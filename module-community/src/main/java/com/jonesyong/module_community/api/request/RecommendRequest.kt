package com.jonesyong.module_community.api.request

import com.jonesyong.library_base.model.BaseViewModel
import com.jonesyong.library_openeyes.OpenEyeRequest
import com.jonesyong.module_community.api.CommunityService

class RecommendRequest(viewModel: BaseViewModel) : OpenEyeRequest(viewModel) {

    suspend fun fetchRecommendData() = request<CommunityService>().fetRecommendInfo()

    fun fetchData() = request<CommunityService>().getTextData()

    fun fetchData1() = request<CommunityService>().getTextData1()
}