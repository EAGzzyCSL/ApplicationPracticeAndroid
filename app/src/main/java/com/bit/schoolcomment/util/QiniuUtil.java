package com.bit.schoolcomment.util;

import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;

public class QiniuUtil {

    private static volatile UploadManager sUploadManager;

    public static UploadManager getInstance() {
        if (sUploadManager == null) {
            synchronized (QiniuUtil.class) {
                if (sUploadManager == null) {
                    sUploadManager = new UploadManager(new Configuration.Builder().build());
                }
            }
        }
        return sUploadManager;
    }

    // 参考文档：http://developer.qiniu.com/code/v7/sdk/android.html
}
