package com.bit.schoolcomment.event.shop;

import com.bit.schoolcomment.model.list.ShopListModel;

public class HotShopEvent {

    public ShopListModel shopListModel;

    public HotShopEvent(ShopListModel shopListModel) {
        this.shopListModel = shopListModel;
    }
}
