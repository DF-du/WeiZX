package com.dlf.weizx.model;

import com.dlf.weizx.base.BaseModel;
import com.dlf.weizx.net.ResultCallBack;
import com.dlf.weizx.util.ThreadManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.exceptions.HyphenateException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainModel extends BaseModel {


    public void getConstacts(final ResultCallBack<Map<String, EaseUser>> callBack) {
        ThreadManager.getInstance()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            HashMap<String, EaseUser> map = new HashMap<>();
                            //获取好友列表
                            List<String> usernames =
                                    EMClient.getInstance().contactManager().getAllContactsFromServer();


                            for (int i = 0; i < usernames.size(); i++) {
                                String name = usernames.get(i);
                                EaseUser easeUser = new EaseUser(name);
                                easeUser.setChat_type(EaseConstant.CHATTYPE_SINGLE);
                                map.put(name,easeUser);
                            }

                            //从服务器获取自己加入的和创建的群组列表，此api获取的群组sdk会自动保存到内存和db。
                            List<EMGroup> grouplist = EMClient.getInstance()
                                                            .groupManager()
                                                            .getJoinedGroupsFromServer();//需异步处理

                            for (int i = 0; i < grouplist.size(); i++) {
                                EMGroup emGroup = grouplist.get(i);
                                EaseUser easeUser = new EaseUser(emGroup.getGroupId());
                                easeUser.setChat_type(EaseConstant.CHATTYPE_GROUP);
                                map.put(emGroup.getGroupId(),easeUser);
                            }

                            callBack.onSuccess(map);
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
