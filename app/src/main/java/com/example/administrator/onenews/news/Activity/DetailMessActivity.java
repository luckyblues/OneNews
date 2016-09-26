package com.example.administrator.onenews.news.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.administrator.onenews.R;

public class DetailMessActivity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mess);
        webView = (WebView) findViewById(R.id.webView);
        //打开页面时，自适应大小
        WebSettings webSettings = webView.getSettings();
        //自适应大小，可任意比例缩放
        webSettings.setUseWideViewPort(true);

        //获得上以页面传过来的url
        int position = getIntent().getIntExtra("position", 0);
        String url = getUrlByPosition(position);
        //显示url的页面
        webView.loadUrl(url);

        //设置Home键可用
        Toolbar toobar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toobar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //设置回退按钮的点击事件
        toobar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private String getUrlByPosition(int position) {
        String oldUrl = getIntent().getStringExtra("url");
        //字符串拼接
        StringBuilder url = new StringBuilder();

        //获取网页形式的路径中的数字
        String num = getMainNum(oldUrl);
        switch (position) {
            case 0:
                url.append("http://inews.ifeng.com/").append(num).append("/news.shtml");//要闻
                break;
            case 1:
                url.append("http://ifinance.ifeng.com/").append(num).append("/news.shtml");//财经
                break;
            case 2:
                url.append("http://isports.ifeng.com/").append(num).append("/news.shtml");//体育
                break;
            case 3:
                url.append("http://imil.ifeng.com/").append(num).append("/news.shtml");//军事
                break;
            case 4:
                url.append("http://itech.ifeng.com/").append(num).append("/news.shtml");//科技
                break;
            case 5:
                url.append("http://ihistory.ifeng.com/").append(num).append("/news.shtml");//历史
                break;
            case 6:
                url.append("http://iwemedia.ifeng.com/").append(num).append("/news.shtml");//娱乐
                break;
        }
        return url.toString();
    }

    //获取中间数字的方法
    private String getMainNum(String oldUrl) {
        //截取字符串
        oldUrl = oldUrl.substring(oldUrl.lastIndexOf("/") + 1, oldUrl.lastIndexOf("."));
        if (oldUrl.contains("_")) {
            oldUrl = oldUrl.substring(0, oldUrl.lastIndexOf("_"));
        }
        return oldUrl;
    }
}
