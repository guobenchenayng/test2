package com.okhttp.builder;

import android.net.Uri;

import com.okhttp.request.GetRequest;
import com.okhttp.request.RequestCall;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import cn.kidstone.cartoon.common.QuickAsy;


/**
 * Created by zhy on 15/12/14.
 */
public class GetBuilder extends OkHttpRequestBuilder<GetBuilder> implements HasParamsable {

    private boolean isExceptSign;
    private Map<String, String> signParams;

    @Override
    public RequestCall build() {
        if (params != null) {
            checkSign();
            url = appendParams(url, params);
        }

        return new GetRequest(url, tag, params, headers, id).build();
    }

    @Override
    public OkHttpRequestBuilder removeKey(boolean isRemove, String removeString) {
        return null;
    }

    /**
     * 因为ui和ui_id是必填参数，所以在没有五参数的情况下也要初始化params集合
     *
     * @return
     */
    public GetBuilder initParams() {
        if (params == null)
            params = new HashMap<String, String>();
        return this;
    }

    protected String appendParams(String url, Map<String, String> params) {
        if (url == null || params == null || params.isEmpty()) {
            return url;
        }
        Uri.Builder builder = Uri.parse(url).buildUpon();
        Set<String> keys = params.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            builder.appendQueryParameter(key, params.get(key));
        }
        return builder.build().toString();
    }


    @Override
    public GetBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    @Override
    public GetBuilder addParams(String key, String val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val);
        return this;
    }

    @Override
    public GetBuilder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    /**
     * @param isExceptSign
     * @param signParams
     */
    public GetBuilder signExceptParams(boolean isExceptSign, Map<String, String> signParams) {
        this.isExceptSign = isExceptSign;
        this.signParams = signParams;
        if (params != null)
            params.putAll(signParams);
        return this;
    }

    @Override
    public GetBuilder setSign(boolean sign, String signkey) {
        this.sign = sign;
        this.signkey = signkey;
        return this;
    }

    //判断params 参数有没有ui,ui_id字段，若没有，则加上
    private void addKsParams() {
        if (params != null && !params.containsKey("ui")) {
            //ui,ui_id肯定是一起添加，判断一个就可以了
            params.put("ui", "default");
            params.put("ui_id", "0");
        }
    }

    private void checkSign() {
        addKsParams();
        if (sign) {
            if (params != null && !params.isEmpty()) {
                if (!params.containsKey("ui")) {
                    params.put("ui_id", "0");
                    params.put("ui", "default");
                }
                if (isExceptSign) {
                    LinkedHashMap<String, String> addSignParams = new LinkedHashMap<>();
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        addSignParams.put(entry.getKey(), entry.getValue());
                    }
                    for (Map.Entry<String, String> entry : signParams.entrySet()) {
                        addSignParams.remove(entry.getKey());
                    }
                    params.put("sign", QuickAsy.getStringSign(addSignParams, signkey));
                } else {
                    params.put("sign", QuickAsy.getStringSign(params, signkey));
                }
            }
        }
    }
}
