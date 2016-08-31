package com.bit.schoolcomment;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MyApplication extends Application {

    private static MyApplication sApplication;
    public RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        mRequestQueue = Volley.newRequestQueue(this);
    }

    public static MyApplication getDefault() {
        return sApplication;
    }
}
