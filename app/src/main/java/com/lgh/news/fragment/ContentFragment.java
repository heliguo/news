package com.lgh.news.fragment;

import android.view.View;
import android.view.ViewGroup;

import com.lgh.news.R;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ContentFragment extends BaseFragment {

    private ViewPager viewPager;

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_content, null);
        viewPager = view.findViewById(R.id.fragment_content_vp);
        return view;
    }

    @Override
    public void initDatas() {
        super.initDatas();
    }

    class ContentAdapter extends PagerAdapter {

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }

}
