package com.bit.schoolcomment.activity;

import com.bit.schoolcomment.R;

public class AddCommentActivity extends BaseActivity {

    @Override
    protected int getLayoutID() {
        return R.layout.activity_add_comment;
    }

    @Override
    protected void initView() {
        initToolbar(R.id.add_comment_toolbar, "test");
    }
}
