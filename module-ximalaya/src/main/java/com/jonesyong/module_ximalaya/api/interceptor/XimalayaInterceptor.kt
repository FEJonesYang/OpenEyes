package com.jonesyong.module_ximalaya.api.interceptor

import com.didi.drouter.annotation.Service
import com.jonesyong.library_foundation.util.net.RpcInterceptor
import com.jonesyong.module_ximalaya.util.RequestDataProvider.providerCommonParams
import com.jonesyong.module_ximalaya.util.RequestDataProvider.providerSigParam
import com.jonesyong.module_ximalaya.util.XiMALAYA_BASE_URL
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

@Service(function = [RpcInterceptor::class])
class XimalayaInterceptor : RpcInterceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (intercept(request)) {
            val query = request.url.query
            val params = mutableMapOf<String, Any>()
            query?.split("&")?.forEach { item ->
                val key = item.split("=")[0]
                val value = item.split("=")[1]
                if (key.isNotEmpty() && value.isNotEmpty()) {
                    params[key] = value
                }
            }
            val commonParams = providerCommonParams()
            commonParams.forEach {
                params[it.key] = it.value
            }
            val build = request.url.newBuilder()
            build.addQueryParameter("sig", providerSigParam(params))
            commonParams.forEach {
                build.addQueryParameter(it.key, it.value.toString())
            }
            request = request.newBuilder().url(build.build()).build()
        }
        return chain.proceed(request)
    }

    private fun intercept(request: Request) = request.url.toString().startsWith(XiMALAYA_BASE_URL)
}