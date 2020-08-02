package com.example.day02zuoye;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class IModel implements Model {
    @Override
    public void getData(final CallBack callBack) {
        Retrofit build = new Retrofit.Builder().baseUrl(ApiService.baseurl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = build.create(ApiService.class);
        Observable<FuliBean> data = apiService.getData();
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FuliBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FuliBean fuliBean) {
                            callBack.dui(fuliBean.getResults());
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.cuo("数据获取失败+"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
