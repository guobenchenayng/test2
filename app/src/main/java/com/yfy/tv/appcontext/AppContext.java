package com.yfy.tv.appcontext;

import android.app.Application;
import android.content.Intent;

import com.yfy.tv.service.InitSDKIntentService;

public class AppContext extends Application {

    private static AppContext appContext;

    public static AppContext getAppcontextInstance(){
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        init();
    }

    private void init() {
        startSDKIntentService();
        initOkhttp();
    }

    /**
     * 初始化okhttp
     */
    private void initOkhttp() {

    }

    /**
     * 开启intentService
     */
    private void startSDKIntentService() {
        Intent intent = new Intent(this, InitSDKIntentService.class);
        startService(intent);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
