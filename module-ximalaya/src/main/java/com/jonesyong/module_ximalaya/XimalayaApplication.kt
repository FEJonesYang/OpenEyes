package com.jonesyong.module_ximalaya

import android.content.Context
import com.didi.drouter.annotation.Service
import com.jonesyong.library_base.application.IApplicationProvider
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest
import com.ximalaya.ting.android.opensdk.datatrasfer.DeviceInfoProviderDefault

@Service(function = [IApplicationProvider::class])
class XimalayaApplication : IApplicationProvider {

    override fun onCreate(context: Context) {
        initXimalayaSDK(context)
    }

    override fun onTrimMemory(level: Int) {

    }

    private fun initXimalayaSDK(context: Context?) {
        val mXimalaya = CommonRequest.getInstanse()
        mXimalaya.setAppkey(PersonalInfo.appKey)
        mXimalaya.setPackid(PersonalInfo.packName)
        // 优先取oaid作为设备ID，如果获取不到再按照列表顺序优先级进行获取，如果出于用户隐私数据安全考虑，可以对得到的设备ID再进行MD5/SHA1/SHA256哈希，注意不要加盐，并请告知平台技术支持同学。
        mXimalaya.init(
            context, PersonalInfo.appSecret, object : DeviceInfoProviderDefault(context) {
                override fun oaid(): String {
                    // 合作方要尽量优先回传用户真实的oaid，使用oaid可以关联并打通喜马拉雅主app中记录的用户画像数据，对后续个性化推荐接口推荐给用户内容的准确性会有极大的提升！
                    // demo已实现oaid的接入可以参考下
                    return "!!!这里要传入真正的oaid oaid 接入请访问 http://www.msa-alliance.cn/col.jsp?id=120"
                }
            })
        // 支持 Https 进行访问
        CommonRequest.getInstanse().useHttps = true
    }
}