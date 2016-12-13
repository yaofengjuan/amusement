package com.yao.amusement38demo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yao.amusement38demo.R;
import com.yao.amusement38demo.bean.SisterContentList;

/**
 * Created by YaoFengjuan on 2016/12/5.
 */

public class TextViewHolder {

    public View getView(LayoutInflater layoutInflater, View convertView, SisterContentList.ShowapiResBodyEntity.PagebeanEntity.ContentlistEntity entity) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_sister_text, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        boolean isMatch = convertView.getTag() instanceof ViewHolder;
        if (!isMatch) {
            convertView = layoutInflater.inflate(R.layout.item_sister_text, null);
            convertView.setTag(new ViewHolder(convertView));
        }

        initializeViews(layoutInflater.getContext(), entity, (ViewHolder) convertView.getTag());
        return convertView;
    }

    public void initializeViews(Context context, SisterContentList.ShowapiResBodyEntity.PagebeanEntity.ContentlistEntity object, ViewHolder holder) {
        //TODO implement
        Glide.with(context).load(object.profile_image).into(holder.head);
        holder.name.setText(object.name);
        holder.publishDate.setText(object.create_time);
        holder.descri.setText(object.text);
        holder.loveCount.setText(object.love);
        holder.unloveCount.setText(object.hate);
        Log.w(this.getClass().getName(), "Type: " +  object.type  + "    ContentlistEntity-->" + object );
    }

    public class ViewHolder {
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
        }
    }
}
