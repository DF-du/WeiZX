package com.dlf.weizx.net;

import com.dlf.weizx.utils.LogUtils;
import com.dlf.weizx.utils.ToastUtil;

import io.reactivex.subscribers.ResourceSubscriber;

public abstract class ResultSubscriber<T> extends ResourceSubscriber<T> {
    @Override
    public void onError(Throwable t) {
        ToastUtil.showToastShort(toString());
        LogUtils.print(t.toString());
    }

    @Override
    public void onComplete() {

    }
}
