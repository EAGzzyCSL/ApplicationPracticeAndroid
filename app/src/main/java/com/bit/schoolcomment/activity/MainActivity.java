package com.bit.schoolcomment.activity;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.bit.schoolcomment.dialog.SchoolDialog;
import com.bit.schoolcomment.event.LoginEvent;
import com.bit.schoolcomment.event.LogoutEvent;
import com.bit.schoolcomment.event.SchoolSelectEvent;
import com.bit.schoolcomment.fragment.goods.HotGoodsListFragment;
import com.bit.schoolcomment.fragment.shop.HotShopListFragment;
import com.bit.schoolcomment.util.DataUtil;
import com.bit.schoolcomment.util.DimensionUtil;
import com.bit.schoolcomment.util.PreferenceUtil;
import com.bit.schoolcomment.util.PullUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Toolbar mToolbar;
    private SlidingPaneLayout mSlidingPaneLayout;
    private TextView mNameTv;
    private TextView mSignTv;
    private SimpleDraweeView mAvatarIv;

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
            mNameTv = (TextView) navHeader.findViewById(R.id.main_user_name);
            mSignTv = (TextView) navHeader.findViewById(R.id.main_user_sign);
            mAvatarIv = (SimpleDraweeView) navHeader.findViewById(R.id.main_user_avatar);
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
        if (schoolBtn != null) {
            schoolBtn.setOnClickListener(this);
            if (!PreferenceUtil.contains("schoolId")) schoolBtn.callOnClick();
        }
    }

    private void updateNavHeader() {
        if (mNameTv == null || mSignTv == null) {
            return;
        }
        if (DataUtil.isLogin()) {
            mNameTv.setText(DataUtil.getUserModel().name);
            mSignTv.setText(getString(R.string.edit_sign));
            if (!DataUtil.getUserModel().avatar.equals("")) {
                mAvatarIv.setImageURI(DataUtil.getUserModel().avatar);
            }
        } else {
            mNameTv.setText(getString(R.string.please_login));
            mSignTv.setText(getString(R.string.login_discover_more));
            mAvatarIv.setImageURI("");
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (!DataUtil.isLogin()) {
            LoginActivity.launch(this);
            return true;
        }

        int itemId = item.getItemId();
        if (itemId == R.id.menu_main_logout) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(getString(R.string.hint)).
                    setMessage(getString(R.string.confirm_logout)).
                    setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PullUtil.getInstance().logout();
                        }
                    }).setNegativeButton(getString(R.string.cancel), null).
                    show();

        } else {
            Intent intent = new Intent(this, UserHistoryActivity.class);
            switch (item.getItemId()) {
                case R.id.menu_main_collection:
                    intent.putExtra("tab", UserHistoryActivity.COLLECTION);
                    break;

                case R.id.menu_main_comment:
                    intent.putExtra("tab", UserHistoryActivity.COMMENT);
                    break;
            }
            startActivity(intent);
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
                if (DataUtil.isLogin()) {
                    Intent intent = new Intent(this, UserInfoActivity.class);
                    startActivity(intent);
                } else {
                    LoginActivity.launch(this);
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
