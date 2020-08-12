package com.dlf.weizx.model;

import com.dlf.weizx.base.BaseModel;
import com.dlf.weizx.bean.DailyNewsBean;
import com.dlf.weizx.net.HttpUtil;
import com.dlf.weizx.net.ResultCallBack;
import com.dlf.weizx.net.ResultSubscriber;
import com.dlf.weizx.net.RxUtils;

public class DailyNewsModel extends BaseModel {
    public void getLatestNews(final ResultCallBack<DailyNewsBean> callBack) {
        addDisposable(
                HttpUtil.getInstance()
                        .getZhihuService()
                        .getLatestNews()
                        .compose(RxUtils.<DailyNewsBean>rxSchedulerHelper())
                        .subscribeWith(new ResultSubscriber<DailyNewsBean>() {
                            @Override
                            public void onNext(DailyNewsBean dailyNewsBean) {
                                callBack.onSuccess(dailyNewsBean);
                            }
                        })
        );
    }

    public void getOldNews(String date, final ResultCallBack<DailyNewsBean> callBack) {
        addDisposable(
                HttpUtil.getInstance()
                        .getZhihuService()
                        .getOldNews(date)
                        .compose(RxUtils.<DailyNewsBean>rxSchedulerHelper())
                        .subscribeWith(new ResultSubscriber<DailyNewsBean>() {
                            @Override
                            public void onNext(DailyNewsBean dailyNewsBean) {
                                callBack.onSuccess(dailyNewsBean);
                            }
                        })
        );
    }
}
