package com.bit.schoolcomment.fragment.goods;

import com.bit.schoolcomment.util.PullUtil;

public class ShopGoodsListFragment extends GoodsListFragment {

    @Override
    protected void pullNewData() {
        int shopId = getActivity().getIntent().getIntExtra("shopId", 0);
        System.out.println(shopId);
        PullUtil.getInstance().getShopGoods(shopId);
    }
}
