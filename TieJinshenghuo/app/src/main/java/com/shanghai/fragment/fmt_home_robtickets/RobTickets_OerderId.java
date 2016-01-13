package com.shanghai.fragment.fmt_home_robtickets;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shanghai.R;
import com.shanghai.data.data_robtickets.OrderStatus_O_Data;
import com.shanghai.data.data_robtickets.OrderStatus_O_Passengers_Data;
import com.shanghai.data.data_robtickets.OrderStatus_O_Passengers_Returntickets_Data;
import com.shanghai.data.data_robtickets.OrderStatus_O_Result_Data;
import com.shanghai.data.data_utils.RespData;
import com.shanghai.data.data_robtickets.TicketsPayAmountData;
import com.shanghai.soeasylib.adapter.XXListViewAdapter;
import com.shanghai.soeasylib.util.XXHttpClient;
import com.shanghai.view.CustomListView;
import com.shanghai.utils.Util;

import xinfu.com.pidanview.alerterview.alerterview.AlertView;
import xinfu.com.pidanview.alerterview.alerterview.OnItemClickListener;
import xinfu.com.pidanview.alerterview.progress.SVProgressHUD;

/**
 * Created by Administrator on 2016/1/10.
 */
public class RobTickets_OerderId extends Fragment implements View.OnClickListener {
    private View view;
    private String oerderId = null;
    private Handler Handler_serchOrderid = null;
    private String TAG = "NewClient";
    private SVProgressHUD svProgressHUD = new SVProgressHUD();
    private XXListViewAdapter<OrderStatus_O_Data> adapter;
    private CustomListView lv_orderId;
    private Button btn_order_sub;
    private TextView tv_order_ticket_no;//票号
    private TextView tv_order_cxin;//座位号
    private Handler handler_pay = null;
    private OrderStatus_O_Result_Data result_data = null;
    private TextView tv_order_ordernumber;//取票订单号
    private TextView tv_order_price;
    private Handler getHandler_serchOrderid_sendToServiceSQl = null;

    @Override
    public void onDestroy() {
        if (svProgressHUD.isShowing(getActivity())) {
            svProgressHUD.dismiss(getActivity());

        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fmt_robtickets_oerderid, null);
        initView();
        initData();
        return view;
    }

