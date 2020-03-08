package com.lgh.news.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class NoScrollViewPager extends ViewPager {

    private int startX;
    private int startY;

    public NoScrollViewPager(@NonNull Context context) {
        this(context, null);
    }

    public NoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 事件处理 dispatchTouchEvent -> onInterceptTouchEvent -> onTouchEvent
     * 1、上下滑动，需要父控件拦截（父控件处理）
     * 2、左滑：最后一个页面拦截
     * 3、右滑：第一个页面拦截
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //请求父控件和所有祖先控件不要拦截事件
//        getParent().requestDisallowInterceptTouchEvent(true);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int endX = (int) ev.getX();
                int endY = (int) ev.getY();
                int dx = endX - startX;
                int dy = endY - startY;

                if (Math.abs(dx) > Math.abs(dy)) {//左右滑动
                    int currentItem = getCurrentItem();
                    if (dx > 0) {//右滑
                        if (currentItem == 0) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    } else {//左滑
                        //用adapter获得item总数
                        if (currentItem == getAdapter().getCount()) {
                            getParent().requestDisallowInterceptTouchEvent(true);
                        }
                    }
                }else {//上下滑动
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 本身的拦截事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //false 不拦截
        return false;
    }

    /**
     * 父类的onTouchEvent
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //true不消耗事件
        return true;
    }


}
