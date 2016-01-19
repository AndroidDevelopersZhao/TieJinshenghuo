package com.shanghai.fragment.fmt_home_navigation;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shanghai.R;
import com.shanghai.listener.listener_util.OnInitNavigationListener;
import com.shanghai.utils.Util;
import com.shanghai.utils.util_navigation.Util_Navigation;

/**
 * Created by Administrator on 2016/1/19.
 */
public class NavigationFirstView extends Fragment {
    private View view;
    private String TAG = "NewClient";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fmt_navigation_first, null);
        new Util_Navigation(getActivity(), new OnInitNavigationListener() {
            @Override
            public void onSucc() {
                Log.d(TAG, "导航SDK初始化成功");
            //先装个逼回来在写
            }

            @Override
            public void onError(String msg) {
                Log.e(TAG, "导航SDK初始化失败，" + msg);
            }
        });
        return view;
    }

}
