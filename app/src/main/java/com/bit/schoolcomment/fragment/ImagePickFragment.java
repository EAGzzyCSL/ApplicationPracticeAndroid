package com.bit.schoolcomment.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.event.GetTokenEvent;
import com.bit.schoolcomment.util.PullUtil;
import com.bit.schoolcomment.util.QiniuUtil;
import com.bit.schoolcomment.util.ToastUtil;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ImagePickFragment extends Fragment {
    private String qiniuToken = null;
    private int maxImage = 3;
    public static int REQUEST_code_pick_image = 333;
    private HashMap<ImageView, File> imageList = new HashMap<>(maxImage);
    private LinearLayout imageLinear;
    private ImageView currentClick = null;
    private View.OnClickListener onImageViewClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
            getIntent.setType("image/*");
            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");
            Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
            currentClick = (ImageView) v;
            startActivityForResult(
                    chooserIntent, ImagePickFragment.REQUEST_code_pick_image);
        }
    };

    private ArrayList<String> imageHash = new ArrayList<>();

    private ProgressBar pgb_uploading;
    private OnImageUploadDoneListener onImageUploadDoneListener;


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
        imageLinear.addView(getImageView());
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private ImageView getImageView() {
        ImageView imageView = new ImageView(getActivity());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
        imageView.setImageResource(R.drawable.ic_add_photo);
        imageView.setOnClickListener(onImageViewClick);
        return imageView;
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
                File imageFile = new File(getRealPathFromURI(uri));
                imageList.put(currentClick, imageFile);
                Log.i("QQQ", imageFile.toString());
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                    if (currentClick != null) {
                        currentClick.setImageBitmap(bitmap);
                    }
                    addImageView();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
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

    public void upload() {
        if (qiniuToken == null) {
            ToastUtil.show("token尚未获取");
        } else {
            for (int i = 0; i < imageLinear.getChildCount(); i++) {
                File f = imageList.get((ImageView) imageLinear.getChildAt(i));
                if (f == null) {
                    break;
                }
                QiniuUtil.
                        getInstance().
                        put(f,
                                null,
                                qiniuToken,
                                new UpCompletionHandler() {
                                    @Override
                                    public void complete(String key, ResponseInfo info, JSONObject response) {
                                        if (info.isOK()) {
                                            if (response != null) {
                                                try {
                                                    String hash = response.getString("hash");
                                                    Log.i("图片hash:", hash);
                                                    imageHash.add(hash);
                                                    if (imageHash.size() == imageList.size()) {
                                                        submitForm();
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        } else {
                                            ToastUtil.show("上传图片失败");
                                        }
                                    }
                                },
                                new UploadOptions(
                                        null, null, false,
                                        new UpProgressHandler() {
                                            public void progress(String key, double percent) {
                                                pgb_uploading.setProgress((int) (percent * 100));
                                                Log.i("XXX", key + ": " + percent);
                                            }
                                        }, null));
            }
        }
    }

    public void submitForm() {
        // 发送提交表单事件
        onImageUploadDoneListener.onImageUploadDone(imageHash);
    }

    public ArrayList<String> getImageHash() {
        return this.imageHash;
    }

    public boolean check() {
        if (imageList.isEmpty()) {
            ToastUtil.show("未选择图片");
            return false;
        } else {
            return true;
        }
    }

    @Subscribe
    public void handleOnTokenReceive(GetTokenEvent getTokenEvent) {
        if (getTokenEvent.targetClass == getClass()) {
            qiniuToken = getTokenEvent.token;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    public void init(int maxImage, OnImageUploadDoneListener onImageUploadDoneListener) {
        this.maxImage = maxImage;
        this.onImageUploadDoneListener = onImageUploadDoneListener;
    }

    public interface OnImageUploadDoneListener {
        void onImageUploadDone(ArrayList<String> imageHash);
    }
}
