package com.bit.schoolcomment.dialog;

import android.content.Context;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.event.ShopSelectEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ShopDialog extends BaseDialog {

    public ShopDialog(Context context) {
        super(context);
    }

    @Override
    protected boolean isEventBusOn() {
        return true;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.dialog_shop;
    }

    @Override
    protected void initView() {
        initToolbar(R.id.dialog_shop_toolbar, getContext().getString(R.string.change_shop));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleShopSelect(ShopSelectEvent event) {
        dismiss();
    }
}
