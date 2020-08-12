package com.dlf.weizx.model;

import com.dlf.weizx.base.BaseModel;
import com.dlf.weizx.bean.ChapterTabBean;
import com.dlf.weizx.net.HttpUtil;
import com.dlf.weizx.net.ResultCallBack;
import com.dlf.weizx.net.ResultSubscriber;
import com.dlf.weizx.net.RxUtils;

public class ItInfoModel extends BaseModel {
    public void getTab(final ResultCallBack<ChapterTabBean> callBack) {
        addDisposable(
                HttpUtil.getInstance()
                .getWanService()
                .getChapterTab()
                .compose(RxUtils.<ChapterTabBean>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<ChapterTabBean>() {
                    @Override
                    public void onNext(ChapterTabBean chapterTabBean) {
                        callBack.onSuccess(chapterTabBean);
                    }
                })
        );
    }
}
