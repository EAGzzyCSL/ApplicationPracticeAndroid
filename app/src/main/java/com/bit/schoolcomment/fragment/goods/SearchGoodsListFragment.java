package com.bit.schoolcomment.fragment.goods;

import com.bit.schoolcomment.activity.SearchActivity;

public class SearchGoodsListFragment extends GoodsListFragment {

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
