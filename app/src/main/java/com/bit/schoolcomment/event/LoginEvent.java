package com.bit.schoolcomment.event;

import com.bit.schoolcomment.model.UserModel;

public class LoginEvent {

    public UserModel userModel;

    public LoginEvent(UserModel userModel) {
        this.userModel = userModel;
    }
}
