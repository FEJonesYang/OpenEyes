package com.jonesyong.module_ximalaya.api.data

import java.io.Serializable

data class AlbumsDetailData(
    val album_id: Int,
    val total_page: Long,
    val total_count: Long,
    val current_page: Long,
    val album_title: String,
    val album_Longro: String,
    val category_id: Long,
    val cover_url: String,
    val cover_url_small: String,
    val cover_url_middle: String,
    val cover_url_large: String,
    val can_download: Boolean,
    val tracks: MutableList<Tracks>,
    val selling_poLong: String,
    val recommend_reason: String,
    val short_rich_Longro: String,
    val channel_play_count: Long
) : Serializable

data class Tracks(
    val id: Long,
    val kind: String,
    val category_id: Long,
    val track_title: String,
    val track_tags: String,
    val track_Longro: String,
    val cover_url_small: String,
    val cover_url_middle: String,
    val cover_url_large: String,
    val announcer: Announcer,
    val duration: Long,
    val play_count: Long,
    val favorite_count: Long,
    val comment_count: Long,
    val play_url_32: String,
    val play_size_32: Long,
    val play_url_64: String,
    val play_size_64: Long,
    val play_url_64_m4a: String,
    val play_size_64_m4a: Long,
    val play_url_24_m4a: String,
    val play_size_24_m4a: Long,
    val play_url_amr: String,
    val play_size_amr: Long,
    val contain_video: Boolean,
    val position: Long,
    val subordinated_album: Subordinated_album,
    val source: Long,
    val vip_first_status: Long,
    val is_22kbps: Boolean,
    val updated_at: Long,
    val created_at: Long,
    val order_num: Long
) : Serializable

data class Subordinated_album(
    val id: Long,
    val album_title: String,
    val cover_url_small: String,
    val cover_url_middle: String,
    val cover_url_large: String
) : Serializable
