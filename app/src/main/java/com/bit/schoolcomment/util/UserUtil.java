package com.bit.schoolcomment.util;

import com.bit.schoolcomment.model.UserModel;

public class UserUtil {

    private static UserModel sUserModel;

    public static boolean isLogin() {
        return sUserModel != null;
    }

    public static void setModel(UserModel userModel) {
        sUserModel = userModel;
    }

    public static void clearModel() {
        sUserModel = null;
    }

    public static int getId() {
        return sUserModel.ID;
    }

    public static String getToken() {
        return sUserModel.token;
    }

    public static String getName() {
        return sUserModel.name;
    }
}
