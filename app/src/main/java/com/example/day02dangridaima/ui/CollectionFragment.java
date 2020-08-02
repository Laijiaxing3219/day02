package com.example.day02dangridaima.ui;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.day02dangridaima.R;
import com.example.day02dangridaima.adapter.RlvMainAdapter;
import com.example.day02dangridaima.bean.Bean;
import com.example.day02dangridaima.presenter.CollectionPresenter;
import com.example.day02dangridaima.util.LogUtil;
import com.example.day02dangridaima.view.CollectionView;

import java.util.ArrayList;

public class CollectionFragment extends BaseLazyLoadFragment implements CollectionView {
    private RecyclerView mRlv;
    private RlvMainAdapter mAdapter;
    public static CollectionFragment getInstance(){
        CollectionFragment fragment = new CollectionFragment();
        return fragment;
    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_main_page;
    }

    @Override
    public void initData() {
        ((CollectionPresenter)mPresenter).query();
    }

    @Override
    public void initView(View inflate) {
        mRlv = inflate.findViewById(R.id.rlv);

        ArrayList<Bean.ResultsBean> list = new ArrayList<>();
        mAdapter = new RlvMainAdapter(getContext(), list);
        mRlv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRlv.setAdapter(mAdapter);

        LogUtil.logd("initView :收藏");
    }

    @Override
    protected Object initPresenter() {
        return new CollectionPresenter(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.logd("destroy:收藏");
    }

    @Override
    public void setData(ArrayList<Bean.ResultsBean> list) {
        mAdapter.mList.clear();
        mAdapter.addAll(list);
    }

}
