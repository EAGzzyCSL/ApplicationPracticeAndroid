package com.bit.schoolcomment.event.goods;

import com.bit.schoolcomment.model.list.GoodsListModel;

public class HotGoodsListEvent {

    public GoodsListModel goodsListModel;

    public HotGoodsListEvent(GoodsListModel goodsListModel) {
        this.goodsListModel = goodsListModel;
    }
}
