package com.lgh.news;

import android.app.Application;

import com.mob.MobSDK;

import cn.jpush.android.api.JPushInterface;

public class NewsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MobSDK.init(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
