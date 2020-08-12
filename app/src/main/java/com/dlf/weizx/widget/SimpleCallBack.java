package com.dlf.weizx.widget;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;


public class SimpleCallBack extends ItemTouchHelper.Callback {
    private DragSwipeCallBack mDragSwipeCallBack;
    private boolean mSwipe = true;
    private boolean mDrag = true;

    public SimpleCallBack(DragSwipeCallBack adapter){
        mDragSwipeCallBack = adapter;
    }

    /**
     * 设置item侧滑和拖拽的方向
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int drag = ItemTouchHelper.UP|ItemTouchHelper.DOWN;
        int swipe = ItemTouchHelper.LEFT;
        //设置拖拽和侧滑的方向
        return makeMovementFlags(drag,swipe);
    }

    /**
     * 这个方法就是子条目拖拽移动的时候系统会同这个方法告诉我们
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */
    @Override
    public boolean onMove(RecyclerView recyclerView,
                          RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        mDragSwipeCallBack.onItemMoved(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return false;
    }

    /**
     * 子条目侧滑的时候会回调方法
     * @param viewHolder
     * @param direction
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mDragSwipeCallBack.onItemRemove(viewHolder.getAdapterPosition());
    }

    /**
     * 是否允许上下拖拽
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return mDrag;
    }

    /**
     * 是否允许左右侧滑
     * @return
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return mSwipe;
    }

    //设置是否允许左右侧滑
    public void setSwipe(boolean swipe){
        mSwipe = swipe;
    }

    public void setDrag(boolean drag){

        mDrag = drag;
    }
}
