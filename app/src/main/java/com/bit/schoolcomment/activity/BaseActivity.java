package com.bit.schoolcomment.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.bit.schoolcomment.R;

public abstract class BaseActivity extends AppCompatActivity {

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
