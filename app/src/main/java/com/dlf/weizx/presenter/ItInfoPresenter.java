package com.dlf.weizx.presenter;

import com.dlf.weizx.base.BasePresenter;
import com.dlf.weizx.base.Constants;
import com.dlf.weizx.bean.ChapterTabBean;
import com.dlf.weizx.model.ItInfoModel;
import com.dlf.weizx.net.ResultCallBack;
import com.dlf.weizx.view.ItInfoView;

public class ItInfoPresenter extends BasePresenter<ItInfoView> {

    private ItInfoModel mItInfoModel;

    @Override
    protected void initModel() {
        mItInfoModel = new ItInfoModel();
        addModel(mItInfoModel);
    }

    public void getTabData() {
        mItInfoModel.getTab(new ResultCallBack<ChapterTabBean>() {
            @Override
            public void onSuccess(ChapterTabBean chapterTabBean) {
                if (chapterTabBean.getErrorCode() == Constants.SUCCESS_CODE) {
                    mView.setTab(chapterTabBean);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
