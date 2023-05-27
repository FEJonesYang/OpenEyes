package com.jonesyong.module_ximalaya.data

/**
 * @author: Jony
 * @date: 2022/5/13
 * @description: 接口请求返回的转态
 */
class ResponseStatus constructor() {

    private var responseCode = 0
    private var success = true

    constructor (responseCode: Int, success: Boolean) : this() {
        this.responseCode = responseCode
        this.success = success
    }

    fun getResponseCode(): Int {
        return responseCode
    }

    fun isSuccess(): Boolean {
        return success
    }

}