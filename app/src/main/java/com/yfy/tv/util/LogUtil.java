package com.yfy.tv.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * auto：xkn on 2017/7/12 09:38
 */

public class LogUtil {
    private static boolean debug = false;

    public static void e(String msg) {
        if (debug) {
            if (!TextUtils.isEmpty(msg))
                e("MyLog", msg);
        }
    }

    public static void e(String name, String msg) {
        if (debug) {
            if (!TextUtils.isEmpty(msg))
                Log.e(name, msg);
        }
    }

    public static void i(String msg) {
        if (debug) {
            if (!TextUtils.isEmpty(msg))
                i("MyLog", msg);
        }
    }

    public static void i(String name, String msg) {
        if (debug) {
            if (!TextUtils.isEmpty(msg))
                Log.i(name, msg);
        }
    }

    public static void d(String msg) {
        if (debug) {
            if (!TextUtils.isEmpty(msg))
                d("MyLog", msg);
        }
    }

    public static void w(String name, String msg) {
        if (debug) {
            if (!TextUtils.isEmpty(msg))
                Log.w(name, msg);
        }
    }

    public static void d(String name, String msg) {
        if (debug) {
            if (!TextUtils.isEmpty(msg))
                Log.d(name, msg);
        }
    }
}
