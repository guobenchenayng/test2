package com.okhttp;


import com.okhttp.builder.GetBuilder;
import com.okhttp.builder.HeadBuilder;
import com.okhttp.builder.OtherRequestBuilder;
import com.okhttp.builder.PostFileBuilder;
import com.okhttp.builder.PostFormBuilder;
import com.okhttp.builder.PostStringBuilder;
import com.okhttp.callback.Callback;
import com.okhttp.request.RequestCall;
import com.okhttp.utils.Platform;
import com.yfy.tv.appcontext.AppContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by zhy on 15/8/17.
 */
public class OkHttpUtils {
    public static final long DEFAULT_MILLISECONDS = 10_000L;
    private volatile static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Platform mPlatform;
    //    static Map<String,String> headers;
    private static String appUserAgent;

    public OkHttpUtils(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        } else {
            mOkHttpClient = okHttpClient;
        }

        mPlatform = Platform.get();
    }


    public static OkHttpUtils initClient(OkHttpClient okHttpClient) {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtils(okHttpClient);
                }
            }
        }
        return mInstance;
    }

    public static OkHttpUtils getInstance() {
        return initClient(null);
    }


    public Executor getDelivery() {
        return mPlatform.defaultCallbackExecutor();
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public static GetBuilder get() {
        return new GetBuilder().headers(getHeaders());
    }

    public static GetBuilder get(boolean isHeader) {
        return new GetBuilder().headers(getHeaders(isHeader));
    }

    public static PostStringBuilder postString() {
        return new PostStringBuilder().headers(getHeaders());
    }

    public static PostFileBuilder postFile() {
        return new PostFileBuilder().headers(getHeaders());
    }

    public static PostFormBuilder post() {
        return post(true);
    }

    public static PostFormBuilder post (boolean header) {
        PostFormBuilder builder = new PostFormBuilder();
        if (header) {
            builder.headers(getHeaders());
        }
        return builder;
    }

    public static OtherRequestBuilder put() {
        return new OtherRequestBuilder(METHOD.PUT);
    }

    public static HeadBuilder head() {
        return new HeadBuilder();
    }

    public static OtherRequestBuilder delete() {
        return new OtherRequestBuilder(METHOD.DELETE);
    }

    public static OtherRequestBuilder patch() {
        return new OtherRequestBuilder(METHOD.PATCH);
    }

    public void execute(final RequestCall requestCall, Callback callback) {
        if (callback == null)
            callback = Callback.CALLBACK_DEFAULT;
        final Callback finalCallback = callback;
        final int id = requestCall.getOkHttpRequest().getId();

        requestCall.getCall().enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                finalCallback.getErrorCode(-101,e.toString());
                sendFailResultCallback(call, e, finalCallback, id);
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                try {
                    if (call.isCanceled()) {
                        finalCallback.getErrorCode(-100,"链接取消");
                        sendFailResultCallback(call, new IOException("Canceled!"), finalCallback, id);
                        return;
                    }

                    if (!finalCallback.validateReponse(response, id)) {
                        finalCallback.getErrorCode(response.code(),"请求失败");
                        sendFailResultCallback(call, new IOException("request failed , reponse's code is : " + response.code()), finalCallback, id);
                        return;
                    }

                    Object o = finalCallback.parseNetworkResponse(response, id);
                    sendSuccessResultCallback(o, finalCallback, id);
                } catch (Exception e) {
                    finalCallback.getErrorCode(-102,e.toString());
                    sendFailResultCallback(call, e, finalCallback, id);
                } finally {
                    if (response.body() != null)
                        response.body().close();
                }

            }
        });
    }


    public void sendFailResultCallback(final Call call, final Exception e, final Callback callback, final int id) {
        if (callback == null) return;

        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onError(call, e, id);
                callback.onAfter(id);
            }
        });
    }

    public void sendSuccessResultCallback(final Object object, final Callback callback, final int id) {
        if (callback == null) return;
        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(object, id);
                callback.onAfter(id);
            }
        });
    }

    public void cancelTag(Object tag) {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    public static class METHOD {
        public static final String HEAD = "HEAD";
        public static final String DELETE = "DELETE";
        public static final String PUT = "PUT";
        public static final String PATCH = "PATCH";
    }

    private static Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", getUserAgent());
        headers.put("Charset", "UTF-8");
        return headers;
    }

    private static Map<String, String> getHeaders(boolean isHeader) {
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", getUserAgent());
        headers.put("Charset", "UTF-8");
        return headers;
    }

    public static String getUserAgent() {
        if (appUserAgent == null || !"".equals(appUserAgent)) {
            //童石的UserAgent
            //User-Agent，用户代理。用户在上网访问的时候会作为HTTP的包头的一部分向服务器发送，用于识别用户的当前环境，如浏览器及版本号、操作系统等信息
            AppContext appContext = AppContext.getAppcontextInstance();
            StringBuilder ua = new StringBuilder("kidstone.cn");
//            ua.append('/' + appContext.getCurVersionName());
//            ua.append('/' + "" + appContext.getCurVersionCode());
//            ua.append("/Android");
//            ua.append("/" + android.os.Build.VERSION.RELEASE);
//            ua.append("/" + android.os.Build.MODEL);
//            //ua.append("/"+appContext.getAppId());
//            ua.append("/" + appContext.getDeviceCode());
//            ua.append("/" + appContext.getChannel());
//            ua.append("/shumei_" + appContext.getDevice_sm());
            appUserAgent = ua.toString();
        }
        return appUserAgent;
    }

}

