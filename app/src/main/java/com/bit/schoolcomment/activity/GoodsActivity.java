package com.bit.schoolcomment.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.util.DimensionUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.ref.WeakReference;

public class GoodsActivity extends BaseActivity
        implements AppBarLayout.OnOffsetChangedListener, ViewPager.OnPageChangeListener, View.OnClickListener {

    private static final int MSG_WHAT = 1;
    private static final int TIME_DELAY = 4000;

    private View mTitleView;
    private Handler mHandler;
    private SimpleDraweeView[] mImageDv;
    private RadioButton[] mRadioButton;

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.sendEmptyMessageDelayed(MSG_WHAT, TIME_DELAY);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeMessages(MSG_WHAT);
    }

    @Override
    protected boolean isEventBusOn() {
        return false;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_goods;
    }

    @Override
    protected void initView() {
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.goods_appBarLayout);
        if (appBarLayout != null) appBarLayout.addOnOffsetChangedListener(this);
        initToolbar(R.id.goods_toolbar, "test");
        initToolbar(R.id.goods_toolbar2, "test");

        mTitleView = findViewById(R.id.goods_toolbar2);
        ViewPager viewPager = (ViewPager) findViewById(R.id.goods_viewPager);
        if (viewPager != null) {
            viewPager.setAdapter(new ImagePagerAdapter());
            viewPager.addOnPageChangeListener(this);
            mHandler = new ImageHandler(new WeakReference<>(viewPager));
        }

        View addBt = findViewById(R.id.goods_btn_add);
        if (addBt != null) addBt.setOnClickListener(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (appBarLayout.getTotalScrollRange() == Math.abs(verticalOffset)) {
            mTitleView.setVisibility(View.VISIBLE);
        } else {
            mTitleView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mRadioButton[position].setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goods_btn_add:
                Intent intent = new Intent(this, AddCommentActivity.class);
                startActivity(intent);
                break;
        }
    }

    private class ImagePagerAdapter extends PagerAdapter {

        private final int DIMEN = DimensionUtil.Dp2Px(10);

        public ImagePagerAdapter() {
            mImageDv = new SimpleDraweeView[3];
            mRadioButton = new RadioButton[3];
            RadioGroup radioGroup = (RadioGroup) findViewById(R.id.goods_radioGroup);

            for (int i = 0; i < 3; i++) {
                mImageDv[i] = new SimpleDraweeView(GoodsActivity.this);
                mImageDv[i].setImageURI("https://www.baidu.com/img/bd_logo1.png");

                mRadioButton[i] = new RadioButton(GoodsActivity.this);
                mRadioButton[i].setBackgroundResource(R.drawable.bg_banner_radio);
                mRadioButton[i].setButtonDrawable(null);
                mRadioButton[i].setEnabled(false);
                if (radioGroup != null) {
                    RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(DIMEN, DIMEN);
                    if (i > 0) params.setMarginStart(DIMEN);
                    radioGroup.addView(mRadioButton[i], params);
                }
            }
            mRadioButton[0].setChecked(true);
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

    private static class ImageHandler extends Handler {

        private ViewPager mViewPager;

        public ImageHandler(WeakReference<ViewPager> reference) {
            mViewPager = reference.get();
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == MSG_WHAT) {
                int currentItem = mViewPager.getCurrentItem();
                int nextItem = currentItem + 1;
                if (nextItem == 3) nextItem = 0;
                mViewPager.setCurrentItem(nextItem, true);
                sendEmptyMessageDelayed(MSG_WHAT, TIME_DELAY);
            }
        }
    }
}
