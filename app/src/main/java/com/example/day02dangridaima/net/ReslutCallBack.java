package com.example.day02dangridaima.net;

public interface ReslutCallBack<T> {
    void onSuccess(T t);
    void onFail(String msg);
}
