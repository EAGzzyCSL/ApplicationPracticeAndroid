package com.bit.schoolcomment.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.event.SchoolSelectEvent;
import com.bit.schoolcomment.fragment.goods.SearchGoodsListFragment;
import com.bit.schoolcomment.fragment.shop.SearchShopListFragment;
import com.bit.schoolcomment.util.DataUtil;
import com.bit.schoolcomment.util.PullUtil;
import com.bit.schoolcomment.util.ToastUtil;
import com.bit.schoolcomment.view.SchoolDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SearchActivity extends BaseActivity
        implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private SearchView mSearchView;
    private RadioButton mTypeShopRb;
    private RadioButton mTypeGoodsRb;
    private TextView mSchoolTv;

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
        initToolbar(R.id.search_toolbar, "");

        RadioGroup mRadioGroup = (RadioGroup) findViewById(R.id.search_type);
        if (mRadioGroup != null) mRadioGroup.setOnCheckedChangeListener(this);
        mTypeShopRb = (RadioButton) findViewById(R.id.search_type_shop);
        mTypeGoodsRb = (RadioButton) findViewById(R.id.search_type_goods);
        mTypeShopRb.setChecked(true);

        mSchoolTv = (TextView) findViewById(R.id.search_school);
        mSchoolTv.setText(DataUtil.getSchoolModel().name);
        mSchoolTv.setOnClickListener(this);

        View searchBtn = findViewById(R.id.search_btn_start);
        if (searchBtn != null) searchBtn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        mSearchView = (SearchView) menu.findItem(R.id.menu_search_search).getActionView();
        mSearchView.onActionViewExpanded();
        return true;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (checkedId == R.id.search_type_shop) {
            transaction.replace(R.id.search_fragment, new SearchShopListFragment());
        } else if (checkedId == R.id.search_type_goods) {
            transaction.replace(R.id.search_fragment, new SearchGoodsListFragment());
        }
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_school:
                SchoolDialog dialog = new SchoolDialog(this);
                dialog.show();
                break;

            case R.id.search_btn_start:
                String keyword = mSearchView.getQuery().toString();
                if (keyword.isEmpty()) ToastUtil.show(getString(R.string.please_input_keyword));
                else if (mTypeShopRb.isChecked()) PullUtil.getInstance().searchShop(keyword);
                else if (mTypeGoodsRb.isChecked()) PullUtil.getInstance().searchGoods(keyword);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleSchoolSelect(SchoolSelectEvent event) {
        mSchoolTv.setText(event.schoolModel.name);
    }
}
