package com.dlf.weizx.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.dlf.weizx.R;
import com.dlf.weizx.base.BaseLazyFragment;
import com.dlf.weizx.ui.adapter.VpZhihuAdapter;
import com.dlf.weizx.ui.fragment.DailyNewsFragment;
import com.dlf.weizx.ui.fragment.HotFragment;
import com.dlf.weizx.ui.fragment.SpecialFragment;

import java.util.ArrayList;

public class ZhihuActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mVp;
    private ArrayList<String> mTitles;
    private ArrayList<BaseLazyFragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu);
        initView();
    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mVp = (ViewPager) findViewById(R.id.vp);

        initTitles();
        initFragments();

        VpZhihuAdapter adapter = new VpZhihuAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mVp.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mVp);
    }

    private void initFragments() {
        mFragments = new ArrayList<>();
        mFragments.add(DailyNewsFragment.getInstance());
        mFragments.add(SpecialFragment.getInstance());
        mFragments.add(HotFragment.getInstance());
    }

    private void initTitles() {
        mTitles = new ArrayList<>();
        mTitles.add("日报");
        mTitles.add("专栏");
        mTitles.add("热门");
    }
}
