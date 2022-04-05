package com.jonesyong.library_base;


import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.DeviceInfoProviderDefault;
import com.ximalaya.ting.android.player.XMediaPlayerConstants;

/**
 * @Author JonesYang
 * @Date 2022-01-13
 * @Description 应用的 Application，组件模式下的 Application 需要继承自此 Application
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initARouter();
        initXimalaya();
    }

    private void initARouter() {
        if (XMediaPlayerConstants.isDebug) {
            // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    private void initXimalaya() {
        CommonRequest mXimalaya = CommonRequest.getInstanse();
        mXimalaya.setAppkey(PersonalInfo.appKey);
        mXimalaya.setPackid(PersonalInfo.packName);
        // 优先取oaid作为设备ID，如果获取不到再按照列表顺序优先级进行获取，如果出于用户隐私数据安全考虑，可以对得到的设备ID再进行MD5/SHA1/SHA256哈希，注意不要加盐，并请告知平台技术支持同学。
        mXimalaya.init(this, PersonalInfo.appSecret, new DeviceInfoProviderDefault(this) {
            @Override
            public String oaid() {
                // 合作方要尽量优先回传用户真实的oaid，使用oaid可以关联并打通喜马拉雅主app中记录的用户画像数据，对后续个性化推荐接口推荐给用户内容的准确性会有极大的提升！
                // demo已实现oaid的接入可以参考下
                return "!!!这里要传入真正的oaid oaid 接入请访问 http://www.msa-alliance.cn/col.jsp?id=120";
            }
        });
        // 支持 Https 进行访问
        CommonRequest.getInstanse().setUseHttps(true);
    }
}
