package com.jonesyong.module_ximalaya

import android.content.Context
import com.bun.miitmdid.core.MdidSdkHelper
import com.didi.drouter.annotation.Service
import com.jonesyong.library_base.application.IApplicationProvider
import com.jonesyong.module_ximalaya.util.RequestDataProvider.oaid


@Service(function = [IApplicationProvider::class])
class XimalayaApplication : IApplicationProvider {

    override fun onCreate(context: Context) {
        initXimalayaSDK(context)
    }

    override fun onTrimMemory(level: Int) {

    }

    private fun initXimalayaSDK(context: Context?) {
        try {
            MdidSdkHelper.InitSdk(context, true) { _, idSupplier ->
                if (idSupplier != null && idSupplier.isSupported) {
                    oaid = idSupplier.oaid
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}