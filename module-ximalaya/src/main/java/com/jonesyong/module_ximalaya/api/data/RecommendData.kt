package com.jonesyong.module_ximalaya.api.data

import java.io.Serializable

data class Categories(
    val id: Long,
    val kind: String,
    val category_name: String,
    val cover_url_small: String,
    val cover_url_middle: String,
    val cover_url_large: String,
    val order_num: Long
) : Serializable

data class TagData(val tag_name: String) : Serializable

data class AlbumsData(
    val category_id: Long,
    val total_page: Long,
    val total_count: Long,
    val current_page: Long,
    val tag_name: String,
    val albums: MutableList<Albums>
) : Serializable

data class Albums(
    val id: Int,
    val kind: String,
    val category_id: Long,
    val album_title: String,
    val album_tags: String,
    val album_intro:String,
    val album_Longro: String,
    val cover_url_small: String,
    val cover_url_middle: String,
    val cover_url_large: String,
    val announcer: Announcer,
    val play_count: Long,
    val share_count: Long,
    val favorite_count: Long,
    val subscribe_count: Long,
    val include_track_count: Long,
    val last_uptrack: Last_uptrack,
    val is_finished: Long,
    val can_download: Boolean,
    val updated_at: Long,
    val created_at: Long
) : Serializable

data class Last_uptrack(
    val track_id: Long,
    val track_title: String,
    val duration: Long,
    val created_at: Long,
    val updated_at: Long
) : Serializable


data class Announcer(
    val id: Long,
    val kind: String,
    val nickname: String,
    val avatar_url: String,
    val is_verified: Boolean
) : Serializable
