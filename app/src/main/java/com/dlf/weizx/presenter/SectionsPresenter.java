package com.dlf.weizx.presenter;


import com.dlf.weizx.base.BasePresenter;
import com.dlf.weizx.bean.SpecialBean;
import com.dlf.weizx.model.SectionsModel;
import com.dlf.weizx.net.ResultCallBack;
import com.dlf.weizx.view.SectionsView;

public class SectionsPresenter extends BasePresenter<SectionsView> {

    private SectionsModel sectionsModel;

    @Override
    protected void initModel() {
        sectionsModel = new SectionsModel();
        addModel(sectionsModel);
    }

    public void getSection(){
        sectionsModel.getSpecialData(new ResultCallBack<SpecialBean>() {
            @Override
            public void onSuccess(SpecialBean specialBean) {
                mView.setData(specialBean);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
