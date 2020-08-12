package com.dlf.weizx.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.dlf.weizx.bean.DataBean;

import java.util.ArrayList;


//FragmentPagerAdapter
//FragmentStatePagerAdapter

/**
 * fragment生命周期不同
 *
 * FragmentStatePagerAdapter:只缓存fragment的状态,销毁的时候:onPause-->onStop()-->onDestroyView-->onDestroy-->onDetach
 * FragmentPagerAdapter:会缓存fragmnet,销毁的时候:onPause-->onStop()-->onDestroyView
 */
public class VpItAdapter extends FragmentStatePagerAdapter {
    private final ArrayList<DataBean> mTitles;
    private final ArrayList<Fragment> mFragments;
    private final ArrayList<String> mStringTitles;

    public VpItAdapter(FragmentManager fm, ArrayList<DataBean> titles, ArrayList<Fragment> fragments) {
        super(fm);
        //无论是否勾选,它都是14个
        mTitles = titles;
        mFragments = fragments;

        mStringTitles = new ArrayList<>();
        for (int i = 0; i < mTitles.size(); i++) {
            DataBean bean = mTitles.get(i);
            if (bean.isSelect()){
                //只有select字段是true,对应的fragmnet和title才会显示
                mStringTitles.add(bean.getName());
            }
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mStringTitles.get(position);
    }
}
