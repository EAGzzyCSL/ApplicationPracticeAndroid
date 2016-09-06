package com.bit.schoolcomment.event.goods;

import com.bit.schoolcomment.model.list.GoodsListModel;

public class HotGoodsEvent {

    public GoodsListModel goodsListModel;

    public HotGoodsEvent(GoodsListModel goodsListModel) {
        this.goodsListModel = goodsListModel;
    }
}
