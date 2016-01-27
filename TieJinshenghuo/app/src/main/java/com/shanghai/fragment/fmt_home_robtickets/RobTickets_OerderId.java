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
                tv_order_price.setText(getItem(i).getResult().getOrderamount());

                tv_order_submit_time.setText(getItem(i).getResult().getSubmit_time());

                StringBuffer name = new StringBuffer();
                StringBuffer type = new StringBuffer();
                StringBuffer no = new StringBuffer();

                for (int q = 0; q < getItem(i).getResult().getPassengers().length; q++) {
                    name.append(getItem(i).getResult().getPassengers()[q].getPassengersename()).append(",");
                    type.append(getItem(i).getResult().getPassengers()[q].getPassporttypeseidname()).append(",");
                    no.append(getItem(i).getResult().getPassengers()[q].getPassportseno()).append(",");

                }
                tv_order_passengersename.setText(name.toString().substring(0, name.toString().length() - 1));

                tv_order_passporttypeseidname.setText(type.toString().substring(0, type.toString().length() - 1));

                tv_order_passportseno.setText(no.toString().substring(0, no.toString().length() - 1));

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
                    final OrderStatus_O_Data all = new Gson().fromJson(data, OrderStatus_O_Data.class);
                    if (all.getError_code() == 0) {

//                        result_data = orderStatus_o_data.getResult();
//                        int i = 0;
//                        for (OrderStatus_O_Passengers_Data passengers_data : result_data.getPassengers()) {
//                            Log.d(TAG, "第" + (i++) + "个 passengers_data对象");
//                        }
//                        if (i >= 0 && orderStatus_o_data != null && result_data != null) {
//                            Log.d(TAG, "查询结果已全部解析完成,改变UI在送望SQL数据库后执行");
//                            //TODO 当数据成功存入数据库时刷新UI
//
                        getHandler_serchOrderid_sendToServiceSQl = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                if (svProgressHUD.isShowing(getActivity())) {
                                    svProgressHUD.dismiss(getActivity());
                                }
                                result_data = all.getResult();
                                switch (msg.what) {
                                    case 1:
                                        //添加到数据库成功，刷新UI
                                        if (result_data.getStatus().equals("2")) {
//                            btn_order_sub.setEnabled(true);
                                            OrderStatus_O_Passengers_Data[] passengers_datas=result_data.getPassengers();
                                            StringBuffer order_ticket_no = new StringBuffer();
                                            StringBuffer order_cxin = new StringBuffer();
                                            for (int i = 0; i < passengers_datas.length; i++) {
                                                order_ticket_no.append(passengers_datas[i].getTicket_no()).append(",");
                                                order_cxin.append(passengers_datas[i].getCxin()).append(",");

                                            }
                                            tv_order_ticket_no.setText(order_ticket_no.toString().substring(0, order_ticket_no.length() - 1));
                                            tv_order_cxin.setText(order_cxin.toString().substring(0, order_cxin.length() - 1));
                                            //服务器存储待支付订单。用户可通过待支付页面支付订单
                                        }
                                        if (result_data.getStatus().equals("4")) {
                                            tv_order_ordernumber.setText(result_data.getOrdernumber());
                                        }
                                        adapter.addItem(all);
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

                        sendTickestStatusAndDataToServiceMySQL(username, oerderId, all);


//                        } else {
//                            if (svProgressHUD.isShowing(getActivity())) {
//                                svProgressHUD.dismiss(getActivity());
//                            }
//                            Toast.makeText(getActivity(), "数据异常", Toast.LENGTH_LONG).show();
//                            return;
//                        }
                    } else {

                        if (svProgressHUD.isShowing(getActivity())) {
                            svProgressHUD.dismiss(getActivity());
                        }
                        Toast.makeText(getActivity(), all.getReason(), Toast.LENGTH_LONG).show();
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

    private void sendTickestStatusAndDataToServiceMySQL(String username, String oerderId, OrderStatus_O_Data o_data_all) {
        OrderStatus_O_Data all = o_data_all;
        OrderStatus_O_Result_Data result = all.getResult();
        OrderStatus_O_Passengers_Data[] passengers_data = result.getPassengers();//陈科信息
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
        client.put("user_orderid", result.getUser_orderid());
        client.put("msg", result.getMsg());
        client.put("orderamount", result.getOrderamount());
        client.put("status", result.getStatus());
        client.put("checi", result.getCheci());
        client.put("ordernumber", result.getOrdernumber());
        client.put("submit_time", result.getSubmit_time());
        client.put("deal_time", result.getDeal_time());
        client.put("cancel_time", result.getCancel_time());
        client.put("pay_time", result.getPay_time());
        client.put("finished_time", result.getFinished_time());
        client.put("refund_time", result.getRefund_time());
        client.put("juhe_refund_time", result.getJuhe_refund_time());
        client.put("train_date", result.getTrain_date());
        client.put("from_station_name", result.getFrom_station_name());
        client.put("from_station_code", result.getFrom_station_code());
        client.put("to_station_name", result.getTo_station_name());
        client.put("to_station_code", result.getTo_station_code());
        client.put("refund_money", result.getRefund_money());
        StringBuffer passengerid = new StringBuffer();
        StringBuffer passengersename = new StringBuffer();
        StringBuffer piaotype = new StringBuffer();
        StringBuffer piaotypename = new StringBuffer();
        StringBuffer passporttypeseid = new StringBuffer();
        StringBuffer passporttypeseidname = new StringBuffer();
        StringBuffer passportseno = new StringBuffer();
        StringBuffer price = new StringBuffer();
        StringBuffer zwcode = new StringBuffer();
        StringBuffer zwname = new StringBuffer();
        StringBuffer ticket_no = new StringBuffer();
        StringBuffer cxin = new StringBuffer();
        StringBuffer reason = new StringBuffer();

        //退票小光

        StringBuffer returnsuccess = new StringBuffer();
        StringBuffer returnmoney = new StringBuffer();
        StringBuffer returntime = new StringBuffer();
        StringBuffer returnfailid = new StringBuffer();
        StringBuffer returnfailmsg = new StringBuffer();
        StringBuffer returntype = new StringBuffer();

        for (int i = 0; i < passengers_data.length; i++) {
            passengerid.append(passengers_data[i].getPassengerid()).append(",");
            passengersename.append(passengers_data[i].getPassengersename()).append(",");
            piaotype.append(passengers_data[i].getPiaotype()).append(",");
            piaotypename.append(passengers_data[i].getPiaotypename()).append(",");
            passporttypeseid.append(passengers_data[i].getPassporttypeseid()).append(",");
            passporttypeseidname.append(passengers_data[i].getPassporttypeseidname()).append(",");
            passportseno.append(passengers_data[i].getPassportseno()).append(",");
            price.append(passengers_data[i].getPrice()).append(",");
            zwcode.append(passengers_data[i].getZwcode()).append(",");
            zwname.append(passengers_data[i].getZwname()).append(",");
            ticket_no.append(passengers_data[i].getTicket_no()).append(",");
            cxin.append(passengers_data[i].getCxin()).append(",");
            reason.append(passengers_data[i].getReason()).append(",");
            try {
                returnsuccess.append(passengers_data[i].getReturntickets().isReturnsuccess());
                returnmoney.append(passengers_data[i].getReturntickets().getReturnmoney());
                returntime.append(passengers_data[i].getReturntickets().getReturntime());
                returnfailid.append(passengers_data[i].getReturntickets().getReturnfailid());
                returnfailmsg.append(passengers_data[i].getReturntickets().getReturnfailmsg());
                returntype.append(passengers_data[i].getReturntickets().getReturntype());
            } catch (Throwable t) {
                Log.e(TAG, "退款部分数据抛出异常");
            }


        }
        client.put("passengerid", passengerid.toString().substring(0, passengerid.toString().length() - 1));
        client.put("passengersename", passengersename.toString().substring(0, passengersename.toString().length() - 1));
        client.put("piaotype", piaotype.toString().substring(0, piaotype.toString().length() - 1));
        client.put("piaotypename", piaotypename.toString().substring(0, piaotypename.toString().length() - 1));
        client.put("passporttypeseid", passporttypeseid.toString().substring(0, passporttypeseid.toString().length() - 1));
        client.put("passporttypeseidname", passporttypeseidname.toString().substring(0, passporttypeseidname.toString().length() - 1));
        client.put("passportseno", passportseno.toString().substring(0, passportseno.toString().length() - 1));

        client.put("price", price.toString().substring(0, price.toString().length() - 1));
        client.put("zwcode", zwcode.toString().substring(0, zwcode.toString().length() - 1));
        client.put("zwname", zwname.toString().substring(0, zwname.toString().length() - 1));
        client.put("ticket_no", ticket_no.toString().substring(0, ticket_no.toString().length() - 1));
        client.put("cxin", cxin.toString().substring(0, cxin.toString().length() - 1));
        client.put("reason", reason.toString().substring(0, reason.toString().length() - 1));

        try {
            client.put("returnsuccess", returnsuccess.toString().substring(0, returnsuccess.length() - 1));
            client.put("returnmoney", returnmoney.toString().substring(0, returnmoney.length() - 1));
            client.put("returntime", returntime.toString().substring(0, returntime.length() - 1));
            client.put("returnfailid", returnfailid.toString().substring(0, returnfailid.length() - 1));
            client.put("returnfailmsg", returnfailmsg.toString().substring(0, returnfailmsg.length() - 1));
            client.put("returntype", returntype.toString().substring(0, returntype.length() - 1));
        } catch (Throwable throwable) {
            Log.e(TAG, "退款部分数据抛出异常");
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
                        if (amount < price) {
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
