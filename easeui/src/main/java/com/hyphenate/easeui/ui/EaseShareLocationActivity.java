package com.hyphenate.easeui.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.walknavi.WalkNavigateHelper;
import com.baidu.mapapi.walknavi.adapter.IWEngineInitListener;
import com.baidu.mapapi.walknavi.adapter.IWRoutePlanListener;
import com.baidu.mapapi.walknavi.model.WalkRoutePlanError;
import com.baidu.mapapi.walknavi.params.WalkNaviLaunchParam;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.utils.NumberUtil;

import java.util.List;

public class EaseShareLocationActivity extends EaseBaseActivity
        implements View.OnClickListener, EMMessageListener {

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private WalkNaviLaunchParam mParam;
    private LocationClient mLocationClient;
    private LatLng mUserLatLng;
    private boolean mIsFirst = true;
    private MapView mBmapView;
    /**
     * 我的位置
     */
    private Button mBtnMe;
    /**
     * 朋友的位置
     */
    private Button mBtnFriend;
    //todo 测试用
    private LatLng mFriendLatLng = new LatLng(40.602119,116.559204);;
    private String mToChatUserName;
    private String TAG = "ShareLocation";

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.ease_acitivity_share_location);
        mToChatUserName = getIntent().getStringExtra("data");
        initView();
    }

    private void initView() {
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        location();
        mBmapView = (MapView) findViewById(R.id.bmapView);
        mBtnMe = (Button) findViewById(R.id.btnMe);
        mBtnMe.setOnClickListener(this);
        mBtnFriend = (Button) findViewById(R.id.btnFriend);
        mBtnFriend.setOnClickListener(this);

        EMClient.getInstance().chatManager().addMessageListener(this);

    }


    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        //记得在不需要的时候移除listener，如在activity的onDestroy()时
        EMClient.getInstance().chatManager().removeMessageListener(this);
    }

    private void location() {
        //开启地图的定位图层
        mBaiduMap.setMyLocationEnabled(true);

        //通过LocationClient发起定位
        //定位初始化
        mLocationClient = new LocationClient(this);

//通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(3000);//设置时间间隔


//设置locationClientOption
        mLocationClient.setLocOption(option);

//注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
//开启地图定位图层
        mLocationClient.start();

        //marker的点击事件
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            //marker被点击时回调的方法
            //若响应点击事件，返回true，否则返回false
            //默认返回false
            @Override
            public boolean onMarkerClick(final Marker marker) {
                //步行导航
                new AlertDialog.Builder(EaseShareLocationActivity.this)
                        .setTitle("是否开启步行导航")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                walkNavi(marker.getPosition());
                            }
                        })
                        .create()
                        .show();


                return true;
            }
        });

    }

    private void walkNavi(final LatLng end) {
        // 获取导航控制类
// 引擎初始化
        WalkNavigateHelper.getInstance().initNaviEngine(this, new IWEngineInitListener() {

            @Override
            public void engineInitSuccess() {
                //引擎初始化成功的回调
                Log.d(TAG, "engineInitSuccess: ");
                routeWalkPlanWithParam(end);
            }

            @Override
            public void engineInitFail() {
                //引擎初始化失败的回调
                Log.d(TAG, "engineInitFail: ");
            }
        });
    }

    //发起算路
    private void routeWalkPlanWithParam(LatLng end) {
//起终点位置
//构造WalkNaviLaunchParam
        mParam = new WalkNaviLaunchParam().stPt(mUserLatLng).endPt(end);

        //发起算路
        WalkNavigateHelper.getInstance().routePlanWithParams(mParam, new IWRoutePlanListener() {
            @Override
            public void onRoutePlanStart() {
                //开始算路的回调
                Log.d(TAG, "onRoutePlanStart: ");
            }

            @Override
            public void onRoutePlanSuccess() {
                Log.d(TAG, "onRoutePlanSuccess: ");
                //算路成功
                //跳转至诱导页面
                Intent intent = new Intent(EaseShareLocationActivity.this,
                        WNaviGuideActivity.class);
                startActivity(intent);
            }

            @Override
            public void onRoutePlanFail(WalkRoutePlanError walkRoutePlanError) {
                //算路失败的回调
                Log.d(TAG, "onRoutePlanFail: "+walkRoutePlanError);
            }
        });
    }

    //将地图视图移动到用户位置
    private void move2Location(LatLng latLng) {
        //定位到海里有3个原因
        //1。定位没开--位置信息
        //2。设备有问题
        //3。sha1值有问题，---鉴权错误信息
        if (latLng != null) {
            MapStatusUpdate status2 = MapStatusUpdateFactory.newLatLng(latLng);
            mBaiduMap.setMapStatus(status2);
        }
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.btnMe){
            move2Location(mUserLatLng);
        }else if (id == R.id.btnFriend){
            move2Location(mFriendLatLng);
        }
    }

    @Override
    public void onMessageReceived(List<EMMessage> list) {

    }

    private void dealAction(String action) {
        String[] split = action.split(",");
        if (split != null && split.length== 3 && split[0].startsWith("share")){
            mFriendLatLng = new LatLng(NumberUtil.parseString2Double(split[1]),
                    NumberUtil.parseString2Double(split[2]));

            Log.d("share", "dealAction: "+split[1]+","+split[2]);
            addMarker(mFriendLatLng);
        }
    }

    private void addMarker(LatLng latLng) {
        mBaiduMap.clear();
        //定义Maker坐标点
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_marka);
        //构建MarkerOption，用于在地图上添加Marker
        MarkerOptions option = new MarkerOptions()
                .position(latLng)
                .animateType(MarkerOptions.MarkerAnimateType.none)//动画类型
                .draggable(false)//是否允许拖拽
                .icon(bitmap);

        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);

    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {
//子线程
        for (int i = 0; i < list.size(); i++) {
            //只处理透传消息
            EMMessage emMessage = list.get(i);
            EMMessageBody body = emMessage.getBody();
            if (body instanceof EMCmdMessageBody){
                EMCmdMessageBody messageBody = (EMCmdMessageBody) body;
                String action = messageBody.action();
                Log.d("share", "onCmdMessageReceived: "+action);
                dealAction(action);
            }
        }
    }

    @Override
    public void onMessageRead(List<EMMessage> list) {

    }

    @Override
    public void onMessageDelivered(List<EMMessage> list) {

    }

    @Override
    public void onMessageRecalled(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }

    //构造地图数据
    //我们通过继承抽象类BDAbstractListener并重写其onReceieveLocation方法来获取定位数据，
    // 并将其传给MapView。
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();

            //定位到用户的经纬度
            mUserLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            Log.d("location", "onReceiveLocation: "+location.getLatitude()+","+location.getLongitude());
            mBaiduMap.setMyLocationData(locData);
            if (mIsFirst) {
                move2Location(mUserLatLng);
                mIsFirst = false;
            }

            sendUserLocation2Friend();
        }
    }

    //发送不在界面展示的消息,透传消息
    private void sendUserLocation2Friend() {
        EMMessage cmdMsg = EMMessage.createSendMessage(EMMessage.Type.CMD);

        //支持单聊和群聊，默认单聊，如果是群聊添加下面这行
        //cmdMsg.setChatType(EMMessage.ChatType.GroupChat)
        ////action可以自定义
        String action="share,"+mUserLatLng.latitude+","+mUserLatLng.longitude;
        EMCmdMessageBody cmdBody = new EMCmdMessageBody(action);
        cmdMsg.setTo(mToChatUserName);
        cmdMsg.addBody(cmdBody);
        EMClient.getInstance().chatManager().sendMessage(cmdMsg);
    }


}
