package com.bit.schoolcomment.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.event.GetTokenEvent;
import com.bit.schoolcomment.util.DimensionUtil;
import com.bit.schoolcomment.util.PullUtil;
import com.bit.schoolcomment.util.QiniuUtil;
import com.bit.schoolcomment.util.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ImagePickFragment extends Fragment {
    private final String QiNiu_url = "http://ocsyd0pft.bkt.clouddn.com/";
    public static int REQUEST_code_pick_image = 333;
    // fragment init
    private int maxImage = 3;
    private OnImageUploadDoneListener onImageUploadDoneListener;
    // 存放image的file和返回的hash
    private HashMap<SimpleDraweeView, File> imageList = new HashMap<>(maxImage);
    private ArrayList<String> imageHash = new ArrayList<>();
    // token
    private String qiniuToken = null;
    // view部分
    private LinearLayout imageLinear;
    private SimpleDraweeView currentClick = null;
    private ProgressBar pgb_uploading;
    private View pgb_uploading_mask;
    private int imageViewXml;
    private View.OnClickListener onImageViewClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
            getIntent.setType("image/*");
            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");
            Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
            currentClick = (SimpleDraweeView) v;
            startActivityForResult(
                    chooserIntent, ImagePickFragment.REQUEST_code_pick_image);
        }
    };
    private String defaultImgUri;
    // 是否在上传中
    private boolean inUploading = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        PullUtil.getInstance().get_qiniu_token(getClass());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_pick_image, container, false);
        imageLinear = (LinearLayout) v.findViewById(R.id.linear_imageContainer);
        pgb_uploading = (ProgressBar) v.findViewById(R.id.pgb_uploading);
        pgb_uploading_mask = v.findViewById(R.id.pgb_uploading_mask);
        return v;
    }

    /**
     * 初始化该fragment
     *
     * @param maxImage                  最多允许选择的image数量
     * @param onImageUploadDoneListener 当image上传完成后的回调
     */
    public void init(int maxImage, OnImageUploadDoneListener onImageUploadDoneListener, int imageViewXml) {
        this.maxImage = maxImage;
        this.onImageUploadDoneListener = onImageUploadDoneListener;
        this.imageViewXml = imageViewXml;
        addImageView();
    }

    public void init(int maxImage, OnImageUploadDoneListener onImageUploadDoneListener,
                     int imageViewXml, String defaultImgUri) {
        this.maxImage = maxImage;
        this.onImageUploadDoneListener = onImageUploadDoneListener;
        this.imageViewXml = imageViewXml;
        this.defaultImgUri = defaultImgUri;
        addImageView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    private SimpleDraweeView getImageView() {
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) LayoutInflater.
                from(getActivity()).inflate(imageViewXml, imageLinear, false);
        if (!TextUtils.isEmpty(defaultImgUri)) {
            simpleDraweeView.setImageURI(defaultImgUri);
        }
        simpleDraweeView.setOnClickListener(onImageViewClick);
        return simpleDraweeView;
    }

    private void addImageView() {
        if (imageLinear.getChildCount() < maxImage) {
            imageLinear.addView(getImageView());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_code_pick_image && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                File imageFile = new File(getRealPathFromURI(getActivity(), uri));
                imageList.put(currentClick, imageFile);
                currentClick.setImageURI(uri);
                addImageView();
            }
        }
    }


    public boolean check() {
        if (imageList.isEmpty()) {
            ToastUtil.show("未选择图片");
            return false;
        } else {
            return true;
        }
    }

    public void imageUploadDone() {
        onImageUploadDoneListener.onImageUploadDone(
                new Gson().toJson(imageHash)
        );
    }


    public void upload() {
        if (inUploading) {
            return;
        }
        if (qiniuToken == null) {
            ToastUtil.show("token尚未获取");
        } else {
            inUploading = true;
            int i = 0;
            for (; i < imageLinear.getChildCount(); i++) {
                File f = imageList.get((SimpleDraweeView) imageLinear.getChildAt(i));
                if (f == null) {
                    break;
                }
                if (pgb_uploading_mask.getVisibility() == View.INVISIBLE) {
                    pgb_uploading_mask.setVisibility(View.VISIBLE);
                }
                QiniuUtil.
                        getInstance().
                        put(f, null,
                                qiniuToken,
                                new UpCompletionHandler() {
                                    @Override
                                    public void complete(String key, ResponseInfo info, JSONObject response) {
                                        if (info.isOK()) {
                                            if (response != null) {
                                                try {
                                                    String hash = response.getString("hash");
                                                    Log.i("图片hash:", hash);
                                                    imageHash.add(QiNiu_url + hash);
                                                    if (imageHash.size() == imageList.size()) {
                                                        pgb_uploading_mask.setVisibility(View.INVISIBLE);
                                                        imageUploadDone();
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        } else {
                                            ToastUtil.show("上传图片失败");
                                        }
                                    }
                                }, null);
            }
            // 如果没有图片要上传的时候也调用done
            if (i == 0) {
                imageUploadDone();
            }
        }
    }

    @Subscribe
    public void handleOnTokenReceive(GetTokenEvent getTokenEvent) {
        if (getTokenEvent.targetClass == getClass()) {
            qiniuToken = getTokenEvent.token;
        }
    }

    private String getRealPathFromURI(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public interface OnImageUploadDoneListener {
        void onImageUploadDone(String imageJson);
    }

}
