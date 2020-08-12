package com.dlf.weizx.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.dlf.weizx.R;
import com.dlf.weizx.bean.DailyNewsBean;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RlvDailyNewsAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private final ArrayList<DailyNewsBean.TopStoriesBean> mBannerList;
    public final ArrayList<DailyNewsBean.StoriesBean> mNewsList;
    private static int VIEW_BANNER=0;
    private static int VIEW_TIME=1;
    private static int VIEW_NEWS=2;
    private final LayoutInflater mInflater;
    private String mTime = "今日新闻";
    private OnItemClickListener mListener;

    public RlvDailyNewsAdapter(Context context, ArrayList<DailyNewsBean.TopStoriesBean> bannerList, ArrayList<DailyNewsBean.StoriesBean> newsList) {

        mContext = context;
        mBannerList = bannerList;
        mNewsList = newsList;
        mInflater = LayoutInflater.from(mContext);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_BANNER){
            return new BannerVH(mInflater.inflate(R.layout.item_banner,parent,false));
        }else if (viewType == VIEW_TIME){
            return new TimeVH(mInflater.inflate(R.layout.item_time,parent,false));
        }else {
            return new NewsVH(mInflater.inflate(R.layout.item_news,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        if (viewType == VIEW_BANNER){
            BannerVH bannerVH = (BannerVH) holder;
            bannerVH.mBanner.setImages(mBannerList)
                    .setImageLoader(new ImageLoader() {
                        @Override
                        public void displayImage(Context context, Object path, ImageView imageView) {
                            //Object path 类型是有mBannerList的泛型决定的
                            DailyNewsBean.TopStoriesBean bean = (DailyNewsBean.TopStoriesBean) path;
                            Glide.with(mContext).load(bean.getImage()).into(imageView);
                        }
                    }).start();
        }else if (viewType == VIEW_TIME){
            TimeVH timeVH = (TimeVH) holder;
            timeVH.mTv.setText(mTime);
        }else {

            int newPostion = position-1;
            if (mBannerList.size()>0){
                newPostion -= 1;
            }
            DailyNewsBean.StoriesBean bean = mNewsList.get(newPostion);
            NewsVH newsVH = (NewsVH) holder;
            newsVH.mTv.setText(bean.getTitle());
            if (bean.getImages() != null && bean.getImages().size()>0){
                Glide.with(mContext).load(bean.getImages().get(0)).into(newsVH.mIv);
            }

            final int finalNewPostion = newPostion;
            newsVH.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null){
                        mListener.onItemClick(v, finalNewPostion);
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mBannerList.size()>0){
            if (position == 0){
                return VIEW_BANNER;
            }else if (position == 1){
                return VIEW_TIME;
            }else {
                return VIEW_NEWS;
            }
        }else {
            if (position == 0){
                return VIEW_TIME;
            }else {
                return VIEW_NEWS;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mBannerList.size()>0){
            return 1+1+mNewsList.size();
        }else {
            return 1+mNewsList.size();
        }

    }

    public void setData(DailyNewsBean dailyNewsBean) {
        mBannerList.clear();
        mNewsList.clear();
        if (dailyNewsBean != null){
            mTime = dailyNewsBean.getDate();
            List<DailyNewsBean.TopStoriesBean> top_stories = dailyNewsBean.getTop_stories();
            if (top_stories != null && top_stories.size()>0){
                mBannerList.addAll(top_stories);
            }

            List<DailyNewsBean.StoriesBean> stories = dailyNewsBean.getStories();
            if (stories != null && stories.size()>0){
                mNewsList.addAll(stories);
            }
        }

        notifyDataSetChanged();
    }

    class BannerVH extends RecyclerView.ViewHolder{

        @BindView(R.id.banner)
        Banner mBanner;

        public BannerVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    class TimeVH extends RecyclerView.ViewHolder{
        @BindView(R.id.tv)
        TextView mTv;

        public TimeVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    class NewsVH extends RecyclerView.ViewHolder{
        @BindView(R.id.tv)
        TextView mTv;
        @BindView(R.id.iv)
        ImageView mIv;

        public NewsVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }
}
