package com.lgh.news.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.bytedance.sdk.account.common.utils.Md5Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * 本地缓存
 */
public class LocalCacheUtils {

    String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +
            LocalCacheUtils.class.getSimpleName() + File.separator;

    /**
     * 设置缓存
     *
     * @param bitmap 缓存内容
     * @param url    下载地址
     */
    public void setLocalCache(Bitmap bitmap, String url) {
        File file = new File(PATH);
        if (!file.exists() && !file.isFile()) {
            file.mkdirs();//创建文件夹
        }
        File cacheFile = new File(file, Md5Utils.hexDigest(url));//md5
        try {
            //将图片保存到文件
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(cacheFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getLocalCache(String url) {

        File cacheFile = new File(PATH, Md5Utils.hexDigest(url));
        if (cacheFile.exists()) {
            try {
                return BitmapFactory.decodeStream(new FileInputStream(cacheFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
