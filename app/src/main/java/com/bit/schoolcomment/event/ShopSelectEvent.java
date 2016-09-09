package com.bit.schoolcomment.event;

import com.bit.schoolcomment.model.ShopModel;

public class ShopSelectEvent {

    public ShopModel shopModel;

    public ShopSelectEvent(ShopModel shopModel) {
        this.shopModel = shopModel;
    }
}
