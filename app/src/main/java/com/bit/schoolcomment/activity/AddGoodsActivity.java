package com.bit.schoolcomment.activity;

import android.util.Log;
import android.view.View;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.fragment.ImagePickFragment;
import com.bit.schoolcomment.util.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;

public class AddGoodsActivity extends BaseActivity implements View.OnClickListener {
    public static final String EXTRA_shopId = "shopId";
    public static final String EXTRA_shopName = "shopName";
    private ImagePickFragment imagePickFragment;

    @Override
    protected boolean isEventBusOn() {
        return false;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_add_goods;
    }

    @Override
    protected void initView() {
        initToolbar(R.id.add_goods_toolbar, getString(R.string.find_new_goods));
        View v = findViewById(R.id.fab_done);
        if (v != null) {
            v.setOnClickListener(this);
        }
        imagePickFragment = (ImagePickFragment) getFragmentManager().findFragmentById(R.id.fragment_goodsPick);
        imagePickFragment.init(3, new ImagePickFragment.OnImageUploadDoneListener() {
                    @Override
                    public void onImageUploadDone(ArrayList<String> imageHash) {
                        Log.i("WWW", Arrays.toString(imageHash.toArray()));
                        ToastUtil.show("imageDone");
                    }
                }
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_done: {
                if (imagePickFragment.check()) {
                    imagePickFragment.upload();
                }
            }
        }
    }


}
