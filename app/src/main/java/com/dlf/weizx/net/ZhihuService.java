package com.dlf.weizx.net;

import com.dlf.weizx.bean.DailyNewsBean;
import com.dlf.weizx.bean.HotBean;
import com.dlf.weizx.bean.NewsDetailBean;
import com.dlf.weizx.bean.SearchBean;
import com.dlf.weizx.bean.SpecialBean;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ZhihuService {
    String sUrl = "http://news-at.zhihu.com/api/4/";

    /**
     * 2)最新日报
     * news/latest
     * 3)往期日报
     * news/before/{date}   日期格式(20181206)
     */

    @GET("news/latest")
    Flowable<DailyNewsBean> getLatestNews();


    @GET("news/before/{date}")
    Flowable<DailyNewsBean> getOldNews(@Path("date") String date);

    @GET("news/{news_id}")
    Flowable<NewsDetailBean> getNewsDetail(@Path("news_id") int newsId);
    /**
     * 获取专栏数据
     *
     * @return
     */
    @GET("sections")
    Flowable<SpecialBean> getSpecialData();

    /**
     * 获取热门数据
     *
     * @return
     */
    @GET("news/hot")
    Flowable<HotBean> getHotData();

    /**
     * 搜索
     *
     * @param page    页码,从1开始
     * @param keyword 关键字
     * @return
     */
    @POST("article/query/{page}/json")
    @FormUrlEncoded
    Flowable<SearchBean> getSearchData(@Path("page") int page,
                                       @Field("k") String keyword);
}
