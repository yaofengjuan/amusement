package com.yao.amusement38demo.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.yao.amusement38demo.bean.SisterContentList;
import com.yao.amusement38demo.inter.ILoadInfoAProgressView;
import com.yao.amusement38demo.model.MainActivityModel;

import java.util.List;

/**
 * Created by YaoFengjuan on 2016/9/22.
 */
public class MainActivityPresenter {

    private final MainActivityModel model;
    private ILoadInfoAProgressView view;
    protected Handler mHandler = new Handler();

    public boolean isShowProgress = true;

    public MainActivityPresenter(ILoadInfoAProgressView view) {
        this.view = view;
        this.model = new MainActivityModel();
    }

    public void setShowProgress(boolean showProgress) {
        isShowProgress = showProgress;
    }

    public void loadInfo(final String title, final String type, final int page) {
        Log.i(this.getClass().getName(), "type:=" + type);
        if (isShowProgress)
            view.showBlueProgress();
        new Thread() {
            //在新线程中发送网络请求
            public void run() {
                final SisterContentList list = model.loadInfo(type, title, page);
                if (list != null) {
                    Log.d(this.getClass().getName(), "list:" + list);
                    //把返回内容通过handler对象更新到界面
                    mHandler.post(new Thread() {
                        public void run() {
                            view.setData(list);
                            if (isShowProgress)
                                view.dismissBlueProgress();
                        }
                    });
                }
            }
        }.start();
    }

    public void bindService(Context context, ServiceConnection connection) {
        Intent intent = new Intent(context, LoadPreviewImageService.class);
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }


}
