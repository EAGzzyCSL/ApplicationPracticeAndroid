package com.bit.schoolcomment.fragment.goods;

import com.bit.schoolcomment.event.goods.ShopGoodsListEvent;
import com.bit.schoolcomment.util.PullUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ShopGoodsListFragment extends GoodsListFragment {

    @Override
    protected void pullNewData() {
        int shopId = getActivity().getIntent().getIntExtra("shopId", 0);
        System.out.println(shopId);
        PullUtil.getInstance().getShopGoods(shopId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleShopGoodsList(ShopGoodsListEvent event) {
        updateUI(event.goodsListModel.data);
    }
}
