package com.hyphenate.easeui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
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
import com.hyphenate.easeui.map.PoiOverlay;
import com.hyphenate.easeui.utils.NumberUtil;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;


/**
 * 完成共享
 * 1.自己的位置,通过定位搞定
 *      发送给对方
 * 2.好友位置
 *      怎么拿到?
 *      实时发送给我,环信
 *
 *
 * a.展示自己的位置
 * b.实时接收对方的位置
 * c.将自己的位置发送给对方
 *
 *
 * 1.提示好友发起了共享位置,并且点击可以跳转到共享位置得页面
 * 2.接收到和发送的位置信息,在聊天页面不要展示,不是普通消息
 *
 */
public class MapActivty extends AppCompatActivity implements View.OnClickListener, EMMessageListener {
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private LatLng mUserLatLng;
    private MapView mBmapView;
    /**
     * I'm Here
     */
    private Button mBtnUser;
    private EditText mEt;
    /**
     * 搜索
     */
    private Button mBtnSearch;
    private PoiSearch mPoiSearch;
    private WalkNaviLaunchParam mParam;
    private String mFriend;
    private LatLng mFriendLatLng;
    private Overlay mOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mFriend = getIntent().getStringExtra("data");
        initView();


        //注册监听接收好友发送过来的消息,主要接收含有好友位置的透传消息
        EMClient.getInstance().chatManager().addMessageListener(this);


    }

    @Override
    public void onMessageReceived(List<EMMessage> messages) {
        //收到消息
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {
        //收到透传消息
        //接收好友的位置

        //透传消息:透传消息不会存入本地数据库中，所以在 UI 上是不会显示的
           /* EMMessage cmdMsg = EMMessage.createSendMessage(EMMessage.Type.CMD);

            //支持单聊和群聊，默认单聊，如果是群聊添加下面这行

            String action="share_location";//action可以自定义
            EMCmdMessageBody cmdBody = new EMCmdMessageBody(action);
            String toUsername = mFriend;//发送给某个人
            cmdMsg.setTo(toUsername);
            cmdMsg.addBody(cmdBody);
            //自己的经纬度传进去
            cmdMsg.setAttribute("location",location.getLatitude()+","+location.getLongitude());
            EMClient.getInstance().chatManager().sendMessage(cmdMsg);*/

        for (int i = 0; i < list.size(); i++) {
            //只处理透传消息
            EMMessage emMessage = list.get(i);
            EMMessageBody body = emMessage.getBody();
            if (body instanceof EMCmdMessageBody){
                EMCmdMessageBody messageBody = (EMCmdMessageBody) body;
                String action = messageBody.action();
                Log.d("share", "onCmdMessageReceived: "+action);


                String[] split = action.split(",");
                if (split != null && split.length== 3 && split[0].startsWith("share")){
                    mFriendLatLng = new LatLng(NumberUtil.parseString2Double(split[1]),
                            NumberUtil.parseString2Double(split[2]));

                    Log.d("share", "dealAction: "+split[1]+","+split[2]);

                    if (mOverlay != null){
                        mOverlay.remove();
                    }
                    //构建Marker图标
                    BitmapDescriptor bitmap = BitmapDescriptorFactory
                            .fromResource(R.drawable.icon_walk_route);
//构建MarkerOption，用于在地图上添加Marker
                    OverlayOptions option = new MarkerOptions()
                            .position(mFriendLatLng)
                            .animateType(MarkerOptions.MarkerAnimateType.jump)
                            .icon(bitmap);
//在地图上添加Marker，并显示
                    mOverlay = mBaiduMap.addOverlay(option);


                }
            }
        }
    }

    @Override
    public void onMessageRead(List<EMMessage> messages) {
        //收到已读回执
    }

    @Override
    public void onMessageDelivered(List<EMMessage> message) {
        //收到已送达回执
    }
    @Override
    public void onMessageRecalled(List<EMMessage> messages) {
        //消息被撤回
    }

    @Override
    public void onMessageChanged(EMMessage message, Object change) {
        //消息状态变动
    }

    /**
     * 定位几内亚湾
     * 1.签名信息不对,鉴权错误
     * 2.用的是模拟器/你的设备有毛病
     * 3.GPS没有开
     */
    private void locate() {
        //开启地图的定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //通过LocationClient发起定位
        //定位初始化
        mLocationClient = new LocationClient(this);

//通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(3000);//定位的间隔

//设置locationClientOption
        mLocationClient.setLocOption(option);

//注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
//开启地图定位图层
        mLocationClient.start();
    }

    private void initView() {
        mBmapView = (MapView) findViewById(R.id.bmapView);
        mBtnUser = (Button) findViewById(R.id.btn_user);
        mBtnUser.setOnClickListener(this);
        mEt = (EditText) findViewById(R.id.et);
        mBtnSearch = (Button) findViewById(R.id.btn_search);
        mBtnSearch.setOnClickListener(this);

        //获取地图控件引用

        mBaiduMap = mBmapView.getMap();

        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(18.0f);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

        LatLng GEO_SHANGHAI = new LatLng(31.227, 121.481);

        //move2LatLng(GEO_SHANGHAI);


        locate();

        //地图点击事件
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //地图空白地方的点击事件
                showToast("map click");
                addMarder(latLng);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                //地图上poi图标的点击事件
                showToast(mapPoi.getName());
                return false;
            }
        });

        //marker的点击事件
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //showToast(marker.getPosition().toString());
                walkNavi(marker.getPosition());
                return false;
            }
        });



    }

    //步行导航
    private void walkNavi(final LatLng end) {
    // 获取导航控制类
    // 引擎初始化
        WalkNavigateHelper.getInstance().initNaviEngine(this, new IWEngineInitListener() {

            @Override
            public void engineInitSuccess() {
                //引擎初始化成功的回调
                routeWalkPlanWithParam(end);
            }

            @Override
            public void engineInitFail() {
                //引擎初始化失败的回调
            }
        });
    }

    private void routeWalkPlanWithParam(LatLng end) {
        //发起算路
        //起终点位置

        //构造WalkNaviLaunchParam
        mParam = new WalkNaviLaunchParam().stPt(mUserLatLng).endPt(end);

        //发起算路
        //发起算路
        WalkNavigateHelper.getInstance().routePlanWithParams(mParam, new IWRoutePlanListener() {
            @Override
            public void onRoutePlanStart() {
                //开始算路的回调
            }

            @Override
            public void onRoutePlanSuccess() {
                //算路成功
                //跳转至诱导页面
                Intent intent = new Intent(MapActivty.this, WNaviGuideActivity.class);
                startActivity(intent);
            }

            @Override
            public void onRoutePlanFail(WalkRoutePlanError walkRoutePlanError) {
                //算路失败的回调
            }
        });
    }

    //添加覆盖物
    public void addMarder(LatLng latLng) {

//构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_marka);
//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(latLng)
                .animateType(MarkerOptions.MarkerAnimateType.jump)
                .icon(bitmap);
//在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    //如果一个module作为library存在,里面设计到常量的不能使用switch
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_user) {
            move2LatLng(mUserLatLng);
        }if (id == R.id.btn_search){
            search();
        }
    }

    private void search() {
        String content = mEt.getText().toString().trim();
        if (TextUtils.isEmpty(content)){
            showToast("搜索内容不能为空");
            return;
        }

        //1.创建POI检索实例
        mPoiSearch = PoiSearch.newInstance();
        //3.设置检索监听器
        mPoiSearch.setOnGetPoiSearchResultListener(listener);

        //4.设置PoiCitySearchOption，发起检索请求
        /**
         *  PoiCiySearchOption 设置检索属性
         *  city 检索城市
         *  keyword 检索内容关键字
         *  pageNum 分页页码
         */
       /* mPoiSearch.searchInCity(new PoiCitySearchOption()
                .city("北京") //必填
                .keyword("美食") //必填
                .pageNum(10));*/

       //POI周边检索
        /**
         * 以用户为中心
         */
        if (mUserLatLng != null){
            mPoiSearch.searchNearby(new PoiNearbySearchOption()
                    .location(mUserLatLng)
                    .radius(10000)//搜索半径,单位米
                    .keyword(content)//搜索内容
                    .pageNum(10));//搜索每页数量
        }
    }

    //2.创建POI检索监听器
    OnGetPoiSearchResultListener listener = new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            //检索结果覆盖物
            if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                mBaiduMap.clear();

                //创建PoiOverlay对象
                PoiOverlay poiOverlay = new PoiOverlay(mBaiduMap);

                //设置Poi检索数据
                poiOverlay.setData(poiResult);

                //将poiOverlay添加至地图并缩放至合适级别
                poiOverlay.addToMap();
                poiOverlay.zoomToSpan();
            }
        }
        @Override
        public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

        }
        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
        //废弃
        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }
    };


    //我们通过继承抽象类BDAbstractListener并重写其onReceieveLocation方法来获取定位数据，并将其传给MapView。
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mBmapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);

            mUserLatLng = new LatLng(location.getLatitude(), location.getLongitude());

            //需要将自己的位置信息发送给好友,并且这个消息不能再聊天页面展示,

            //透传消息:透传消息不会存入本地数据库中，所以在 UI 上是不会显示的
            EMMessage cmdMsg = EMMessage.createSendMessage(EMMessage.Type.CMD);

            //支持单聊和群聊，默认单聊，如果是群聊添加下面这行
            //cmdMsg.setChatType(EMMessage.ChatType.GroupChat)
            ////action可以自定义
            String action="share,"+mUserLatLng.latitude+","+mUserLatLng.longitude;
            EMCmdMessageBody cmdBody = new EMCmdMessageBody(action);
            cmdMsg.setTo(mFriend);
            cmdMsg.addBody(cmdBody);
            EMClient.getInstance().chatManager().sendMessage(cmdMsg);

        }
    }

    private void move2LatLng(LatLng latLng) {
        if (latLng != null) {
            MapStatusUpdate status2 = MapStatusUpdateFactory.newLatLng(latLng);
            mBaiduMap.setMapStatus(status2);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mBmapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mBmapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        //释放检索实例
        if (mPoiSearch != null){
            mPoiSearch.destroy();
        }

        //记得在不需要的时候移除listener，如在activity的onDestroy()时
        EMClient.getInstance().chatManager().removeMessageListener(this);

        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mBmapView.onDestroy();
        mBmapView = null;


    }
}
