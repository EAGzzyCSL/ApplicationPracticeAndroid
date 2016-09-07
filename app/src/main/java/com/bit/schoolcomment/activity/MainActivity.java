package com.bit.schoolcomment.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.event.LoginEvent;
import com.bit.schoolcomment.event.LogoutEvent;
import com.bit.schoolcomment.event.school.SchoolSelectEvent;
import com.bit.schoolcomment.fragment.goods.HotGoodsListFragment;
import com.bit.schoolcomment.fragment.shop.HotShopListFragment;
import com.bit.schoolcomment.util.DataUtil;
import com.bit.schoolcomment.util.DimensionUtil;
import com.bit.schoolcomment.util.PreferenceUtil;
import com.bit.schoolcomment.util.PullUtil;
import com.bit.schoolcomment.view.SchoolDialog;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Toolbar mToolbar;
    private SlidingPaneLayout mSlidingPaneLayout;
    private SimpleDraweeView mAvatarDv;
    private TextView mNameTv;
    private TextView mSignTv;

    @Override
    protected boolean isEventBusOn() {
        return true;
    }

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
            View navHeader = navigationView.getHeaderView(0);
            navHeader.findViewById(R.id.main_user_wrapper).setOnClickListener(this);
            mAvatarDv = (SimpleDraweeView) navHeader.findViewById(R.id.main_user_avatar);
            mNameTv = (TextView) navHeader.findViewById(R.id.main_user_name);
            mSignTv = (TextView) navHeader.findViewById(R.id.main_user_sign);
            updateNavHeader();
        }

        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        if (mToolbar != null) {
            mToolbar.setTitle(DataUtil.getSchoolModel().name);
            mToolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        }
        setSupportActionBar(mToolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tabLayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.main_viewPager);
        if (tabLayout != null && viewPager != null) {
            viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
            tabLayout.setupWithViewPager(viewPager);
        }

        View schoolBtn = findViewById(R.id.main_btn_school);
        if (schoolBtn != null) schoolBtn.setOnClickListener(this);
    }

    private void updateNavHeader() {
        Uri uri = Uri.parse("res://" + getPackageName() + "/" + R.drawable.ic_avatar_default);
        mAvatarDv.setImageURI(uri);
        if (DataUtil.isLogin()) {
            mNameTv.setText(DataUtil.getUserModel().name);
        } else {
            mNameTv.setText(getString(R.string.please_login));
            mSignTv.setText(getString(R.string.login_discover_more));
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_logout:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle(getString(R.string.hint));
                dialog.setMessage(getString(R.string.confirm_logout));
                dialog.setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PullUtil.getInstance().logout();
                    }
                });
                dialog.setNegativeButton(getString(R.string.cancel), null);
                dialog.show();
                break;
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mSlidingPaneLayout.openPane();
                break;

            case R.id.menu_main_search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;
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

            case R.id.main_user_wrapper:
                if (!DataUtil.isLogin()) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mSlidingPaneLayout.isOpen()) mSlidingPaneLayout.closePane();
        else super.onBackPressed();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void handleLogin(LoginEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
        DataUtil.setUserModel(event.userModel);
        updateNavHeader();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleLogout(LogoutEvent event) {
        DataUtil.clearUserModel();
        PreferenceUtil.remove("userId");
        PreferenceUtil.remove("token");
        updateNavHeader();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleSchoolSelect(SchoolSelectEvent event) {
        DataUtil.setSchoolModel(event.schoolModel);
        mToolbar.setTitle(event.schoolModel.name);
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLE = {getString(R.string.canteen), getString(R.string.dishes)};
        private final Fragment[] FRAGMENTS = {new HotShopListFragment(), new HotGoodsListFragment()};

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
