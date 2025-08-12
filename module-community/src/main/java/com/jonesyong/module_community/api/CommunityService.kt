package com.jonesyong.module_community.api

import retrofit2.http.GET


interface CommunityService {

    @GET("/api/v7/community/tab/rec")
    suspend fun fetRecommendInfo()

    @GET("/api/v6/community/tab/follow")
    suspend fun fetFollowInfo()
}