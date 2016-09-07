package com.bit.schoolcomment.event.goods;

import com.bit.schoolcomment.model.list.GoodsListModel;

public class SearchGoodsListEvent {

    public GoodsListModel goodsListModel;

    public SearchGoodsListEvent(GoodsListModel goodsListModel) {
        this.goodsListModel = goodsListModel;
    }
}
