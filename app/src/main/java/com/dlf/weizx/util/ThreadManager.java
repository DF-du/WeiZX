package com.dlf.weizx.util;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadManager {
    private static volatile ThreadManager sThreadManager;
    private final ThreadPoolExecutor mExecutor;

    private ThreadManager(){
        //初始化线程池
        /**
         * int corePoolSize,核心线程的数量,一旦创建不会销毁
         * int maximumPoolSize, 最大线程数量
         * long keepAliveTime, 非核心线程存活时间
         * TimeUnit unit, 非核心线程存活时间的单位
         * BlockingQueue<Runnable> workQueue, 排队策略
         * ThreadFactory threadFactory,线程工厂
         */
        mExecutor = new ThreadPoolExecutor(3, 10, 1, TimeUnit.MINUTES,
                new LinkedBlockingDeque<Runnable>(), Executors.defaultThreadFactory());
    }

    public static ThreadManager getInstance(){
        if (sThreadManager == null){
            synchronized (ThreadManager.class){
                if (sThreadManager == null){
                    sThreadManager = new ThreadManager();
                }
            }
        }

        return sThreadManager;
    }

    //执行任务
    public void execute(Runnable runnable){
        mExecutor.execute(runnable);
    }

    public void remove(Runnable runnable){
        mExecutor.remove(runnable);
    }
}
