package com.bit.schoolcomment.event.goods;

import com.bit.schoolcomment.model.list.GoodsListModel;

public class GoodsCollectionListEvent {

    public GoodsListModel goodsListModel;

    public GoodsCollectionListEvent(GoodsListModel goodsListModel) {
        this.goodsListModel = goodsListModel;
    }
}
