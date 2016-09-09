package com.bit.schoolcomment.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bit.schoolcomment.R;

import org.greenrobot.eventbus.EventBus;

public abstract class BaseDialog extends Dialog {

    public BaseDialog(Context context) {
        super(context);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        params.width = (int) (metrics.widthPixels * 0.9);
        window.setAttributes(params);
    }

    @Override
    public void show() {
        super.show();
        if (isEventBusOn()) EventBus.getDefault().register(this);
        setContentView(getLayoutID());
        initView();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (isEventBusOn()) EventBus.getDefault().unregister(this);
    }

    protected abstract boolean isEventBusOn();

    protected abstract int getLayoutID();

    protected abstract void initView();

    protected void initToolbar(int id, String title) {
        Toolbar toolbar = (Toolbar) findViewById(id);
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
