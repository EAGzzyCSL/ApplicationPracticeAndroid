package com.bit.schoolcomment.util;

import android.widget.Toast;

import com.bit.schoolcomment.MyApplication;

public class ToastUtil {

    private static Toast sToast;

    public static void show(String msg) {
        if (sToast == null) {
            sToast = Toast.makeText(MyApplication.getDefault(), msg, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(msg);
        }
        sToast.show();
    }
}
