package com.bit.schoolcomment.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;

import com.bit.schoolcomment.R;
import com.facebook.drawee.view.SimpleDraweeView;

public class ShopActivity extends BaseActivity
        implements AppBarLayout.OnOffsetChangedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        initView();
    }

    private void initView() {
        initToolbar(R.id.shop_toolbar, "test");
        SimpleDraweeView imageDv = (SimpleDraweeView) findViewById(R.id.shop_image);
        if (imageDv != null) imageDv.setImageURI("https://www.baidu.com/img/bd_logo1.png");

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.shop_appBarLayout);
        if (appBarLayout != null) appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
        float alpha = calc(Math.abs(verticalOffset));
        System.out.println(verticalOffset + " " + alpha);
        //findViewById(R.id.shop_cardView).setScaleX(alpha);
        findViewById(R.id.shop_cardView).setScaleY(alpha);
        //findViewById(R.id.shop_cardView).setAlpha(alpha);
        //findViewById(R.id.shop_cardView).setTranslationY(verticalOffset/8);
    }

    private float calc(int now) {
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.shop_appBarLayout);
        float yifen = 1 / (float) appBarLayout.getTotalScrollRange();
        return 1-now * yifen;
    }
}
