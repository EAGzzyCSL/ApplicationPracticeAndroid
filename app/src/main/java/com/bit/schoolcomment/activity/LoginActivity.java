package com.bit.schoolcomment.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.view.View;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.event.LoginEvent;
import com.bit.schoolcomment.util.PreferenceUtil;
import com.bit.schoolcomment.util.PullUtil;
import com.bit.schoolcomment.util.ToastUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class LoginActivity extends BaseActivity
        implements View.OnClickListener {

    private TextInputEditText mUserNameEt;
    private TextInputEditText mPasswordEt;

    public static void launch(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected boolean isEventBusOn() {
        return true;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        mUserNameEt = (TextInputEditText) findViewById(R.id.login_username);
        mPasswordEt = (TextInputEditText) findViewById(R.id.login_password);
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

                if (msg == null) PullUtil.getInstance().login(username, password);
                else ToastUtil.show(msg);
                break;

            case R.id.login_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleLogin(LoginEvent event) {
        PreferenceUtil.putInt("userId", event.userModel.ID);
        PreferenceUtil.putString("token", event.userModel.token);
        finish();
    }
}
