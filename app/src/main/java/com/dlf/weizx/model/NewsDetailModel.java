package com.dlf.weizx.model;

import com.dlf.weizx.base.BaseModel;
import com.dlf.weizx.bean.NewsDetailBean;
import com.dlf.weizx.net.HttpUtil;
import com.dlf.weizx.net.ResultCallBack;
import com.dlf.weizx.net.ResultSubscriber;
import com.dlf.weizx.net.RxUtils;

public class NewsDetailModel extends BaseModel {
    public void getNewsDetail(int newsId, final ResultCallBack<NewsDetailBean> callBack) {
        addDisposable(
                HttpUtil.getInstance()
                    .getZhihuService()
                    .getNewsDetail(newsId)
                    .compose(RxUtils.<NewsDetailBean>rxSchedulerHelper())
                    .subscribeWith(new ResultSubscriber<NewsDetailBean>() {
                        @Override
                        public void onNext(NewsDetailBean newsDetailBean) {
                            callBack.onSuccess(newsDetailBean);
                        }
                    })
        );
    }
}
