package com.yfy.tv.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yfy.tv.mytencent.R;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView{

    /**
     * 子类有一个抽象方法createPresenter()创建presenter 但是返回值不确定
     * 如果返回值用BasePresenter   那么所有的子类中mPresenter只能拿到BasePresenter实例   这显然是不对的
     * 那么就给返回值以不确定性   那么就是T
     * 但是返回值又有确定性   会是BasePresenter的子类
     * 那么综合   我们就用约束泛型
     */
    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentView());

        initIntent();
        initView();
        setListener();
        mPresenter = createPresenter();
        if (mPresenter != null)
            mPresenter.attachView(this);
    }

    protected abstract int contentView();

    protected void initIntent() {}

    protected abstract void initView();

    protected abstract T createPresenter();

    protected void setListener() {}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showErr() {

    }

    @Override
    public Context getContext() {
        return BaseActivity.this;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
