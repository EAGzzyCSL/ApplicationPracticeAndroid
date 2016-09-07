package com.bit.schoolcomment.fragment.goods;

import com.bit.schoolcomment.event.goods.SearchGoodsListEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SearchGoodsListFragment extends GoodsListFragment {

    @Override
    protected void pullNewData() {
        setRefreshing(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleSearchGoodsList(SearchGoodsListEvent event) {
        updateUI(event.goodsListModel.data);
    }
}
