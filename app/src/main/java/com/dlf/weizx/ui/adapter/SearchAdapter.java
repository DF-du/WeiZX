package com.dlf.weizx.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlf.weizx.R;
import com.dlf.weizx.bean.NaviBean;
import com.dlf.weizx.bean.SearchBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private Context context;
    private ArrayList<SearchBean.DataBean.DatasBean> list;

    public SearchAdapter(Context context, ArrayList<SearchBean.DataBean.DatasBean> list) {
        this.context = context;
        this.list = list;
    }
    /*public SearchAdapter(Context context, ArrayList<SearchBean.DataBean.DatasBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }*/

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wx_article_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchBean.DataBean.DatasBean datasBean = list.get(position);
        holder.tvChapter.setText(datasBean.getSuperChapterName() + "/" + datasBean.getChapterName());
        holder.tvName.setText(datasBean.getAuthor());
        holder.tvTime.setText(datasBean.getNiceDate());
        holder.tvTitle.setText(datasBean.getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_chapter)
        TextView tvChapter;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public void setData(SearchBean searchBean) {
        list.clear();
        if (searchBean != null) {
            List<SearchBean.DataBean.DatasBean> datas = searchBean.getData().getDatas();
            if (datas != null && datas.size() > 0) {
                list.addAll(datas);
            }

            List<SearchBean.DataBean.DatasBean> datasBeans = searchBean.getData().getDatas();
            if (datasBeans != null && datasBeans.size() > 0) {
                list.addAll(datasBeans);
            }
        }
        notifyDataSetChanged();
    }
}
