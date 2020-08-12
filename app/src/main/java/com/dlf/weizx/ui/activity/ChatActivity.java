package com.dlf.weizx.ui.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.dlf.weizx.R;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;


import java.io.IOException;

public class ChatActivity extends EaseBaseActivity {
    public static ChatActivity activityInstance;
    private EaseChatFragment chatFragment;
    String toChatUsername;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_chat);
        activityInstance = this;
        //user or group id
        toChatUsername = getIntent().getExtras().getString(EaseConstant.EXTRA_USER_ID);
        chatFragment = new EaseChatFragment();
        //set arguments
        chatFragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();

        chatFragment.setChatFragmentHelper(new EaseChatFragment.EaseChatFragmentHelper() {
            /**
             * 设置消息扩展属性
             */
            @Override
            public void onSetMessageAttributes(EMMessage message) {

            }

            /**
             * 进入会话详情
             */
            @Override
            public void onEnterToChatDetails() {

            }

            /**
             * 用户头像点击事件
             * @param username
             */
            @Override
            public void onAvatarClick(String username) {

            }

            /**
             * 用户头像长按事件
             * @param username
             */
            @Override
            public void onAvatarLongClick(String username) {

            }


            /**
             * 消息气泡框点击事件
             */
            @Override
            public boolean onMessageBubbleClick(EMMessage message) {
                EMMessageBody body = message.getBody();
                if (body instanceof EMVoiceMessageBody){
                    EMVoiceMessageBody voiceMessageBody = (EMVoiceMessageBody) body;
                    String localUrl = voiceMessageBody.getLocalUrl();

                    if (!TextUtils.isEmpty(localUrl)){
                        startVoice(localUrl);
                    }
                }else if (body instanceof EMTextMessageBody){
                    //共享位置得扩展消息
                    //chatFragment.go2ShareLocation();
                    //文本消息
                    boolean share = message.getBooleanAttribute("share", false);
                    if (share){
                        //说明这条消息是共享位置得消息
                        chatFragment.go2ShareLocation();
                    }
                }
                return false;
            }

            @Override
            public void onMessageBubbleLongClick(EMMessage message) {

            }

            @Override
            public boolean onExtendMenuItemClick(int itemId, View view) {
                return false;
            }

            @Override
            public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
                return null;
            }
        });
        
    }

    private void startVoice(String localUrl) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(localUrl);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
        // enter to chat activity when click notification bar, here make sure only one chat activiy
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }
    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
    }
    
    public String getToChatUsername(){
        return toChatUsername;
    }
}
