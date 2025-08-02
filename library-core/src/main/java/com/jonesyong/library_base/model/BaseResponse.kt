package com.jonesyong.library_base.model

import java.io.Serializable

open class BaseResponse<T> : Serializable {
    var code: Int? = null
    var message: String? = null
    var data: T? = null
}