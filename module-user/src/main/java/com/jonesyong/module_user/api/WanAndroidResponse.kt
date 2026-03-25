package com.jonesyong.module_user.api

import com.google.gson.annotations.SerializedName

class WanAndroidResponse<T> {
    @SerializedName("errorCode") val code: Int = -1
    @SerializedName("errorMsg") val message: String = ""
    val data: T? = null
}
