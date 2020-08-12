package com.dlf.weizx.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dlf.weizx.R;
import com.dlf.weizx.util.ThreadManager;
import com.dlf.weizx.util.ToastUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolBar;
    /**
     * 请输入用户名
     */
    private EditText mEtName;
    /**
     * 请输入密码
     */
    private EditText mEtPsd;
    /**
     * 注册
     */
    private Button mBtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        mToolBar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(mToolBar);
        mEtName = (EditText) findViewById(R.id.et_name);
        mEtPsd = (EditText) findViewById(R.id.et_psd);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
        mBtnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_register:
                register();
                break;
        }
    }

    private void register() {
        //开发的时候一般不会自己直接new线程,一般会使用线程池
        final String name = mEtName.getText().toString().trim();
        final String psd = mEtPsd.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(psd)) {
            ToastUtil.showToastShort("用户名和密码不能为空");
            return;
        }

        ThreadManager.getInstance()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        //注册失败会抛出HyphenateException
                        try {
                            EMClient.getInstance().createAccount(name, psd);//同步方法
                            showToast("注册成功");
                            finish();
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            showToast("注册失败");
                        }
                    }
                });
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
