package com.jonesyong.module_community.api.model

import java.io.Serializable

class FollowData(val type: String, val data: Data, val id: Int) : Serializable

class Data(val dataType: String, val header: Header, val content: Content) : Serializable

class Header(
    val id: Int,
    val actionUrl: String,
    val icon: String,
    val iconType: String,
    val time: Int,
    val showHateVideo: Boolean,
    val followType: String,
    val issuerName: String,
    val topShow: Boolean
) : Serializable

class Content(
    val type: String,
    val data: ContentData,
) : Serializable

class ContentData(
    val dataType: String,
    val id: Int,
    val title: String,
    val description: String,
    val library: String,
    val tags: List<Tag>,
    val consumption: Consumption,
    val resourceType: String,
    val provider: Provider,
    val category: String,
    val author: Author,
    val cover: Cover,
    val playUrl: String,
    val duration: Long,
    val webUrl: WebUrl,
    val releaseTime: Int,
    val playInfo: List<Any>,
    val descriptionEditor: String,
) : Serializable

class Cover(
    val feed: String,
    val detail: String,
    val blurred: String,
    val homepage: String
) : Serializable

class Author(
    val id: Int,
    val icon: String,
    val name: String,
    val description: String,
    val latestReleaseTime: Int,
    val videoNum: Int,
) : Serializable

class WebUrl(val raw: String, val forWeibo: String) : Serializable

class Provider(val name: String, val alias: String, val icon: String) : Serializable

class Consumption(
    val collectionCount: Int,
    val shareCount: Int,
    val replyCount: Int,
    val realCollectionCount: Int
) : Serializable

class Tag(
    val id: Int,
    val name: String,
    val actionUrl: String,
    val desc: String,
    val bgPicture: String,
    val headerImage: String,
    val tagRecType: String,
) : Serializable