    private void initData() {
        try {
            oerderId = getArguments().getString("oerderId");
            username = getArguments().getString("username");
            svProgressHUD.showWithStatus(getActivity(), "正在查询订单状态...");
            serchOrderid(oerderId);
        } catch (Throwable throwable) {
            Toast.makeText(getActivity(), "订单号为空，请重试", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(getActivity(), "查询最新订单状态请下拉列表，订单状态为待支付时才能付款哦", Toast.LENGTH_LONG).show();
    }

    private void initView() {
        lv_orderId = (CustomListView) view.findViewById(R.id.lv_orderId);
        adapter = new XXListViewAdapter<OrderStatus_O_Data>(getActivity(), R.layout.item_orderid) {
            @Override
            public void initGetView(int i, View view, ViewGroup viewGroup) {

                TextView tv_order_checi = (TextView) view.findViewById(R.id.tv_order_checi);
                TextView tv_order_train_date = (TextView) view.findViewById(R.id.tv_order_train_date);
                TextView tv_order_from_station_name = (TextView) view.findViewById(R.id.tv_order_from_station_name);
                TextView tv_order_to_station_name = (TextView) view.findViewById(R.id.tv_order_to_station_name);
                TextView tv_order_orderid = (TextView) view.findViewById(R.id.tv_order_orderid);
                TextView tv_order_status = (TextView) view.findViewById(R.id.tv_order_status);
                tv_order_price = (TextView) view.findViewById(R.id.tv_order_price);
                TextView tv_order_submit_time = (TextView) view.findViewById(R.id.tv_order_submit_time);
                TextView tv_order_passengersename = (TextView) view.findViewById(R.id.tv_order_passengersename);
                TextView tv_order_passporttypeseidname = (TextView) view.findViewById(R.id.tv_order_passporttypeseidname);
                TextView tv_order_passportseno = (TextView) view.findViewById(R.id.tv_order_passportseno);

                tv_order_ticket_no = (TextView) view.findViewById(R.id.tv_order_ticket_no);
                tv_order_cxin = (TextView) view.findViewById(R.id.tv_order_cxin);

                tv_order_ordernumber = (TextView) view.findViewById(R.id.tv_order_ordernumber);
                btn_order_sub = (Button) view.findViewById(R.id.btn_order_sub);
                btn_order_sub.setOnClickListener(RobTickets_OerderId.this);
                tv_order_checi.setText(getItem(i).getResult().getCheci());
                tv_order_train_date.setText(getItem(i).getResult().getTrain_date());
                tv_order_from_station_name.setText(getItem(i).getResult().getFrom_station_name());
                tv_order_to_station_name.setText(getItem(i).getResult().getTo_station_name());
                tv_order_orderid.setText(getItem(i).getResult().getOrderid());
                tv_order_status.setText(getItem(i).getResult().getMsg());
                tv_order_price.setText(getItem(i).getResult().getPassengers()[0].getPrice());

                tv_order_submit_time.setText(getItem(i).getResult().getSubmit_time());

                tv_order_passengersename.setText(getItem(i).getResult().getPassengers()[0].getPassengersename());

                tv_order_passporttypeseidname.setText(getItem(i).getResult().getPassengers()[0].getPassporttypeseidname());

                tv_order_passportseno.setText(getItem(i).getResult().getPassengers()[0].getPassportseno());

                btn_order_sub.setEnabled(false);
                if (result_data.getStatus().equals("2")) {
                    btn_order_sub.setEnabled(true);
                }
                if (result_data.getStatus().equals("4")) {
                    btn_order_sub.setEnabled(false);

                }

            }
        };
        lv_orderId.setAdapter(adapter);
        lv_orderId.setOnRefreshListner(new CustomListView.OnRefreshListner() {
            @Override
            public void onRefresh() {
                adapter.removeAll();
                serchOrderid(oerderId);

            }
        });
    }

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

                lv_orderId.onRefreshComplete();
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
                            Log.d(TAG, "查询结果已全部解析完成,改变UI在送望SQL数据库后执行");
                            //TODO 当数据成功存入数据库时刷新UI

                            getHandler_serchOrderid_sendToServiceSQl = new Handler() {
                                @Override
                                public void handleMessage(Message msg) {
                                    if (svProgressHUD.isShowing(getActivity())) {
                                        svProgressHUD.dismiss(getActivity());
                                    }
                                    switch (msg.what) {
                                        case 1:
                                            //添加到数据库成功，刷新UI
                                            if (result_data.getStatus().equals("2")) {
//                            btn_order_sub.setEnabled(true);
                                                tv_order_ticket_no.setText(result_data.getPassengers()[0].getTicket_no());
                                                tv_order_cxin.setText(result_data.getPassengers()[0].getCxin());
                                                //服务器存储待支付订单。用户可通过待支付页面支付订单
                                            }
                                            if (result_data.getStatus().equals("4")) {
                                                tv_order_ordernumber.setText(result_data.getOrdernumber());

                                            }
                                            adapter.addItem(orderStatus_o_data);
                                            adapter.notifyDataSetChanged();
                                            break;
                                        case -1:
                                            //添加到数据库失败，请重新刷新
                                            Toast.makeText(getActivity(), msg.getData().getString("data"), Toast.LENGTH_LONG).show();
                                            break;
                                    }
                                }
                            };
                            //向服务器发送订单状态以及数据
                            sendTickestStatusAndDataToServiceMySQL(username, oerderId, result_data, result_data.getPassengers()[0], result_data.getPassengers()[0].getReturntickets());


                        } else {
                            if (svProgressHUD.isShowing(getActivity())) {
                                svProgressHUD.dismiss(getActivity());
                            }
                            Toast.makeText(getActivity(), "数据异常", Toast.LENGTH_LONG).show();
                            return;
                        }
                    } else {

                        if (svProgressHUD.isShowing(getActivity())) {
                            svProgressHUD.dismiss(getActivity());
                        }
                        Toast.makeText(getActivity(), orderStatus_o_data.getReason(), Toast.LENGTH_LONG).show();
                    }
                } else {

                    if (svProgressHUD.isShowing(getActivity())) {
                        svProgressHUD.dismiss(getActivity());
                    }
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

    private void sendTickestStatusAndDataToServiceMySQL(String username, String oerderId, OrderStatus_O_Result_Data result_data,
                                                        OrderStatus_O_Passengers_Data passengers,
                                                        OrderStatus_O_Passengers_Returntickets_Data returntickets_data) {
        XXHttpClient client = new XXHttpClient(Util.url_my, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, byte[] bytes) {
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
        }catch (Throwable throwable){
            Log.e(TAG,"退款部分数据抛出异常");
        }




        client.doPost(15000);


    }


    /**
     * 为订单付款
     *
     * @param orderid
     */
    private void payAmount(String orderid) {

        handler_pay = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (svProgressHUD.isShowing(getActivity())) {

                    svProgressHUD.dismiss(getActivity());
                }
                String data = msg.getData().getString("data");
                switch (msg.what) {
                    case 1:
//                        svProgressHUD.showSuccessWithStatus(getActivity(), "付款成功，等待出票");
                        TicketsPayAmountData amountData = new Gson().fromJson(data, TicketsPayAmountData.class);
                        String Msg = amountData.getReason();
                        if (amountData.getError_code() == 0) {
                            svProgressHUD.showSuccessWithStatus(getActivity(), "付款成功，等待出票,请刷新页面");
                        } else {
                            Toast.makeText(getActivity(), Msg, Toast.LENGTH_LONG).show();
                        }
                        break;

                    case -1:
                        Toast.makeText(getActivity(), data, Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };
        XXHttpClient client = new XXHttpClient(Util.url_ticket4, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, byte[] bytes) {
                Log.d(TAG, "付款的返回：" + new String(bytes));
                Message message = handler_pay.obtainMessage();
                message.what = 1;
                Bundle bundle = new Bundle();
                bundle.putString("data", new String(bytes));
                message.setData(bundle);
                handler_pay.sendMessage(message);
            }

            @Override
            public void onError(int i, Throwable throwable) {
                Log.e(TAG, "网络异常");
                Message message = handler_pay.obtainMessage();
                message.what = -1;
                Bundle bundle = new Bundle();
                bundle.putString("data", "网络异常");
                message.setData(bundle);
                handler_pay.sendMessage(message);
            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
        client.put("key", Util.appid_ticket);
        client.put("orderid", orderid);
        client.doPost(15000);


    }

    private String username = null;
    private Handler handler = null;

    @Override
    public void onClick(View v) {
        svProgressHUD.showWithStatus(getActivity(), "正在检查余额...");

//

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (svProgressHUD.isShowing(getActivity())) {
                    svProgressHUD.dismiss(getActivity());
                }
                switch (msg.what) {
                    case 1:
                        Double amount = Double.valueOf(msg.getData().getString("data"));
                        Double price = Double.valueOf(tv_order_price.getText().toString().trim());
                        if (amount <= price) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(500);
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                new AlertView("提示", "账户余额不足，请充值", "取消", new String[]{"充值"}, null,
                                                        getActivity(), AlertView.Style.Alert, new OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(Object o, int position) {
                                                        Log.d(TAG, position + "");
                                                        if (position == 0) {
                                                            Intent intent = new Intent();
                                                            intent.setAction("android.intent.action.VIEW");
                                                            Uri content_url = Uri.parse("http://wpa.qq.com/msgrd?v=3&uin=3648415&site=qq&menu=yes");
                                                            intent.setData(content_url);
                                                            startActivity(intent);
                                                        }
                                                    }
                                                }).show();
                                            }
                                        });
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getActivity(), "异常关闭", Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                }
                            }).start();

                            return;

                        } else {
                            payAmount(oerderId);
                        }
                        break;

                    case -1:
                        Toast.makeText(getActivity(), msg.getData().getString("data"), Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };

        XXHttpClient client = new XXHttpClient(Util.url_my, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, byte[] bytes) {
                RespData respData = new Gson().fromJson(new String(bytes), RespData.class);
                Message message_getAmount = handler.obtainMessage();
                Bundle bundle = new Bundle();
                if (respData.getCode() == 200) {
                    message_getAmount.what = 1;


                } else {
                    message_getAmount.what = -1;
                }
                bundle.putString("data", respData.getData());
                message_getAmount.setData(bundle);
                handler.sendMessage(message_getAmount);
            }

            @Override
            public void onError(int i, Throwable throwable) {

            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
        client.put("type", 5);
        client.put("username", username);
        client.doPost(15000);
    }
}
