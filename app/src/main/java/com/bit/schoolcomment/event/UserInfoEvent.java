package com.bit.schoolcomment.event;

import com.bit.schoolcomment.model.UserModel;

public class UserInfoEvent {

    public UserModel userModel;

    public UserInfoEvent(UserModel userModel) {
        this.userModel = userModel;
    }
}
