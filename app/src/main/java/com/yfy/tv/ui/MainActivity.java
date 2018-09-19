package com.yfy.tv.ui;

import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.view.View;
import android.widget.ImageView;

import com.yfy.tv.base.BaseActivity;
import com.yfy.tv.base.BasePresenter;
import com.yfy.tv.mytencent.R;
import com.yfy.tv.presenter.MainPresenter;
import com.yfy.tv.service.BindService;
import com.yfy.tv.service.StartService;
import com.yfy.tv.view.MainView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView, View.OnClickListener {
    private ImageView mImageLive;
    private ImageView mImageVideo;
    private ImageView mImageComic;
    private ImageView mImageNovel;
    private ServiceConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int contentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mImageLive = findViewById(R.id.iv_tab_live);
        mImageVideo = findViewById(R.id.iv_tab_video);
        mImageComic = findViewById(R.id.iv_tab_comic);
        mImageNovel = findViewById(R.id.iv_tab_novel);
    }

    @Override
    protected void setListener() {
        mImageLive.setOnClickListener(this);
        mImageVideo.setOnClickListener(this);
        mImageComic.setOnClickListener(this);
        mImageNovel.setOnClickListener(this);
//        Thread;      //一个线程是一段可执行的代码 线程是有生命周期的   当线程里面的可执行代码执行完之后
//                     //线程的生命周期随之结束   作为APP的主线程  主线程里面的东西执行完之后是否意味着主线程的生命周期结束  那么APP就会自动退出
//                     //为了不让他自动退出   我们就在主线程插入一段死循环    这就是为什么我们的应用不会自动退出呢   这个时候Looper就出现了
//
//        Looper;      //Looper.prepare()会把当前的线程变成Looper线程(其实就是new 一个Looper)   Looper.loop()里面是一个死循环   会一直等待消息的到来
//                     // private Looper(boolean quitAllowed) {
//                     //    mQueue = new MessageQueue(quitAllowed);
//                     //    mThread = Thread.currentThread();}
//
//        MessageQueue;//为什么会存在MessageQueue   在一个线程里面并不能够再并发的去执行了  所以只能先后执行   那么就要把这个消息保存在一个列表中
//                     //子线程怎么往主线程的MessageQueue里面放消息呢   同一进程中线程和线程资源共享   想办法拿到MessageQueue的实例
//
//        Handler;     //用于同一个进程间的线程通讯   怎么往MessageQueue里面发消息呢    new Handler()就会拿到该线程的Looper  该looper就能拿到MessageQueue
//
//        Message;     //消息载体
//
//        HandlerThread;
    }

    @Override
    protected MainPresenter createPresenter() {
        mPresenter = new MainPresenter();
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_tab_live:

                break;

            case R.id.iv_tab_video:

                break;

            case R.id.iv_tab_comic:

                break;

            case R.id.iv_tab_novel:

                break;
        }
    }
}
