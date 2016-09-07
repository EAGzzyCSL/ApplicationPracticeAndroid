package com.bit.schoolcomment.util;

import com.bit.schoolcomment.model.SchoolModel;
import com.bit.schoolcomment.model.UserModel;

public class DataUtil {

    private static UserModel sUserModel;
    private static SchoolModel sSchoolModel;

    public static boolean isLogin() {
        return sUserModel != null;
    }

    public static void setUserModel(UserModel userModel) {
        sUserModel = userModel;
    }

    public static UserModel getUserModel() {
        return sUserModel;
    }

    public static void clearUserModel() {
        sUserModel = null;
    }

    public static void setSchoolModel(SchoolModel schoolModel) {
        sSchoolModel = schoolModel;
    }

    public static SchoolModel getSchoolModel() {
        return sSchoolModel;
    }
}
