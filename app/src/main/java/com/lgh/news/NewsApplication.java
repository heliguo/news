package com.lgh.news;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.mob.MobSDK;

import cn.jpush.android.api.JPushInterface;

public class NewsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //shareSDK
        MobSDK.init(this);
        //极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        //科大讯飞语音
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=");
    }
}
