package com.dlf.weizx.net;

import com.dlf.weizx.bean.NavBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;

public interface WanService {
    String sWanUrl = "https://wanandroid.com/";

    //公众号tab
    //导航
    @GET("navi/json")
    Flowable<NavBean> getNavi();
}
