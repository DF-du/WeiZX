package com.dlf.weizx.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.dlf.weizx.R;
import com.dlf.weizx.base.BaseActivity;
import com.dlf.weizx.bean.SearchBean;
import com.dlf.weizx.presenter.SearchPresenter;
import com.dlf.weizx.ui.adapter.SearchAdapter;
import com.dlf.weizx.view.SearchView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

import butterknife.BindView;

public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchView {

    private MenuItem mSearchViewItem;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.search_view)
    MaterialSearchView mSearchView;
    @BindView(R.id.toolbar_container)
    FrameLayout mToolbarContainer;
    @BindView(R.id.rlv)
    RecyclerView mRlv;
    private ArrayList<SearchBean.DataBean.DatasBean> datasBeans;
    private SearchAdapter searchAdapter;

    @Override
    protected SearchPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mPresenter = new SearchPresenter();
    }

    @Override
    protected void initView() {
        mToolbar.setTitle("Search");
        setSupportActionBar(mToolbar);
        mRlv.setLayoutManager(new LinearLayoutManager(this));
        datasBeans = new ArrayList<>();
        searchAdapter = new SearchAdapter(this, datasBeans);
        mRlv.setAdapter(searchAdapter);

        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //搜索内容提交监听
                //ToastUtil.showToastShort("Submit:"+query);
                //在这里进行网络
                mPresenter.getData(0, "");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //搜索发生改变的监听
                //ToastUtil.showToastShort("changed:"+newText);
                return false;
            }
        });

        mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //搜索框显示
                //ToastUtil.showToastShort("show");
            }

            @Override
            public void onSearchViewClosed() {
                //搜索框隐藏
                //ToastUtil.showToastShort("close");
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mSearchView.isSearchOpen()) {
            //搜索框展开状态
            mSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void setData(SearchBean searchBean) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        mSearchViewItem = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(mSearchViewItem);
        return super.onCreateOptionsMenu(menu);
    }
}
