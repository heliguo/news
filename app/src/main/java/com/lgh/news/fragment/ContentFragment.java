package com.lgh.news.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lgh.news.R;
import com.lgh.news.base.BasePager;
import com.lgh.news.impl.GovAffairPager;
import com.lgh.news.impl.HomePager;
import com.lgh.news.impl.NewsPager;
import com.lgh.news.impl.SettingPager;
import com.lgh.news.impl.SmartServicePager;
import com.lgh.news.view.NoScrollViewPager;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ContentFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {

    private NoScrollViewPager viewPager;
    private ArrayList<BasePager> pagers;
    private RadioGroup radioGroup;
    private RadioButton homeRb;
    private RadioButton newsRb;
    private RadioButton serviceRb;
    private RadioButton govRb;
    private RadioButton settingRb;

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_content, null);
        viewPager = view.findViewById(R.id.fragment_content_vp);
        radioGroup = view.findViewById(R.id.content_rg);
        radioGroup.setOnCheckedChangeListener(this);
        homeRb = view.findViewById(R.id.content_rb_home);
        homeRb.toggle();
        newsRb = view.findViewById(R.id.content_rb_news);
        serviceRb = view.findViewById(R.id.content_rb_service);
        govRb = view.findViewById(R.id.content_rb_gov);
        settingRb = view.findViewById(R.id.content_rb_setting);
        return view;
    }

    @Override
    public void initDatas() {
        //初始化标签页面
        pagers = new ArrayList<>();
        pagers.add(new HomePager(mActivity));
        pagers.add(new NewsPager(mActivity));
        pagers.add(new SmartServicePager(mActivity));
        pagers.add(new GovAffairPager(mActivity));
        pagers.add(new SettingPager(mActivity));

        viewPager.setAdapter(new ContentAdapter());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pagers.get(position).initDatas();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pagers.get(0).initDatas();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.content_rb_home:
                viewPager.setCurrentItem(0, false);
                break;
            case R.id.content_rb_news:
                viewPager.setCurrentItem(1, false);
                break;
            case R.id.content_rb_service:
                viewPager.setCurrentItem(2, false);
                break;
            case R.id.content_rb_gov:
                viewPager.setCurrentItem(3, false);
                break;
            case R.id.content_rb_setting:
                viewPager.setCurrentItem(4, false);
                break;
            default:
                break;
        }
    }

    class ContentAdapter extends PagerAdapter {

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            BasePager pager = pagers.get(position);
            //初始化数据会导致下一界面提前加载，浪费资源，应做懒加载处理
//            pager.initDatas();
            container.addView(pager.rootView);
            return pager.rootView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return pagers.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }

}
