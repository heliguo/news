package com.lgh.news.utils;


import android.widget.ImageView;

/**
 * 三级缓存工具类
 */
public class BitmapUtils {

    private NetCacheUtils netCacheUtils;
    private LocalCacheUtils localCacheUtils;
    private MemoryCacheUtils memoryCacheUtils;

    public BitmapUtils() {
        memoryCacheUtils = new MemoryCacheUtils();
        localCacheUtils = new LocalCacheUtils();
        netCacheUtils = new NetCacheUtils(localCacheUtils,memoryCacheUtils);

    }

    public void display(ImageView imageView, String url) {
        //内存
        if (memoryCacheUtils.getMemoryCache(url) != null) {
            imageView.setImageBitmap(memoryCacheUtils.getMemoryCache(url));
            return;
        }
        //本地
        if (localCacheUtils.getLocalCache(url) != null) {
            imageView.setImageBitmap(localCacheUtils.getLocalCache(url));
            return;
        }
        //网络
        netCacheUtils.getBitmapFromNet(imageView, url);

    }
}
