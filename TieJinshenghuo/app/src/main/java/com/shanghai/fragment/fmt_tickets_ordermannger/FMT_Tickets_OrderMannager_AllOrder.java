package com.shanghai.fragment.fmt_tickets_ordermannger;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shanghai.App;
import com.shanghai.R;
import com.shanghai.data.data_robtickets.OrderStatus_O_Data;
import com.shanghai.data.data_robtickets.OrderStatus_O_Passengers_Data;
import com.shanghai.data.data_robtickets.OrderStatus_O_Passengers_Returntickets_Data;
import com.shanghai.data.data_robtickets.OrderStatus_O_Result_Data;
import com.shanghai.data.data_robtickets.RespData_order;
import com.shanghai.data.data_utils.RespData;
import com.shanghai.soeasylib.adapter.XXListViewAdapter;
import com.shanghai.soeasylib.util.XXHttpClient;
import com.shanghai.utils.Util;
import com.shanghai.view.CustomListView;

import java.util.ArrayList;
import java.util.List;


/**
 * //TODO 全部订单。到后台取数据并且在后台确认订单最后状态
 * Created by Administrator on 2016/1/11.
 */
public class FMT_Tickets_OrderMannager_AllOrder extends android.support.v4.app.Fragment implements CustomListView.OnRefreshListner {
    private View view;
    private String url = Util.url_my_all;
    private final String TAG = "NewClient";
    private CustomListView lv_allorder;
    private XXListViewAdapter<OrderStatus_O_Data> adapter;
    private List<OrderStatus_O_Data> dataList;
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
        view = inflater.inflate(R.layout.fmt_tickets_ordermannager_allorder, null);
        dataList=new ArrayList<>();
        getAllOrder(App.username);
        initView();
        return view;
    }

    private void initView() {

        lv_allorder = (CustomListView) view.findViewById(R.id.lv_allorder);
        adapter = new XXListViewAdapter<OrderStatus_O_Data>(getActivity(), R.layout.item_lv_allorder) {
            @Override
            public void initGetView(int i, View view, ViewGroup viewGroup) {
                TextView tv_all_orderid = (TextView) view.findViewById(R.id.tv_all_orderid);
                tv_all_orderid.setText(getItem(i).getResult().getOrderid());

                TextView tv_all_status = (TextView) view.findViewById(R.id.tv_all_status);
                tv_all_status.setText(getItem(i).getResult().getMsg());

                TextView tv_all_amount = (TextView) view.findViewById(R.id.tv_all_amount);
                tv_all_amount.setText(getItem(i).getResult().getPassengers()[0].getPrice());

                TextView tv_all_from = (TextView) view.findViewById(R.id.tv_all_from);
                tv_all_from.setText(getItem(i).getResult().getFrom_station_name());

                TextView tv_all_to = (TextView) view.findViewById(R.id.tv_all_to);
                tv_all_to.setText(getItem(i).getResult().getTo_station_name());

                TextView tv_all_time = (TextView) view.findViewById(R.id.tv_all_time);
                tv_all_time.setText(getItem(i).getResult().getSubmit_time());



            }
        };
        lv_allorder.setAdapter(adapter);
        lv_allorder.setOnRefreshListner(this);
    }


    /**
     * 获取所有订单
     *
     * @param username
     */
    private void getAllOrder(String username) {
        XXHttpClient client = new XXHttpClient(url, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, byte[] bytes) {
                Log.d(TAG, "全部订单请求成功，返回：" + new String(bytes));
                RespData_order respData_order = new Gson().fromJson(new String(bytes), RespData_order.class);
                if (new String(bytes) != null && respData_order.getCode() == 200) {
                    Log.d(TAG, "开始查询各订单最新状态");
                    getAllorderDetails(respData_order.getOrders());
                } else {
                    Log.e(TAG, "返回数据解析出错");
                }
            }

            @Override
            public void onError(int i, Throwable throwable) {
                Log.e(TAG, "全部订单请求失败");
            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
        client.put("type", 10);
        client.put("username", username);
        client.doPost(15000);
    }

    /**
     * 查询传入集合中每个订单的最新状态，查询完成后上送服务器
     *
     * @param orders
     */
    private void getAllorderDetails(ArrayList<String> orders) {
        for (final String order : orders) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            serchOrderid(order);
                        }
                    });

                }
            }).start();

        }
    }


    private Handler getHandler_serchOrderid_sendToServiceSQl = null;
    private OrderStatus_O_Result_Data result_data = null;

    /**
     * 查询订单状态
     *
     * @param oerderId
     */
    private void serchOrderid(final String oerderId) {
        Log.d(TAG, "调用查询订单状态的接口，传入orderID=" + oerderId);
        Handler_serchOrderid = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String data = msg.getData().getString("data");

                lv_allorder.onRefreshComplete();
                if (msg.what == 1) {
//
                    final OrderStatus_O_Data orderStatus_o_data = new Gson().fromJson(data, OrderStatus_O_Data.class);
                    if (orderStatus_o_data.getError_code() == 0) {

                        result_data = orderStatus_o_data.getResult();
                        int i = 0;
                        for (OrderStatus_O_Passengers_Data passengers_data : result_data.getPassengers()) {
                            Log.d(TAG, "第" + (i++) + "个 passengers_data对象");
                        }
                        if (i >= 0 && orderStatus_o_data != null && result_data != null) {
                            Log.d(TAG, "订单号：" + result_data.getOrderid() + ";最新状态：" + result_data.getStatus());
//                            dataList.add(orderStatus_o_data);
                            adapter.addItem(orderStatus_o_data);
                            adapter.notifyDataSetChanged();
//                            Log.d(TAG, "查询结果已全部解析完成,改变UI在送望SQL数据库后执行");
////                            //TODO 当数据成功存入数据库时刷新UI
////
                            getHandler_serchOrderid_sendToServiceSQl = new Handler() {
                                @Override
                                public void handleMessage(Message msg) {
//                                    if (svProgressHUD.isShowing(getActivity())) {
//                                        svProgressHUD.dismiss(getActivity());
//                                    }
                                    switch (msg.what) {
                                        case 1:

                                            Log.d(TAG,"订单号："+result_data.getOrderid()+";最新状态："+result_data.getStatus());
                                            //添加到数据库成功，刷新UI
//                                            if (result_data.getStatus().equals("2")) {
////                            btn_order_sub.setEnabled(true);
////                                                tv_order_ticket_no.setText(result_data.getPassengers()[0].getTicket_no());
////                                                tv_order_cxin.setText(result_data.getPassengers()[0].getCxin());
//                                                //服务器存储待支付订单。用户可通过待支付页面支付订单
//                                            }
//                                            if (result_data.getStatus().equals("4")) {
//                                                tv_order_ordernumber.setText(result_data.getOrdernumber());
//
//                                            }
//                                            adapter.addItem(orderStatus_o_data);
//                                            adapter.notifyDataSetChanged();
                                            break;
                                        case -1:
                                            //添加到数据库失败，请重新刷新
                                            Toast.makeText(getActivity(), msg.getData().getString("data"), Toast.LENGTH_LONG).show();
                                            break;
                                    }
                                }
                            };
                            //向服务器发送订单状态以及数据
                            Log.d(TAG, "开始送最新订单信息给后台");
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.d(TAG,"开始发送一条更新语句");
                                            sendTickestStatusAndDataToServiceMySQL(App.username, oerderId, result_data, result_data.getPassengers()[0], result_data.getPassengers()[0].getReturntickets());
                                        }
                                    });
                                }
                            }).start();



                        } else {
//                            if (svProgressHUD.isShowing(getActivity())) {
//                                svProgressHUD.dismiss(getActivity());
//                            }
                            Toast.makeText(getActivity(), "数据异常", Toast.LENGTH_LONG).show();
                            return;
                        }
                    } else {

//                        if (svProgressHUD.isShowing(getActivity())) {
//                            svProgressHUD.dismiss(getActivity());
//                        }
                        Toast.makeText(getActivity(), orderStatus_o_data.getReason(), Toast.LENGTH_LONG).show();
                    }
                } else {

//                    if (svProgressHUD.isShowing(getActivity())) {
//                        svProgressHUD.dismiss(getActivity());
//                    }
                    Toast.makeText(getActivity(), data, Toast.LENGTH_LONG).show();
                }
            }
        };
        XXHttpClient client = new XXHttpClient(Util.url_ticket5, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, byte[] bytes) {

                Log.d(TAG, "订单查询的返回：" + new String(bytes));

                Message me
                        = Handler_serchOrderid.obtainMessage();
                me.what = 1;
                Bundle bundle = new Bundle();
                bundle.putString("data", new String(bytes));
                me.setData(bundle);
                Handler_serchOrderid.sendMessage(me);
            }

            @Override
            public void onError(int i, Throwable throwable) {
                Log.e(TAG, "网络异常");
                Message me
                        = Handler_serchOrderid.obtainMessage();
                me.what = -1;
                Bundle bundle = new Bundle();
                bundle.putString("data", "网络异常");
                me.setData(bundle);
                Handler_serchOrderid.sendMessage(me);
            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
        client.put("key", Util.appid_ticket);
        client.put("orderid", oerderId);
        client.doPost(15000);
        Log.d(TAG, "查询订单状态提交的所有数据：" + client.getAllParams());
    }

    private Handler Handler_serchOrderid = null;

    private void sendTickestStatusAndDataToServiceMySQL(String username, String oerderId, OrderStatus_O_Result_Data result_data,
                                                        OrderStatus_O_Passengers_Data passengers,
                                                        OrderStatus_O_Passengers_Returntickets_Data returntickets_data) {
        XXHttpClient client = new XXHttpClient(Util.url_my_UpOrder, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, byte[] bytes) {
                Log.d(TAG,"后台返回："+new String(bytes));
                RespData respData = new Gson().fromJson(new String(bytes), RespData.class);
                Message message = getHandler_serchOrderid_sendToServiceSQl.obtainMessage();
                Bundle bundle = new Bundle();
                if (respData.getCode() == 200) {
                    message.what = 1;

                } else {
                    message.what = -1;
                }
                bundle.putString("data", respData.getData());
                message.setData(bundle);
                getHandler_serchOrderid_sendToServiceSQl.sendMessage(message);
            }

            @Override
            public void onError(int i, Throwable throwable) {
                Message message = getHandler_serchOrderid_sendToServiceSQl.obtainMessage();
                Bundle bundle = new Bundle();
                message.what = -1;
                bundle.putString("data", "网络异常");
                message.setData(bundle);
                getHandler_serchOrderid_sendToServiceSQl.sendMessage(message);
            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
        client.put("type", 9);
        client.put("username", username);
        client.put("orderId", oerderId);
        client.put("user_orderid", result_data.getUser_orderid());
        client.put("msg", result_data.getMsg());
        client.put("orderamount", result_data.getOrderamount());
        client.put("status", result_data.getStatus());
        client.put("checi", result_data.getCheci());
        client.put("ordernumber", result_data.getOrdernumber());
        client.put("submit_time", result_data.getSubmit_time());
        client.put("deal_time", result_data.getDeal_time());
        client.put("cancel_time", result_data.getCancel_time());
        client.put("pay_time", result_data.getPay_time());
        client.put("finished_time", result_data.getFinished_time());
        client.put("refund_time", result_data.getRefund_time());
        client.put("juhe_refund_time", result_data.getJuhe_refund_time());
        client.put("train_date", result_data.getTrain_date());
        client.put("from_station_name", result_data.getFrom_station_name());
        client.put("from_station_code", result_data.getFrom_station_code());
        client.put("to_station_name", result_data.getTo_station_name());
        client.put("to_station_code", result_data.getTo_station_code());
        client.put("refund_money", result_data.getRefund_money());


        client.put("passengerid", passengers.getPassengerid());
        client.put("passengersename", passengers.getPassengersename());
        client.put("piaotype", passengers.getPiaotype());
        client.put("piaotypename", passengers.getPiaotypename());
        client.put("passporttypeseid", passengers.getPassporttypeseid());
        client.put("passporttypeseidname", passengers.getPassporttypeseidname());
        client.put("passportseno", passengers.getPassportseno());
        client.put("price", passengers.getPrice());
        client.put("zwcode", passengers.getZwcode());
        client.put("zwname", passengers.getZwname());
        client.put("ticket_no", passengers.getTicket_no());
        client.put("cxin", passengers.getCxin());
        client.put("reason", passengers.getReason());

        try {
            client.put("returnsuccess", returntickets_data.isReturnsuccess());
            client.put("returnmoney", returntickets_data.getReturnmoney());
            client.put("returntime", returntickets_data.getReturntime());
            client.put("returnfailid", returntickets_data.getReturnfailid());
            client.put("returnfailmsg", returntickets_data.getReturnfailmsg());
            client.put("returntype", returntickets_data.getReturntype());
        } catch (Throwable throwable) {
            Log.e(TAG, "退款部分数据抛出异常");
        }


        client.doPost(15000);


    }

    @Override
    public void onRefresh() {
        getAllOrder(App.username);
    }
}
