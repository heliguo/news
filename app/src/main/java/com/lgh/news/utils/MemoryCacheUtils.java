package com.lgh.news.utils;

import android.graphics.Bitmap;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.collection.LruCache;

/**
 * 内存缓存
 * LruCache
 */
public class MemoryCacheUtils {

    //    private HashMap<String, SoftReference<Bitmap>> mHashMap = new HashMap<>();
    /**
     * LRU :least recently use 最近最少使用算法
     */
    private LruCache<String, Bitmap> mLruCache;

    public MemoryCacheUtils() {
        long maxSize = Runtime.getRuntime().maxMemory();
        mLruCache = new LruCache<String, Bitmap>((int) (maxSize / 8)) {

            /**
             * 单个对象占用大小
             * @param key
             * @param value
             * @return
             */
            @Override
            protected int sizeOf(@NonNull String key, @NonNull Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public void setMemoryCache(String url, Bitmap bitmap) {
//        SoftReference<Bitmap> softReference = new SoftReference<>(bitmap);
//        mHashMap.put(url, softReference);
        mLruCache.put(url, bitmap);
    }

    public Bitmap getMemoryCache(String url) {
//        SoftReference<Bitmap> softReference = mHashMap.get(url);
//        if (softReference != null) {
//            return softReference.get();
//        }
        return mLruCache.get(url);
    }
}
