package com.dlf.weizx.presenter;

import com.dlf.weizx.base.BasePresenter;
import com.dlf.weizx.model.SearchModel;

public class SearchPresenter extends BasePresenter {

    private SearchModel searchModel;

    @Override
    protected void initModel() {
        searchModel = new SearchModel();
        addModel(searchModel);
    }

    public void getData(int page,String word){

    }
}
