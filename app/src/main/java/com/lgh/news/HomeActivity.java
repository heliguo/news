package com.lgh.news;

import android.os.Bundle;
import android.view.Window;

import com.lgh.news.fragment.ContentFragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * 当一个activity要展示Fragment时，必须继承FragmentActivity；
 * Fragment 可以替换 Activity 中的某一布局
 */
public class HomeActivity extends FragmentActivity {

    private static final String TAG_CONTENT = "FRAGMENT_CONTENT";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        initFragment();
    }

    private void initFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();//开启一个事务
        ft.replace(R.id.fl_content, new ContentFragment());
        /**
         * 通过tag可以找到Fragment实例
         */
        ft.replace(R.id.fl_content, new ContentFragment(), TAG_CONTENT);
        ft.commit();//提交事务
        ContentFragment cf = (ContentFragment) fm.findFragmentByTag(TAG_CONTENT);
    }

    public Fragment getContentFragment() {
        FragmentManager fm = getSupportFragmentManager();
//        ContentFragment contentFragment = (ContentFragment) fm.findFragmentById(R.id.fl_content);
        ContentFragment cf = (ContentFragment) fm.findFragmentByTag(TAG_CONTENT);
        return cf;
    }
}
