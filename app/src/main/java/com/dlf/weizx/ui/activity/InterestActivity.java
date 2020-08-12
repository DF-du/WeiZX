package com.dlf.weizx.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.dlf.weizx.R;
import com.dlf.weizx.base.BaseApp;
import com.dlf.weizx.base.Constants;
import com.dlf.weizx.bean.DataBean;
import com.dlf.weizx.ui.adapter.RlvInterestAdapter;
import com.dlf.weizx.widget.SimpleCallBack;

import java.util.ArrayList;

public class InterestActivity extends AppCompatActivity {

    ArrayList<DataBean> mTitles;
    private Toolbar mToolBar;
    private RecyclerView mRlv;
    private RlvInterestAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);
        mTitles = (ArrayList<DataBean>) getIntent().getSerializableExtra(Constants.DATA);
        initView();

    }

    private void initView() {
        mToolBar = (Toolbar) findViewById(R.id.toolBar);
        mRlv = (RecyclerView) findViewById(R.id.rlv);
        mRlv.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new RlvInterestAdapter(this, mTitles);
        mRlv.setAdapter(mAdapter);
        mRlv.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        SimpleCallBack simpleCallBack = new SimpleCallBack(mAdapter);
        //禁掉侧滑
        simpleCallBack.setSwipe(false);
        ItemTouchHelper helper = new ItemTouchHelper(simpleCallBack);
        helper.attachToRecyclerView(mRlv);
    }

    @Override
    public void onBackPressed() {

        //将数据存储到数据库中
        BaseApp.getInstance()
                .getDaoSession()
                .getDataBeanDao()
                .insertOrReplaceInTx(mTitles);


        //数据回传到It资讯页面
        Intent intent = new Intent();
        intent.putExtra(Constants.DATA,mTitles);
        //intent.putExtra(Constants.DATA,mAdapter.mList);
        setResult(Activity.RESULT_OK,intent);
        super.onBackPressed();
    }
}
