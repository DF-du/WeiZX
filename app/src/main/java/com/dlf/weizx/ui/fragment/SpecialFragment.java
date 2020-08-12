package com.dlf.weizx.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dlf.weizx.R;
import com.dlf.weizx.base.BaseLazyFragment;
import com.dlf.weizx.bean.SpecialBean;
import com.dlf.weizx.presenter.SectionsPresenter;
import com.dlf.weizx.ui.adapter.SpecialAdapter;
import com.dlf.weizx.view.SectionsView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SpecialFragment extends BaseLazyFragment<SectionsPresenter> implements SectionsView {
    @BindView(R.id.special_rlv)
    RecyclerView specialRlv;
    private ArrayList<SpecialBean.DataBean> dataBeans;
    private SpecialAdapter adapter;

    /**
     * @return
     */
    public static SpecialFragment getInstance() {
        SpecialFragment fragment = new SpecialFragment();
        return fragment;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mPresenter.getSection();
    }

    @Override
    protected SectionsPresenter initPresenter() {
        return new SectionsPresenter();
    }

    @Override
    protected void initView(View view) {
        specialRlv.setLayoutManager(new GridLayoutManager(getActivity(),2));
        dataBeans = new ArrayList<>();
        adapter = new SpecialAdapter(getActivity(), dataBeans);
        specialRlv.setAdapter(adapter);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_special;
    }

    @Override
    public void setData(SpecialBean specialBean) {
        adapter.setData(specialBean);
    }
}
