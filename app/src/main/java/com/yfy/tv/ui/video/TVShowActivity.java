package com.yfy.tv.ui.video;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.yfy.tv.base.BaseActivity;
import com.yfy.tv.mytencent.R;
import com.yfy.tv.presenter.TVShowPresenter;
import com.yfy.tv.view.TVShowView;

public class TVShowActivity extends BaseActivity<TVShowPresenter> implements TVShowView{


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
        TextView tv = (TextView) findViewById(R.id.tv_test);
    }

    @Override
    protected TVShowPresenter createPresenter() {
        /**
         *attachView()  detachView()如果不写在BaseActivity里面的话   每个activity都要单独去写一下   显得很low  复用性很低
         昨天是为什么不把它放在基类  当初的想法是attachView必须拿到这个Presenter实例才能调用  因为BaseActivity不含有Presenter实例   故写在子类里面
         但是其实是可以写在BaseActivity里面的 ---> 在BaseActivity<T extends BasePresenter> 传入泛型T 并且约束它必须BasePresenter的子类
         然后再抽象createPresenter()  让他在子类中实现
         这样子在BaseActivity就能拿到presenter的实例  就能attachView()  detachView()
         mTVShowPresenter.attachView(this);
         */
        mPresenter = new TVShowPresenter();
        mPresenter.getNetData(this,"success");
        return mPresenter;
    }

    @Override
    public void getTVShowData(String data) {
        Toast.makeText(this,data,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
