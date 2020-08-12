package com.dlf.weizx.model;

import com.dlf.weizx.base.BaseModel;
import com.dlf.weizx.bean.ItInfoItemBean;
import com.dlf.weizx.net.HttpUtil;
import com.dlf.weizx.net.ResultCallBack;
import com.dlf.weizx.net.ResultSubscriber;
import com.dlf.weizx.net.RxUtils;

public class ItInfoItemModel extends BaseModel {
    public void getData(int page, long id, final ResultCallBack<ItInfoItemBean> callBack) {
        addDisposable(
                HttpUtil.getInstance()
                .getWanService()
                .getItInfoList(id,page)
                .compose(RxUtils.<ItInfoItemBean>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<ItInfoItemBean>() {
                    @Override
                    public void onNext(ItInfoItemBean itInfoItemBean) {
                        callBack.onSuccess(itInfoItemBean);
                    }
                })
        );
    }
}
