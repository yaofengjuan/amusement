package com.yao.amusement38demo.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.yao.amusement38demo.R;
import com.yao.amusement38demo.largeimage.LongImageView;
import com.yao.amusement38demo.util.Utils;

import java.io.File;
import java.io.IOException;

public class LargeImageActivity extends AppCompatActivity {

    private static final String universal_name = "universal";//共用一个名称，通用一个路径，不会造成内存溢出，因为会覆盖原来的文件，始终只存在一个文件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        final LongImageView largeImage = (LongImageView) findViewById(R.id.largeImage);

        ;
        Glide.with(this)
                .load(getIntent().getStringExtra("url"))
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                          @Override
                          public void onResourceReady(Bitmap bitmap,
                                                      GlideAnimation<? super Bitmap> glideAnimation) {
                              int width = bitmap.getWidth();
                              int height = bitmap.getHeight();
                              Log.i(this.getClass().getName(), "width:" + width + " height：" + height + " size:" + Utils.getBitmapSize(bitmap));
                              try {
                                  File largeBitmapFile = Utils.saveMyBitmap(LargeImageActivity.this, bitmap, universal_name);
                                  if (largeBitmapFile != null) {
                                      Log.i(this.getClass().getName(), "large:" + largeBitmapFile.getAbsolutePath());
                                      largeImage.setImage(largeBitmapFile.getAbsolutePath());
                                  }
                              } catch (IOException e) {
                                  e.printStackTrace();
                              }

                          }

                      }

                );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
