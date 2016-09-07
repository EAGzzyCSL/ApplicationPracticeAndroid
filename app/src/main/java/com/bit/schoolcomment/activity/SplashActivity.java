package com.bit.schoolcomment.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.widget.TextView;

import com.bit.schoolcomment.MyApplication;
import com.bit.schoolcomment.R;
import com.bit.schoolcomment.model.SchoolModel;
import com.bit.schoolcomment.util.PreferenceUtil;
import com.bit.schoolcomment.util.PullUtil;
import com.bit.schoolcomment.util.DataUtil;

public class SplashActivity extends BaseActivity {

    private static final int DELAY = 1000;

    @Override
    protected boolean isEventBusOn() {
        return false;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        TextView titleTv = (TextView) findViewById(R.id.splash_title);
        TextView subtitleTv = (TextView) findViewById(R.id.splash_subtitle);

        if (titleTv != null) titleTv.startAnimation(MyApplication.getDefault().mItemAnimation);
        ObjectAnimator.ofFloat(subtitleTv, "rotationX", 0f, 360f).setDuration(DELAY).start();

        if (PreferenceUtil.contains("userId")) {
            int id = PreferenceUtil.getInt("userId");
            String token = PreferenceUtil.getString("token");
            PullUtil.getInstance().checkToken(id, token);
        }

        SchoolModel model = new SchoolModel();
        if (PreferenceUtil.contains("schoolId")) {
            model.ID = PreferenceUtil.getInt("schoolId");
            model.name = PreferenceUtil.getString("schoolName");
        } else {
            model.ID = 3;
            model.name = "北京理工大学";
        }
        DataUtil.setSchoolModel(model);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, DELAY);
    }
}
