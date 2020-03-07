package com.lgh.news.base;


import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lgh.news.R;

/**
 * 5个标签页的基类
 * <p>
 * 共性：标题栏
 */
public class BasePager {

    public Activity mActivity;
    public TextView titleTv;
    public ImageView leftIv;
    public ImageView rightIv;
    public FrameLayout frameLayout;
    public View rootView;


    public BasePager(Activity activity) {
        this.mActivity = activity;
        rootView = initView();
    }

    public View initView() {
        View view = View.inflate(mActivity, R.layout.base_pager, null);
        titleTv = view.findViewById(R.id.pager_title);
        leftIv = view.findViewById(R.id.paget_left_iv);
        rightIv = view.findViewById(R.id.pager_right_iv);
        frameLayout = view.findViewById(R.id.pager_cotainer);
        return view;
    }

    public void initDatas() {

    }

}
