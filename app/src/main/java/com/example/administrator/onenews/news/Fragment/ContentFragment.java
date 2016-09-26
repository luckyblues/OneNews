package com.example.administrator.onenews.news.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.administrator.onenews.R;
import com.example.administrator.onenews.news.Activity.DetailMessActivity;
import com.example.administrator.onenews.news.Adapter.FirstPageAdapter;
import com.example.administrator.onenews.news.Bean.BannerBean;
import com.example.administrator.onenews.news.Bean.Data;
import com.example.administrator.onenews.news.Util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

public class ContentFragment extends Fragment implements JsonUtil.CallBackListener {
    private RecyclerView recyclerView;
    FirstPageAdapter firstPageAdapter;
    private JsonUtil jsonUtil;

    private int now_num = 7;//当前的几条数据，三条数据分配给banner，
    private final int LOADING_NUM = 4;//当前的几条数据，三条数据分配给banner，
    private int mposition;

    private List<Data>[] msg_list;
    private List<Data> item_list=new ArrayList<>();//正常布局的存放容器
    private BannerBean bannerbean;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int lastVisible;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_content, (ViewGroup) getActivity().findViewById(R.id.viewPager), false);
        item_list = new ArrayList<>();
        bannerbean = new BannerBean();
        getData();
        recyclerView = (RecyclerView) v.findViewById(R.id.recycleView);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                now_num += LOADING_NUM;
                initData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firstPageAdapter = new FirstPageAdapter(getActivity(), item_list, bannerbean);
        recyclerView.setAdapter(firstPageAdapter);


        initListData();
        return v;
    }

    private void initListData() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //滚动状态发生变化时监听
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisible + 1 == firstPageAdapter.getItemCount()) {
                    //延时更新
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            now_num += LOADING_NUM;
                            initData();//改变加载数据
                        }
                    }, 1500);

                }
            }

            //滚动时监听
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //上拉加载时找到最后一个位置
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastVisible = lm.findLastVisibleItemPosition();
            }
        });
        //设置接口回调
        firstPageAdapter.setMyItemClickListener(new FirstPageAdapter.MyItemClickListener() {
            @Override
            public void onClick(View itenView, int position) {
                String url=item_list.get(position-1).getUrl();
                Intent intent=new Intent(getActivity(), DetailMessActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("position",mposition);
                startActivity(intent);
            }
        });
    }

    /**
     * 获取网络数据的方法
     */
    private void getData() {

        //调用JsonUtil的方法getResult()解析Json数据
        jsonUtil = new JsonUtil(this);
        jsonUtil.getResult();
    }

    @Override
    public void upDate(List<Data>[] msg_list) {
        this.msg_list = msg_list;
        initData();

    }

    public void initData() {

        if (msg_list != null) {
            String[] img = new String[3];
            String[] title = new String[3];
            String[] toUrl = new String[3];

            List<Data> data = msg_list[mposition];

            for (int i = 0; i < 3; i++) {
                img[i] = data.get(i).getThumbnail();
                title[i] = data.get(i).getTitle();
                toUrl[i] = data.get(i).getUrl();
            }

            bannerbean.setTitle(title);
            bannerbean.setImg_url(img);
            bannerbean.setToUrl(toUrl);
            //清除缓存
            item_list.clear();
            for (int i = 3; i < now_num; i++) {
                item_list.add(data.get(i));
            }
            //更新数据
            firstPageAdapter.notifyDataSetChanged();
        }

    }


    //延时更新

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public void setPosition(int position) {
        mposition = position;
        initData();
    }

}
