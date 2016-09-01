package com.bit.schoolcomment.activity;

import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.bit.schoolcomment.R;

public class MainActivity extends BaseActivity {

    private SlidingPaneLayout mSlidingPaneLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        if (toolbar != null) {
            toolbar.setTitle("test");
            toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        }
        setSupportActionBar(toolbar);

        mSlidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.main_slidingPaneLayout);
        mSlidingPaneLayout.setParallaxDistance(600);
//        mSlidingPaneLayout.setCoveredFadeColor(getResources().getColor(R.color.colorAccent));
//        mSlidingPaneLayout.setSliderFadeColor(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mSlidingPaneLayout.openPane();
        }
        return true;
    }
}
