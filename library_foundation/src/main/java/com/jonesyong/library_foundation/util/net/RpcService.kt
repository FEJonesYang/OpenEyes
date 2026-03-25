package com.jonesyong.library_foundation.util.net

import com.didi.drouter.api.DRouter
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object HttpServiceManager {

    var cookieJar: CookieJar = object : CookieJar {
        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {}
        override fun loadForRequest(url: HttpUrl) = emptyList<Cookie>()
    }

    private fun providerHttpClient(): OkHttpClient {
        val build = OkHttpClient.Builder()
        val interceptors = DRouter.build(RpcInterceptor::class.java).getAllService()
        interceptors.forEach {
            build.addInterceptor(it)
        }
        build.connectTimeout(10, TimeUnit.SECONDS)
        build.readTimeout(10, TimeUnit.SECONDS)
        build.writeTimeout(10, TimeUnit.SECONDS)
        build.cookieJar(cookieJar)
        return build.build()
    }

    fun <T> rpcService(url: String, clazz: Class<T>): T = Retrofit.Builder()
        .baseUrl(url)
        .client(providerHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(clazz)

    fun <T> rxRpcService(url: String, clazz: Class<T>): T = Retrofit.Builder()
        .baseUrl(url)
        .client(providerHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build().create(clazz)
}


