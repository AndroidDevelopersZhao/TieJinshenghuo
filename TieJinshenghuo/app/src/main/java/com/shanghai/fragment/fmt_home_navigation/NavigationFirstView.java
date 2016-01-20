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
import com.shanghai.soeasylib.util.XXUtils;
import com.shanghai.utils.util_navigation.Util_Navigation;

import java.text.SimpleDateFormat;
import java.util.Date;

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
                Log.e(TAG, new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(new Date()));
                //TODO 先装个逼回来在写
                /*
                关关雎鸠，在河之洲。
                窈窕淑女，君子好逑。
                参差荇菜，左右流之。
                窈窕淑女，寤寐求之。
                求之不得，寤寐思服。
                优哉游哉，辗转反侧。
                参差荇菜，左右采之。
                窈窕淑女，琴瑟友之。
                参差荇菜，左右毛之。
                窈窕淑女，钟鼓乐之。
                 */
            }

            @Override
            public void onError(String msg) {
                Log.e(TAG, "导航SDK初始化失败，" + msg);
            }
        });
        return view;
    }

}
