package com.dlf.weizx.ui.fragment;

import android.view.View;

import com.dlf.weizx.R;
import com.dlf.weizx.base.BaseLazyFragment;
import com.dlf.weizx.presenter.HotPresenter;

public class HotFragment extends BaseLazyFragment<HotPresenter> {
    /**
     *
     * @return
     */
    public static HotFragment getInstance(){
        HotFragment fragment = new HotFragment();
        return fragment;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected HotPresenter initPresenter() {
        return new HotPresenter();
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_hot;
    }
}
