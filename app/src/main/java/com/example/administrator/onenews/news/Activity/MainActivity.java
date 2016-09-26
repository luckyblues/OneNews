package com.example.administrator.onenews.news.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.onenews.R;
import com.example.administrator.onenews.news.Adapter.MyAdapter;
import com.example.administrator.onenews.news.Fragment.ContentFragment;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout mTablayout;
    private String[] mTitles;
    private ViewPager mViewPager;
    private MyAdapter myAdapter;
    private List<ContentFragment> mFragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Fresco.initialize(this);

        initView();
        initData();
        initListener();//标题栏的点击事件
    }

    private void initListener() {
        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
             int position=tab.getPosition();
                for (int i = 0; i <mFragments.size() ; i++) {
                    mFragments.get(position).setPosition(position);
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

    private void initData() {
        //获得标题
        mTitles = getResources().getStringArray(R.array.titleArray);
        mFragments = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            ContentFragment contentFragment = new ContentFragment();
            mFragments.add(contentFragment);
        }
        myAdapter = new MyAdapter(MainActivity.this, mFragments, mTitles, getSupportFragmentManager());
        mViewPager.setAdapter(myAdapter);//设置Adapter
        mTablayout.setupWithViewPager(mViewPager);//和ViewPager一起联动
    }

    private void initView() {
        mTablayout = (TabLayout) findViewById(R.id.tablayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
    }
}
