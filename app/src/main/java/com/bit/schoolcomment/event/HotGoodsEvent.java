package com.bit.schoolcomment.event;

import com.bit.schoolcomment.model.GoodsListModel;

public class HotGoodsEvent {

    public GoodsListModel goodsListModel;

    public HotGoodsEvent(GoodsListModel goodsListModel) {
        this.goodsListModel = goodsListModel;
    }
}
