package com.bit.schoolcomment.event.shop;

import com.bit.schoolcomment.model.list.ShopListModel;

public class HotShopListEvent {

    public ShopListModel shopListModel;

    public HotShopListEvent(ShopListModel shopListModel) {
        this.shopListModel = shopListModel;
    }
}
