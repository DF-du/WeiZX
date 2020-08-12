package com.dlf.weizx.presenter;

import com.dlf.weizx.base.BasePresenter;
import com.dlf.weizx.bean.NewsDetailBean;
import com.dlf.weizx.model.NewsDetailModel;
import com.dlf.weizx.net.ResultCallBack;
import com.dlf.weizx.view.NewsDetailView;

import java.util.Calendar;

public class NewsDetailPresenter extends BasePresenter<NewsDetailView> {

    private NewsDetailModel mNewsDetailModel;

    @Override
    protected void initModel() {
        mNewsDetailModel = new NewsDetailModel();
        addModel(mNewsDetailModel);
    }

    public void getNewsDetail(int newsId) {
        mNewsDetailModel.getNewsDetail(newsId, new ResultCallBack<NewsDetailBean>() {
            @Override
            public void onSuccess(NewsDetailBean newsDetailBean) {
                mView.setData(newsDetailBean);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
