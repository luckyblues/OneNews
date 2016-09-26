package com.example.administrator.onenews.news.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.onenews.news.Fragment.ContentFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/9/18.
 */
public class MyAdapter extends FragmentPagerAdapter {
    private Context context;
    private List<ContentFragment> fragmentslists;
    private String[] titles;


    public MyAdapter(Context context, List<ContentFragment> fragmentslists, String[] titles, FragmentManager fm) {
        super(fm);
        this.context = context;
        this.fragmentslists = fragmentslists;
        this.titles = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentslists.get(position);
    }

    @Override
    public int getCount() {
        return fragmentslists.size();
    }
}
