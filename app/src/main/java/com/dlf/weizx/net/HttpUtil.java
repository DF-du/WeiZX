package com.dlf.weizx.net;

import com.dlf.weizx.base.Constants;
import com.dlf.weizx.utils.LogUtils;
import com.dlf.weizx.utils.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpUtil {
    private static volatile HttpUtil sHttpUtil;
    private Retrofit.Builder mBuilder;
    public HttpUtil() {
        //1.初始化OK
        OkHttpClient client = initOK();
        //2.初始化Retrofit
        initRetrofit(client);
    }

    private void initRetrofit(OkHttpClient client) {
        mBuilder = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    //1.有缓存
    private OkHttpClient initOK() {
        //设置本地缓存文件
        File file = new File(Constants.PATH_CACHE);
        //设置缓存文件的大小
        //1M = 1024kb = 1024 * 1024 byte
        Cache cache = new Cache(file, 1024 * 1024 * 100);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cache(cache)//缓存
                .addInterceptor(new MyCacheinterceptor())
                //添加了请求头拦截器之后，所有使用网络框架的网络请求都会添加拦截器中的请求头
                //不需要请求头的也加了请求头，是不影响请求
                .addInterceptor(new HeadersInterceptor())
                .addNetworkInterceptor(new MyCacheinterceptor())
                //设置写入的时间
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                //如果是debug状态（码农调试应用)，添加日志拦截器
                //如果是正式上线了isDebug改false，就不打印日志
                .retryOnConnectionFailure(true);
        if (Constants.isDebug) {
            builder.addInterceptor(new LoggingInterceptor());
        }

        return builder.build();
    }

    public static HttpUtil getInstance() {
        if (sHttpUtil == null) {
            synchronized (HttpUtil.class) {
                if (sHttpUtil == null) {
                    sHttpUtil = new HttpUtil();
                }
            }
        }
        return sHttpUtil;
    }

    volatile WanService mWanService;
    public WanService getWanService(){
        if (mWanService == null){
            synchronized (HttpUtil.class){
                if (mWanService == null){
                    mWanService = mBuilder.baseUrl(WanService.sWanUrl)
                            .build()
                            .create(WanService.class);
                }
            }
        }

        return mWanService;
    }


    private class MyCacheinterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
//这里就是说判断我们的网络条件，要是有网络的话我们就直接获取
// 网络上面的数据。要是没有网络的话我们就去缓存里面取数据
            if (!SystemUtils.isNetworkConnected()) {
                request = request
                        .newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response proceed = chain.proceed(request);
            if (SystemUtils.isNetworkConnected()) {
                int maxAge = 0;
                return proceed.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public,max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24;
                return proceed.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public,only-id-cached," + "max-age" + maxStale)
                        .build();
            }
        }
    }

    private class HeadersInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //LogUtils.print("token:"+token);
            Request request = chain.request().newBuilder()
                    .addHeader("Accept-Encoding", "identity")
                    .addHeader("os","android")
                    .build();
            return chain.proceed(request);
        }
    }

    private class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            long time = System.nanoTime();
            Request request = chain.request();
            StringBuilder stringBuilder = new StringBuilder();
            if ("GET".equals(request.method())) {
                HttpUrl httpUrl = request.url().newBuilder().build();
                stringBuilder.append("GET,");
                Set<String> strings = httpUrl.queryParameterNames();
                for (String string : strings) {
                    String values = httpUrl.queryParameter(string);
                    stringBuilder.append(string + ":" + values + ",");
                }
            } else if ("POST".equals(request.method())) {
                stringBuilder.append("POST,");
                if (request.body() instanceof FormBody) {
                    FormBody formBody = (FormBody) request.body();
                    for (int i = 0; i < formBody.size(); i++) {
                        stringBuilder.append(formBody.name(i) + ":" + formBody.value(i) + ",");
                    }
                }
            }
            LogUtils.logD("tag", String.format("Sending request %s %n %s %n%s", request.url(), stringBuilder.toString(), request.headers()));
            Response proceed = chain.proceed(request);
            long now = System.nanoTime();
            LogUtils.logD("Received:",String.format("Received response for %s in %.1fms%n%s",
                    proceed.request().url(), (now - time) / 1e6d, proceed.headers()));

            LogUtils.logD("Data:", proceed.peekBody(4096).string());
            return proceed;
        }
    }
}
