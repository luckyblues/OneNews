package com.example.administrator.onenews.news.Bean;

/**
 * Created by Administrator on 2016/9/22.
 */
public class BannerBean {
    private String[] img_url;
    private String[] title;
    private String[] toUrl;
    public  BannerBean(){

    }

    public String[] getImg_url() {
        return img_url;
    }

    public void setImg_url(String[] img_url) {
        this.img_url = img_url;
    }

    public String[] getTitle() {
        return title;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }

    public String[] getToUrl() {
        return toUrl;
    }

    public void setToUrl(String[] toUrl) {
        this.toUrl = toUrl;
    }
}
