package com.shanghai.fragment.fmt_tickets_ordermannger;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shanghai.App;
import com.shanghai.R;
import com.shanghai.listener.listener_tickets.OnGetOrderIdListener;
import com.shanghai.utils.Util;

import java.util.ArrayList;

/**
 * TODO 已付款待出票页面
 * Created by Administrator on 2016/1/11.
 */
public class FMT_Tickets_OrderMannager_PayedOrder extends android.support.v4.app.Fragment implements OnGetOrderIdListener {
    private View view;
    private final String TAG = "NewClient";
    private String username = App.username;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fmt_tickets_ordermannager_payedorder,null);
        Util.getOrderIdFromService(username, 12, this);//获取已付款待出票订单
        return view;
    }

    @Override
    public void onSucc(ArrayList<String> orders) {
        Log.d(TAG, "已支付待出票订单获取成功，" + orders.toString());
    }

    @Override
    public void onError(String errorMsg) {
        Log.e(TAG, "已支付待出票订单获取失败," + errorMsg);
    }
}
