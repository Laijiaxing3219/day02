package com.example.day02zuoye;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {
    String baseurl="https://gank.io/api/data/%E7%A6%8F%E5%88%A9/";
    @GET("20/2")
    Observable<FuliBean> getData();
}
