package com.yao.amusement38demo.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yao.amusement38demo.R;
import com.yao.amusement38demo.bean.SisterContentList;
import com.yao.amusement38demo.util.MediaManager;

import java.util.HashMap;

/**
 * Created by YaoFengjuan on 2016/12/5
 * todo .
 */

public class VoiceViewHolder {

    private BaseAdapter adapter;

    public void setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
    }

    private HashMap<String, Integer> isPlayMap = new HashMap<>();

    public View getView(LayoutInflater layoutInflater, View convertView, SisterContentList.ShowapiResBodyEntity.PagebeanEntity.ContentlistEntity entity) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_sister_voice, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        boolean isMatch = convertView.getTag() instanceof ViewHolder;
        if (!isMatch) {
            convertView = layoutInflater.inflate(R.layout.item_sister_voice, null);
            convertView.setTag(new ViewHolder(convertView));
        }

        initializeViews(layoutInflater.getContext(), entity, (ViewHolder) convertView.getTag());
        return convertView;
    }


    public void initializeViews(final Context context, final SisterContentList.ShowapiResBodyEntity.PagebeanEntity.ContentlistEntity object, final ViewHolder holder) {
        Glide.with(context).load(object.profile_image).into(holder.head);
        holder.name.setText(object.name);
        holder.publishDate.setText(object.create_time);
        holder.descri.setText(object.text);
        holder.loveCount.setText(object.love);
        holder.unloveCount.setText(object.hate);
        Glide.with(context).load(object.image3).into(holder.previewImage);

        holder.symbolPlay.setImageResource(R.drawable.ic_play_arrow_grey600_36dp);

        Log.i(this.getClass().getName(), " holder.symbolPlay png name:" + holder.symbolPlay);
        if (isPlayMap.containsKey(object.id)) {
            holder.symbolPlay.setImageResource(isPlayMap.get(object.id));
        }

        holder.symbolPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.symbolPlay.getDrawable().getConstantState().equals(context.getResources().getDrawable(R.drawable.ic_pause_grey600_36dp).getConstantState())) {
                    //如果显示的是停止按钮图标
                    holder.symbolPlay.setImageResource(R.drawable.ic_play_arrow_grey600_36dp);
                    MediaManager.pause();
                    isPlayMap.put(object.id, R.drawable.ic_play_arrow_grey600_36dp);
                } else if (holder.symbolPlay.getDrawable().getConstantState().equals(context.getResources().getDrawable(R.drawable.ic_play_arrow_grey600_36dp).getConstantState())) {
                    //如果显示的是播放按钮图标
                    isPlayMap.clear();
                    adapter.notifyDataSetChanged();
                    holder.symbolPlay.setImageResource(R.drawable.ic_pause_grey600_36dp);
                    isPlayMap.put(object.id, R.drawable.ic_pause_grey600_36dp);
                    // 播放音频
                    Log.w(this.getClass().getName(), "path:" + object.voice_uri);
                    MediaManager.playSound(object.voice_uri,
                            new MediaPlayer.OnCompletionListener() {

                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    holder.symbolPlay.setImageResource(R.drawable.ic_play_arrow_grey600_36dp);
                                    isPlayMap.put(object.id, R.drawable.ic_play_arrow_grey600_36dp);
                                }
                            });
                }

            }
        });

    }

    public class ViewHolder {
        private ImageView previewImage;
        private ImageView symbolPlay;
        private ImageView head;
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
            previewImage = (ImageView) view.findViewById(R.id.preview_image);
            symbolPlay = (ImageView) view.findViewById(R.id.symbol_play);
            unloveCount = (TextView) view.findViewById(R.id.unlove_count);
        }
    }
}
