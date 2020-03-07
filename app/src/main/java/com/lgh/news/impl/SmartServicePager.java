package com.lgh.news.impl;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.lgh.news.base.BasePager;

/**
 * 首页
 */

public class SmartServicePager extends BasePager {

    private static final String TAG = "SmartServicePager";

    public SmartServicePager(Activity activity) {
        super(activity);
    }

    @Override
    public void initDatas() {
        Log.d(TAG, "initDatas: ");
        //
        TextView textView = new TextView(mActivity);
        textView.setText("智慧服务");
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        frameLayout.addView(textView);
        titleTv.setText("智慧服务");
    }
}
