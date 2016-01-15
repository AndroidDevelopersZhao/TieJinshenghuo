package com.shanghai.fragment.fmt_tickets_ordermannger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shanghai.App;
import com.shanghai.R;
import com.shanghai.listener.listener_tickets.OnGetOrderIdListener;
import com.shanghai.utils.Util;

import java.util.ArrayList;

/**
 * TODO 出票成功页面
 * Created by Administrator on 2016/1/12.
 */
public class FMT_Tickets_OrderMannager_SuccOrder extends android.support.v4.app.Fragment implements OnGetOrderIdListener {
    private View view;
    private final String TAG = "NewClient";
    private String username = App.username;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fmt_tickets_ordermannager_cancleorder, null);
        Util.getOrderIdFromService(username, 12, this);//获取出票成功订单
        return view;
    }

    @Override
    public void onSucc(ArrayList<String> orders) {
        Log.d(TAG, "出票成功订单获取成功，" + orders.toString());
    }

    @Override
    public void onError(String errorMsg) {
        Log.e(TAG, "出票成功订单获取失败," + errorMsg);
    }
}