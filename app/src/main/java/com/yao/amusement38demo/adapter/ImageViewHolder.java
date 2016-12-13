package com.yao.amusement38demo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.yao.amusement38demo.R;
import com.yao.amusement38demo.activities.DebugActivity;
import com.yao.amusement38demo.activities.LargeImageActivity;
import com.yao.amusement38demo.bean.SisterContentList;
import com.yao.amusement38demo.content.Constant;
import com.yao.amusement38demo.largeimage.LongImageView;
import com.yao.amusement38demo.util.Utils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by YaoFengjuan on 2016/12/5.
 */

public class ImageViewHolder {


    private Activity activity;
    ExecutorService executorService = Executors.newFixedThreadPool(3);
    private int width;

    public View getView(LayoutInflater layoutInflater, View convertView, SisterContentList.ShowapiResBodyEntity.PagebeanEntity.ContentlistEntity entity) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_sister_image, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        boolean isMatch = convertView.getTag() instanceof ViewHolder;
        if (!isMatch) {
            convertView = layoutInflater.inflate(R.layout.item_sister_image, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews(layoutInflater.getContext(), entity, (ViewHolder) convertView.getTag());
        return convertView;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
        width = Utils.getScreenWidth(activity);
    }

    public void initializeViews(final Context context, final SisterContentList.ShowapiResBodyEntity.PagebeanEntity.ContentlistEntity object, final ViewHolder holder) {
        Glide.with(context).load(object.profile_image).into(holder.head);
        holder.name.setText(object.name);
        holder.publishDate.setText(object.create_time);
        holder.descri.setText(object.text);
        holder.loveCount.setText(object.love);
        holder.unloveCount.setText(object.hate);
        Log.w(this.getClass().getName(), "Type: " + object.type + "    ContentlistEntity-->" + object);
        if (isGif(object.image0)) {
            ((ViewHolder) holder).fullScreen.setVisibility(View.GONE);
            Glide.with(activity).load(object.image0).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(((ViewHolder) holder).previewImage);

        } else {
            final ViewGroup.LayoutParams lp = ((ViewHolder) holder).previewImage.getLayoutParams();

            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        final Bitmap cropBitmap = Glide.with(activity)
                                .load(object.image0)
                                .asBitmap()
                                .centerCrop()
                                .into(width, lp.height)
                                .get();
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((ViewHolder) holder).previewImage.setImageBitmap(cropBitmap);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            });

            Glide.with(activity)
                    .load(object.image0)
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(final Bitmap bitmap,
                                                    GlideAnimation<? super Bitmap> glideAnimation) {
                            int width = bitmap.getWidth();
                            int height = bitmap.getHeight();
                            Log.i(this.getClass().getName(), "width:" + width + " height：" + height + " size:" + Utils.getBitmapSize(bitmap));
                            int heightS = Utils.getScreenWidth(context);
                            boolean isLargeImage = bitmap.getHeight() > heightS * 0.7;
                            if (isLargeImage) {
                                ((ViewHolder) holder).fullScreen.setVisibility(View.VISIBLE);//大于屏幕的0.8则视为大图
                            } else {
                                ((ViewHolder) holder).fullScreen.setVisibility(View.GONE);
                            }
                        }
                    });
        }

        ((ViewHolder) holder).fullScreen.setOnClickListener(new View.OnClickListener()

                                                            {
                                                                @Override
                                                                public void onClick(View v) {
                                                                    //看大图
                                                                    context.startActivity(new Intent(context, LargeImageActivity.class).putExtra("url", object.image0));
                                                                }
                                                            }

        );
    }

    public class ViewHolder {
        public ImageView previewImage;
        public RelativeLayout fullScreen;
        public ImageView head;
        private TextView name;
        private TextView publishDate;
        private TextView descri;
        private ImageView love;
        private TextView loveCount;
        private ImageView unlove;
        private TextView unloveCount;

        public ViewHolder(View view) {
            head = (ImageView) view.findViewById(R.id.head);
            name = (TextView) view.findViewById(R.id.name);
            publishDate = (TextView) view.findViewById(R.id.publish_date);
            descri = (TextView) view.findViewById(R.id.descri);
            love = (ImageView) view.findViewById(R.id.love);
            loveCount = (TextView) view.findViewById(R.id.love_count);
            unlove = (ImageView) view.findViewById(R.id.unlove);
            unloveCount = (TextView) view.findViewById(R.id.unlove_count);
            previewImage = (ImageView) view.findViewById(R.id.preview_image);
            fullScreen = (RelativeLayout) view.findViewById(R.id.full_screen);

        }

    }

    private boolean isGif(String url) {
        int index = url.lastIndexOf(".");
        String suffix = url.substring(index + 1, url.length());
        Log.i(this.getClass().getName(), suffix);
        if (suffix.equals("jpg")) {
            return false;
        } else if (suffix.equals("gif")) {
            return true;
        }
        return false;
    }


}
