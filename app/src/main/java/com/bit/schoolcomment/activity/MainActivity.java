package com.bit.schoolcomment.activity;

import android.os.Bundle;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.util.PullUtil;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar(R.id.main_toolbar, "test");

        PullUtil.getInstance().searchGasStation();
    }
}
