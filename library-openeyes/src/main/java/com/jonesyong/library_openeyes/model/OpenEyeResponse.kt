package com.jonesyong.library_openeyes.model

import java.io.Serializable

class OpenEyeResponse<T : Serializable>(
    val itemList: MutableList<T>,
    val count: Int,
    val total: Int,
    val nextPageUrl: String,
    val adExist: Boolean
) : Serializable