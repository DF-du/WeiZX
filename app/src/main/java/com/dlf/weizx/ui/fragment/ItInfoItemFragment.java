package com.dlf.weizx.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dlf.weizx.R;
import com.dlf.weizx.base.BaseLazyFragment;
import com.dlf.weizx.base.BaseRlvAdapter;
import com.dlf.weizx.base.Constants;
import com.dlf.weizx.bean.ItInfoItemBean;
import com.dlf.weizx.presenter.ItInfoItemPresenter;
import com.dlf.weizx.util.LogUtil;
import com.dlf.weizx.view.ItInfoItemView;

import java.util.ArrayList;

import butterknife.BindView;

public class ItInfoItemFragment extends BaseLazyFragment<ItInfoItemPresenter> implements ItInfoItemView {

    private String mTitle;
    private long mId;

    private int mPage = 1;
    private BaseRlvAdapter<ItInfoItemBean.DataBean.DatasBean> mAdapter;

    public static ItInfoItemFragment getInstance(String tabTitle, long id){
        ItInfoItemFragment fragment = new ItInfoItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TITLE,tabTitle);
        bundle.putLong(Constants.ID,id);
        fragment.setArguments(bundle);

        return fragment;
    }

    @BindView(R.id.rlv)
    RecyclerView mRlv;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mPresenter.getData(mPage,mId);
    }

    @Override
    protected ItInfoItemPresenter initPresenter() {
        return new ItInfoItemPresenter();
    }

    @Override
    protected void initView(View view) {

        Bundle arguments = getArguments();
        mTitle = arguments.getString(Constants.TITLE);
        mId = arguments.getLong(Constants.ID);
        LogUtil.print("initView title:"+ mTitle);

        ArrayList<ItInfoItemBean.DataBean.DatasBean> list = new ArrayList<>();
        mAdapter = new BaseRlvAdapter<ItInfoItemBean.DataBean.DatasBean>(getContext(),list) {
            @Override
            protected void bindData(BaseViewHolder holder, ItInfoItemBean.DataBean.DatasBean datasBean) {
                holder.setText(R.id.tv_author,datasBean.getAuthor());
                holder.setText(R.id.tv_time,datasBean.getNiceDate());
                holder.setText(R.id.tv_title,datasBean.getTitle());
                holder.setText(R.id.tv_wechat,datasBean.getChapterName());
            }

            @Override
            protected int getLayout() {
                return R.layout.item_it_info;
            }
        };

        mRlv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRlv.setAdapter(mAdapter);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_itinfo_item;
    }

    @Override
    public void setData(ItInfoItemBean itInfoItemBean) {
        mAdapter.addData(itInfoItemBean.getData().getDatas());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.print("ondestroyView:"+mTitle);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.print("onDestroy:"+mTitle);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.print("onDetach:"+mTitle);
    }
}
