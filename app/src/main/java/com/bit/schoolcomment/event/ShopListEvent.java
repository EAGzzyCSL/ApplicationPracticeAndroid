package com.bit.schoolcomment.event;

import com.bit.schoolcomment.model.list.ShopListModel;

public class ShopListEvent {

    public ShopListModel shopListModel;
    public Class targetClass;

    public ShopListEvent(ShopListModel shopListModel, Class targetClass) {
        this.shopListModel = shopListModel;
        this.targetClass = targetClass;
    }
}
