package com.bit.schoolcomment.fragment.shop;

import com.bit.schoolcomment.activity.SearchActivity;

public class SearchShopListFragment extends ShopListFragment {

    private boolean mCreated;
    private SearchActivity mActivity;

    @Override
    protected void pullNewData() {
        if (!mCreated) {
            mCreated = true;
            setRefreshing(false);
            mActivity = (SearchActivity) getActivity();
        } else {
            mActivity.search();
        }
    }
}
