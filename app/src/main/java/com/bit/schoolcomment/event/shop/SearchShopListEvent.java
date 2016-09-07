package com.bit.schoolcomment.event.shop;

import com.bit.schoolcomment.model.list.ShopListModel;

public class SearchShopListEvent {

    public ShopListModel shopListModel;

    public SearchShopListEvent(ShopListModel shopListModel) {
        this.shopListModel = shopListModel;
    }
}
