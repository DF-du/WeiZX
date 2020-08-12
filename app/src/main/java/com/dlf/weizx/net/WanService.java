package com.dlf.weizx.net;

import com.dlf.weizx.bean.ChapterTabBean;
import com.dlf.weizx.bean.ItInfoItemBean;
import com.dlf.weizx.bean.NaviBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface WanService {
    String sWanUrl = "https://wanandroid.com/";

    //公众号tab
    //导航
    @GET("navi/json")
    Flowable<NaviBean> getNavi();

    //公众号tab
    @GET("wxarticle/chapters/json")
    Flowable<ChapterTabBean> getChapterTab();

    //公众号列表
    //id,公众号tab中的公众号id
    //page,页码,从1开始
    @GET("wxarticle/list/{id}/{page}/json")
    Flowable<ItInfoItemBean> getItInfoList(@Path("id") long id,
                                           @Path("page") int page);
}
