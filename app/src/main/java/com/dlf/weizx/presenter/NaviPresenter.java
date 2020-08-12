package com.dlf.weizx.presenter;

import com.dlf.weizx.base.BasePresenter;
import com.dlf.weizx.bean.NaviBean;
import com.dlf.weizx.model.NaviModel;
import com.dlf.weizx.net.ResultCallBack;
import com.dlf.weizx.view.NaviView;

public class NaviPresenter extends BasePresenter<NaviView> {

    private NaviModel mNaviModel;

    @Override
    protected void initModel() {
        mNaviModel = new NaviModel();
        addModel(mNaviModel);
    }

    public void getData() {
       mNaviModel.getData(new ResultCallBack<NaviBean>() {
           @Override
           public void onSuccess(NaviBean naviBean) {
               mView.setData(naviBean);
           }

           @Override
           public void onFail(String msg) {

           }
       });
    }
}
