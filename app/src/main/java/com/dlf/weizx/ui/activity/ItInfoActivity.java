package com.dlf.weizx.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.ArrayMap;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.dlf.weizx.R;
import com.dlf.weizx.base.BaseActivity;
import com.dlf.weizx.base.BaseApp;
import com.dlf.weizx.base.Constants;
import com.dlf.weizx.bean.ChapterTabBean;
import com.dlf.weizx.bean.DataBean;
import com.dlf.weizx.presenter.ItInfoPresenter;
import com.dlf.weizx.ui.adapter.VpItAdapter;
import com.dlf.weizx.ui.fragment.ItInfoItemFragment;
import com.dlf.weizx.view.ItInfoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * created by xts
 * 2020 - 0810
 * 这是个IT资讯页面
 * <p>
 * fix bug by 贺进进,修改页面ui
 * fix time:
 */


public class ItInfoActivity extends BaseActivity<ItInfoPresenter> implements ItInfoView {

    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.iv)
    ImageView mIv;
    @BindView(R.id.vp)
    ViewPager mVp;
    private ArrayList<DataBean> mTitles;
    private ArrayList<Fragment> mFragments;

    @Override
    protected ItInfoPresenter initPresenter() {
        return new ItInfoPresenter();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mPresenter.getTabData();
    }

    @Override
    protected void initView() {
        mFragments = new ArrayList<>();
        mToolBar.setTitle("IT资讯");
        setSupportActionBar(mToolBar);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_it_info;
    }

    @OnClick(R.id.iv)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.iv:
                go2InterestSelected();
                break;
        }
    }

    private void go2InterestSelected() {
        if (mTitles != null && mTitles.size() > 0) {
            Intent intent = new Intent(this, InterestActivity.class);
            intent.putExtra(Constants.DATA, mTitles);
            startActivityForResult(intent, 100);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 100 && resultCode == RESULT_OK) {
            if (data != null) {
                mTitles = (ArrayList<DataBean>) data.getSerializableExtra(Constants.DATA);
                setVp();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //menu.add()
        getMenuInflater().inflate(R.menu.it_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
            case R.id.navi:
                startActivity(new Intent(this, NaviActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTab(ChapterTabBean chapterTabBean) {
        //设置tab,创建fragment
        //网络数据,默认全部感兴趣,需要根据数据库的select决定用户究竟是否真的感兴趣
        mTitles = chapterTabBean.getData();

        //1.需要将网络数据和数据库的数据进行比对,select字段的值要以数据库为准
        List<DataBean> list = BaseApp.getInstance().getDaoSession().getDataBeanDao().loadAll();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                DataBean bean = list.get(i);
                long id = bean.getId();

                for (int j = 0; j < mTitles.size(); j++) {
                    DataBean bean1 = mTitles.get(j);
                    long id1 = bean1.getId();
                    if (id == id1) {
                        //同一个tab,需要将数据库中的select值,设置到mTitles上
                        bean1.setSelect(bean.getSelect());
                        break;
                    }
                }
            }
        }


        setVp();

    }

    private void setVp() {
        //创建fragmnet
        mFragments.clear();
        for (int i = 0; i < mTitles.size(); i++) {
            DataBean bean = mTitles.get(i);
            if (bean.isSelect()) {
                ItInfoItemFragment fragment = ItInfoItemFragment.getInstance(bean.getName(), bean.getId());
                mFragments.add(fragment);
            }
        }
        VpItAdapter adapter = new VpItAdapter(getSupportFragmentManager(), mTitles, mFragments);
        mVp.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mVp);
    }

}
