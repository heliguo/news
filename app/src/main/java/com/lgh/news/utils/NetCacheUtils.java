package com.lgh.news.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 网络缓存
 */
public class NetCacheUtils {

    private String url;
    private LocalCacheUtils localCacheUtils;
    private MemoryCacheUtils memoryCacheUtils;

    public NetCacheUtils(LocalCacheUtils localCacheUtils, MemoryCacheUtils memoryCacheUtils) {
        this.localCacheUtils = localCacheUtils;
        this.memoryCacheUtils = memoryCacheUtils;
    }

    public void getBitmapFromNet(ImageView imageView, String url) {
        //异步下载图片
        imageView.setTag(url);
        new BitmapTask().execute(imageView, url);
        this.url = url;
    }

    /**
     * 第一个泛型：传入的参数，用于后台操作的参数
     * 第二个泛型：用于进度显示
     * 第三个泛型：执行完成后的返回结果
     */
    class BitmapTask extends AsyncTask<Object, Integer, Bitmap> {
        private ImageView imageView;

        /**
         * 进度条显示
         */
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        /**
         * @param params 传入的参数，可为多个不同类型，也可不传
         * @return 返回结果
         */
        @Override
        protected Bitmap doInBackground(Object... params) {
            imageView = (ImageView) params[0];
            String url = ((String) params[1]);
            Bitmap bitmap = download(url);

            return bitmap;
        }

        /**
         * 处理返回结果
         *
         * @param result
         */
        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                if (((String) imageView.getTag()).equals(url))
                    imageView.setImageBitmap(result);
            }
            localCacheUtils.setLocalCache(result, url);
            memoryCacheUtils.setMemoryCache(url, result);
        }

        /**
         * 更新进度
         *
         * @param values
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    private Bitmap download(String url) {
        HttpURLConnection connection = null;
        InputStream in = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(6000);
            connection.setReadTimeout(6000);
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                in = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }
}
