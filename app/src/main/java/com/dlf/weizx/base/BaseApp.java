package com.dlf.weizx.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.dlf.weizx.db.DaoMaster;
import com.dlf.weizx.db.DaoSession;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.util.Iterator;
import java.util.List;


/**
 * 1.基类:
 * BaseActivity(设置布局,initView,initData,初始化P并且关联,界面销毁),
 * BasePresenter:初始化M,持有V层对象,界面销毁的时候可以收到通知通知M层
 * BaseModel:收到P层发送的界面销毁通知,取消网络请求
 * BaseFragment:
 * BaseLazyFragment:
 * BaseAdapter:
 * 2.网络框架: Rxjava+Retrofit+Ok(日志拦截器,缓存拦截器,请求头...)
 * 3.工具类:ToastUtil,打印,dp2px,px2dp,网络状态
 *
 *
 * youmeng:5f27ad1bb4b08b653e90a1cb
 *
 * baidumap ak :ykdxyeMPa2e3HVvkVtEV2cVYYf8FlPwT
 *
 *
 * 月活(月活跃用户) >=1000
 * 日活(日活跃用户) 1000
 */
public class BaseApp extends Application {
    public static BaseApp sBaseApp;
    private DaoMaster.DevOpenHelper mHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        sBaseApp = this;

        initUmeng();
        initEaseMob();
        initBaiduMap();
        setDatabase();
    }

    private void initBaiduMap() {
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    private void initEaseMob() {

        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        //options.setAcceptInvitationAlways(true);
// 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
        options.setAutoTransferMessageAttachments(true);
// 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
        options.setAutoDownloadThumbnail(true);

        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
// 如果APP启用了远程的service，此application:onCreate会被调用2次
// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
// 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null || !processAppName.equalsIgnoreCase(getPackageName())) {
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        EaseUI.getInstance().init(this, options);

//初始化
        //EMClient.getInstance().init(this, options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    private void initUmeng() {
        UMConfigure.setLogEnabled(true);
        //1:上下文
        //2.友盟的 ak
        //3.渠道号
        //4.设备类型
        //5.友盟推送的ak
        UMConfigure.init(this,"5f27ad1bb4b08b653e90a1cb"
                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0

        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        //豆瓣RENREN平台目前只能在服务器端配置
        PlatformConfig.setSinaWeibo("2839837265", "0f3bedc595ae4ef611f979ce4ecc19c9","http://sns.whalecloud.com");
        PlatformConfig.setQQZone("101887793", "d2a9ec70a334ceca7a133f64a4133c36");
        PlatformConfig.setQQFileProvider("com.h1909d.weinfo.fileprovider");
    }

    /**
     * 设置greenDao
     */
    private void setDatabase() {
        //通过DaoMaster内部类DevOpenHelper可以获取一个SQLiteOpenHelper 对象
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        // 此处MyDb表示数据库名称 可以任意填写

        ///"MyDb.db" 数据库名字,高版本手机必须以.db结尾
        mHelper = new DaoMaster.DevOpenHelper(this, "MyDb.db", null);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        //Android 9 默认使用了wal模式,需要关闭wal模式
        db.disableWriteAheadLogging();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public static BaseApp getInstance(){
        return sBaseApp;
    }

    public DaoSession getDaoSession(){
        return mDaoSession;
    }
}
