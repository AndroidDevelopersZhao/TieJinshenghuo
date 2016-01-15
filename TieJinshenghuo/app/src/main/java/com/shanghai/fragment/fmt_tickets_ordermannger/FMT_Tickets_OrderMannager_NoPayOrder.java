package com.shanghai.fragment.fmt_tickets_ordermannger;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shanghai.App;
import com.shanghai.R;
import com.shanghai.data.data_robtickets.RespData_order;
import com.shanghai.listener.listener_tickets.OnGetOrderIdListener;
import com.shanghai.soeasylib.util.XXHttpClient;
import com.shanghai.utils.Util;

import java.util.ArrayList;

/**
 * TODO 待支付页面
 * Created by Administrator on 2016/1/11.
 */
public class FMT_Tickets_OrderMannager_NoPayOrder extends
        android.support.v4.app.Fragment implements OnGetOrderIdListener {
    private View view;
    private final String TAG = "NewClient";
    private String username = App.username;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fmt_tickets_ordermannager_nopayorder, null);
        Util.getOrderIdFromService(username, 11, this);//获取待支付订单
        return view;
    }

    @Override
    public void onSucc(ArrayList<String> orders) {
        Log.d(TAG, "待支付订单获取成功，" + orders.toString());
    }

    @Override
    public void onError(String errorMsg) {
        Log.e(TAG, "待支付订单获取失败," + errorMsg);
    }
}
