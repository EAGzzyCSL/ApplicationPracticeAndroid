package com.bit.schoolcomment.activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.view.View;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.event.RegisterEvent;
import com.bit.schoolcomment.util.MD5Util;
import com.bit.schoolcomment.util.PullUtil;
import com.bit.schoolcomment.util.ToastUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class RegisterActivity extends BaseActivity
        implements View.OnClickListener {

    private TextInputEditText mUserNameEt;
    private TextInputEditText mPasswordEt;
    private TextInputEditText mPassword2Et;

    @Override
    protected boolean isEventBusOn() {
        return true;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        mUserNameEt = (TextInputEditText) findViewById(R.id.register_username);
        mPasswordEt = (TextInputEditText) findViewById(R.id.register_password);
        mPassword2Et = (TextInputEditText) findViewById(R.id.register_password2);

        View registerBt = findViewById(R.id.register_button);
        View loginBt = findViewById(R.id.register_login);
        if (registerBt != null) registerBt.setOnClickListener(this);
        if (loginBt != null) loginBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_button:
                String username = mUserNameEt.getText().toString();
                String password = mPasswordEt.getText().toString();
                String password2 = mPassword2Et.getText().toString();

                String msg = null;
                if (username.isEmpty()) msg = getString(R.string.please_input_username);
                else if (password.isEmpty()) msg = getString(R.string.please_input_password);
                else if (password2.isEmpty()) msg = getString(R.string.please_input_password_again);
                else if (!password.equals(password2)) msg = getString(R.string.not_same_password);
                else password = MD5Util.string2MD5(password);

                if (msg == null) PullUtil.getInstance().register(username, password);
                else ToastUtil.show(msg);
                break;

            case R.id.register_login:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleRegister(RegisterEvent event) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
