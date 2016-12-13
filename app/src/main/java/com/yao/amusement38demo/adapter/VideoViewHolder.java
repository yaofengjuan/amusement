package com.yao.amusement38demo.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yao.amusement38demo.R;
import com.yao.amusement38demo.activities.LoadPreviewImageService;
import com.yao.amusement38demo.activities.VideoActivity;
import com.yao.amusement38demo.bean.SisterContentList;
import com.yao.amusement38demo.util.Utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by YaoFengjuan on 2016/12/5.
 */

public class VideoViewHolder {
    private ExecutorService executorService = Executors.newFixedThreadPool(3);

    private LoadPreviewImageService service;
    private Activity activity;

    public View getView(LayoutInflater layoutInflater, View convertView, SisterContentList.ShowapiResBodyEntity.PagebeanEntity.ContentlistEntity entity) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_sister_video, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        boolean isMatch = convertView.getTag() instanceof ViewHolder;
        if (!isMatch) {
            convertView = layoutInflater.inflate(R.layout.item_sister_video, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews(layoutInflater.getContext(), entity, (ViewHolder) convertView.getTag());
        return convertView;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setService(LoadPreviewImageService service) {
        this.service = service;
    }

    public void initializeViews(final Context context, final SisterContentList.ShowapiResBodyEntity.PagebeanEntity.ContentlistEntity object, final ViewHolder holder) {
        Glide.with(context).load(object.profile_image).into(holder.head);
        holder.name.setText(object.name);
        holder.publishDate.setText(object.create_time);
        holder.descri.setText(object.text);
        holder.loveCount.setText(object.love);
        holder.unloveCount.setText(object.hate);
        Log.w(this.getClass().getName(), "Type: " + object.type + "    ContentlistEntity-->" + object);
        ((ViewHolder) holder).previewImage.setImageBitmap(null);
        ((ViewHolder) holder).previewImage.setImageResource(R.color.ijk_transparent_dark);
        try {
            final File file = new File(Utils.getloadDirFile(activity), object.id + ".jpg");

            if (file.exists()) {
                ((ViewHolder) holder).previewImage.setImageURI(Uri.fromFile(file));
                // Glide.with(activity).load(Uri.fromFile(file)).into(((ViewHolder) holder).previewImage);
                // Log.i("REPEAT"," new view: " + object.text + " " +  object.video_uri);
            } else {
                final int width = ((ViewHolder) holder).previewImage.getWidth();
                final int height = ((ViewHolder) holder).previewImage.getHeight();
                //Log.i("REPEAT"," reuse view: " + object.text + " uri: " + object.video_uri);
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        createVideoThumbnail(object.video_uri, width, height, object.id);
                        try {
                            Thread.sleep(6000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((ViewHolder) holder).previewImage.setImageURI(Uri.fromFile(file));
                            }
                        });
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        ((ViewHolder) holder).symbolPlay
                .setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String title = object.text;
                                            VideoActivity.intentTo(context, object.video_uri, object.text);
                                        }
                                    }

                );
    }

    class ViewHolder {
        public ImageView symbolPlay;
        public ImageView previewImage;
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
            symbolPlay = (ImageView) view.findViewById(R.id.symbol_play);
            previewImage = (ImageView) view.findViewById(R.id.preview_image);

        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)

    private void createVideoThumbnail(String url, int width, int height, String desName) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        try {
            Utils.saveMyBitmap(activity, bitmap, desName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmap.recycle();
        bitmap = null;
    }
}
