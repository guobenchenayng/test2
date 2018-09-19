package com.okhttp.builder;


import com.okhttp.request.PostStringRequest;
import com.okhttp.request.RequestCall;

import java.util.Map;

import cn.kidstone.cartoon.common.QuickAsy;
import okhttp3.MediaType;

/**
 * Created by zhy on 15/12/14.
 */
public class PostStringBuilder extends OkHttpRequestBuilder<PostStringBuilder>
{
    private String content;
    private MediaType mediaType;


    public PostStringBuilder content(String content)
    {
        this.content = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType)
    {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public PostStringBuilder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    @Override
    public RequestCall build()
    {
        checkSign();
        return new PostStringRequest(url, tag, params, headers, content, mediaType,id).build();
    }

    @Override
    public PostStringBuilder setSign(boolean sign, String signkey) {
        this.sign = sign;
        this.signkey = signkey;
        return this;
    }

    private void checkSign(){
        if(sign){
            if(params != null && !params.isEmpty()){
                if(!params.containsKey("ui")){
                    params.put("ui_id","0");
                    params.put("ui","default");
                }
                params.put("sign", QuickAsy.getStringSign(params,signkey));
            }
        }
    }
}
