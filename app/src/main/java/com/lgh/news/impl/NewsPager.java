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

public class NewsPager extends BasePager {

    private static final String TAG = "NewsPager";

    public NewsPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initDatas() {
        Log.d(TAG, "initDatas: ");
        //
        TextView textView = new TextView(mActivity);
        textView.setText("新闻中心");
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        frameLayout.addView(textView);
        titleTv.setText("新闻");
    }
}
