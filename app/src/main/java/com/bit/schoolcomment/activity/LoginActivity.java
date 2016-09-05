package com.bit.schoolcomment.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.util.PullUtil;
import com.bit.schoolcomment.util.ToastUtil;

public class LoginActivity extends BaseActivity
        implements View.OnClickListener {

    private EditText mUserNameEt;
    private EditText mPasswordEt;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        mUserNameEt = (EditText) findViewById(R.id.login_username);
        mPasswordEt = (EditText) findViewById(R.id.login_password);
        View loginBt = findViewById(R.id.login_button);
        View registerBt = findViewById(R.id.login_register);
        if (loginBt != null) loginBt.setOnClickListener(this);
        if (registerBt != null) registerBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                String username = mUserNameEt.getText().toString();
                String password = mPasswordEt.getText().toString();

                String msg = null;
                if (username.isEmpty()) msg = getString(R.string.please_input_username);
                else if (password.isEmpty()) msg = getString(R.string.please_input_password);

                if (msg == null) PullUtil.getInstance().login();
                else ToastUtil.show(msg);
                break;

            case R.id.login_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
