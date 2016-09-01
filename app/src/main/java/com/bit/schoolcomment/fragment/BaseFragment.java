package com.bit.schoolcomment.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.greenrobot.eventbus.EventBus;

public abstract class BaseFragment extends Fragment {

    private boolean mEventBusOn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mEventBusOn) EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mEventBusOn) EventBus.getDefault().unregister(this);
    }

    protected void setEventBusOn(boolean eventBusOn) {
        mEventBusOn = eventBusOn;
    }
}
