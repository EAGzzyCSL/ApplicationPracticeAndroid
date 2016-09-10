package com.bit.schoolcomment.activity;

import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.dialog.SchoolDialog;
import com.bit.schoolcomment.dialog.ShopDialog;
import com.bit.schoolcomment.event.SchoolSelectEvent;
import com.bit.schoolcomment.event.ShopSelectEvent;
import com.bit.schoolcomment.fragment.goods.SearchGoodsListFragment;
import com.bit.schoolcomment.fragment.shop.SearchShopListFragment;
import com.bit.schoolcomment.util.DataUtil;
import com.bit.schoolcomment.util.PullUtil;
import com.bit.schoolcomment.util.ToastUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SearchActivity extends BaseActivity
        implements SearchView.OnQueryTextListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private static final int RATE_DESCEND = 0;
    private static final int RATE_ASCEND = 1;
    private static final int PRICE_ASCEND = 2;
    private static final int PRICE_DESCEND = 3;

    private AppBarLayout mAppBarLayout;
    private SearchView mSearchView;

    private RadioButton mTypeShopRb;
    private RadioButton mTypeGoodsRb;
    private RadioButton mRateDescend;
    private RadioButton mRateAscend;
    private RadioButton mPriceAscend;
    private RadioButton mPriceDescend;

    private TextView mSchoolTv;
    private TextView mShopTv;

    @Override
    protected boolean isEventBusOn() {
        return true;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        mAppBarLayout = (AppBarLayout) findViewById(R.id.search_appBarLayout);
        initToolbar(R.id.search_toolbar, "");

        RadioGroup mRadioGroup = (RadioGroup) findViewById(R.id.search_type);
        if (mRadioGroup != null) mRadioGroup.setOnCheckedChangeListener(this);
        mTypeShopRb = (RadioButton) findViewById(R.id.search_type_shop);
        mTypeGoodsRb = (RadioButton) findViewById(R.id.search_type_goods);

        mRateDescend = (RadioButton) findViewById(R.id.search_rate_descend);
        mRateAscend = (RadioButton) findViewById(R.id.search_rate_ascend);
        mPriceAscend = (RadioButton) findViewById(R.id.search_price_ascend);
        mPriceDescend = (RadioButton) findViewById(R.id.search_price_descend);

        mSchoolTv = (TextView) findViewById(R.id.search_school);
        mSchoolTv.setText(DataUtil.getSchoolModel().name);
        mSchoolTv.setLabelFor(DataUtil.getSchoolModel().ID);
        mSchoolTv.setOnClickListener(this);

        mShopTv = (TextView) findViewById(R.id.search_shop);
        mShopTv.setText(getString(R.string.all));
        mShopTv.setLabelFor(0);
        mShopTv.setOnClickListener(this);

        mTypeShopRb.setChecked(true);
        mRateDescend.setChecked(true);

        View searchBtn = findViewById(R.id.search_btn_start);
        if (searchBtn != null) searchBtn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        mSearchView = (SearchView) menu.findItem(R.id.menu_search_search).getActionView();
        mSearchView.onActionViewExpanded();
        mSearchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        search();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.isEmpty()) mAppBarLayout.setExpanded(true);
        return true;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (checkedId == R.id.search_type_shop) {
            transaction.replace(R.id.search_fragment, new SearchShopListFragment());
            mPriceAscend.setVisibility(View.GONE);
            mPriceDescend.setVisibility(View.GONE);
            mShopTv.setVisibility(View.GONE);

        } else if (checkedId == R.id.search_type_goods) {
            transaction.replace(R.id.search_fragment, new SearchGoodsListFragment());
            mPriceAscend.setVisibility(View.VISIBLE);
            mPriceDescend.setVisibility(View.VISIBLE);
            mShopTv.setVisibility(View.VISIBLE);
        }
        mRateDescend.setChecked(true);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_school:
                SchoolDialog schoolDialog = new SchoolDialog(this);
                schoolDialog.show();
                break;

            case R.id.search_shop:
                ShopDialog shopDialog = new ShopDialog(this);
                shopDialog.show();
                break;

            case R.id.search_btn_start:
                search();
                break;
        }
    }

    public void search() {
        String keyword = mSearchView.getQuery().toString();
        if (keyword.isEmpty()) {
            ToastUtil.show(getString(R.string.please_input_keyword));
        } else {
            mAppBarLayout.setExpanded(false);

            int schoolId = mSchoolTv.getLabelFor();
            int shopId = mShopTv.getLabelFor();
            int order = 0;

            if (mRateDescend.isChecked()) order = RATE_DESCEND;
            else if (mRateAscend.isChecked()) order = RATE_ASCEND;
            else if (mPriceAscend.isChecked()) order = PRICE_ASCEND;
            else if (mPriceDescend.isChecked()) order = PRICE_DESCEND;

            if (mTypeShopRb.isChecked()) {
                PullUtil.getInstance().searchShop(keyword, order, schoolId);
            } else if (mTypeGoodsRb.isChecked()) {
                PullUtil.getInstance().searchGoods(keyword, order, schoolId, shopId);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleSchoolSelect(SchoolSelectEvent event) {
        mSchoolTv.setText(event.schoolModel.name);
        mSchoolTv.setLabelFor(event.schoolModel.ID);
        mShopTv.setText(getString(R.string.all));
        mShopTv.setLabelFor(0);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleShopSelect(ShopSelectEvent event) {
        mShopTv.setText(event.shopModel.name);
        mShopTv.setLabelFor(event.shopModel.ID);
    }
}
