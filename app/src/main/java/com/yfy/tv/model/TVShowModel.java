package com.yfy.tv.model;

import com.yfy.tv.base.Callback;

public class TVShowModel {

    /**
     *
     */
    public static void getDataFromNet(final String params, final Callback callback){
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("模仿网络请求数据...");
                switch (params){
                    case "success":
                        callback.onSuccess("成功");
                        break;
                    case "failure":
                        callback.onFailure("失败");
                        break;
                    case "error":
                        callback.onError();
                        break;
                    case "complite":
                        callback.onComplete();
                        break;
                }
            }
        },3000);
    }

}
