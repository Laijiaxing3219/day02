package com.example.day02zuoye;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.tabs.TabLayout;
/*
TabLayout+容器非viewpager）+Fragment+RecyclerView+Rxjava+Retrofit+MVP+Popupwindow+通知
*/
public class MainActivity extends AppCompatActivity {

    private FrameLayout mFragment;
    private TabLayout mTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }

    private void initView() {
        mFragment = (FrameLayout) findViewById(R.id.fragment);
        mTab = (TabLayout) findViewById(R.id.tab);

        mTab.addTab(mTab.newTab().setText("首页"));
        mTab.addTab(mTab.newTab().setText("收藏"));

        final FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment,new ShouYeFragment()).commit();

        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if(position==0){
                    manager.beginTransaction().replace(R.id.fragment,new ShouYeFragment()).commit();
                }else {
                    manager.beginTransaction().replace(R.id.fragment,new ShouCangFragment()).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
