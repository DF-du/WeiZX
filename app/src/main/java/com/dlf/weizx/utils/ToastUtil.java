package com.dlf.weizx.utils;

import android.widget.Toast;

import com.dlf.weizx.base.BaseApp;

public class ToastUtil {
    public static void showToastShort(String msg){
        Toast.makeText(BaseApp.sBaseApp, msg, Toast.LENGTH_SHORT).show();
    }
    public static void showToastLong(String msg){
        Toast.makeText(BaseApp.sBaseApp, msg, Toast.LENGTH_LONG).show();
    }
}
