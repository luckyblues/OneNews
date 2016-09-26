package com.example.administrator.onenews.news.Bean;

/**
 * Created by Administrator on 2016/9/21.
 */
public class Data {
    private String thumbnail;
    private String title;
    private String url;

    /**
     * 无参的构造方法
     * @return
     */
    public Data(){

    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
