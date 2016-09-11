package com.bit.schoolcomment.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.fragment.ImagePickFragment;
import com.bit.schoolcomment.model.GoodsModel;
import com.bit.schoolcomment.util.PullUtil;
import com.bit.schoolcomment.util.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class AddGoodsActivity extends BaseActivity implements View.OnClickListener {
    public static final String EXTRA_shopId = "shopId";
    public static final String EXTRA_shopName = "shopName";
    public static final String EXTRA_schoolId = "schoolId";
    private ImagePickFragment imagePickFragment;

    private EditText et_dish;
    private EditText et_prices;

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
        View v = findViewById(R.id.add_goods_button);
        if (v != null) {
            v.setOnClickListener(this);
        }
        imagePickFragment = (ImagePickFragment) getFragmentManager().findFragmentById(R.id.fragment_goodsPick);
        imagePickFragment.init(3, new ImagePickFragment.OnImageUploadDoneListener() {
                    @Override
                    public void onImageUploadDone(String imageJson) {
                        int shopId = getIntent().getIntExtra(EXTRA_shopId, -1);
                        int schoolId = getIntent().getIntExtra(EXTRA_schoolId, -1);
                        String images = new Gson().toJson(imageJson);
                        PullUtil.getInstance().addNewGoods(
                                et_dish.getText().toString(),
                                et_prices.getText().toString(),
                                shopId,
                                schoolId,
                                images
                        );
                    }
                }, R.layout.item_image_pick_fresco
        );
        et_dish = (EditText) findViewById(R.id.et_dish);
        et_prices = (EditText) findViewById(R.id.et_price);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_goods_button: {
                if (formCheck()) {
                    imagePickFragment.upload();
                    finish();
                }
            }
        }
    }

    private boolean formCheck() {
        if (TextUtils.isEmpty(et_dish.getText())) {
            ToastUtil.show("未填写餐厅名字");
            return false;
        }
        if (TextUtils.isEmpty(et_prices.getText())) {
            ToastUtil.show("未填写菜品价格");
            return false;
        }
        try {
            if (Float.valueOf(et_prices.getText().toString()) <= 0) {
                ToastUtil.show("价格必须大于0");
                return false;
            }
        } catch (NumberFormatException e) {
            ToastUtil.show("价格不是合法的数字");
            return false;
        }
        return imagePickFragment.check();
    }


}
