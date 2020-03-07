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

public class SettingPager extends BasePager {

    private static final String TAG = "SettingPager";

    public SettingPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initDatas() {
        Log.d(TAG, "initDatas: ");
        //
        TextView textView = new TextView(mActivity);
        textView.setText("设置");
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        frameLayout.addView(textView);
        titleTv.setText("设置");
    }
}
