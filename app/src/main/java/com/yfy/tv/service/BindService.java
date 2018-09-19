package com.yfy.tv.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class BindService extends Service {
    //通过binder实现调用者client与Service之间的通信
    private MyBinder binder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("-------------------BindService:onCreate()");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        System.out.println("-------------------BindService:onStart()");
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("-------------------BindService:onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("-------------------BindService:onBind()");
        return binder;
    }

    @Override
    public void onRebind(Intent intent) {
        System.out.println("-------------------BindService:onRebind()");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("-------------------BindService:onUnbind()");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        System.out.println("-------------------BindService:onDestroy()");
        super.onDestroy();
    }

    private class MyBinder extends Binder{
        public BindService getService() {
            return BindService.this;
        }

    }
}
