package com.bit.schoolcomment.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.fragment.CommentListFragment;
import com.bit.schoolcomment.fragment.GoodsListFragment;

public class UserHistoryActivity extends BaseActivity {

    @Override
    protected int getLayoutID() {
        return R.layout.activity_user_history;
    }

    @Override
    protected void initView() {
        initToolbar(R.id.user_history_toolbar, "test");

        TabLayout tabLayout = (TabLayout) findViewById(R.id.user_history_tabLayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.user_history_viewPager);
        if (tabLayout != null && viewPager != null) {
            viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLE = {"我的收藏", "我的评论", "我赞过的评论"};
        private final Fragment[] FRAGMENTS = {new GoodsListFragment(), new CommentListFragment(), new CommentListFragment()};

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLE[position];
        }

        @Override
        public Fragment getItem(int position) {
            return FRAGMENTS[position];
        }

        @Override
        public int getCount() {
            return FRAGMENTS.length;
        }
    }
}
