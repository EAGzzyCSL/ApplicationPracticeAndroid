package com.bit.schoolcomment.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.fragment.ImagePickFragment;
import com.bit.schoolcomment.util.PullUtil;
import com.bit.schoolcomment.util.ToastUtil;
import com.google.gson.Gson;

public class AddCommentActivity extends BaseActivity
        implements View.OnClickListener {
    private ImagePickFragment fragment_imagePick;
    private Button add_comment_button;
    private EditText et_content;
    private RatingBar rb_rate;

    @Override
    protected boolean isEventBusOn() {
        return false;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_add_comment;
    }

    @Override
    protected void initView() {
        initToolbar(R.id.add_comment_toolbar, "添加新的评论");
        add_comment_button = (Button) findViewById(R.id.add_comment_button);
        fragment_imagePick = (ImagePickFragment) getFragmentManager().findFragmentById(R.id.fragment_imagePick);
        fragment_imagePick.init(3, new ImagePickFragment.OnImageUploadDoneListener() {
                    @Override
                    public void onImageUploadDone(String images) {
                        int goods_id = getIntent().getIntExtra(GoodsActivity.EXTRA_goodsId, 0);
                        PullUtil.getInstance().addComment(
                                et_content.getText().toString(),
                                goods_id,
                                rb_rate.getRating(),
                                images
                        );
                    }
                }, R.layout.item_image_pick_fresco
        );
        add_comment_button.setOnClickListener(this);
        et_content = (EditText) findViewById(R.id.et_content);
        rb_rate = (RatingBar) findViewById(R.id.rb_rate);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_comment_button:
                if (formCheck()) {
                    submitComment();
                }
                break;
        }
    }

    private boolean formCheck() {
        if (TextUtils.isEmpty(et_content.getText())) {
            ToastUtil.show("请填写你对这道菜的评价");
            return false;
        }
        if (rb_rate.getRating() <= 0) {
            ToastUtil.show("请填写对这道菜的评星");
            return false;
        }
        return true;
    }

    private void submitComment() {

        fragment_imagePick.upload();
        finish();
    }
}
