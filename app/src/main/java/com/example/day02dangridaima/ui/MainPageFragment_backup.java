package com.example.day02dangridaima.ui;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.day02dangridaima.R;
import com.example.day02dangridaima.adapter.RlvMainAdapter;
import com.example.day02dangridaima.bean.Bean;
import com.example.day02dangridaima.presenter.MainPagePresenter;
import com.example.day02dangridaima.sqlite.MyHelper;
import com.example.day02dangridaima.util.LogUtil;
import com.example.day02dangridaima.view.MainPageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;

public class MainPageFragment_backup extends Fragment implements MainPageView {
    private MainPagePresenter mPresenter;
    private RecyclerView mRlv;
    private SmartRefreshLayout mSrl;
    int mPage = 1;
    private RlvMainAdapter mAdapter;
    private int mPosition;
    private MyHelper mMyHelper;

    //避免数据多次进行请求
    //数据是否加载了
    boolean mDataLoaded = false;
    //view是否创建了
    boolean mIsViewCreated = false;
    private View mInflate;
    //Fragment是否可见
    private boolean mIsVisibleToUser;

    public static MainPageFragment_backup getInstance() {
        MainPageFragment_backup fragment = new MainPageFragment_backup();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mInflate == null){
            mInflate = inflater.inflate(R.layout.fragment_main_page, null);
            mMyHelper = new MyHelper(getContext());
            mPresenter = new MainPagePresenter(this);
            initView(mInflate);

            mIsViewCreated = true;
            lazyLoad();
        }
        return mInflate;
    }

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

    private void lazyLoad() {
        //如果mInflate不是null那么意味着p对象也不为null
        if (mIsVisibleToUser && mIsViewCreated && !mDataLoaded){
            initData();
        }
    }

    private void initData() {
        mPresenter.getData(mPage);
        mDataLoaded = true;
    }

    private void initView(View inflate) {
        LogUtil.logd("initview: 首页");
        mRlv = inflate.findViewById(R.id.rlv);
        mSrl = inflate.findViewById(R.id.srl);

        ArrayList<Bean.ResultsBean> list = new ArrayList<>();
        mAdapter = new RlvMainAdapter(getContext(), list);
        mRlv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRlv.setAdapter(mAdapter);

        //下拉刷新和上拉加载更多
        mSrl.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                //加载更多
                mPage++;
                mPresenter.getData(mPage);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //下拉刷新
                mAdapter.mList.clear();
                mPage = 1;
                mPresenter.getData(mPage);
            }
        });


        registerForContextMenu(mRlv);

        mAdapter.setOnItemLongClickListener(new RlvMainAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, int position) {
                mPosition = position;
            }
        });
    }

    @Override
    public void setData(Bean bean) {
        //&:并且 条件1 & 条件2,条件1是false,条件2的逻辑走
        //&&:条件1 & 条件2,条件1是false,条件2的逻辑不用走了
        if (bean != null && bean.getResults() != null && bean.getResults().size() > 0) {
            mAdapter.addAll(bean.getResults());
        }

        //关闭下拉头,上拉布局
        mSrl.finishLoadmore();
        mSrl.finishRefresh();
    }

    //上下文菜单
    //1.注册
    //2.onCreateContextMenu
    //3.onContextItemSelected

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        //menu.add()
        //new MenuInflater().inflate();
        //int groupId, 组id
        // int itemId, 菜单id
        // int order, 顺序
        // CharSequence title) 标题
        menu.add(0, 0, 1, "收藏");
        menu.add(0, 1, 0, "删除");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                collect(mAdapter.mList.get(mPosition));
                break;
            case 1:

                break;
        }
        return super.onContextItemSelected(item);
    }

    private void collect(Bean.ResultsBean resultsBean) {
        //区别:getReadableDatabase();getWritableDatabase();
        //都可以读写数据库
        //如果磁盘满了getWritableDatabase 获取database的时候就崩了
        SQLiteDatabase db = mMyHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //db.execSQL("create table info(id varchar  primary key ,text url,text des)");
        values.put("id", resultsBean.get_id());
        values.put("url", resultsBean.getUrl());
        values.put("des", resultsBean.getDesc());
        db.insert("info", null, values);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.logd("destroy:首页");
    }

}
