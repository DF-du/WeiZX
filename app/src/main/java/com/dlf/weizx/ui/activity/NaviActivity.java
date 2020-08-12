package com.dlf.weizx.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dlf.weizx.R;
import com.dlf.weizx.base.BaseActivity;
import com.dlf.weizx.bean.NaviBean;
import com.dlf.weizx.presenter.NaviPresenter;
import com.dlf.weizx.ui.adapter.RlvNaviAdapter;
import com.dlf.weizx.view.NaviView;

import java.util.ArrayList;

import butterknife.BindView;
import qdx.stickyheaderdecoration.NormalDecoration;

public class NaviActivity extends BaseActivity<NaviPresenter> implements NaviView {

    @BindView(R.id.rlv)
    RecyclerView mRlv;
    private RlvNaviAdapter mAdapter;

    @Override
    protected NaviPresenter initPresenter() {
        return new NaviPresenter();
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
        mRlv.setLayoutManager(new LinearLayoutManager(this));
        final ArrayList<NaviBean.DataBean> list = new ArrayList<>();
        mAdapter = new RlvNaviAdapter(this, list);
        mRlv.setAdapter(mAdapter);

        NormalDecoration decoration = new NormalDecoration() {
            @Override
            public String getHeaderName(int i) {
                return list.get(i).getName();
            }
        };

        mRlv.addItemDecoration(decoration);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_navi;
    }

    @Override
    public void setData(NaviBean naviBean) {
        if (naviBean != null&& naviBean.getData() != null &&naviBean.getData().size()>0){
            mAdapter.addData(naviBean.getData());
        }
    }
}
