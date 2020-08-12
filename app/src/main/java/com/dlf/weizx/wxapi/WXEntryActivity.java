package com.dlf.weizx.wxapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dlf.weizx.R;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

public class WXEntryActivity extends WXCallbackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);
    }
}
