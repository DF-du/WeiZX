package com.dlf.weizx.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;

import com.dlf.weizx.R;
import com.dlf.weizx.base.BaseRlvAdapter;
import com.dlf.weizx.bean.DataBean;
import com.dlf.weizx.widget.DragSwipeCallBack;

import java.util.Collections;
import java.util.List;


//拿数据控制界面展示
public class RlvInterestAdapter extends BaseRlvAdapter<DataBean> implements DragSwipeCallBack {

    public RlvInterestAdapter(Context context, List<DataBean> list) {
        super(context, list);
    }

    @Override
    protected void bindData(BaseViewHolder holder, final DataBean dataBean) {
        holder.setText(R.id.tv,dataBean.getName());
        SwitchCompat sc = (SwitchCompat) holder.findView(R.id.sc);
        sc.setChecked(dataBean.isSelect());

        sc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //用户是否按控件
                if (buttonView.isPressed()){
                    dataBean.setSelect(isChecked);
                }
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.item_interest;
    }

    //触发拖拽移动时调用这个方法
    @Override
    public void onItemMoved(int startPosition ,int endPosition){
        //交换集合中两个位置数据
        Collections.swap(mList,startPosition,endPosition);
        //,局部刷新的api
        notifyItemMoved(startPosition,endPosition);

    }

    //触发侧滑删除的时候调用这个方法实现删除一个item
    @Override
    public void onItemRemove(int position){
        mList.remove(position);
        notifyDataSetChanged();
    }
}
