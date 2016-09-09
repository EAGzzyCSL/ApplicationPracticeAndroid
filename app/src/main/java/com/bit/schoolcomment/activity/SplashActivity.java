package com.bit.schoolcomment.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.model.SchoolModel;
import com.bit.schoolcomment.util.DataUtil;
import com.bit.schoolcomment.util.PreferenceUtil;
import com.bit.schoolcomment.util.PullUtil;

public class SplashActivity extends BaseActivity {

    private static final int DELAY = 2000;

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
        ObjectAnimator.ofFloat(titleTv, "alpha", 0f, 1f).setDuration(DELAY / 3).start();

        final TextView subtitleTv = (TextView) findViewById(R.id.splash_subtitle);
        if (subtitleTv != null) {
            subtitleTv.postDelayed(new Runnable() {

                @Override
                public void run() {
                    subtitleTv.setVisibility(View.VISIBLE);
                    ObjectAnimator.ofFloat(subtitleTv, "rotationX", 0f, 360f).setDuration(DELAY * 2 / 3).start();
                }
            }, DELAY / 3);
        }

        if (PreferenceUtil.contains("userId")) {
            int id = PreferenceUtil.getInt("userId", 0);
            String token = PreferenceUtil.getString("token", null);
            PullUtil.getInstance().checkToken(id, token);
        }

        SchoolModel model = new SchoolModel(
                PreferenceUtil.getInt("schoolId", 3),
                PreferenceUtil.getString("schoolName", "北京理工大学")
        );
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
