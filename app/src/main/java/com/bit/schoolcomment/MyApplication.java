package com.bit.schoolcomment;

import android.app.Application;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MyApplication extends Application {

    private static MyApplication sApplication;

    public RequestQueue mRequestQueue;
    public Animation mItemAnimation;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        mRequestQueue = Volley.newRequestQueue(this);
        mItemAnimation = AnimationUtils.loadAnimation(this, R.anim.item_bottom_in);
    }

    public static MyApplication getDefault() {
        return sApplication;
    }
}
