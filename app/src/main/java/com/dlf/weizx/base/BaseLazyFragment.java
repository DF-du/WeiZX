package com.dlf.weizx.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dlf.weizx.util.ToastUtil;
import com.dlf.weizx.widget.LoadingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseLazyFragment<P extends BasePresenter> extends Fragment implements BaseView {

    private Unbinder mUnbinder;
    protected P mPresenter;
    private LoadingDialog mLoadingDialog;
    //fragment是否可见
    private boolean mIsVisibleToUser;
    //视图是否创建
    private boolean mIsViewCreated;
    //数据是否加载
    private boolean mDataLoaded;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null){
            mView = inflater.inflate(getLayout(),null);

            mUnbinder = ButterKnife.bind(this, mView);
            mPresenter = initPresenter();
            if (mPresenter != null){
                mPresenter.bindView(this);
            }
            initView(mView);
            mIsViewCreated = true;
            lazyLoad();
            initListener();
        }

        return mView;
    }

    private void lazyLoad() {
        if (mIsViewCreated && mIsVisibleToUser && !mDataLoaded){
            mDataLoaded = true;
            //如果fragmnet每次显示都需要重新请求数据,在子类的initData中将mDataLoaded改成false
            initData();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.mIsVisibleToUser = isVisibleToUser;

        lazyLoad();
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
    public void showToast(String msg) {
        ToastUtil.showToastShort(msg);
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
