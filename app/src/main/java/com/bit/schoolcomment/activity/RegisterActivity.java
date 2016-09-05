package com.bit.schoolcomment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.util.PullUtil;
import com.bit.schoolcomment.util.ToastUtil;

public class RegisterActivity extends BaseActivity
        implements View.OnClickListener {

    private EditText mUserNameEt;
    private EditText mPasswordEt;
    private EditText mPassword2Et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        mUserNameEt = (EditText) findViewById(R.id.register_username);
        mPasswordEt = (EditText) findViewById(R.id.register_password);
        mPassword2Et = (EditText) findViewById(R.id.register_password2);

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

                if (msg == null) PullUtil.getInstance().register();
                else ToastUtil.show(msg);
                break;

            case R.id.register_login:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
