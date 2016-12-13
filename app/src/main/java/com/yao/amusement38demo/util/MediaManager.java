package com.yao.amusement38demo.util;


import java.io.IOException;
import java.util.logging.Logger;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.util.Log;

public class MediaManager {


    public static MediaPlayer mPlayer;

    private static boolean isPause;

    public static void playSound(String filePathString,
                                 OnCompletionListener onCompletionListener) {
        
        if (mPlayer == null) {
            Log.d("MediaManager", "new MediaPlayer");
            mPlayer = new MediaPlayer();
            //保险起见，设置报错监听
            mPlayer.setOnErrorListener(new OnErrorListener() {

                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    mPlayer.reset();
                    return false;
                }
            });
        } else {
            mPlayer.reset();//就回复
        }

        try {
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnCompletionListener(onCompletionListener);
            mPlayer.setDataSource(filePathString);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //停止函数
    public static void pause() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
            isPause = true;
        }
    }

    //继续
    public static void resume() {
        if (mPlayer != null && isPause) {
            mPlayer.start();
            isPause = false;
        }
    }


    public static void release() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
}
