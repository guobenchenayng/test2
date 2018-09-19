package com.okhttp.callback;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.UnknownHostException;
import com.yfy.tv.model.bean.BaseResponse;
import com.yfy.tv.mytencent.R;
import com.yfy.tv.util.LogUtil;
import com.yfy.tv.util.ToastUtil;
import okhttp3.Call;
import okhttp3.Response;

/**
 * auto：xkn on 2017/3/6 14:00
 * changeAuto:01-00240 on 2017
 */

public class GsonCallback<T> extends Callback<T> {

    private Context mContext;
    private String toast_parse_failed;
    private String toast_http_error;
    private Type mType;
    private Class mClazz;
    private boolean noToast;
    private boolean isDisCode;

    public GsonCallback(Context context) {
        init(context, null, null);
        this.toast_parse_failed = mContext.getString(R.string.xml_parser_failed);
        this.toast_http_error = mContext.getString(R.string.no_net);
    }

    public GsonCallback(Context context, Class clazz) {
        init(context, null, clazz);
        this.toast_parse_failed = mContext.getString(R.string.xml_parser_failed);
        this.toast_http_error = mContext.getString(R.string.no_net);
    }

    public GsonCallback(Context context, Class clazz, boolean isDisCode){
        init(context,clazz,isDisCode);
    }

    public void init(Context context, Class clazz, boolean isDisCode){
        this.isDisCode = isDisCode;
        init(context,null,clazz);
    }

    public void setNoToast(boolean noToast) {
        this.noToast = noToast;
    }

    /**
     * 解析list时需要另外传type，否则解析不出
     *
     * @param context
     * @param type
     */
    public GsonCallback(Context context, Type type) {
        init(context, type, null);
    }

    private void init(Context context, Type type, Class clazz) {
        this.mContext = context;
        this.toast_parse_failed = mContext.getString(R.string.xml_parser_failed);
        this.toast_http_error = mContext.getString(R.string.no_net);
        this.mType = type;
        this.mClazz = clazz;
    }

    @Override
    public T parseNetworkResponse(Response response, int id)  throws Exception {
        T t = null;
        String str = response.body().string();
        LogUtil.e(GsonCallback.class.getSimpleName(), str);
        Gson gson = new Gson();
        BaseResponse base = gson.fromJson(str, BaseResponse.class);
        int code = base.getCode();
        if (code != 0 && code != 50) {
            throw new ParseException(str, base.getMsg(), code);
        } else {
            JsonElement data = base.getData();
            if (data.isJsonArray() && data.getAsJsonArray().size() == 0) {
                return null;
            }
            if (mType != null) {
                t = gson.fromJson(data, mType);
            } else if (mClazz != null) {
                t = (T) gson.fromJson(data, mClazz);
            } else {
                t = (T) str;
            }
        }
        return t;
    }

    public void disCode(int code, int id) {

    }

    @Override
    public void onError(Call call, Exception e, int id) {
        e.printStackTrace();
        if (noToast) {
            return;
        }
        if (e instanceof UnknownHostException) {
            ToastUtil.showShort(mContext, R.string.no_net);
        } else if (e instanceof IOException) {
            ToastUtil.showShort(mContext, R.string.no_net);
        } else if (e instanceof ParseException) {
            if(isDisCode){
                disCode(((ParseException) e).getCode(),id);
            }
            String msg = ((ParseException) e).getMsg();
            System.out.println("=====getMessage====" + e.getMessage());
            System.out.println("======getLocalizedMessage===" + e.getLocalizedMessage());
            if (!TextUtils.isEmpty(msg)) {
                ToastUtil.showShort(mContext, msg);
            }
        } else {
            ToastUtil.showShort(mContext, toast_parse_failed);
        }
    }

    @Override
    public void onResponse(T response, int id) {

    }
}
