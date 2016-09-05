package com.bit.schoolcomment.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.bit.schoolcomment.R;

import org.greenrobot.eventbus.EventBus;

public abstract class BaseActivity extends AppCompatActivity {

    private boolean mEventBusOn;

    protected void setEventBusOn(boolean eventBusOn) {
        mEventBusOn = eventBusOn;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mEventBusOn) EventBus.getDefault().register(this);
        setContentView(getLayoutID());
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mEventBusOn) EventBus.getDefault().unregister(this);
    }

    protected abstract int getLayoutID();

    protected abstract void initView();

    protected void initToolbar(int id, String title) {
        Toolbar toolbar = (Toolbar) findViewById(id);
        if (toolbar != null) {
            toolbar.setTitle(title);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        }
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return true;
    }
}
