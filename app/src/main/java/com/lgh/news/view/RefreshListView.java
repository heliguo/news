package com.lgh.news.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.lgh.news.R;

/**
 * 带下拉刷新头布局的listview
 */

public class RefreshListView extends ListView {

    private View mHeaderView;
    private int measuredHeight;
    private int startY = -1;

    private static final int STATE_PULL_TO_REFRESH = 0;//下拉刷新
    private static final int STATE_RELEASE_TO_REFRESH = 1;//松开刷新
    private static final int STATE_PULL_REFRESHING = 2;//正在刷新

    private int mCurrentState = STATE_PULL_TO_REFRESH;//默认状态

    public RefreshListView(Context context) {
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
    }

    private void initHeaderView() {
        mHeaderView = View.inflate(getContext(), R.layout.base_pager, null);
        addHeaderView(mHeaderView);
        mHeaderView.getHeight();//拿不到高度，绘制未完成
        mHeaderView.measure(0, 0);//只测量，没有其他用途
        measuredHeight = mHeaderView.getMeasuredHeight();
        mHeaderView.setPadding(0, -measuredHeight, 0, 0);//负值表示隐藏
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (startY == -1) {//没有获取到按下的事件，被viewpager消耗
                    startY = (int) ev.getY();

                }
                int endY = (int) ev.getY();
                int dy = endY - startY;
                //当前显示第一个item的位置
                int firstVisiblePosition = this.getFirstVisiblePosition();
                if (dy > 0 && firstVisiblePosition == 0) {
                    //下拉&&当前listview顶部
                    int padding = dy - measuredHeight;
                    if (padding > 0 && mCurrentState != STATE_RELEASE_TO_REFRESH) {
                        //切换松开刷新
                        mCurrentState = STATE_RELEASE_TO_REFRESH;
                        refreshState();
                    } else if (padding <= 0 && mCurrentState != STATE_PULL_TO_REFRESH) {
                        //切换下拉刷新状态
                        mCurrentState = STATE_PULL_TO_REFRESH;
                        refreshState();
                    }
                    mHeaderView.setPadding(0, padding, 0, 0);

                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;
                if (mCurrentState == STATE_RELEASE_TO_REFRESH) {
                    //正在刷新
                    mCurrentState = STATE_PULL_REFRESHING;
                    refreshState();
                } else if (mCurrentState == STATE_PULL_TO_REFRESH) {
                    //隐藏刷新控件
                    mHeaderView.setPadding(0, -measuredHeight, 0, 0);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 根据当前状态刷新界面
     */
    private void refreshState() {

    }
}
