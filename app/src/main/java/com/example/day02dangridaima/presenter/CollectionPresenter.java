package com.example.day02dangridaima.presenter;

import com.example.day02dangridaima.bean.Bean;
import com.example.day02dangridaima.model.CollectionModel;
import com.example.day02dangridaima.net.ReslutCallBack;
import com.example.day02dangridaima.view.CollectionView;

import java.util.ArrayList;

public class CollectionPresenter {
    private CollectionView mView;
    private final CollectionModel mCollectionModel;

    public CollectionPresenter(CollectionView view) {

        mView = view;
        mCollectionModel = new CollectionModel();
    }

    public void query() {
        mCollectionModel.query(new ReslutCallBack<ArrayList<Bean.ResultsBean>>() {
            @Override
            public void onSuccess(ArrayList<Bean.ResultsBean> resultsBeans) {
                mView.setData(resultsBeans);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
