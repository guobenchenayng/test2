package com.okhttp.builder;


import android.text.TextUtils;

import com.okhttp.request.PostFormRequest;
import com.okhttp.request.RequestCall;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.kidstone.cartoon.common.QuickAsy;
import okhttp3.MediaType;

/**
 * Created by zhy on 15/12/14.
 */
public class PostFormBuilder extends OkHttpRequestBuilder<PostFormBuilder> implements HasParamsable {
    private List<FileInput> files = new ArrayList<>();
    private MediaType mediaType;
    private boolean isExceptSign = false;
    private Map<String, String> singExceptParams;

    private boolean isRemoveKey = false;
    private String removeString = "";

    @Override
    public RequestCall build() {
        checkSign();
        return new PostFormRequest(url, tag, params, headers, files, mediaType, id).build();
    }

    public PostFormBuilder files(String key, Map<String, File> files) {
        for (String filename : files.keySet()) {
            this.files.add(new FileInput(key, filename, files.get(filename)));
        }
        return this;
    }

    public PostFormBuilder addFile(String name, String filename, File file) {
        files.add(new FileInput(name, filename, file));
        return this;
    }

    @Override
    public PostFormBuilder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    @Override
    public PostFormBuilder setSign(boolean sign, String signkey) {
        this.sign = sign;
        this.signkey = signkey;
        return this;
    }

    /**
     * @param isExceptSign
     * @param signParms
     */
    public PostFormBuilder signExceptParams(boolean isExceptSign, Map<String, String> signParms) {
        this.isExceptSign = isExceptSign;
        this.singExceptParams = signParms;
        if (params != null) {
            params.putAll(signParms);
        }
        return this;
    }

    public static class FileInput {
        public String key;
        public String filename;
        public File file;

        public FileInput(String name, String filename, File file) {
            this.key = name;
            this.filename = filename;
            this.file = file;
        }

        @Override
        public String toString() {
            return "FileInput{" +
                    "key='" + key + '\'' +
                    ", filename='" + filename + '\'' +
                    ", file=" + file +
                    '}';
        }
    }

    @Override
    public PostFormBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    @Override
    public PostFormBuilder addParams(String key, String val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val);
        return this;
    }

    public OkHttpRequestBuilder mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public PostFormBuilder removeKey(boolean isRemove, String removeString) {
        this.isRemoveKey = isRemove;
        this.removeString = removeString;
        return this;
    }

    private void checkSign() {
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
                    for (Map.Entry<String, String> entry : singExceptParams.entrySet()) {
                        addSignParams.remove(entry.getKey());
                    }
                    if (isRemoveKey == true) {
                        HashMap<String, String> hashMaps = new HashMap<>(params);
                        if (!TextUtils.isEmpty(removeString)) {
                            if (hashMaps.containsKey(removeString)) {
                                hashMaps.remove(removeString);
                            }
                        }
                        params.put("sign", QuickAsy.getStringSign(hashMaps, signkey));
                        removeString = "";
                        isRemoveKey = false;
                    } else {
                        params.put("sign", QuickAsy.getStringSign(addSignParams, signkey));
                    }
                } else {
                    if (isRemoveKey == true) {
                        HashMap<String, String> hashMaps = new HashMap<>(params);
                        if (!TextUtils.isEmpty(removeString)) {
                            if (hashMaps.containsKey(removeString)) {
                                hashMaps.remove(removeString);
                            }
                        }
                        params.put("sign", QuickAsy.getStringSign(hashMaps, signkey));
                        removeString = "";
                        isRemoveKey = false;
                    } else {
                        params.put("sign", QuickAsy.getStringSign(params, signkey));
                    }
                }
            }
        }
    }
}
