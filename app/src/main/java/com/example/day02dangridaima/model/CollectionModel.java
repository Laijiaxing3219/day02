package com.example.day02dangridaima.model;

import com.example.day02dangridaima.bean.Bean;
import com.example.day02dangridaima.net.ReslutCallBack;
import com.example.day02dangridaima.sqlite.SqliteUtil;

import java.util.ArrayList;

public class CollectionModel {
    public void query(ReslutCallBack<ArrayList<Bean.ResultsBean>> callBack) {
        ArrayList<Bean.ResultsBean> list = SqliteUtil.getInstance()
                .queryAll();
        callBack.onSuccess(list);
    }
}
