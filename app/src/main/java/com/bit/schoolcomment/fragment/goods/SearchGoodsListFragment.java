package com.bit.schoolcomment.fragment.goods;

public class SearchGoodsListFragment extends GoodsListFragment {

    @Override
    protected void pullNewData() {
        setRefreshing(false);
    }
}
