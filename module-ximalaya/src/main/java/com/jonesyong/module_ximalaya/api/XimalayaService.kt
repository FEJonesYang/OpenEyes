package com.jonesyong.module_ximalaya.api

import com.jonesyong.module_ximalaya.api.data.AlbumsData
import com.jonesyong.module_ximalaya.api.data.AlbumsDetailData
import com.jonesyong.module_ximalaya.api.data.Categories
import com.jonesyong.module_ximalaya.api.data.TagData
import retrofit2.http.GET
import retrofit2.http.Query

interface XimalayaService {

    @GET("/categories/list")
    suspend fun fetchCategories(): MutableList<Categories>

    @GET("/v2/tags/list")
    suspend fun fetchTagList(
        @Query("category_id") id: Int,
        @Query("type") type: Int
    ): List<TagData>

    @GET("/v2/albums/list")
    suspend fun fetchAlbumsList(
        @Query("category_id") id: Int,
        @Query("calc_dimension") type: Int
    ): AlbumsData

    @GET("/albums/browse")
    suspend fun fetchAlbumsBrowse(@Query("album_id") albumId: Int?): AlbumsDetailData
}



