package com.shanghai.fragment.fmt_home_addphoneamount;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shanghai.R;

/**
 * Created by Administrator on 2016/1/25.
 */
public class AddAmountHome extends Fragment {
    private View view;
    private String TAG = "NewClient";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fmt_addamounthome, null);
        Log.d(TAG, getArguments().getString("username"));

        return view;
    }
}
