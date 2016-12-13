package com.yao.amusement38demo.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ImageView;

import com.yao.amusement38demo.R;
import com.yao.amusement38demo.activities.LoadPreviewImageService;
import com.yao.amusement38demo.bean.SisterContentList;
import com.yao.amusement38demo.content.Constant;

public class ItemSisterAdapter extends BaseAdapter {

    private List<SisterContentList.ShowapiResBodyEntity.PagebeanEntity.ContentlistEntity> objects = new ArrayList<SisterContentList.ShowapiResBodyEntity.PagebeanEntity.ContentlistEntity>();

    private Activity context;
    private LayoutInflater layoutInflater;

    private ImageViewHolder imageViewHolder = new ImageViewHolder();
    private VideoViewHolder videoViewHolder = new VideoViewHolder();
    private TextViewHolder textViewHolder = new TextViewHolder();
    private VoiceViewHolder voiceViewHolder = new VoiceViewHolder();


    public ItemSisterAdapter(Activity context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        imageViewHolder.setActivity(context);
        videoViewHolder.setActivity(context);
        voiceViewHolder.setAdapter(this);
    }

    public void setService(LoadPreviewImageService service) {
        videoViewHolder.setService(service);
    }

    @Override
    public int getCount() {
        return objects.size();
    }


    @Override
    public SisterContentList.ShowapiResBodyEntity.PagebeanEntity.ContentlistEntity getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String type = getItem(position).type;
        if (type.equals(Constant.SISTER_DATA_TYPE_IMAGE)) {
            return imageViewHolder.getView(layoutInflater, convertView, getItem(position));
        } else if (type.equals(Constant.SISTER_DATA_TYPE_VIDEO)) {
            return videoViewHolder.getView(layoutInflater, convertView, getItem(position));
        } else if (type.equals(Constant.SISTER_DATA_TYPE_TEXT)) {
            return textViewHolder.getView(layoutInflater, convertView, getItem(position));
        } else if (type.equals(Constant.SISTER_DATA_TYPE_VOICE)) {
            return voiceViewHolder.getView(layoutInflater, convertView, getItem(position));
        } else {
            return textViewHolder.getView(layoutInflater, convertView, getItem(position));
        }
    }

    public void refresh(SisterContentList data) {
        if (data != null) {
            objects.clear();
            objects.addAll(data.showapi_res_body.pagebean.contentlist);
            notifyDataSetChanged();
        }
    }

    public void addData(SisterContentList data) {
        if (data != null) {
            objects.addAll(data.showapi_res_body.pagebean.contentlist);
            notifyDataSetChanged();
        }
    }

    protected class ViewHolder {
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
