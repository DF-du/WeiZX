package com.dlf.weizx.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.dlf.weizx.R;
import com.dlf.weizx.base.BaseFragment;
import com.dlf.weizx.presenter.DiscoveryPresenter;
import com.dlf.weizx.ui.activity.ItInfoActivity;
import com.dlf.weizx.ui.activity.LoginActivity;
import com.dlf.weizx.ui.activity.ZhihuActivity;
import com.dlf.weizx.view.DiscoveryView;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DiscoveryFragment extends BaseFragment<DiscoveryPresenter> implements DiscoveryView {
    @BindView(R.id.btn)
    Button mBtn;
    @BindView(R.id.btn_zhihu)
    Button mBtnZhi;
    @BindView(R.id.btn_itinfo)
    Button mBtnIt;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected DiscoveryPresenter initPresenter() {
        return new DiscoveryPresenter();
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_discovery;
    }

    @OnClick({R.id.btn,R.id.btn_zhihu,R.id.btn_itinfo})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn:
                logout();
                break;
            case R.id.btn_zhihu:
                go2ZHihu();
                //startActivity(new Intent(getContext(), NewsDetailActivity.class));
                break;
            case R.id.btn_itinfo:
                go2ItInfo();
                break;
        }
    }

    private void go2ItInfo() {
        startActivity(new Intent(getContext(), ItInfoActivity.class));
    }

    private void go2ZHihu() {
        startActivity(new Intent(getContext(), ZhihuActivity.class));
    }

    private void logout() {
        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                showToast("退出成功");
                go2Login();
            }

            @Override
            public void onProgress(int progress, String status) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onError(int code, String message) {
                // TODO Auto-generated method stub
                showToast("退出失败");
            }
        });
    }

    private void go2Login() {
        getActivity().finish();
        startActivity(new Intent(getContext(), LoginActivity.class));
    }
}
