package com.yfy.tv.model.bean;

import com.google.gson.JsonElement;

/**
 * auto：xkn on 2017/3/20 10:57
 * changeAuto:01-00240 on 2017
 */

public class BaseResponse {
    private int code; //
    private String msg; //
    private JsonElement data;
    private int id;
    private int userid;
    private String cdn;

    public String getCdn() {
        return cdn;
    }

    public void setCdn(String cdn) {
        this.cdn = cdn;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
