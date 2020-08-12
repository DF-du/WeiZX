package com.dlf.weizx.ui.adapter;

import android.content.Context;

import com.dlf.weizx.base.BaseRlvAdapter;
import com.dlf.weizx.bean.NaviBean;

import java.util.List;

public class RlvAdapter extends BaseRlvAdapter<NaviBean.DataBean> {
    public RlvAdapter(Context context, List<NaviBean.DataBean> list) {
        super(context, list);
    }

    @Override
    protected void bindData(BaseRlvAdapter.BaseViewHolder holder, NaviBean.DataBean dataBean) {
        //不要在这里找控件
        //为什么 ???
        //1.我们懒
        //2.findViewById找控件是从视图树中一层一层往下找,直到找到或者找不到,效率比较低,但是至少要找一次
        //这个方法是不是只会调用一次???
        //不是,bindData()是在onBindViewHolder(),
        //onBindViewHolder():每显示一个条目,这个方法就调用1次
        //onCreateViewHolder:调用的次数是界面显示条目+1,除非调用刷新
        //ViewHolder的好处在于避免多次查找控件

    }

    @Override
    protected int getLayout() {
        return 0;
    }
}
