package com.shanghai.fragment.fmt_tickets_ordermannger;

import android.app.Fragment;
import android.os.Bundle;
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
 * Created by Administrator on 2016/1/11.
 */
public class FMT_Tickets_OrderMannager_PayedOrder extends android.support.v4.app.Fragment {
    private View view;
    private final String TAG = "NewClient";
    private String url = Util.url_my;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fmt_tickets_ordermannager_payedorder,null);
        if (App.username != null) {
            getPayedLodingOutTicketsOrder(App.username);
        }
        return view;
    }

    /**
     * 获取待出票订单
     * @param username
     */
    private void getPayedLodingOutTicketsOrder(String username) {
        XXHttpClient client = new XXHttpClient(url, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, byte[] bytes) {
                Log.d(TAG, "已支付待出票订单请求成功，返回：" + new String(bytes));
                RespData_order respData_order = new Gson().fromJson(new String(bytes), RespData_order.class);
                Log.w(TAG,respData_order.getResult());
            }

            @Override
            public void onError(int i, Throwable throwable) {
                Log.d(TAG, "已支付待出票订单请求失败");
            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
        client.put("type", 12);
        client.put("username", username);
        client.doPost(15000);
    }
}
