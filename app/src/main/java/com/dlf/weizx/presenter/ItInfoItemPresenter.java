package com.dlf.weizx.presenter;
import com.dlf.weizx.base.BasePresenter;
import com.dlf.weizx.bean.ItInfoItemBean;
import com.dlf.weizx.model.ItInfoItemModel;
import com.dlf.weizx.net.ResultCallBack;
import com.dlf.weizx.view.ItInfoItemView;

public class ItInfoItemPresenter extends BasePresenter<ItInfoItemView> {

    private ItInfoItemModel mItInfoItemModel;

    @Override
    protected void initModel() {
        mItInfoItemModel = new ItInfoItemModel();
        addModel(mItInfoItemModel);
    }

    public void getData(int page, long id) {
        mItInfoItemModel.getData(page,id, new ResultCallBack<ItInfoItemBean>() {
            @Override
            public void onSuccess(ItInfoItemBean itInfoItemBean) {
                mView.setData(itInfoItemBean);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
