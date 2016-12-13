package com.yao.amusement38demo.activities;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.yao.amusement38demo.ijkplayer.widget.media.AndroidMediaController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by YaoFengjuan on 2016/12/7.
 */

public class LoadPreviewImageService extends Service {

    ExecutorService executorService = Executors.newFixedThreadPool(4);
    private MyServiceBinder myBinder = new MyServiceBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public void load(final Context mContext, final String _urlStr, final String desName) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    loadPreviewImage(mContext, _urlStr, desName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public File getloadDirFile(Context mContext) throws IOException {
        File dir = null;
//        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
//            Log.d(mContext.getClass().getName(), "sdcard doesn't exit, save mp4 to app dir");
//
        dir = mContext.getFilesDir();
//        } else {
//        dir = new File(Environment.getExternalStorageDirectory()
//                .getPath() + "/Asst/cache/");
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//        }
        return dir;
    }


    /**
     * 下载MP4的缩略图（下载3*1024个字节）
     *
     * @param _urlStr
     * @param desName 独一份
     */
    public void loadPreviewImage(Context mContext, String _urlStr, String desName) throws IOException {
        Log.i(this.getClass().getName(), "进入下载");
        File loadFile;
//        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
//            Log.d(mContext.getClass().getName(), "sdcard doesn't exit, save mp4 to app dir");
//
        loadFile = new File(mContext.getFilesDir(), desName + ".mp4");
//        } else {
        // File dir = new File(Environment.getExternalStorageDirectory()
        // .getPath() + "/Asst/cache/");
        //  if (!dir.exists()) {
        //    dir.mkdirs();
        // }
        //  loadFile = new File(dir, desName + ".mp4");
//        loadFile.createNewFile();
//        }
        try {
            // 构造URL
            URL url = new URL(_urlStr);
            // 打开连接
            URLConnection con = url.openConnection();
            //获得文件的长度
            int contentLength = con.getContentLength();
            System.out.println("长度 :" + contentLength);
            // 输入流
            InputStream is = con.getInputStream();
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            OutputStream os = new FileOutputStream(loadFile);
            int i = 0;
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                Log.i(this.getClass().getName(), "i--->" + i);
                if (i <= 10) {
                    os.write(bs, 0, len);
                } else {
                    break;
                }
                i++;
            }
            // 完毕，关闭所有链接
            os.close();
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public class MyServiceBinder extends Binder {
        public LoadPreviewImageService getService() {
            return LoadPreviewImageService.this;
        }
    }



}
