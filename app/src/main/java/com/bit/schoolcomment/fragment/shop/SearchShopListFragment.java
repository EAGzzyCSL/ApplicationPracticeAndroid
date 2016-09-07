package com.bit.schoolcomment.fragment.shop;

import com.bit.schoolcomment.event.shop.SearchShopListEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SearchShopListFragment extends ShopListFragment {

    @Override
    protected void pullNewData() {
        setRefreshing(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleSearchShopList(SearchShopListEvent event) {
        updateUI(event.shopListModel.data);
    }
}
