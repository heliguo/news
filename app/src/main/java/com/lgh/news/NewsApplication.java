package com.lgh.news;

import android.app.Application;

import com.mob.MobSDK;

public class NewsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MobSDK.init(this);
    }
}
