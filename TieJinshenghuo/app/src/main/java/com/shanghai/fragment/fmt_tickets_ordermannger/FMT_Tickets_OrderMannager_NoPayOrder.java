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
 * Created by Administrator on 2016/1/11.
 */
public class FMT_Tickets_OrderMannager_NoPayOrder extends android.support.v4.app.Fragment{
    private View view;
    private final String TAG = "NewClient";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fmt_tickets_ordermannager_nopayorder,null);
        Log.w(TAG, "进入未支付订单页面");


        XXHttpClient client= new XXHttpClient(Util.url_my,true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, byte[] bytes) {
                Log.d(TAG,"未支付订单返回："+new String(bytes));
                RespData_order order = new Gson().fromJson(new String(bytes), RespData_order.class);
                Log.d(TAG,"用户未支付订单号："+order.getOrders().toString());
                Log.d(TAG,"返回码："+order.getCode());
            }

            @Override
            public void onError(int i, Throwable throwable) {

            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
        client.put("username",App.username);
        client.put("type",11);
        client.doPost(15000);
//        if (App.username != null) {
////            Log.d(TAG, "开始请求未支付订单");
//            Util.getOrderId(App.username, 11, new OnGetOrderIdListener() {
//                @Override
//                public void onSucc(ArrayList<String> orders) {
//                    Toast.makeText(getActivity(),"该账户的未支付订单:"+ orders.toString(), Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onError(String errorMsg) {
//                    Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
        return view;
    }
}
