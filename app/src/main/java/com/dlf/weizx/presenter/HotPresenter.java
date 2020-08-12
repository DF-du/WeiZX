package com.dlf.weizx.presenter;

import com.dlf.weizx.base.BasePresenter;
import com.dlf.weizx.bean.HotBean;
import com.dlf.weizx.model.HotModel;
import com.dlf.weizx.net.ResultCallBack;
import com.dlf.weizx.view.DailyNewsView;
import com.dlf.weizx.view.HotView;

public class HotPresenter extends BasePresenter<HotView> {

    private HotModel hotModel;

    @Override
    protected void initModel() {
        hotModel = new HotModel();
        addModel(hotModel);
    }

    public void getHotData(){
        hotModel.getHotData(new ResultCallBack<HotBean>() {
            @Override
            public void onSuccess(HotBean hotBean) {
                mView.setData(hotBean);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
