package com.shanghai.fragment.fmt_tickets_ordermannger;

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
 * Created by Administrator on 2016/1/12.
 */
public class FMT_Tickets_OrderMannager_SuccOrder extends android.support.v4.app.Fragment {
    private View view;
    private final String TAG = "NewClient";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fmt_tickets_ordermannager_cancleorder,null);
        Log.w(TAG,"进入出票成功页面");
//        if (App.username != null) {
////            Log.d(TAG, "开始请求待出行订单");
//            Util.getOrderId(App.username, 12, new OnGetOrderIdListener() {
//                @Override
//                public void onSucc(ArrayList<String> orders) {
//                    Toast.makeText(getActivity(), "该账户的待出行订单:" + orders.toString(), Toast.LENGTH_SHORT).show();
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