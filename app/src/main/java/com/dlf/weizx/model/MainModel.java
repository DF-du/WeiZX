package com.dlf.weizx.model;

import com.dlf.weizx.base.BaseModel;
import com.dlf.weizx.bean.NavBean;
import com.dlf.weizx.net.HttpUtil;
import com.dlf.weizx.net.ResultCallBack;
import com.dlf.weizx.net.ResultSubscriber;
import com.dlf.weizx.net.RxUtils;

public class MainModel extends BaseModel {
    public void getData(final ResultCallBack<NavBean> callBack) {
        //resourceSubscriber就是subscribeWith里面传递的对象,ResourceSubscriber就是Disposable的子类
        //调用 Disposable.dispose()
        // 1.切断观察者和被观察者的连接,
        // 2.并且如果Rxjava配合Retrofit使用,它还可以取消网络请求
       /* ResourceSubscriber<NaviBean> resourceSubscriber = new Retrofit.Builder()
                .baseUrl(WanService.sWanUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WanService.class)
                .getNavi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResultSubscriber<NaviBean>() {
                    @Override
                    public void onNext(NaviBean naviBean) {
                        callBack.onSuccess(naviBean);
                    }
                });

        addDisposable(resourceSubscriber);
        */
        addDisposable(
                HttpUtil.getInstance()
                        .getWanService()
                        .getNavi()
                        /* .subscribeOn(Schedulers.io())
                         .observeOn(AndroidSchedulers.mainThread())*/
                        .compose(RxUtils.<NavBean>rxSchedulerHelper())//切换线程
                        .subscribeWith(new ResultSubscriber<NavBean>() {
                            @Override
                            public void onNext(NavBean naviBean) {
                                callBack.onSuccess(naviBean);
                            }
                        })
        );
    }
}
