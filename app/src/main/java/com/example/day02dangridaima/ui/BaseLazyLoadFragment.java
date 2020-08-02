package com.example.day02dangridaima.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.day02dangridaima.util.LogUtil;

public abstract class BaseLazyLoadFragment extends Fragment {
    //避免数据多次进行请求
    //数据是否加载了
    boolean mDataLoaded = false;
    //view是否创建了
    boolean mIsViewCreated = false;
    private View mInflate;
    //Fragment是否可见
    private boolean mIsVisibleToUser;

    public Object mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mInflate == null){
            mInflate = inflater.inflate(getLayout(), null);
            mPresenter = initPresenter();
            initView(mInflate);

            mIsViewCreated = true;
            lazyLoad();
        }
        return mInflate;
    }

    protected abstract int getLayout();

    protected void lazyLoad(){
        //如果mInflate不是null那么意味着p对象也不为null
        if (mIsVisibleToUser && mIsViewCreated && !mDataLoaded){
            initData();
            mDataLoaded = true;
        }
    }

    protected abstract void initData();

    protected abstract void initView(View inflate);

    protected abstract Object initPresenter();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        //isVisibleToUser :true代表到底哪个前fragmnet可见
        LogUtil.logd("首页:isVisibleToUser" + isVisibleToUser);
        /*if (isVisibleToUser && mPresenter != null) {
            // initData();
        }*/

        this.mIsVisibleToUser = isVisibleToUser;
        lazyLoad();
    }
}
