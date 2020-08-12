package com.dlf.weizx.base;

import java.util.ArrayList;

public abstract class BasePresenter<V extends BaseView> {
    private ArrayList<BaseModel> mModels;

    public BasePresenter(){
        initModel();
    }

    protected abstract void initModel();

    public V mView;

    //持有V层对象
    public void bindView(V view) {
        mView = view;
    }

    public void destroy() {
        //将持有的V层对象释放,避免内存泄漏
        mView = null;
        //通知M层取消网络请求,需要拿到M层的对象
        if (mModels != null && mModels.size()>0){
            for (int i = 0; i < mModels.size(); i++) {
                mModels.get(i).destroy();
            }
        }
    }

    //子类没new 一个model都需要调用这个方法
    public void addModel(BaseModel model){
        if (mModels == null){
            mModels = new ArrayList<>();
        }

        mModels.add(model);
    }
}
