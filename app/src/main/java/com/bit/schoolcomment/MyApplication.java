package com.bit.schoolcomment;

import android.app.Application;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;

public class MyApplication extends Application {

    private static MyApplication sApplication;

    public RequestQueue mRequestQueue;
    public Animation mItemAnimation;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        mRequestQueue = Volley.newRequestQueue(this);
        mItemAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_item_show);
        Fresco.initialize(this);
    }

    public static MyApplication getDefault() {
        return sApplication;
    }
}
