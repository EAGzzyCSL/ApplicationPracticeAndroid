package com.bit.schoolcomment.dialog;

import android.content.Context;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.event.SchoolSelectEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SchoolDialog extends BaseDialog {

    public SchoolDialog(Context context) {
        super(context);
    }

    @Override
    protected boolean isEventBusOn() {
        return true;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.dialog_school;
    }

    @Override
    protected void initView() {
        initToolbar(R.id.dialog_school_toolbar, getContext().getString(R.string.change_school));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleSchoolSelect(SchoolSelectEvent event) {
        dismiss();
    }
}
