package com.bit.schoolcomment.view;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bit.schoolcomment.R;

public class SchoolDialog extends Dialog {

    public SchoolDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_school);
        initView();
    }

    private void initView() {
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        params.width = (int) (metrics.widthPixels * 0.8);
        window.setAttributes(params);

        Toolbar toolbar = (Toolbar) findViewById(R.id.dialog_school_toolbar);
        toolbar.setTitle(getContext().getString(R.string.change_school));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
