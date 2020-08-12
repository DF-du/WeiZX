package com.dlf.weizx.widget;

public interface DragSwipeCallBack {
    //交换两个条目
    void onItemMoved(int startPosition, int endPosition);
    //侧滑删除某个条目
    void onItemRemove(int position);
}
