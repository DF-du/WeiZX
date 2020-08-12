package com.dlf.weizx.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dlf.weizx.R;
import com.dlf.weizx.base.BaseActivity;
import com.dlf.weizx.bean.NewsDetailBean;
import com.dlf.weizx.presenter.NewsDetailPresenter;
import com.dlf.weizx.util.LogUtil;
import com.dlf.weizx.view.NewsDetailView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailActivity extends BaseActivity<NewsDetailPresenter> implements NewsDetailView {


    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.appBar)
    AppBarLayout mAppBar;
    @BindView(R.id.cdl)
    CoordinatorLayout mCdl;
    @BindView(R.id.iv)
    ImageView mIv;
    @BindView(R.id.ctl)
    CollapsingToolbarLayout mCtl;
//    @BindView(R.id.html_text)
//    HtmlTextView mHtmlTextView;
    private int mNewsId;

    @Override
    protected NewsDetailPresenter initPresenter() {
        return new NewsDetailPresenter();
    }

    @Override
    protected void initListener() {
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //verticalOffset:垂直方向的偏移量,0到-600,1 dp = 3px
                LogUtil.print("verticalOffset:" + verticalOffset);
            }
        });
    }

    @Override
    protected void initData() {
        //因为新闻的详情主题,后端给的是html的一部分,所以展示的时候不能使用列表
        //1.webView
        //2.Spanned spanned = Html.fromHtml("html"); 拿Textview设置
        //3.开源WebView,开源Textview

        mPresenter.getNewsDetail(mNewsId);
    }

    @Override
    protected void initView() {
        mNewsId = getIntent().getIntExtra("newsId", 0);
        setSupportActionBar(mToolBar);

        //标题必须在CollapsingToolbarLayout设置
        mCtl.setTitle("折叠ToolBar");
        //扩张时候的title颜色
        mCtl.setExpandedTitleColor(getResources().getColor(R.color.colorAccent));
        //收缩后显示title的颜色
        mCtl.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_news_detail;
    }

    @Override
    public void setData(NewsDetailBean newsDetailBean) {
        //
        mCtl.setTitle(newsDetailBean.getTitle());
        Glide.with(this).load(newsDetailBean.getImage()).into(mIv);
//        mHtmlTextView.setHtml(newsDetailBean.getBody(),
//                new HtmlHttpImageGetter(mHtmlTextView));
    }
}
