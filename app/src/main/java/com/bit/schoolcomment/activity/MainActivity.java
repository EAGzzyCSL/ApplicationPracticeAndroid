package com.bit.schoolcomment.activity;

import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.fragment.GoodsListFragment;
import com.bit.schoolcomment.fragment.ShopListFragment;
import com.bit.schoolcomment.util.DimensionUtil;
import com.bit.schoolcomment.view.SchoolDialog;
import com.facebook.drawee.view.SimpleDraweeView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private SlidingPaneLayout mSlidingPaneLayout;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mSlidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.main_slidingPaneLayout);
        if (mSlidingPaneLayout != null) {
            int distance = DimensionUtil.Dp2Px(300);
            mSlidingPaneLayout.setParallaxDistance(distance);
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.main_navigationView);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
            View nav_header = navigationView.getHeaderView(0);
            SimpleDraweeView avatarDv = (SimpleDraweeView) nav_header.findViewById(R.id.main_user_avatar);
            Uri uri = Uri.parse("res://" + getPackageName() + "/" + R.drawable.ic_avatar_default);
            avatarDv.setImageURI(uri);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        if (toolbar != null) {
            toolbar.setTitle("test");
            toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        }
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tabLayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.main_viewPager);
        if (tabLayout != null && viewPager != null) {
            viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
            tabLayout.setupWithViewPager(viewPager);
        }

        View schoolBtn = findViewById(R.id.main_btn_school);
        if (schoolBtn != null) schoolBtn.setOnClickListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.menu_main_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mSlidingPaneLayout.openPane();
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_btn_school:
                SchoolDialog dialog = new SchoolDialog(this);
                dialog.show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mSlidingPaneLayout.isOpen()) mSlidingPaneLayout.closePane();
        else super.onBackPressed();
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLE = {getString(R.string.canteen), getString(R.string.dishes)};
        private final Fragment[] FRAGMENTS = {new ShopListFragment(), new GoodsListFragment()};

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
