package com.bit.schoolcomment.activity;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.event.UserInfoEvent;
import com.bit.schoolcomment.util.DataUtil;
import com.bit.schoolcomment.util.PullUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;

public class UserInfoActivity extends BaseActivity
        implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private static final int DEFAULT = 0;
    private static final int MALE = 1;
    private static final int FEMALE = 2;

    private TextView nameTv;
    private RadioButton maleRb;
    private RadioButton femaleRb;
    private TextView birthTv;
    private TextView dormitoryTv;

    @Override
    protected boolean isEventBusOn() {
        return true;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initView() {
        PullUtil.getInstance().getUserInfo();
        initToolbar(R.id.user_info_toolbar, getString(R.string.personal_info));

        nameTv = (TextView) findViewById(R.id.user_info_name);
        maleRb = (RadioButton) findViewById(R.id.user_info_male);
        femaleRb = (RadioButton) findViewById(R.id.user_info_female);
        birthTv = (TextView) findViewById(R.id.user_info_birth);
        if (birthTv != null) birthTv.setOnClickListener(this);
        dormitoryTv = (TextView) findViewById(R.id.user_info_dormitory);

        View saveBtn = findViewById(R.id.user_info_btn_save);
        if (saveBtn != null) saveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_info_birth:
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(
                        this, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
                break;

            case R.id.user_info_btn_save:
                int sex = DEFAULT;
                if (maleRb.isChecked()) sex = MALE;
                else if (femaleRb.isChecked()) sex = FEMALE;
                String birth = birthTv.getText().toString();
                String dormitory = dormitoryTv.getText().toString();
                PullUtil.getInstance().updateUserInfo(sex, birth, dormitory);
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        birthTv.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleUserInfo(UserInfoEvent event) {
        nameTv.setText(DataUtil.getUserModel().name);
        switch (event.userModel.sex) {
            case DEFAULT:
                break;
            case MALE:
                maleRb.setChecked(true);
                break;
            case FEMALE:
                femaleRb.setChecked(true);
                break;
        }
        if (event.userModel.birth != null) birthTv.setText(event.userModel.birth);
        if (event.userModel.dormitory != null) dormitoryTv.setText(event.userModel.dormitory);
    }
}
