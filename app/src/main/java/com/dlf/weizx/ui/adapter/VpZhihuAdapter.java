package com.dlf.weizx.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.dlf.weizx.base.BaseLazyFragment;

import java.util.ArrayList;

public class VpZhihuAdapter extends FragmentPagerAdapter {
    private final ArrayList<BaseLazyFragment> mFragments;
    private final ArrayList<String> mTitles;

    public VpZhihuAdapter(FragmentManager fm, ArrayList<BaseLazyFragment> fragments, ArrayList<String> titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
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
        return mTitles.get(position);
    }
}
