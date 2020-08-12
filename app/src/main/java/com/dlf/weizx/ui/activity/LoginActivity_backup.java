package com.dlf.weizx.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.dlf.weizx.R;
import com.dlf.weizx.util.LogUtil;
import com.dlf.weizx.util.ToastUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.Map;

public class LoginActivity_backup extends AppCompatActivity {

    /**
     * 带面板的分享
     */
    private Button mBtn;
    /**
     * qq分享
     */
    private Button mBtnQq;
    /**
     * 微信分享
     */
    private Button mBtnWechat;
    /**
     * sina分享
     */
    private Button mBtnSina;
    /**
     * qq登录
     */
    private Button mBtnQqLogin;
    /**
     * 微信登录
     */
    private Button mBtnWechatLogin;
    /**
     * sina登录
     */
    private Button mBtnSinaLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //initView();
        initPers();
    }

    private void initPers() {
        String[] pers = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE
        };
        ActivityCompat.requestPermissions(this, pers, 100);
    }


    //QQ与新浪不需要添加Activity，但需要在使用QQ分享或者授权的Activity中，添加：
    //注意onActivityResult不可在fragment中实现，如果在fragment中调用登录或分享，需要在fragment依赖的Activity中实现
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /*private void initView() {
        mBtn = (Button) findViewById(R.id.btn);
        mBtn.setOnClickListener(this);
        mBtnQq = (Button) findViewById(R.id.btn_qq);
        mBtnQq.setOnClickListener(this);
        mBtnWechat = (Button) findViewById(R.id.btn_wechat);
        mBtnWechat.setOnClickListener(this);
        mBtnSina = (Button) findViewById(R.id.btn_sina);
        mBtnSina.setOnClickListener(this);
        mBtnQqLogin = (Button) findViewById(R.id.btn_qq_login);
        mBtnQqLogin.setOnClickListener(this);
        mBtnWechatLogin = (Button) findViewById(R.id.btn_wechat_login);
        mBtnWechatLogin.setOnClickListener(this);
        mBtnSinaLogin = (Button) findViewById(R.id.btn_sina_login);
        mBtnSinaLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn:
                shareBoard();
                break;
            case R.id.btn_qq:
                share(SHARE_MEDIA.QQ);
                break;
            case R.id.btn_wechat:
                share(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.btn_sina:
                share(SHARE_MEDIA.SINA);
                break;
            case R.id.btn_qq_login:
                login(SHARE_MEDIA.QQ);
                break;
            case R.id.btn_wechat_login:
                login(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.btn_sina_login:
                login(SHARE_MEDIA.SINA);
                break;
        }
    }*/

    private void login(SHARE_MEDIA type) {
        UMShareAPI.get(this).getPlatformInfo(this, type, authListener);
    }

    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            ToastUtil.showToastShort("成功");
            printMap(data);
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            ToastUtil.showToastShort("失败");
         }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
           ToastUtil.showToastShort("取消");
        }
    };

    private void printMap(Map<String, String> data) {
        for (Map.Entry<String,String> entry: data.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            LogUtil.print(key+":"+value);
        }
    }

    private void share(SHARE_MEDIA type) {
        UMImage image = new UMImage(this, R.drawable.umeng_socialize_qq);//资源文件
        new ShareAction(this)
                .setPlatform(type)//传入平台
                .withText("hello")//分享内容
                .withMedia(image)
                .setCallback(shareListener)//回调监听器
                .share();
    }

    private void shareBoard() {
        UMImage image = new UMImage(this, R.drawable.umeng_socialize_qq);//资源文件
        new ShareAction(this)
                .withText("hello")
                .withMedia(image)//分享媒体资源
                .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                .setCallback(shareListener).open();
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtil.showToastShort("成功");
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtil.showToastShort("失败");
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtil.showToastShort("取消");
        }
    };
}
