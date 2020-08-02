package com.example.day02dangridaima.model;

import com.example.day02dangridaima.bean.Bean;
import com.example.day02dangridaima.net.HttpUtil;
import com.example.day02dangridaima.net.ReslutCallBack;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

public class MainPageModel {
    public void getData(int page, final ReslutCallBack<Bean> callBack) {
        /*HttpUtil.getInstance()
                .getApiService()
                .getData()
                .subscribeOn(Schedulers.io())//被观察者的运行线程
                .observeOn(AndroidSchedulers.mainThread())//观察者运行的线程
                .subscribe(new Observer<Bean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Bean bean) {
                        callBack.onSuccess(bean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFail(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });*/

        HttpUtil.getInstance()
                .getApiService()
                .getData2(page)
                .subscribeOn(Schedulers.io())//被观察者的运行线程
                .observeOn(AndroidSchedulers.mainThread())//观察者运行的线程
                .subscribeWith(new ResourceSubscriber<Bean>() {
                    @Override
                    public void onNext(Bean bean) {
                        callBack.onSuccess(bean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        callBack.onFail(t.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
