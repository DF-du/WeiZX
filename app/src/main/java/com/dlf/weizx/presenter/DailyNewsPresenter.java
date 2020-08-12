package com.dlf.weizx.presenter;

import com.dlf.weizx.base.BasePresenter;
import com.dlf.weizx.bean.DailyNewsBean;
import com.dlf.weizx.model.DailyNewsModel;
import com.dlf.weizx.net.ResultCallBack;
import com.dlf.weizx.view.DailyNewsView;

public class DailyNewsPresenter extends BasePresenter<DailyNewsView> {

    private DailyNewsModel mModel;

    @Override
    protected void initModel() {
        mModel = new DailyNewsModel();
        addModel(mModel);
    }

    public void getLastesNews() {
       mModel.getLatestNews(new ResultCallBack<DailyNewsBean>() {
           @Override
           public void onSuccess(DailyNewsBean dailyNewsBean) {
               mView.setData(dailyNewsBean);
           }

           @Override
           public void onFail(String msg) {

           }
       });
    }

    public void getOldNews(String s) {
        mModel.getOldNews(s,new ResultCallBack<DailyNewsBean>() {
            @Override
            public void onSuccess(DailyNewsBean dailyNewsBean) {
                mView.setData(dailyNewsBean);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
