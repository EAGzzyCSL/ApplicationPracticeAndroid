package com.bit.schoolcomment.activity;

import android.view.View;

import com.bit.schoolcomment.R;

public class AddCommentActivity extends BaseActivity
        implements View.OnClickListener {

    @Override
    protected boolean isEventBusOn() {
        return false;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_add_comment;
    }

    @Override
    protected void initView() {
        initToolbar(R.id.add_comment_toolbar, "test");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_comment_button:
                break;
        }
    }
}
