package com.bit.schoolcomment.event.goods;

import com.bit.schoolcomment.model.list.GoodsListModel;

public class ShopGoodsListEvent {

    public GoodsListModel goodsListModel;

    public ShopGoodsListEvent(GoodsListModel goodsListModel) {
        this.goodsListModel = goodsListModel;
    }
}
