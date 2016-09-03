package com.bit.schoolcomment.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.bit.schoolcomment.R;
import com.facebook.drawee.view.SimpleDraweeView;

public class GoodsActivity extends BaseActivity
        implements AppBarLayout.OnOffsetChangedListener {

    private View mTitleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        initView();
    }

    private void initView() {
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.goods_appBarLayout);
        if (appBarLayout != null) appBarLayout.addOnOffsetChangedListener(this);
        initToolbar(R.id.goods_toolbar, "test");
        initToolbar(R.id.goods_toolbar2, "test");

        mTitleView = findViewById(R.id.goods_toolbar2);

        ViewPager viewPager = (ViewPager) findViewById(R.id.goods_viewPager);
        if (viewPager != null) viewPager.setAdapter(new ImagePagerAdapter());
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (appBarLayout.getTotalScrollRange() == Math.abs(verticalOffset)) {
            mTitleView.setVisibility(View.VISIBLE);
        } else {
            mTitleView.setVisibility(View.GONE);
        }
    }

    private class ImagePagerAdapter extends PagerAdapter {

        private SimpleDraweeView[] mImageDv;

        public ImagePagerAdapter() {
            mImageDv = new SimpleDraweeView[3];
            for (int i = 0; i < 3; i++) {
                mImageDv[i] = new SimpleDraweeView(GoodsActivity.this);
                mImageDv[i].setImageURI("https://www.baidu.com/img/bd_logo1.png");
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageDv[position], new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return mImageDv[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mImageDv[position]);
        }
    }
}
