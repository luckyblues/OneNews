package com.example.administrator.onenews.news.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import android.widget.TextView;

import com.example.administrator.onenews.R;
import com.example.administrator.onenews.news.Bean.BannerBean;
import com.example.administrator.onenews.news.Bean.Data;
import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.List;

/**
 * Created by Administrator on 2016/9/18.
 */
public class FirstPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int HEAD_BANNER = 0;
    private static final int HEAD_NOAMAL = 1;
    private static final int ITEM_LOADING = 2;
    private Context context;
    private List<Data> item_url;
    private BannerBean bean;


    public FirstPageAdapter(Context context, List<Data> item_url, BannerBean bean) {
        this.context = context;
        this.item_url = item_url;
        this.bean = bean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == HEAD_BANNER) {
            holder = new BannerViewHolder(LayoutInflater.from(context).inflate(R.layout.head_banner, parent, false));
        } else if (viewType == HEAD_NOAMAL) {
            holder = new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.normal_item, parent, false));
        } else if (viewType == ITEM_LOADING) {
            holder = new FooterViewHolder(LayoutInflater.from(context).inflate(R.layout.loading, parent, false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BannerViewHolder) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);//设置指示器
            bannerViewHolder.banner.setBannerTitle(bean.getTitle());//设置标题
            bannerViewHolder.banner.setImages(bean.getImg_url());
        } else if (holder instanceof ItemViewHolder) {
            //每个item布局
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.simpleDraweeView.setImageURI(item_url.get(position - 1).getThumbnail());
            itemViewHolder.textView.setText(item_url.get(position - 1).getTitle());

            //监听系统的单击事件
            if (myItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myItemClickListener.onClick(view, position);
                    }
                });
            }
        } else if (holder instanceof FooterViewHolder) {
            if (item_url.size() > 0) {
                //当有数据的时候让Progress出现
                ((FooterViewHolder) holder).progress_linear.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return item_url.size() + 1 + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEAD_BANNER;
        } else if (position + 1 == getItemCount()) {
            return ITEM_LOADING;
        } else {
            return HEAD_NOAMAL;
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView simpleDraweeView;
        TextView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.simpleDraweeView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        Banner banner;

        public BannerViewHolder(View itemView) {
            super(itemView);
            banner = (Banner) itemView.findViewById(R.id.banner);
        }
    }


    class FooterViewHolder extends RecyclerView.ViewHolder {
        LinearLayout progress_linear;

        public FooterViewHolder(View itemView) {
            super(itemView);
            progress_linear = (LinearLayout) itemView.findViewById(R.id.progress_linear);
        }

    }

    /**
     * RecycleView的单击事件
     */
    public interface MyItemClickListener {
        void onClick(View itemView, int position);
    }

    //本类中保存一个接口的引用
    MyItemClickListener myItemClickListener;

    public void setMyItemClickListener(MyItemClickListener myItemClickListener) {
        this.myItemClickListener = myItemClickListener;

    }
}
