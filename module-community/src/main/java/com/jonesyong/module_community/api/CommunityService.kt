package com.jonesyong.module_community.api

import com.jonesyong.library_openeyes.model.OpenEyeResponse
import com.jonesyong.module_community.api.model.CommunityData
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET


interface CommunityService {

    @GET("/api/v7/community/tab/rec")
    suspend fun fetRecommendInfo(): OpenEyeResponse<CommunityData>

    @GET("/api/v6/community/tab/follow")
    suspend fun fetFollowInfo(): OpenEyeResponse<CommunityData>

    fun getTextData() : Observable<CommunityData>

    fun getTextData1() : Flowable<CommunityData>
}