package com.lgh.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class GuideActivity extends BaseActivity {

    private LinearLayout linearLayout;
    private Button button;
    private ViewPager viewPager;
    private ArrayList<ImageView> imageViews = new ArrayList<>();
    private int[] mIamgedIds = {R.drawable.bg_android, R.drawable.bg_ios, R.drawable.bg_js};

    private float pointDis;

    private ImageView redImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏在setContentView之前
        setContentView(R.layout.activity_guide);
        viewPager = findViewById(R.id.guide_viewpager);
        linearLayout = findViewById(R.id.guide_ll_container);
        redImageView = findViewById(R.id.guide_point_choose);
        button = findViewById(R.id.guide_btn_start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this, HomeActivity.class));
                //使用sharedPreferences 记录引导页访问记录，只加载一次
                finish();
            }
        });
        initData();
    }

    private void initData() {

        for (int i = 0; i < mIamgedIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(mIamgedIds[i]);
            imageViews.add(imageView);
            ImageView point = new ImageView(this);
            point.setImageResource(R.drawable.shape_guide_point_normal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i > 0) {
                params.leftMargin = 10;
            }
            point.setLayoutParams(params);
            linearLayout.addView(point);
        }
        viewPager.setAdapter(new GuideAdapter());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                float distance = pointDis * positionOffset + pointDis * position;//加入当前位置信息
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) redImageView.
                        getLayoutParams();
                layoutParams.leftMargin = (int) distance;
                redImageView.setLayoutParams(layoutParams);
            }

            @Override
            public void onPageSelected(int position) {
                if (position == imageViews.size() - 1) {
                    button.setVisibility(View.VISIBLE);
                } else {
                    button.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /**
         * linearLayout.getChildAt(1).getLeft()方法在 onLayout() 方法拿到，
         * 而 onLayout() 等三个方法 在onCreate()方法后才能执行
         *
         * linearLayout.getChildAt(1).getLeft()=0；
         */
//        pointDis = linearLayout.getChildAt(1).getLeft() -
//                linearLayout.getChildAt(0).getLeft();

        /**
         * 视图树观察者
         * 监听全局layout
         */
        redImageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            //layout方法布局完成
            @Override
            public void onGlobalLayout() {
                pointDis = linearLayout.getChildAt(1).getLeft() -
                        linearLayout.getChildAt(0).getLeft();
                //移除观察者
                redImageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

    }

    class GuideAdapter extends PagerAdapter {

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(imageViews.get(position));
            return imageViews.get(position);
        }

        @Override
        public int getCount() {
            return mIamgedIds.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
