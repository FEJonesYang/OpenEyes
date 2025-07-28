package com.jonesyong.library_base;


import android.app.Application;
import android.content.Context;

import com.didi.drouter.api.DRouter;
import com.jonesyong.library_base.application.IApplicationProvider;

import java.util.List;

/**
 * @Author JonesYang
 * @Date 2022-01-13
 * @Description 应用的 Application，组件模式下的 Application 需要继承自此 Application
 */
public class BaseApplication extends Application {

    private final List<IApplicationProvider> mApplicationService =
            DRouter.build(IApplicationProvider.class).getAllService();

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        DRouter.init(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (!mApplicationService.isEmpty()) {
            for (int i = 0; i < mApplicationService.size(); i++) {
                mApplicationService.get(i).onCreate(this);
            }
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (!mApplicationService.isEmpty()) {
            for (int i = 0; i < mApplicationService.size(); i++) {
                mApplicationService.get(i).onTrimMemory(level);
            }
        }
    }
}
