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
import com.dlf.weizx.bean.HotBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HotAdapter extends RecyclerView.Adapter<HotAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HotBean.RecentBean> list;

    public HotAdapter(Context context, ArrayList<HotBean.RecentBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_daily_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HotBean.RecentBean recentBean = list.get(position);
        Glide.with(context).load(recentBean.getThumbnail()).into(holder.ivDaily);
        holder.tvTitle.setText(recentBean.getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_daily)
        ImageView ivDaily;
        @BindView(R.id.tv_title)
        TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setData(HotBean hotBean) {
        list.clear();
        if (hotBean != null){
            List<HotBean.RecentBean> recent = hotBean.getRecent();
            if (recent != null && recent.size()>0){
                list.addAll(recent);
            }

            List<HotBean.RecentBean> beanRecent = hotBean.getRecent();
            if (beanRecent != null && beanRecent.size()>0){
                list.addAll(beanRecent);
            }
        }

        notifyDataSetChanged();
    }

}
