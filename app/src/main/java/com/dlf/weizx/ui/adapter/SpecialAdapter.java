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
import com.dlf.weizx.bean.SpecialBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpecialAdapter extends RecyclerView.Adapter<SpecialAdapter.ViewHolder> {

    private Context context;
    private ArrayList<SpecialBean.DataBean> list;

    public SpecialAdapter(Context context, ArrayList<SpecialBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_special, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SpecialBean.DataBean dataBean = list.get(position);
        Glide.with(context).load(dataBean.getThumbnail()).into(holder.ivSpecial);
        holder.tvSpecial.setText(dataBean.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_special)
        ImageView ivSpecial;
        @BindView(R.id.tv_special)
        TextView tvSpecial;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public void setData(SpecialBean specialBean) {
        list.clear();
        if (specialBean != null) {
            List<SpecialBean.DataBean> data = specialBean.getData();
            if (data != null && data.size() > 0) {
                list.addAll(data);
            }

            List<SpecialBean.DataBean> dataBeans = specialBean.getData();
            if (dataBeans != null && dataBeans.size() > 0) {
                list.addAll(dataBeans);
            }
        }
        notifyDataSetChanged();
    }
}
