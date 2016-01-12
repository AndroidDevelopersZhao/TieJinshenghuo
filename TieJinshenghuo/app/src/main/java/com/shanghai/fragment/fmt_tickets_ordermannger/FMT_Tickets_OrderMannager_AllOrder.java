package com.shanghai.fragment.fmt_tickets_ordermannger;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shanghai.App;
import com.shanghai.R;
import com.shanghai.data.data_robtickets.OrderStatus_O_Data;
import com.shanghai.data.data_robtickets.RespData_order;
import com.shanghai.listener.listener_tickets.OnGetOrderIdListener;
import com.shanghai.soeasylib.util.XXHttpClient;
import com.shanghai.utils.Util;

import java.util.ArrayList;


/**
 * //TODO 全部订单。到后台取数据并且在后台确认订单最后状态
 * Created by Administrator on 2016/1/11.
 */
public class FMT_Tickets_OrderMannager_AllOrder extends android.support.v4.app.Fragment {
    private View view;
    private String url = Util.url_my;
    private final String TAG = "NewClient";
    private Handler handler = null;
    /**
     * 1.交换秘钥    -----成功例子-----{"code"=200,"data"="key"}
     * 2.注册账号    -----成功例子-----{"code"=200,"data"="注册成功"}
     * 3.登陆账号    -----成功例子-----{"code"=200,"data"="验证通过"}
     * 4.找回密码    -----成功例子-----{"code"=200,"data"="新密码设置成功"}
     * 5.查询余额    -----成功例子-----{"code"=200,"data"="100.25"}
     * 6.插入购票人信息  -----成功例子-----{"code"=200,"data"="数据插入成功"}
     * 7.查询购票人信息       未支付、带出票、待出行、
     * 8.插入订单信息
     * 9.更新车票订单信息表
     * 10.获取全部订单
     * 11.获取未支付订单
     * 12.获取待出票订单
     * 13.获取出票成功的订单
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fmt_tickets_ordermannager_allorder,null);


        Log.w(TAG,"进入全部订单页面");
//        if (App.username != null) {
////            Log.d(TAG, "开始请求全部订单");
//            Util.getOrderId(App.username, 10, new OnGetOrderIdListener() {
//                @Override
//                public void onSucc(ArrayList<String> orders) {
//                    Toast.makeText(getActivity(),"该账户的全部订单:"+orders.toString(),Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onError(String errorMsg) {
//                    Toast.makeText(getActivity(),errorMsg,Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
        return view;
    }


}
