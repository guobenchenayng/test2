package com.yfy.tv.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by guoben on 18/7/23.
 * 自己封装的okhttp工具类
 */
public class OkHttpUtil {
    //只传入post(get) URL  参数params 我就要返回T
    public static void get(String url, HashMap<String,String> hashMap,Callback callback){
        try {
            StringBuilder urlString = new StringBuilder();
            urlString.append(url);

            //将URL与参数拼接
            for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                entry.getKey();
                entry.getValue();
                //...
            }

            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request
                    .Builder()
                    .get()
                    .addHeader("","")
                    .url(urlString.toString())
                    .build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
