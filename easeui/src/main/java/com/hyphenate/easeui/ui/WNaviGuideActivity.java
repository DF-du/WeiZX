package com.hyphenate.easeui.ui;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.baidu.mapapi.walknavi.WalkNavigateHelper;
import com.baidu.mapapi.walknavi.adapter.IWNaviStatusListener;
import com.baidu.mapapi.walknavi.adapter.IWRouteGuidanceListener;
import com.baidu.mapapi.walknavi.adapter.IWTTSPlayer;
import com.baidu.mapapi.walknavi.model.RouteGuideKind;
import com.baidu.platform.comapi.walknavi.WalkNaviModeSwitchListener;
import com.baidu.platform.comapi.walknavi.widget.ArCameraView;
import com.hyphenate.easeui.R;

//View view = mNaviHelper.onCreate(WNaviGuideActivity.this); 空指针
//解决方案:只能继承ACtivity,不能继承AppCompatActivity
public class WNaviGuideActivity extends Activity {

    private WalkNavigateHelper mNaviHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //新建诱导Activity，创建诱导View，发起导航

        //获取WalkNavigateHelper实例
        mNaviHelper = WalkNavigateHelper.getInstance();
//获取诱导页面地图展示View
        View view = mNaviHelper.onCreate(WNaviGuideActivity.this);
        if (view != null) {
            setContentView(view);
        }

        //获取语音播报文本
        //注意：该接口需要在startWalkNavi方法之前调用。
        mNaviHelper.setTTsPlayer(new IWTTSPlayer() {
            /**
            /**
            /**
             * 诱导文本回调
             * @param s 诱导文本
             * @param b 是否抢先播报
             * @return
             */
            @Override
            public int playTTSText(String s, boolean b) {
                return 0;
            }
        });

        //
        mNaviHelper.startWalkNavi(WNaviGuideActivity.this);

        mNaviHelper.setWalkNaviStatusListener(new IWNaviStatusListener() {
            /**
             * 普通步行导航模式和步行AR导航模式的切换
             * @param mode 导航模式
             * @param walkNaviModeSwitchListener 步行导航模式切换的监听器
             */
            @Override
            public void onWalkNaviModeChange(int mode, WalkNaviModeSwitchListener walkNaviModeSwitchListener) {
                mNaviHelper.switchWalkNaviMode(WNaviGuideActivity.this, mode, walkNaviModeSwitchListener);
            }

            @Override
            public void onNaviExit() {

            }
        });

        mNaviHelper.setRouteGuidanceListener(this, new IWRouteGuidanceListener() {
            //诱导图标更新
            @Override
            public void onRouteGuideIconUpdate(Drawable drawable) {

            }

            //诱导类型枚举
            @Override
            public void onRouteGuideKind(RouteGuideKind routeGuideKind) {

            }

            /**
             * 诱导信息
             * @param charSequence 第一行显示的信息，如“沿当前道路”
             * @param charSequence1  第二行显示的信息，比如“向东出发”，第二行信息也可能为空
             */
            @Override
            public void onRoadGuideTextUpdate(CharSequence charSequence, CharSequence charSequence1) {

            }

            //总的剩余距离
            @Override
            public void onRemainDistanceUpdate(CharSequence charSequence) {

            }

            //总的剩余时间
            @Override
            public void onRemainTimeUpdate(CharSequence charSequence) {

            }

            //GPS状态发生变化，来自诱导引擎的消息
            @Override
            public void onGpsStatusChange(CharSequence charSequence, Drawable drawable) {

            }

            //已经开始偏航
            @Override
            public void onRouteFarAway(CharSequence charSequence, Drawable drawable) {

            }

            //偏航规划中
            @Override
            public void onRoutePlanYawing(CharSequence charSequence, Drawable drawable) {

            }

            //重新算路成功
            @Override
            public void onReRouteComplete() {

            }

            //抵达目的地
            @Override
            public void onArriveDest() {

            }

            //震动
            @Override
            public void onVibrate() {

            }});

    }

    @Override
    protected void onResume() {
        super.onResume();
        mNaviHelper.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mNaviHelper.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNaviHelper.quit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ArCameraView.WALK_AR_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mNaviHelper.startCameraAndSetMapView(WNaviGuideActivity.this);
            } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "需要开启相机使用权限", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
