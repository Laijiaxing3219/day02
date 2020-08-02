package com.example.day02dangridaima.net;

import com.example.day02dangridaima.bean.Bean;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    //android 9.0 只支持https,如果想要让app支持http请求,清单文件添加android:usesCleartextTraffic="true"
    String sUrl = "https://gank.io/api/";

    //观察者:Observer
    @GET("data/%E7%A6%8F%E5%88%A9/10/2")
    Observable<Bean> getData();



    //观察者:ResourceSubscriber,
    //带背压策略的被观察者
    @GET("data/%E7%A6%8F%E5%88%A9/10/{page}")
    Flowable<Bean> getData2(@Path("page")int page);
}
