package com.yao.amusement38demo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by YaoFengjuan on 2016/9/23.
 */
public class Utils {

    /**
     * 获取屏幕对象
     *
     * @param context
     * @return
     */
    private static DisplayMetrics getMetricsFacade(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        return dm;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        return getMetricsFacade(context).widthPixels;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (options > 0 && baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        if (image != null) {
            image.recycle();
            image = null;
            System.gc();
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 通过降低图片的质量来压缩图片
     *
     * @param bitmap  要压缩的图片位图对象
     * @param maxSize 压缩后图片大小的最大值,单位KB
     * @return 压缩后的图片位图对象
     */
    public static Bitmap compressByQuality(Bitmap bitmap, int maxSize) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 100;
        bitmap.compress(CompressFormat.JPEG, quality, baos);
        System.out.println("图片压缩前大小：" + baos.toByteArray().length + "byte");
        boolean isCompressed = false;
        while (baos.toByteArray().length / 1024 > maxSize) {
            quality -= 10;
            baos.reset();
            bitmap.compress(CompressFormat.JPEG, quality, baos);
            System.out.println("质量压缩到原来的" + quality + "%时大小为："
                    + baos.toByteArray().length + "byte");
            isCompressed = true;
        }
        System.out.println("图片压缩后大小：" + baos.toByteArray().length + "byte");
        if (isCompressed) {
            Bitmap compressedBitmap = BitmapFactory.decodeByteArray(
                    baos.toByteArray(), 0, baos.toByteArray().length);
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
                System.gc();
                bitmap = null;
            }
            return compressedBitmap;
        } else {
            return bitmap;
        }
    }

    /**
     * Compress by quality,  and generate image to the path specified
     *
     * @param image
     * @param outPath
     * @param maxSize target will be compressed to be smaller than this size.(kb)
     * @throws IOException
     */
    public void compressAndGenImage(Bitmap image, String outPath, int maxSize) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // scale
        int options = 100;
        // Store the bitmap into output stream(no compress)
        image.compress(CompressFormat.JPEG, options, os);
        // Compress by loop
        while (os.toByteArray().length / 1024 > maxSize) {
            // Clean up os
            os.reset();
            // interval 10
            options -= 10;
            image.compress(CompressFormat.JPEG, options, os);
        }

        // Generate compressed image file
        FileOutputStream fos = new FileOutputStream(outPath);
        fos.write(os.toByteArray());
        fos.flush();
        fos.close();
    }

    public static Bitmap get(Context context, String srcPath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //BitmapFactory.decodeFile(srcPath, options);
        getBitmap(srcPath, options);
        int targetDensity = context.getResources().getDisplayMetrics().densityDpi;
        Log.i("ChatUtils:", "targetDensity:" + targetDensity);

        DisplayMetrics dm = getMetricsFacade(context);

        int x = dm.widthPixels;
        int y = dm.heightPixels;
        options.inSampleSize = calculateInSampleSize(options, x, y);

        double xSScale = ((double) options.outWidth) / ((double) x);
        double ySScale = ((double) options.outHeight) / ((double) y);

        double startScale = xSScale > ySScale ? xSScale : ySScale;

        options.inScaled = true;
        options.inDensity = (int) (targetDensity * startScale);
        options.inTargetDensity = targetDensity;
        options.inJustDecodeBounds = false;
        Log.i("ChatUtils:", "options.inDensity:" + options.inDensity);
        Log.i("ChatUtils:", "options.inTargetDensity:" + options.inTargetDensity);
        Log.i("ChatUtils:", "options.inSampleSize:" + options.inSampleSize);
        Bitmap bitmap = getBitmap(srcPath, options);
        return bitmap;
    }

    public static Bitmap getBitmap(String srcPath, BitmapFactory.Options options) {
//        BitmapFactory.Options bfOptions = new BitmapFactory.Options();
//        bfOptions.inDither = false;
//        bfOptions.inPurgeable = true;
//        bfOptions.inTempStorage = new byte[32 * 1024];
//        bfOptions.inJustDecodeBounds = true;
        File file = new File(srcPath);
        Log.i("ChatUtils:", file == null ? "file is null " : "file is not null");
        FileInputStream fs = null;
        Bitmap bmp = null;
        try {
            fs = new FileInputStream(file);
            Log.i("ChatUtils:", fs == null ? "fs is null " : "fs is not null");
            if (fs != null) {
                bmp = BitmapFactory.decodeFileDescriptor(fs.getFD(), new Rect(), options);
            }
            Log.i("ChatUtils:", bmp == null ? "bmp is null " : "bmp is not null");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static File saveMyBitmap(Context mContext, Bitmap bitmap,
                                    String desName) throws IOException {
        FileOutputStream fOut = null;
        File f = null;
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            Log.d(mContext.getClass().getName(), "sdcard doesn't exit, save png to app dir");
            fOut = mContext.openFileOutput(desName + ".jpg",
                    Context.MODE_PRIVATE);
            f = new File(mContext.getFilesDir(), desName + ".jpg");
        } else {
            File dir = new File(Environment.getExternalStorageDirectory()
                    .getPath() + "/Asst/cache/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            f = new File(Environment.getExternalStorageDirectory()
                    .getPath() + "/Asst/cache/" + desName + ".jpg");
            f.createNewFile();
            fOut = new FileOutputStream(f);
        }
        bitmap.compress(CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
            fOut.close();
            return f;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取文件后缀名
     *
     * @param fileName
     * @return 文件后缀名
     */
    public static String getFileType(String fileName) {
        if (fileName != null) {
            int typeIndex = fileName.lastIndexOf(".");
            if (typeIndex != -1) {
                String fileType = fileName.substring(typeIndex + 1).toLowerCase();
                return fileType;
            }
        }
        return "";
    }

    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)   //API 19

        {
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1)  //API 12

        {
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
    }

    public static File getloadDirFile(Context mContext) throws IOException {
        File dir = null;
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            Log.d(mContext.getClass().getName(), "sdcard doesn't exit, save mp4 to app dir");

            dir = mContext.getFilesDir();
        } else {
            dir = new File(Environment.getExternalStorageDirectory()
                    .getPath() + "/Asst/cache/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
        return dir;
    }
}
