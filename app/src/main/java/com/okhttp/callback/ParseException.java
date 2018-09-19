package com.okhttp.callback;

/**
 * auto：xkn on 2017/3/6 14:03
 * changeAuto:01-00240 on 2017
 */

public class ParseException extends Exception {

    private String msg;
    private int code;

    public ParseException(String detailMessage, String msg, int code) {
        super(detailMessage);
        this.msg=msg;
        this.code=code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String response) {
        this.msg = response;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
