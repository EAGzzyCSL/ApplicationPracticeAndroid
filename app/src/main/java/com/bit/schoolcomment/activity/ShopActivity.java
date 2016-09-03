package com.bit.schoolcomment.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.view.View;

import com.bit.schoolcomment.R;
import com.facebook.drawee.view.SimpleDraweeView;

public class ShopActivity extends BaseActivity
        implements AppBarLayout.OnOffsetChangedListener {

    private View mInfoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        initView();
    }

    private void initView() {
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.shop_appBarLayout);
        if (appBarLayout != null) appBarLayout.addOnOffsetChangedListener(this);
        initToolbar(R.id.shop_toolbar, "test");

        SimpleDraweeView imageDv = (SimpleDraweeView) findViewById(R.id.shop_image);
        if (imageDv != null) imageDv.setImageURI("https://www.baidu.com/img/bd_logo1.png");

        mInfoView = findViewById(R.id.shop_info);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        float fraction = 1 / (float) appBarLayout.getTotalScrollRange();
        float scale = 1 - Math.abs(verticalOffset) * fraction;
        mInfoView.setScaleY(scale);
    }
}
