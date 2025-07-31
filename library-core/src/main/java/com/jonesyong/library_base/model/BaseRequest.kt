package com.jonesyong.library_base.model

import com.jonesyong.library_foundation.util.net.HttpServiceManager

abstract class BaseRequest<VM : BaseViewModel>(vm: VM) {
    abstract var url: String
    inline fun <reified T> request() = HttpServiceManager.rpcService(url, T::class.java)
}