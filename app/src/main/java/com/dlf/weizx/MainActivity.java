package com.dlf.weizx;


import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.dlf.weizx.base.BaseActivity;
import com.dlf.weizx.presenter.MainPresenter;
import com.dlf.weizx.ui.activity.ChatActivity;
import com.dlf.weizx.ui.fragment.DiscoveryFragment;
import com.dlf.weizx.view.MainView;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;

/**
 * 1.gradle.properties 删掉
 * android.useAndroidX=true
 * # Automatically convert third-party libraries to use AndroidX
 * android.enableJetifier=true
 * <p>
 * 2.依赖的东西换成27
 * 3.同步
 * 4.Actiivty,布局改成非androidx的
 * <p>
 * 封装项目:
 * 1.选用框架,MVP,MVVM
 * 2.三方框架: HttpUrlConnection,xUtils,Volley,Ok,Retrofit ,Glide...
 * 3.工具类
 * 4.业务
 * <p>
 * <p>
 * 封装: 相同的类如果每次都需要写的东西可以封装到父类中
 */

/**
 * BaseActivity:
 * BasePresenter:
 * BaseModel:
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainView {

    @BindView(R.id.fl)
    FrameLayout mFl;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    private ArrayList<Fragment> mFragments;
    private FragmentManager mManager;
    //要隐藏的fragmnet的index
    private int mHidePosition;
    private EaseContactListFragment mContactListFragment;

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mPresenter.getContacts();
    }

    @Override
    protected void initView() {
        //避免进入页面EdiText自动弹出软键盘
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //避免进入页面EdiText自动弹出软键盘 且 避免底部控件被顶起
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mManager = getSupportFragmentManager();
        initFragments();
        mTabLayout.addTab(mTabLayout.newTab().setText("会话"));
        mTabLayout.addTab(mTabLayout.newTab().setText("联系人"));
        mTabLayout.addTab(mTabLayout.newTab().setText("发现"));

        //一上来应该先显示一个fragment
        showConversionFrament();

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switchFragment(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void switchFragment(int position) {
        //要显示的fragment
        Fragment showFragment = mFragments.get(position);
        //要隐藏fragmnet
        Fragment hideFragment = mFragments.get(mHidePosition);

        FragmentTransaction transaction = mManager.beginTransaction();
        //fragment只能添加一次,除非remove,会报:fragment is already added
        if (!showFragment.isAdded()){
            transaction.add(R.id.fl,showFragment);
        }
        transaction.hide(hideFragment);
        transaction.show(showFragment);

        transaction.commit();
        //这次显示的fragmnet就是下次再点击tab的时候要隐藏
        mHidePosition = position;
    }

    private void showConversionFrament() {
        mManager.beginTransaction()
                .add(R.id.fl,mFragments.get(0))
                .commit();
    }

    private void initFragments() {
        EaseConversationListFragment conversationListFragment = new EaseConversationListFragment();
        mContactListFragment = new EaseContactListFragment();
        DiscoveryFragment discoveryFragment = new DiscoveryFragment();
        mFragments = new ArrayList<>();
        mFragments.add(conversationListFragment);
        mFragments.add(mContactListFragment);
        mFragments.add(discoveryFragment);

        //会话列表子条目点击事件
        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {

            @Override
            public void onListItemClicked(EMConversation conversation) {
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId());
                //是否是群聊
                boolean group = conversation.isGroup();
                if (group){
                    //是群聊,设置群聊类型
                    intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE,EaseConstant.CHATTYPE_GROUP);
                }
                startActivity(intent);
            }
        });

        //联系人需要调用环信api去拿
        //contactListFragment.setContactsMap(getContacts());
        //联系人列表点击事件
        mContactListFragment.setContactListItemClickListener(new EaseContactListFragment.EaseContactListItemClickListener() {

            @Override
            public void onListItemClicked(EaseUser user) {
                //需要区分单聊群聊
                //EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE,EaseConstant.CHATTYPE_GROUP

                Intent intent = new Intent(MainActivity.this,
                        ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, user.getUsername());
                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE,user.getChat_type());
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void setData() {

    }

    @Override
    public void setContacts(final Map<String, EaseUser> map) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mContactListFragment.setContactsMap(map);
            }
        });
    }

}
