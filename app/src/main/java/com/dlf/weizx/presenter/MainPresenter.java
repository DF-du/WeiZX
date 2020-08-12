package com.dlf.weizx.presenter;

import com.dlf.weizx.base.BasePresenter;
import com.dlf.weizx.model.LoginModel;
import com.dlf.weizx.model.MainModel;
import com.dlf.weizx.net.ResultCallBack;
import com.dlf.weizx.view.MainView;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.Map;

public class MainPresenter extends BasePresenter<MainView> {

    private MainModel mMainModel;
    private LoginModel mLoginModel;

    public void getContacts(){
        mMainModel.getConstacts(new ResultCallBack<Map<String, EaseUser>>() {
            @Override
            public void onSuccess(Map<String, EaseUser> map) {
                mView.setContacts(map);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    //初始化M层的地方
    @Override
    protected void initModel() {
        mMainModel = new MainModel();
        mLoginModel = new LoginModel();
        addModel(mMainModel);
        addModel(mLoginModel);
    }

    public void login(){
        mLoginModel.login();
    }
}
