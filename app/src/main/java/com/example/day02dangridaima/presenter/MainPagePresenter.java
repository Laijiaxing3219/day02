package com.example.day02dangridaima.presenter;

import com.example.day02dangridaima.bean.Bean;
import com.example.day02dangridaima.model.MainPageModel;
import com.example.day02dangridaima.net.ReslutCallBack;
import com.example.day02dangridaima.view.MainPageView;

public class MainPagePresenter {
    private MainPageView mView;
    private final MainPageModel mModel;

    public MainPagePresenter(MainPageView view) {

        mView = view;
        mModel = new MainPageModel();
    }

    public void getData(int page) {
        mModel.getData(page, new ReslutCallBack<Bean>() {
            @Override
            public void onSuccess(Bean bean) {
                mView.setData(bean);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
