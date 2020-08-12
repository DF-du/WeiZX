package com.dlf.weizx.model;

import com.dlf.weizx.base.BaseModel;
import com.dlf.weizx.bean.DailyNewsBean;
import com.dlf.weizx.bean.HotBean;
import com.dlf.weizx.net.HttpUtil;
import com.dlf.weizx.net.ResultCallBack;
import com.dlf.weizx.net.ResultSubscriber;
import com.dlf.weizx.net.RxUtils;

public class HotModel extends BaseModel {
    public void getHotData(final ResultCallBack<HotBean> callBack) {
        addDisposable(
                HttpUtil.getInstance()
                        .getZhihuService()
                        .getHotData()
                        .compose(RxUtils.<HotBean>rxSchedulerHelper())
                        .subscribeWith(new ResultSubscriber<HotBean>() {
                            @Override
                            public void onNext(HotBean hotBean) {
                                callBack.onSuccess(hotBean);
                            }
                        })
        );
    }
}
