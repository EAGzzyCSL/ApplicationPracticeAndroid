package com.bit.schoolcomment.event;

import com.bit.schoolcomment.model.list.GoodsListModel;

public class GoodsListEvent {

    public GoodsListModel goodsListModel;
    public Class targetClass;

    public GoodsListEvent(GoodsListModel goodsListModel, Class targetClass) {
        this.goodsListModel = goodsListModel;
        this.targetClass = targetClass;
    }
}
