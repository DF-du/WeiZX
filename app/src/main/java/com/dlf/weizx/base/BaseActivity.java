package com.dlf.weizx.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView{
    public P mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //每一个activity都需要设置布局，但是基类不能直接决定子类使用什么布局，所以要交给子类决定
        setContentView(getLayout());
        //ButterKnife 必须在setContentView下绑定
        ButterKnife.bind(this);
        mPresenter = initPresenter();
        if (mPresenter != null) {
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
        if (mPresenter != null) {
            mPresenter.destroy();
            mPresenter = null;
        }
    }

    @Override
    public void showToast(String toa) {
        Toast.makeText(this, toa, Toast.LENGTH_SHORT).show();
    }
}
