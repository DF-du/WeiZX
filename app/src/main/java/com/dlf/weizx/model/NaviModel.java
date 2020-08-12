package com.dlf.weizx.model;

import com.dlf.weizx.base.BaseModel;
import com.dlf.weizx.bean.NaviBean;
import com.dlf.weizx.net.HttpUtil;
import com.dlf.weizx.net.ResultCallBack;
import com.dlf.weizx.net.ResultSubscriber;
import com.dlf.weizx.net.RxUtils;

public class NaviModel extends BaseModel {
    public void getData(final ResultCallBack<NaviBean> callBack) {
        addDisposable(
                HttpUtil.getInstance()
                .getWanService()
                .getNavi()
                .compose(RxUtils.<NaviBean>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<NaviBean>() {
                    @Override
                    public void onNext(NaviBean naviBean) {
                        callBack.onSuccess(naviBean);
                    }
                })
        );
    }
}
