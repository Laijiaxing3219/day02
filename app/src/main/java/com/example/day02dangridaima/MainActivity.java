package com.example.day02dangridaima;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

import com.example.day02dangridaima.adapter.VpMainAdapter;
import com.example.day02dangridaima.ui.CollectionFragment;
import com.example.day02dangridaima.ui.MainPageFragment;
import com.example.day02dangridaima.ui.MeFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolBar;
    private ViewPager mVp;
    private TabLayout mTabLayout;
    /**
     * 首页
     */
    private TextView mTv;
    private ArrayList<String> mTitles;
    private ArrayList<Fragment> mFragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView() {

        mToolBar = (Toolbar) findViewById(R.id.toolBar);
        mVp = (ViewPager) findViewById(R.id.vp);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTv = (TextView) findViewById(R.id.tv);

        mToolBar.setTitle("");
        //替换actionbar,style.xml改为NoActionBar样式
        setSupportActionBar(mToolBar);

        initTitles();
        initFragments();

        VpMainAdapter adapter = new VpMainAdapter(getSupportFragmentManager(), mTitles, mFragments);
        mVp.setAdapter(adapter);
        //关联tablayout,ViewPager,tab就有viewpager的适配器帮忙创建
        mTabLayout.setupWithViewPager(mVp);

       /*//添加选择器
        for (int i = 0; i < mTitles.size(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (i == 0) {
                tab.setIcon(R.drawable.se_main);
            }else if (i ==1 ){
                tab.setIcon(R.drawable.se_collection);
            }else {
                tab.setIcon(R.drawable.se_me);
            }
        }*/

        //tablayout选中监听
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //tab选中
                int position = tab.getPosition();
                mTv.setText(mTitles.get(position));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //取消选中
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //重复选中
            }
        });
    }

    private void initFragments() {
        mFragments = new ArrayList<>();

        mFragments.add(MainPageFragment.getInstance());
        mFragments.add(CollectionFragment.getInstance());
        mFragments.add(MeFragment.getInstance());
    }

    private void initTitles() {
        mTitles = new ArrayList<>();
        mTitles.add("首页");
        mTitles.add("收藏");
        mTitles.add("我的");
    }
}
