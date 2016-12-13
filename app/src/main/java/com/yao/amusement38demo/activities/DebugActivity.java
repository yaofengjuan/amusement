package com.yao.amusement38demo.activities;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.show.api.ShowApiRequest;
import com.yao.amusement38demo.R;
import com.yao.amusement38demo.content.Constant;
import com.yao.amusement38demo.largeimage.LongImageView;
import com.yao.amusement38demo.util.Utils;

import java.io.File;
import java.io.IOException;

public class DebugActivity extends AppCompatActivity {

    private LongImageView largeImage;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug);
        largeImage = (LongImageView) findViewById(R.id.largeImage);
        image = (ImageView) findViewById(R.id.image);
        String url = "http://wimg.spriteapp.cn/x/640x400/ugc/2016/09/23/57e45fc869587.jpg";
        int last = url.substring(0, url.lastIndexOf(".") - 1).lastIndexOf("/");
        final String name = url.substring(0, url.lastIndexOf(".") - 1).substring(last + 1);
        Log.i(this.getClass().getName(), "name:" + name);
        //Glide.with(this).load("").into(GlideDrawable)
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
                        if (height > Utils.dip2px(DebugActivity.this, 1000)) {
                            Bitmap largeBitmap = Utils.compressByQuality(bitmap, 1024);
                            Log.i(this.getClass().getName(), " compress width:" + bitmap.getWidth() + " compress  height：" + bitmap.getHeight() + "size:" + Utils.getBitmapSize(largeBitmap));
                            try {
                                File largeBitmapFile = Utils.saveMyBitmap(DebugActivity.this, largeBitmap, name);
                                if (largeBitmapFile != null) {
                                    Log.i(this.getClass().getName(), "large:" + largeBitmapFile.getAbsolutePath());
                                    largeImage.setImage(largeBitmapFile.getAbsolutePath());
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            ViewGroup.LayoutParams lp = image.getLayoutParams();
                            lp.width = Utils.getScreenWidth(DebugActivity.this);
                            float tempHeight = height * ((float) lp.width / width);
                            lp.height = (int) tempHeight;
                            image.setLayoutParams(lp);
                            image.setImageBitmap(bitmap);
                        }

                    }
                });
        BitmapTypeRequest<String> largeImageBitmap = Glide.with(this).load("http://wimg.spriteapp.cn/x/640x400/ugc/2016/09/23/57e45fc869587.jpg").asBitmap();
        sister();
    }

    private void sister() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String res = new ShowApiRequest("http://route.showapi.com/255-1", Constant.appid, Constant.secret)
                        .addTextPara("type", "")
                        .addTextPara("title", "祖国母亲的生日")
                        .addTextPara("page", "")
                        .post();
                Log.i(this.getClass().getName(), "sister:" + res);
            }
        }).start();

    }


}
