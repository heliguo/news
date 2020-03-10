package com.lgh.news.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lgh.news.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 带下拉刷新头布局的listview
 */

public class RefreshListView extends ListView {

    private View mHeaderView;
    private View mFooterView;
    private int measuredHeight;
    private int mFooterViewMeasuredHeight;
    private int startY = -1;

    private static final int STATE_PULL_TO_REFRESH = 0;//下拉刷新
    private static final int STATE_RELEASE_TO_REFRESH = 1;//松开刷新
    private static final int STATE_REFRESHING = 2;//正在刷新

    private TextView tvState;
    private TextView tvTime;
    private ImageView ivArrow;
    private ProgressBar progressBar;

    private RotateAnimation up;
    private RotateAnimation down;

    private int mCurrentState = STATE_PULL_TO_REFRESH;//默认状态

    private boolean isLoadMore;//是否加载更多

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
//        tvState = mHeaderView.findViewById(R.id.tv_state);
//        tvTime = mHeaderView.findViewById(R.id.tv_state);
//        ivArrow = mHeaderView.findViewById(R.id.tv_state);
//        progressBar = mHeaderView.findViewById(R.id.tv_state);
        addHeaderView(mHeaderView);
        mHeaderView.getHeight();//拿不到高度，绘制未完成
        mHeaderView.measure(0, 0);//只测量，没有其他用途
        measuredHeight = mHeaderView.getMeasuredHeight();
        mHeaderView.setPadding(0, -measuredHeight, 0, 0);//负值表示隐藏
        initArrowAnim();
        setRefreshTime();
        initFooterView();
    }

    /**
     * 至于分页加载，第一页带第二页链接地址
     */
    private void initFooterView() {
        mFooterView = View.inflate(getContext(), R.layout.pull_to_refresh_footer, null);
        addFooterView(mFooterView);
        mFooterView.measure(0, 0);
        mFooterViewMeasuredHeight = mFooterView.getMeasuredHeight();
        //隐藏脚布局
        mFooterView.setPadding(0, -mFooterViewMeasuredHeight, 0, 0);
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {//空闲状态
                    //最后一个item的位置
                    int lastVisiblePosition = getLastVisiblePosition();
                    if (lastVisiblePosition == getCount() - 1 && !isLoadMore) {
                        //显示加载中
                        isLoadMore = true;
                        mFooterView.setPadding(0, 0, 0, 0);
                        setSelection(getCount() - 1);//显示最后一个item的位置

                        //加载更多数据
                        if (onRefreshListener != null) {
                            onRefreshListener.onLoadMore();
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
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

                //如果正在刷新就不处理
                if (mCurrentState == STATE_REFRESHING) {
                    break;
                }

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
                    mCurrentState = STATE_REFRESHING;

                    //完整显示刷新控件
                    mHeaderView.setPadding(0, 0, 0, 0);

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

        switch (mCurrentState) {
            case STATE_PULL_TO_REFRESH:
                tvState.setText("下拉刷新");
                progressBar.setVisibility(INVISIBLE);
                ivArrow.setVisibility(VISIBLE);
                ivArrow.setAnimation(down);
                break;
            case STATE_RELEASE_TO_REFRESH:
                tvState.setText("释放刷新");
                progressBar.setVisibility(INVISIBLE);
                ivArrow.setVisibility(VISIBLE);
                ivArrow.setAnimation(up);
                break;
            case STATE_REFRESHING:
                tvState.setText("正在刷新");
                progressBar.setVisibility(VISIBLE);
                ivArrow.clearAnimation();//必须先清理动画，才能设置visible
                ivArrow.setVisibility(INVISIBLE);

                if (onRefreshListener != null) {
                    onRefreshListener.onRefresh();
                }
                break;
        }
    }

    //初始化箭头动画
    private void initArrowAnim() {
        //向上
        up = new RotateAnimation(0, -180,
                Animation.RELATIVE_TO_SELF, 0.5f);
        up.setDuration(300);
        up.setFillAfter(true);//保持状态

        //向下
        down = new RotateAnimation(-180, 0,
                Animation.RELATIVE_TO_SELF, 0.5f);
        down.setDuration(300);
        down.setFillAfter(true);//保持状态
    }

    //刷新完成，恢复初始状态
    public void onRefreshComplete() {
        if (!isLoadMore) {
            mHeaderView.setPadding(0, 0, -measuredHeight, 0);
            mCurrentState = STATE_PULL_TO_REFRESH;
            tvState.setText("下拉刷新");
            progressBar.setVisibility(INVISIBLE);
            ivArrow.setVisibility(VISIBLE);
            setRefreshTime();
        } else {
            mFooterView.setPadding(0, -mFooterViewMeasuredHeight, 0, 0);
            isLoadMore = false;
        }
    }

    private OnRefreshListener onRefreshListener;

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public interface OnRefreshListener {
        void onRefresh();

        void onLoadMore();
    }

    private void setRefreshTime() {
        //HH:24小时制 hh:12小时制
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new Date());
        tvTime.setText(time);
    }

}
