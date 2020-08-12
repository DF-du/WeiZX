package com.dlf.weizx.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import com.dlf.weizx.widget.LoadingDialog;

import butterknife.ButterKnife;


//Activity 基类
public abstract class BaseActivity<P extends BasePresenter>
        extends AppCompatActivity implements BaseView {

    public P mPresenter;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //1.每一个activity都需要设置布局,但是基类不能直接决定子类使用什么布局,所以交给子类
        setContentView(getLayout());
        //必须在setContentView()
        ButterKnife.bind(this);
        mPresenter = initPresenter();
        if (mPresenter != null) {
            //将V层的对象给到P层
            mPresenter.bindView(this);
        }
        initView();
        initListener();
        initData();
    }

    protected abstract P initPresenter();

    protected abstract void initListener();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract int getLayout();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁Activity的时候,V层和P层需要取消关联
        ////通知M层取消网络请求
        if (mPresenter != null) {
            mPresenter.destroy();
            mPresenter = null;
        }

        hideLoading();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
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
