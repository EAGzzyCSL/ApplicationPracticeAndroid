package com.bit.schoolcomment.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.fragment.goods.GoodsCollectionListFragment;
import com.bit.schoolcomment.util.DataUtil;

public class UserHistoryActivity extends BaseActivity {

    public static final int COLLECTION = 0;
    public static final int COMMENT = 1;

    @Override
    protected boolean isEventBusOn() {
        return false;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_user_history;
    }

    @Override
    protected void initView() {
        initToolbar(R.id.user_history_toolbar, DataUtil.getUserModel().name);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.user_history_tabLayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.user_history_viewPager);
        if (tabLayout != null && viewPager != null) {
            viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
            tabLayout.setupWithViewPager(viewPager);
            int tab = getIntent().getIntExtra("tab", COLLECTION);
            viewPager.setCurrentItem(tab, true);
        }
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLE = {getString(R.string.my_collection), getString(R.string.my_comment)};
        private final Fragment[] FRAGMENTS = {new GoodsCollectionListFragment(), new GoodsCollectionListFragment()};

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
