package com.lgh.news.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.lgh.news.base.BasePager;

/**
 * 首页
 */

public class GovAffairPager extends BasePager {

    public GovAffairPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initDatas() {
        //
        TextView textView = new TextView(mActivity);
        textView.setText("政务");
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        frameLayout.addView(textView);
        titleTv.setText("政务");
    }
}
