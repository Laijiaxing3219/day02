package com.example.day02dangridaima.ui;

import android.view.View;

import com.example.day02dangridaima.R;
import com.example.day02dangridaima.util.LogUtil;

public class MeFragment extends BaseLazyLoadFragment{
    public static MeFragment getInstance(){
        MeFragment fragment = new MeFragment();
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View inflate) {

    }

    @Override
    protected Object initPresenter() {
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.logd("destroy:me");
    }
}
