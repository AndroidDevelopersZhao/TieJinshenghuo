package com.shanghai.listener.listener_getimages;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016/1/20.
 */
public interface OnGetImagesListener {
    void onSucc(Bitmap bitmap);
    void onError(String errorMsg);
}
