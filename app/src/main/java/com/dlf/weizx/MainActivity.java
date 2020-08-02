package com.dlf.weizx;

import com.dlf.weizx.base.BaseActivity;
import com.dlf.weizx.base.BasePresenter;
import com.dlf.weizx.presenter.MainPresenter;
import com.dlf.weizx.view.MainView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView {
    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mPresenter.getData();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hindLoading() {

    }

    @Override
    public void setData() {

    }
}
