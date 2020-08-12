package com.dlf.weizx.net;

import com.dlf.weizx.util.LogUtil;
import com.dlf.weizx.util.ToastUtil;

import io.reactivex.subscribers.ResourceSubscriber;

public abstract class ResultSubscriber<T> extends ResourceSubscriber<T> {

    @Override
    public void onError(Throwable t) {
        ToastUtil.showToastShort(t.toString());
        LogUtil.print(t.toString());
    }

    @Override
    public void onComplete() {

    }
}
