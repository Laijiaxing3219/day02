package com.example.day02dangridaima.net;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpUtil {
    private static volatile HttpUtil sHttpUtil;
    private ApiService mApiService;

    private HttpUtil(){
        //okhttpclient
        OkHttpClient client = getClient();
        //retrofit
        retrofit(client);
    }

    private void retrofit(OkHttpClient client) {
        mApiService = new Retrofit.Builder()
                .client(client)
                .baseUrl(ApiService.sUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiService.class);
    }


    public ApiService getApiService(){
        return mApiService;
    }

    private OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new LogInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        return client;
    }

    public static HttpUtil getInstance(){
        if (sHttpUtil == null){
            synchronized (HttpUtil.class){
                if (sHttpUtil == null){
                    sHttpUtil = new HttpUtil();
                }
            }
        }

        return sHttpUtil;
    }

    class LogInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            //拿到的response就是网络请求的结果
            Response response = chain.proceed(request);

            //response.body().string() 只能使用1次,为什么??? 因为底层是io流,使用之后关闭了
            //response.peekBody() 将response.body()拷贝
            Log.d("tag", response.peekBody(4096).string());
            return response;
        }
    }
}
