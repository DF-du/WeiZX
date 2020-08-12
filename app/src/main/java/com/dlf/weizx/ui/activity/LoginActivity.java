package com.dlf.weizx.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.dlf.weizx.MainActivity;
import com.dlf.weizx.R;
import com.dlf.weizx.util.LogUtil;
import com.dlf.weizx.util.ToastUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    /**
     * 请输入用户名
     */
    private EditText mEtName;
    /**
     * 请输入密码
     */
    private EditText mEtPsd;
    /**
     * 登录
     */
    private Button mBtnLogin;
    /**
     * 注册
     */
    private Button mBtnRegister;
    private ImageView mIvQq;
    private ImageView mIvWechat;
    private ImageView mIvSina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //之前有没有登录过,自动登录直接跳过登录页面,进入主页面
        boolean logged = EMClient.getInstance().isLoggedInBefore();
        if (logged){
            go2MainActivity();
        }
        setContentView(R.layout.activity_login);
        initView();
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

    private void initView() {

        mEtName = (EditText) findViewById(R.id.et_name);
        mEtPsd = (EditText) findViewById(R.id.et_psd);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
        mBtnRegister.setOnClickListener(this);
        mIvQq = (ImageView) findViewById(R.id.iv_qq);
        mIvQq.setOnClickListener(this);
        mIvWechat = (ImageView) findViewById(R.id.iv_wechat);
        mIvWechat.setOnClickListener(this);
        mIvSina = (ImageView) findViewById(R.id.iv_sina);
        mIvSina.setOnClickListener(this);
    }


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
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            LogUtil.print(key + ":" + value);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_register:
                go2Register();
                break;
            case R.id.iv_qq:
                login(SHARE_MEDIA.QQ);
                break;
            case R.id.iv_wechat:
                login(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.iv_sina:
                login(SHARE_MEDIA.SINA);
                break;
        }
    }

    private void go2Register() {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    private void login() {
        //开发的时候一般不会自己直接new线程,一般会使用线程池
        final String name = mEtName.getText().toString().trim();
        final String psd = mEtPsd.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(psd)) {
            ToastUtil.showToastShort("用户名和密码不能为空");
            return;
        }

        EMClient.getInstance().login(name,psd,new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d("main", "登录聊天服务器成功！");
                showToast("登录成功");
                go2MainActivity();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.d("main", "登录聊天服务器失败！");
                showToast("登录失败");
            }
        });
    }

    private void go2MainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void showToast(final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showToastShort(msg);
            }
        });
    }
}
