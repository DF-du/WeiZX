package com.dlf.weizx.ui.fragment;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dlf.weizx.R;
import com.dlf.weizx.base.BaseLazyFragment;
import com.dlf.weizx.bean.DailyNewsBean;
import com.dlf.weizx.presenter.DailyNewsPresenter;
import com.dlf.weizx.ui.activity.CalendarActivity;
import com.dlf.weizx.ui.activity.NewsDetailActivity;
import com.dlf.weizx.ui.adapter.RlvDailyNewsAdapter;
import com.dlf.weizx.util.LogUtil;
import com.dlf.weizx.util.TimeUtil;
import com.dlf.weizx.view.DailyNewsView;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.EventListener;

import butterknife.BindView;
import butterknife.OnClick;

public class DailyNewsFragment extends BaseLazyFragment<DailyNewsPresenter> implements DailyNewsView {
    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    private RlvDailyNewsAdapter mAdapter;


    /**
     *
     * @return
     */
    public static DailyNewsFragment getInstance(){
        DailyNewsFragment fragment = new DailyNewsFragment();
        return fragment;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mPresenter.getLastesNews();
    }

    @Override
    protected DailyNewsPresenter initPresenter() {
        return new DailyNewsPresenter();
    }

    @Override
    protected void initView(View view) {
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        mRlv.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<DailyNewsBean.TopStoriesBean> bannerList = new ArrayList<>();
        ArrayList<DailyNewsBean.StoriesBean> newsList = new ArrayList<>();
        mAdapter = new RlvDailyNewsAdapter(getContext(), bannerList, newsList);
        mRlv.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RlvDailyNewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                intent.putExtra("newsId",mAdapter.mNewsList.get(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_daily_news;
    }


    @OnClick(R.id.fab)
    public void click(View v){
        switch (v.getId()) {
            case R.id.fab:
                //启动日历界面会需要日历界面回传一个日期
                //1.广播
                //2.startActivitForResult()
                //3.EventBus
                //4.Handler
                startActivity(new Intent(getContext(), CalendarActivity.class));
                break;
        }
    }

    @Override
    public void setData(DailyNewsBean dailyNewsBean) {
        mAdapter.setData(dailyNewsBean);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe()
    public void receiveDate(CalendarDay date){
        LogUtil.print("date:"+date.toString());

        //选中哪天就是哪天20200807
        String s = TimeUtil.parseTime(date.getCalendar());

/*        if (date是今天的日期){
            mPresenter.getLastesNews();
        }else {
            mPresenter.getOldNews();
        }*/

        //今天的日期,20200809
        Calendar instance = Calendar.getInstance();
        String tody = TimeUtil.parseTime(instance);

        if (tody.equals(s)){
            //选中的日期是今天
            mPresenter.getLastesNews();
        }else {
            //旧新闻给定20200807,后端返回的新闻是20200806的新闻,所以,日期需要+1
            Calendar selectCalendar = date.getCalendar();
            selectCalendar.add(Calendar.DAY_OF_MONTH,1);
            s = TimeUtil.parseTime(selectCalendar);
            mPresenter.getOldNews(s);


        }
    }

}
