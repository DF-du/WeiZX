package com.dlf.weizx.base;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

//T :指的是子条目的数据
//开源框架:
//    implementation 'com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.42'
public abstract class BaseRlvAdapter<T> extends RecyclerView.Adapter<BaseRlvAdapter.BaseViewHolder> {

    public final Context mContext;
    public final List<T> mList;
    private OnItemClickListener mOnItemClickListener;

    public BaseRlvAdapter(Context context, List<T> list){
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(getLayout(), parent, false);
        return new BaseViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, final int position) {
        T t = mList.get(position);
        //具体怎么设置数据,由子类决定,因为父类即不知道子类中有哪些控件,也不知道子条目数据的字段
        bindData(holder,t);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(v,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    //添加更多
    public void addData(List<T> data){
        if (data != null && data.size()>0){
            mList.addAll(data);
            notifyDataSetChanged();
        }
    }

    //刷新
    public void refreshData(List<T> data){
        if (data != null && data.size()>0){
            mList.clear();
            mList.addAll(data);
            notifyDataSetChanged();
        }
    }

    protected abstract void bindData(BaseViewHolder holder, T t);

    protected abstract int getLayout();

    public static class BaseViewHolder extends RecyclerView.ViewHolder{

        //因为hashmap是java的存储键值对的容器,他比较消耗内存
        //HashMap<Integer,View> mMap= new HashMap<>();
        //Android提供了更轻量级的数据结构
        //SparseArray
        //ArrayMap
        SparseArray<View> mMap = new SparseArray();

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        //查找控件
        public View findView(@IdRes int id){
            //封装一下,同一个Viewhoder对象同一个id的控件只能查找一次
            //控件一旦找到之后,需要拿容器存起来,下次先从容器中拿,拿不到再findViewById
            View view = mMap.get(id);
            if (view == null){
                //说明没有找过
                view  = itemView.findViewById(id);
                mMap.put(id,view);
            }
            return view;
        }

        //给textView设置文本
        public void setText(@IdRes int id,String msg){
            View view = (TextView) findView(id);
            // 对象 instanceof 类型:判断对象实付是这个类型的对象
            if (view instanceof TextView){
                TextView view1 = (TextView) view;
                view1.setText(msg);
            }
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }
}
