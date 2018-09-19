package com.yfy.tv.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

public class InitSDKIntentService extends IntentService {

    private String TAG = "InitSDKIntentService";

    /**
     * 构造方法 一定要实现此方法否则Service运行出错。
     */
    public InitSDKIntentService() {
        super("InitSDKIntentService");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public InitSDKIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG,"onHandleIntent");
        //将SDK初始化数据放在IntentService中  优化启动页
        //友盟SDK初始化

        //融云SDK初始化

        //数美SDK初始化

    }
}
