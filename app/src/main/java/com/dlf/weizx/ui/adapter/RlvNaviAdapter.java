package com.dlf.weizx.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;

import com.dlf.weizx.R;
import com.dlf.weizx.base.BaseRlvAdapter;
import com.dlf.weizx.bean.NaviBean;
import com.dlf.weizx.widget.FlowLayout;

import java.util.List;

public class RlvNaviAdapter extends BaseRlvAdapter<NaviBean.DataBean> {

    public RlvNaviAdapter(Context context, List<NaviBean.DataBean> list) {
        super(context, list);
    }

    @Override
    protected void bindData(BaseViewHolder holder, NaviBean.DataBean dataBean) {
        FlowLayout fl = (FlowLayout) holder.itemView;
        List<NaviBean.DataBean.ArticlesBean> articles = dataBean.getArticles();

        for (int i = 0; i < articles.size(); i++) {
            NaviBean.DataBean.ArticlesBean articlesBean = articles.get(i);
            TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_label, null);

            tv.setText(articlesBean.getTitle());

            if (i % 6 == 0) {
                tv.setTextColor(Color.parseColor("#ff0000"));
            } else if (i % 6 == 1) {
                tv.setTextColor(Color.parseColor("#00ff00"));
            } else if (i % 6 == 2) {
                tv.setTextColor(Color.parseColor("#0000ff"));
            }else if (i%6==3){
                tv.setTextColor(Color.parseColor("#66ccff"));
            }else if (i%6==4) {
                tv.setTextColor(Color.parseColor("#99A618"));
            }else {
                tv.setTextColor(Color.parseColor("#7429CF"));
            }
            //添加textview到流式布局中
            fl.addView(tv);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.item_navi;
    }
}
