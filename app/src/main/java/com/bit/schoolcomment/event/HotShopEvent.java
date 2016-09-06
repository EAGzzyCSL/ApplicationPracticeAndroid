package com.bit.schoolcomment.event;

import com.bit.schoolcomment.model.ShopListModel;

public class HotShopEvent {

    public ShopListModel shopListModel;

    public HotShopEvent(ShopListModel shopListModel) {
        this.shopListModel = shopListModel;
    }
}
