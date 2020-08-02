package com.example.day02dangridaima.ui;

import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.day02dangridaima.R;
import com.example.day02dangridaima.adapter.RlvMainAdapter;
import com.example.day02dangridaima.bean.Bean;
import com.example.day02dangridaima.presenter.MainPagePresenter;
import com.example.day02dangridaima.sqlite.SqliteUtil;
import com.example.day02dangridaima.util.LogUtil;
import com.example.day02dangridaima.view.MainPageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;

public class MainPageFragment extends BaseLazyLoadFragment implements MainPageView {
    private RecyclerView mRlv;
    private SmartRefreshLayout mSrl;
    int mPage = 1;
    private RlvMainAdapter mAdapter;
    private int mPosition;


    public static MainPageFragment getInstance() {
        MainPageFragment fragment = new MainPageFragment();
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_main_page;
    }

    @Override
    public void initData() {
        ((MainPagePresenter)mPresenter).getData(mPage);
    }

    @Override
    public void initView(View inflate) {
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
                ((MainPagePresenter)mPresenter).getData(mPage);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //下拉刷新
                mAdapter.mList.clear();
                mPage = 1;
                ((MainPagePresenter)mPresenter).getData(mPage);
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
    protected Object initPresenter() {
        return new MainPagePresenter(this);
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
        SqliteUtil.getInstance()
                .insert(resultsBean);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.logd("destroy:首页");
    }
}
