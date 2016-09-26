package com.example.administrator.onenews.news.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.onenews.R;

public class IndexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_activity);


        //开启一个线程，让闪屏页跳转到MainActivity页面，事件是2秒
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               Intent intent=new Intent(IndexActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },1000*2);
    }
}
