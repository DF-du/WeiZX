package com.dlf.weizx.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dlf.weizx.util.ToastUtil;
import com.dlf.weizx.widget.LoadingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {

    private Unbinder mUnbinder;
    protected P mPresenter;
    private LoadingDialog mLoadingDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(),null);
        mUnbinder = ButterKnife.bind(this, view);
        mPresenter = initPresenter();
        if (mPresenter != null){
            mPresenter.bindView(this);
        }
        initView(view);
        initData();
        initListener();
        return view;
    }

    protected abstract void initListener();

    protected abstract void initData();

    protected abstract P initPresenter();

    protected abstract void initView(View view);

    protected abstract int getLayout();

    @Override
    public void onDestroy() {
        super.onDestroy();
        //需要解绑
        mUnbinder.unbind();
        if (mPresenter != null){
            mPresenter.destroy();
            mPresenter = null;
        }

        hideLoading();
    }


    @Override
    public void showToast(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showToastShort(msg);
            }
        });

    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(getContext());
        }
        mLoadingDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null){
            mLoadingDialog.dismiss();
        }
    }
}
