package com.dlf.weizx.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseModel {

    //这里也拿不到子类进行网络请求的Disposable 对象，所以需要一个容器
    // CompositeDisposable 是RxJava内置容器CompositeDisable进行统一的管理
    CompositeDisposable mCompositeDisposable;

    //如果这个方法被调用了，那么说明界面要销毁了
    //取消网络请求
    public void destroy() {
        if (mCompositeDisposable != null && mCompositeDisposable.size() > 0) {
            mCompositeDisposable.clear();
        }
    }

    //每次进行网络请求需要将生成的Disposable对象调用这个方法添加进来
    public void addDisposable(Disposable disposable){
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }
}
