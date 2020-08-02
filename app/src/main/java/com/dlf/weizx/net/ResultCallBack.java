package com.dlf.weizx.net;

public interface ResultCallBack<T> {
    void onSuccess(T t);
    void onFail(String msg);
}
