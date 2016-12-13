package com.yao.amusement38demo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.yao.amusement38demo.R;

public class Debug2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug2);
        WebView largeImage= (WebView) findViewById(R.id.activity_debug2);
        largeImage.loadUrl("http://wimg.spriteapp.cn/x/640x400/ugc/2016/09/23/57e45fc869587.jpg");
    }
}
