package com.bit.schoolcomment.fragment;

import com.bit.schoolcomment.event.HotShopEvent;
import com.bit.schoolcomment.util.PullUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class HotShopListFragment extends ShopListFragment {

    @Override
    protected void pullNewData() {
        PullUtil.getInstance().getHotShop(3);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleHotShop(HotShopEvent event) {
        updateUI(event.shopListModel.data);
    }
}
