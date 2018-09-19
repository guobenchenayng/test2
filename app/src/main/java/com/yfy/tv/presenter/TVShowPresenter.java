package com.yfy.tv.presenter;

import android.content.Context;

import com.okhttp.OkHttpUtils;
import com.okhttp.callback.GsonCallback;
import com.yfy.tv.base.BasePresenter;
import com.yfy.tv.base.Callback;
import com.yfy.tv.model.OkHttpUtil;
import com.yfy.tv.model.TVShowModel;
import com.yfy.tv.model.bean.BaseResponse;
import com.yfy.tv.view.TVShowView;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

public class TVShowPresenter extends BasePresenter<TVShowView> {

    public void getNetData(Context context,String type){
        if (!isViewAttached())
            return;

        getView().showLoading();

        TVShowModel.getDataFromNet(type, new Callback<String>() {
            @Override
            public void onSuccess(String data) {
                if (isViewAttached())
                    getView().getTVShowData(data);
            }

            @Override
            public void onFailure(String msg) {
                if (isViewAttached())
                    getView().getTVShowData(msg);
            }

            @Override
            public void onError() {
                if (isViewAttached())
                    getView().showErr();
            }

            @Override
            public void onComplete() {
                if (isViewAttached())
                    getView().hideLoading();
            }
        });

        //MVP加自己封装的okhttp
        HashMap<String,String> hashMap = new HashMap<>();
        OkHttpUtil.get("", hashMap, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

        //MVP加别人封装的okhttp
        OkHttpUtils.get()
                .url("")
                .addParams("","")
                .build()
                .execute(new GsonCallback<BaseResponse>(context, BaseResponse.class){
                    @Override
                    public void onResponse(BaseResponse response, int id) {
                        super.onResponse(response, id);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                    }
                });

        //RXJava  加  Retrofit

    }

}
