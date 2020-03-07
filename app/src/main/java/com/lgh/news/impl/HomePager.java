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

public class HomePager extends BasePager {

    public HomePager(Activity activity) {
        super(activity);
    }

    private static final String TAG = "HomePager";
    @Override
    public void initDatas() {
        Log.d(TAG, "initDatas: ");
        //
        TextView textView = new TextView(mActivity);
        textView.setText("首页");
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        frameLayout.addView(textView);
        titleTv.setText("智慧北京");
    }
}
