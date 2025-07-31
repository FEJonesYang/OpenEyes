package com.jonesyong.library_foundation.util.net

import com.didi.drouter.api.DRouter
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object HttpServiceManager {

    private fun providerHttpClient(): OkHttpClient {
        val build = OkHttpClient.Builder()
        val interceptors = DRouter.build(RpcInterceptor::class.java).getAllService()
        interceptors.forEach {
            build.addInterceptor(it)
        }
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


