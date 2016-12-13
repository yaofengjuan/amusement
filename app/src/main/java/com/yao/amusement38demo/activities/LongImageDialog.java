package com.yao.amusement38demo.activities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.yao.amusement38demo.R;
import com.yao.amusement38demo.largeimage.LongImageView;

/**
 * Created by YaoFengjuan on 2016/9/23.
 */
public class LongImageDialog extends Dialog {
    private String imageUrl;

    protected LongImageDialog(Context context) {
        super(context);
    }

    protected LongImageDialog(Context context, int theme) {
        super(context, theme);
    }


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.long_image_layout);
        LongImageView longImageView = (LongImageView) findViewById(R.id.long_img);
       // Glide.with(getContext()).load(imageUrl).
//longImageView.onImageLoadFinished();
        //longImageView.set

    }
}
