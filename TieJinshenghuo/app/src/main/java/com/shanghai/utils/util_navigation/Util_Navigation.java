package com.shanghai.utils.util_navigation;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.shanghai.listener.listener_util.OnInitNavigationListener;

import java.io.File;

/**
 * Created by Administrator on 2016/1/19.
 */
public class Util_Navigation {
    private OnInitNavigationListener onInitNavigationListener;
    private Activity activity;
    private String authinfo;
    private String mSDCardPath;
    private String APP_FOLDER_NAME = "TieJinshenghuo";

    public Util_Navigation(Activity activity, OnInitNavigationListener onInitNavigationListener) {
        this.activity = activity;
        this.onInitNavigationListener = onInitNavigationListener;
        if (initDirs()) {
            initNavi();
        }

    }

    private void initNavi() {
        BaiduNaviManager.getInstance().init(activity, mSDCardPath, APP_FOLDER_NAME,
                new BaiduNaviManager.NaviInitListener() {
                    @Override
                    public void onAuthResult(int status, String msg) {
                        if (0 == status) {
                            authinfo = "key校验成功!";
                            if (onInitNavigationListener != null) {
                                onInitNavigationListener.onSucc();
                            }
                        } else {
                            authinfo = "key校验失败, " + msg;
                            if (onInitNavigationListener != null) {
                                onInitNavigationListener.onError(msg);
                            }
                        }
                        Log.d("OOOOOOOOOOOO", authinfo);
                        activity.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
//                                Toast.makeText(activity, authinfo, Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    public void initSuccess() {
//                        Toast.makeText(activity, "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
                    }

                    public void initStart() {
//                        Toast.makeText(activity, "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
                    }

                    public void initFailed() {
                        Toast.makeText(activity, "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
                    }
                }, null /*mTTSCallback*/);
    }

    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }
}
