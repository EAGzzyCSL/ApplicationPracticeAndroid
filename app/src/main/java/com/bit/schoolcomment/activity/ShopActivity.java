package com.bit.schoolcomment.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.model.ShopModel;
import com.facebook.drawee.view.SimpleDraweeView;

public class ShopActivity extends BaseActivity
        implements AppBarLayout.OnOffsetChangedListener {

    public static final String EXTRA_shopId = "shopId";
    public static final String EXTRA_model = "model";

    private View mInfoView;

    @Override
    protected boolean isEventBusOn() {
        return false;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_shop;
    }

    @Override
    protected void initView() {
        ShopModel model = getIntent().getParcelableExtra(EXTRA_model);
        assert model != null;

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.shop_appBarLayout);
        if (appBarLayout != null) appBarLayout.addOnOffsetChangedListener(this);
        initToolbar(R.id.shop_toolbar, model.name);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)
                findViewById(R.id.shop_collapsingToolbarLayout);
        if (collapsingToolbarLayout != null) collapsingToolbarLayout.setTitle(model.name);

        SimpleDraweeView imageDv = (SimpleDraweeView) findViewById(R.id.shop_image);
        if (imageDv != null)
            imageDv.setImageURI("http://i.k1982.com/design/up/200710/2007102763640374.jpg");

        mInfoView = findViewById(R.id.shop_info);
        TextView addressTv = (TextView) findViewById(R.id.shop_address);
        if (addressTv != null) addressTv.setText(model.address);
        RatingBar rateRb = (RatingBar) findViewById(R.id.shop_rate);
        TextView rateTv = (TextView) findViewById(R.id.shop_rate_num);
        if (rateRb != null) rateRb.setRating(model.rate);
        if (rateTv != null) rateTv.setText(String.valueOf(model.rate));
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        float fraction = 1 / (float) appBarLayout.getTotalScrollRange();
        float current = 1 - Math.abs(verticalOffset) * fraction;
        mInfoView.setAlpha(current);
        mInfoView.setScaleY(current);
    }
}
