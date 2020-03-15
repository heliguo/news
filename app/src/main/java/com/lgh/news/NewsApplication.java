package com.lgh.news;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.mob.MobSDK;
import com.umeng.commonsdk.UMConfigure;

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
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5e6dbce0");

        /*
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:【友盟+】 AppKey(第一步从官网获取到的)
         * 参数3:【友盟+】 Channel（多渠道打包时用的到）
         * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、
         * UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数5:Push推送业务的secret,需要集成Push功能时必须传入Push的secret，否则传空。
         */
        UMConfigure.init(this, "", null,
                UMConfigure.DEVICE_TYPE_PHONE, null);
        // 打开统计SDK调试模式（上线时记得关闭）
        UMConfigure.setLogEnabled(true);
    }
}
