package com.lgh.news.utils;

/**
 * 下载监听接口
 */
public interface DownloadListener {

    void onProgress(int progress);

    void onSuccess();

    void onFailed();

    void onPaused();

    void onCanceled();

}
