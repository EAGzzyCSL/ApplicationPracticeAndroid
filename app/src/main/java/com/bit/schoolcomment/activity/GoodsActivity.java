package com.bit.schoolcomment.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.event.GoodsCollectionEvent;
import com.bit.schoolcomment.model.GoodsModel;
import com.bit.schoolcomment.util.DataUtil;
import com.bit.schoolcomment.util.DimensionUtil;
import com.bit.schoolcomment.util.PullUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;

public class GoodsActivity extends BaseActivity
        implements AppBarLayout.OnOffsetChangedListener, ViewPager.OnPageChangeListener, View.OnClickListener {

    public static final String EXTRA_goodsId = "goodsId";
    public static final String EXTRA_model = "model";

    private static final int MSG_WHAT = 1;
    private static final int TIME_DELAY = 3000;

    private MenuItem mCollectMenu;
    private MenuItem mCancelMenu;

    private Toolbar mTitleView;
    private Handler mHandler;
    private SimpleDraweeView[] mImageDv;
    private RadioButton[] mRadioButton;

    private GoodsModel mGoodsModel;

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
        return true;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_goods;
    }

    @Override
    protected void initView() {
        mGoodsModel = getIntent().getParcelableExtra(EXTRA_model);
        assert mGoodsModel != null;
        if (DataUtil.isLogin()) PullUtil.getInstance().judgeCollection(mGoodsModel.ID);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.goods_appBarLayout);
        if (appBarLayout != null) appBarLayout.addOnOffsetChangedListener(this);
        initToolbar(R.id.goods_toolbar, mGoodsModel.name);

        RatingBar rateRb = (RatingBar) findViewById(R.id.goods_rate);
        TextView rateTv = (TextView) findViewById(R.id.goods_rate_num);
        if (rateRb != null) rateRb.setRating(mGoodsModel.rate);
        if (rateTv != null) rateTv.setText(String.valueOf(mGoodsModel.rate));

        mTitleView = (Toolbar) findViewById(R.id.goods_toolbar2);
        if (mTitleView != null) {
            mTitleView.setTitle(mGoodsModel.name);
            mTitleView.setLabelFor(mGoodsModel.ID);
            mTitleView.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
            mTitleView.setNavigationOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_goods, menu);
        mCollectMenu = menu.findItem(R.id.menu_goods_collect);
        mCancelMenu = menu.findItem(R.id.menu_goods_cancel);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int goodsId = mTitleView.getLabelFor();
        switch (item.getItemId()) {
            case R.id.menu_goods_collect:
                if (!DataUtil.isLogin()) {
                    LoginActivity.launch(this);
                    return true;
                }
                PullUtil.getInstance().addCollection(goodsId);
                break;

            case R.id.menu_goods_cancel:
                if (!DataUtil.isLogin()) {
                    LoginActivity.launch(this);
                    return true;
                }
                PullUtil.getInstance().cancelCollection(goodsId);
                break;
        }

        return super.onOptionsItemSelected(item);
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
                if (DataUtil.isLogin()) {
                    Intent intent = new Intent(this, AddCommentActivity.class);
                    intent.putExtra(EXTRA_goodsId, getIntent().getIntExtra(EXTRA_goodsId, 0));
                    startActivity(intent);
                } else {
                    LoginActivity.launch(this);
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleGoodsCollection(GoodsCollectionEvent event) {
        if (mCancelMenu != null) {
            mCollectMenu.setVisible(!event.collected);
            mCancelMenu.setVisible(event.collected);
        }
    }

    private class ImagePagerAdapter extends PagerAdapter {

        private final int DIMEN = DimensionUtil.Dp2Px(10);

        public ImagePagerAdapter() {
            mImageDv = new SimpleDraweeView[getCount()];
            mRadioButton = new RadioButton[getCount()];
            RadioGroup radioGroup = (RadioGroup) findViewById(R.id.goods_radioGroup);

            for (int i = 0; i < getCount(); i++) {
                mImageDv[i] = new SimpleDraweeView(GoodsActivity.this);
                mImageDv[i].getHierarchy().setPlaceholderImage(R.drawable.ic_loading);
                mImageDv[i].setImageURI(mGoodsModel.images.get(i));

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
            if (mRadioButton.length > 0) {
                mRadioButton[0].setChecked(true);
            }
        }

        @Override
        public int getCount() {
            return mGoodsModel.images == null ? 0 : mGoodsModel.images.size();
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
