package com.dlf.weizx.model;

import com.dlf.weizx.base.BaseModel;
import com.dlf.weizx.bean.SpecialBean;
import com.dlf.weizx.net.HttpUtil;
import com.dlf.weizx.net.ResultCallBack;
import com.dlf.weizx.net.ResultSubscriber;
import com.dlf.weizx.net.RxUtils;

public class SectionsModel extends BaseModel {
    public void getSpecialData(final ResultCallBack<SpecialBean> callBack) {
        addDisposable(
                HttpUtil.getInstance()
                        .getZhihuService()
                        .getSpecialData()
                        .compose(RxUtils.<SpecialBean>rxSchedulerHelper())
                        .subscribeWith(new ResultSubscriber<SpecialBean>() {
                            @Override
                            public void onNext(SpecialBean specialBean) {
                                callBack.onSuccess(specialBean);
                            }
                        })
        );
    }
}
