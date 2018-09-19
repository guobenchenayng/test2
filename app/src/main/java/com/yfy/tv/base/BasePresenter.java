package com.yfy.tv.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * 基类Presenter
 * @param <V>在返回值不明确的情况下又想兼容多个返回类型  并且约束这个类型必须继承BaseView
 */
public class BasePresenter<V extends BaseView> {
    /**
     * 绑定的view
     * 使用WeakReference  弱引用:一般用来防止内存泄漏，要保证内存被虚拟机回收
     *                    只具有弱引用的对象拥有更短暂的生命周期。
     */
    private Reference<V> mvpView;

    /**
     * 绑定view，一般在初始化中调用该方法
     */
    public void attachView(V mvpView) {
        this.mvpView = new WeakReference<V>(mvpView);//mvpView和WeakReference建立关联
    }

    /**
     * 断开view，一般在onDestroy中调用
     */
    public void detachView() {
        if (this.mvpView != null){
            this.mvpView.clear();
            this.mvpView = null;
        }
    }

    /**
     * 是否与View建立连接
     * 每次调用业务请求的时候都要出先调用方法检查是否与View建立连接
     */
    public boolean isViewAttached(){
        return mvpView != null && mvpView.get() != null;
    }

    /**
     * 获取连接的view
     */
    public V getView(){
        return mvpView.get();
    }

}
