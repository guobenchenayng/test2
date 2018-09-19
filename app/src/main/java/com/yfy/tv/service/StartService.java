package com.yfy.tv.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * service是什么?
 * 定义：服务，是Android四大组件之一， 属于 计算型组件
 * 作用：提供 需在后台长期运行的服务如：复杂计算、音乐播放、下载等
 * 特点：无用户界面、在后台运行、生命周期长
 *
 * 为什么需要使用service?
 * service是运行在后台的应用，对于用户来说失去了被关注的焦点。这就跟我们打开了音乐播放之后，便想去看看图片，这时候我们还不想音乐停止，这里就会用到service,因为我要长期在后台运行
 * IPC
 *
 * 他的生命周期是怎样子的?
 * 手动调用startService后  第一次时onCreate()-->onStartCommand()-->onStart()会先后调用   再次启动onStartCommand()-->onStart()
 * 手动调用stopService() 后回调onDestroy()  之后再stopService()不会再回调
 *
 * 他的三种启动方式有什么区别?为什么要设置三种启动方式?三种启动方式的生命周期是一样的吗?
 * startService:onCreate()-->onStartCommand()-->onDestroy()  (onStartCommand()其实是一样的最终都会回调onStart())
 * bindService:onCreate()-->onBind()-->onUnbind-->onDestroy()
 * 两者结合:startService() —> onCreate() —> onStartCommand() —> bindService() —> onBind()
 * unbindService() —> onUnbind() —> stopService() —> onDestroy()
 * startService()适用于在后台做一些操作
 * bindService()IPC
 *
 * 他和IntentService有什么相同和不同?什么情况下使用IntentService 什么时候使用Service?
 * Service是服务 运行在主线程   所以它并不能做耗时的操作  否则将会抛出ANR异常;如果要做耗时操作的话可以在这个service里面开一个子线程
 * IntentService  同样是服务   它继承自Service   但是它里面系统就已经给它开了一个子线程 可以在里面做耗时操作
 *
 *
 */
public class StartService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("-------------------StartService:onCreate()");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        System.out.println("-------------------StartService:onStart()");
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("-------------------StartService:onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("-------------------StartService:onBind");
        return null;
    }

    @Override
    public void onRebind(Intent intent) {
        System.out.println("-------------------StartService:onRebind");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("-------------------StartService:onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        System.out.println("-------------------StartService:onDestroy");
        super.onDestroy();
    }
}
