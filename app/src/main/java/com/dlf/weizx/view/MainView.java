package com.dlf.weizx.view;

import com.dlf.weizx.base.BaseView;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.Map;

public interface MainView extends BaseView {
    void setData();

    void setContacts(Map<String, EaseUser> map);
}
