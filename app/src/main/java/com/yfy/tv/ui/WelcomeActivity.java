package com.yfy.tv.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.KeyEvent;

import com.yfy.tv.base.BasePresenter;
import com.yfy.tv.mytencent.R;
import com.yfy.tv.base.BaseActivity;
import com.yfy.tv.ui.video.TVShowActivity;
import com.yfy.tv.view.WelcomeView;

import java.lang.ref.WeakReference;

public class WelcomeActivity extends BaseActivity implements WelcomeView{

    private Handler mHandler;
    private static int GO_TO_MAINACTIVITY = 1;
    private JumpHandler mJumpHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int contentView() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected BasePresenter createPresenter() {
        mJumpHandler = new JumpHandler(this);
        mJumpHandler.sendEmptyMessageDelayed(GO_TO_MAINACTIVITY,3000);
        return null;
    }

    @Override
    protected void setListener() {
        super.setListener();
    }

    @Override
    public void showWelcomeData() {

    }

    /**
     * 屏蔽物理返回键
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return true;
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null){
            //我们应该在Activity关闭的时候，移除消息队列中的消息。
            mJumpHandler.removeCallbacksAndMessages(null);
        }
    }

    /**
     * 创建一个静态Handler内部类，然后对Handler持有的外部对象使用弱引用，这样在回收时也可以回收Handler持有的对象
     * 静态内部类防止内存泄漏
     * 注意 static 修饰
     * GC在回收时会忽略掉弱引用，即就算有弱引用指向某对象，
     * 但只要该对象没有被强引用指向（实际上多数时候还要求没有软引用，但此处软引用的概念可以忽略），
     * 该对象就会在被GC检查到时回收掉。对于上面的代码，用户在关闭Activity之后，就算后台线程还没结束，
     * 但由于仅有一条来自Handler的弱引用指向Activity，所以GC仍然会在检查的时候把Activity回收掉。这样，内存泄露的问题就不会出现了。
     */
    private static class JumpHandler extends Handler{
        //弱引用防止内存泄漏  activity销毁
        private WeakReference<WelcomeActivity> weakReference;

        public JumpHandler(WelcomeActivity activity){
            weakReference = new WeakReference<WelcomeActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == GO_TO_MAINACTIVITY){
                //使用weakReference.get()获取WelcomeActivity的弱引用
                Intent intent = new Intent(weakReference.get(),ActivityTest.class);
                weakReference.get().startActivity(intent);
                weakReference.get().finish();
            }
        }
    }

    /**
     * 会导致内存泄漏
     mHandler = new Handler(){
    @Override
    public void handleMessage(Message msg) {
    super.handleMessage(msg);
    Intent intent = new Intent(WelcomeActivity.this,TVShowActivity.class);
    startActivity(intent);
    //结束此页面
    finish();
    }
    };
     mHandler.sendEmptyMessageDelayed( GO_TO_MAINACTIVITY,20000);
     */
}
