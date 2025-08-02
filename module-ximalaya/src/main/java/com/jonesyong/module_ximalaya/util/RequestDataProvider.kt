package com.jonesyong.module_ximalaya.util

import android.annotation.SuppressLint
import com.jonesyong.library_common.applicationContext
import com.ximalaya.ting.android.opensdk.httputil.util.BASE64Encoder
import com.ximalaya.ting.android.opensdk.httputil.util.CrypterUtil
import java.util.UUID


object RequestDataProvider {

    var oaid: String? = null

    // appKey
    private const val appKey: String = "c94e9dc99d068d3e77255f6e55b6a7e0"

    // appSecret
    private const val appSecret: String = "28ab93b78b580a44fe9b8855d35d694c"

    fun providerCommonParams() = mutableMapOf<String, Any>().apply {
        this["app_key"] = appKey
        this["client_os_type"] = 2
        this["nonce"] = UUID.randomUUID()
        this["timestamp"] = System.currentTimeMillis()
        this["device_id"] = oaid ?: fetchAndroidId()
        this["device_id_type"] = if (oaid.isNullOrEmpty()) "Android_ID" else "OAID"
        this["server_api_version"] = "1.0.0"
    }

    fun providerSigParam(params: MutableMap<String, Any>): String {
        var urlStr = ""
        val iterator = params.toSortedMap().iterator()
        while (iterator.hasNext()) {
            val node = iterator.next()
            val key = node.key
            val value = node.value
            urlStr += "${key}=${value}"
            if (iterator.hasNext()) {
                urlStr += "&"
            }
        }
        return CrypterUtil.hmacSHA1ToStr(BASE64Encoder.encode(urlStr), appSecret)
    }

    @SuppressLint("HardwareIds")
    private fun fetchAndroidId(): String {
        val android = android.provider.Settings.Secure.getString(
            applicationContext.contentResolver,
            android.provider.Settings.Secure.ANDROID_ID
        )
        return android
    }
}