package com.shanghai.fragment.fmt_tickets_ordermannger;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shanghai.App;
import com.shanghai.R;
import com.shanghai.data.data_robtickets.OrderStatus_O_Data;
import com.shanghai.data.data_robtickets.OrderStatus_O_Passengers_Data;
import com.shanghai.data.data_robtickets.OrderStatus_O_Passengers_Returntickets_Data;
import com.shanghai.data.data_robtickets.OrderStatus_O_Result_Data;
import com.shanghai.data.data_utils.RespData;
import com.shanghai.listener.listener_tickets.OnGetOrderIdListener;
import com.shanghai.soeasylib.adapter.XXListViewAdapter;
import com.shanghai.soeasylib.util.XXHttpClient;
import com.shanghai.utils.Util;
import com.shanghai.view.CustomListView;

import java.util.ArrayList;

import xinfu.com.pidanview.alerterview.alerterview.AlertView;
import xinfu.com.pidanview.alerterview.alerterview.OnItemClickListener;
import xinfu.com.pidanview.alerterview.progress.SVProgressHUD;


/**
 * //TODO 全部订单。到后台取数据并且在后台确认订单最后状态
 * Created by Administrator on 2016/1/11.
 */
public class FMT_Tickets_OrderMannager_AllOrder extends android.support.v4.app.Fragment implements OnGetOrderIdListener, CustomListView.OnRefreshListner, AdapterView.OnItemLongClickListener {
    private static final String TAG = "NewClient";
    private String username = App.username;
    private CustomListView lv_allorder;
    private Handler handler_allorder = null;
    private Handler handler_GetNewOrderStatusForAllOrderFromJuHe = null;
    private XXListViewAdapter<OrderStatus_O_Data> adapter;
    private SVProgressHUD svProgressHUD = new SVProgressHUD();
    private AlertView alertView = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater);
    }

    private View initView(LayoutInflater inflater) {
        showSvp("获取您的全部订单信息，请耐心等待");
        View view = inflater.inflate(R.layout.fmt_tickets_ordermannager_allorder, null);
        Util.getOrderIdFromService(username, 10, this);// 获取所有订单
        lv_allorder = (CustomListView) view.findViewById(R.id.lv_allorder);
        adapter = new XXListViewAdapter<OrderStatus_O_Data>(getActivity(), R.layout.item_lv_allorder) {
            @Override
            public void initGetView(int i, View view, ViewGroup viewGroup) {
                TextView tv_all_orderid = (TextView) view.findViewById(R.id.tv_all_orderid);
                TextView tv_all_status = (TextView) view.findViewById(R.id.tv_all_status);
                TextView tv_all_amount = (TextView) view.findViewById(R.id.tv_all_amount);
                TextView tv_all_from = (TextView) view.findViewById(R.id.tv_all_from);
                TextView tv_all_to = (TextView) view.findViewById(R.id.tv_all_to);
                TextView tv_all_time = (TextView) view.findViewById(R.id.tv_all_time);
                // TODO 设置属性
                tv_all_orderid.setText(getItem(i).getResult().getOrderid());
                tv_all_status.setText(getItem(i).getResult().getMsg());
                tv_all_amount.setText(getItem(i).getResult().getPassengers()[0].getPrice());
                tv_all_from.setText(getItem(i).getResult().getFrom_station_name());
                tv_all_to.setText(getItem(i).getResult().getTo_station_name());
                tv_all_time.setText(getItem(i).getResult().getSubmit_time());

            }
        };
        lv_allorder.setAdapter(adapter);
        lv_allorder.setOnRefreshListner(this);
        lv_allorder.setOnItemLongClickListener(this);
        /**
         * 所有订单请求成功并查询完最新订单状态并且送往后台存入数据库后的handler
         */
        handler_allorder = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                svpDismiss();
                lv_allorder.onRefreshComplete();
                switch (msg.what) {
                    case 1://所有订单的最新状态已存入后台服务器
                        Log.d(TAG, "成功进入最后handler，开始处理UI");
                        OrderStatus_O_Data o__data = (OrderStatus_O_Data) (msg.getData().getSerializable("data"));
                        //处理UI
                        Log.d(TAG, "*******************最后接收参数成功：" + o__data.getResult().getOrderid() + "....................");
                        Log.d(TAG, "***************程序运行结束，开始设置UI********************");
                        setView(o__data);
                        break;

                    case -1://失败时返回原因

                        String data = msg.getData().getString("data");
                        errorToast(data);
//                        Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        return view;
    }

    /**
     * 改变UI
     */
    private void setView(OrderStatus_O_Data odata) {

        adapter.addItem(odata);
        adapter.notifyDataSetChanged();
    }


    /**
     * 所有订单请求成功的回调
     *
     * @param orders
     */
    @Override
    public void onSucc(final ArrayList<String> orders) {
        //迭代出所有订单号，并根据订单号在聚合查询该订单的最新状态。
        //查询完的数据送入后台服务器
        handler_GetNewOrderStatusForAllOrderFromJuHe = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what) {
                    case 1:
                        Log.d(TAG, "进入即将发送handler_allorder中的handler");
                        OrderStatus_O_Data odata = (OrderStatus_O_Data) (msg.getData().getSerializable("data"));
                        Util.sendMsgToHandler(handler_allorder, odata, true);
                        Log.d(TAG, "****************************发出handler_allorder：" + odata.getResult().getOrderid() + "....................");
                        break;

                    case -1:
                        String data = msg.getData().getString("data");
                        Util.sendMsgToHandler(handler_allorder, data, false);
                        break;
                }
            }
        };
        getNewOrderStatusForAllOrderFromJuHe(orders);
        //存储成功后发出如下handler
//        Util.sendMsgToHandler(handler_allorder, orders, true);
        Log.d(TAG, "所有订单获取成功，" + orders.toString());
        Log.d(TAG, "******************程序开始运行...............");
    }

    @Override
    public void onError(String errorMsg) {
        Util.sendMsgToHandler(handler_allorder, errorMsg, false);

//        Log.e(TAG, "所有订单获取失败," + errorMsg);
    }

    /**
     * 迭代所有数组里的订单号，并查询最新数据
     *
     * @param orders
     */
    private void getNewOrderStatusForAllOrderFromJuHe(ArrayList<String> orders) {

        for (int i = 0; i < orders.size(); i++) {
            //查询订单最新状态
            serchOrderid(orders.get(i));
            Log.d(TAG, "******************迭代出所有的订单号：" + orders.get(i) + "...............");
        }
    }


    private Handler handler_serchOrderid = null;//查询订单最新状态的handler
    private Handler handler_saveToService = null;//数据送往后台的handler
    private OrderStatus_O_Data final_o_data = null;
    private OrderStatus_O_Data final_o_all_data = null;

    /**
     * 查询订单状态
     *
     * @param oerderId
     */
    private void serchOrderid(final String oerderId) {
        Log.d(TAG, "调用查询订单状态的接口，传入orderID=" + oerderId);
        handler_serchOrderid = new Handler() {
            @Override
            public void handleMessage(Message msg) {

//                lv_orderId.onRefreshComplete();
//                if (msg.what == 1) {
                switch (msg.what) {

                    case 1:
//                        Log.d(TAG, "**************************handler_serchOrderid成功接收到msg:" + finalData + ".........................");
                        final_o_data = new Gson().fromJson(msg.getData().getString("data"), OrderStatus_O_Data.class);
//
                        //订单最新状态查询成功
                        if (final_o_data.getError_code() == 0) {

                            handler_saveToService = new Handler() {
                                @Override
                                public void handleMessage(Message msg) {
                                    switch (msg.what) {
                                        case 1:
                                            //

                                            final_o_all_data = (OrderStatus_O_Data) (msg.getData().getSerializable("data"));
                                            Util.sendMsgToHandler(handler_GetNewOrderStatusForAllOrderFromJuHe, final_o_all_data, true);
                                            Log.d(TAG, "*****************************进入上送后台成功的handler,发出handler_GetNewOrderStatusForAllOrderFromJuHe:" + final_o_all_data.getResult().getOrderid() + "....................");

                                            break;
                                        case -1:
                                            String errorMsg = msg.getData().getString("data");
                                            Util.sendMsgToHandler(handler_GetNewOrderStatusForAllOrderFromJuHe, errorMsg, false);
                                            break;
                                    }
                                }
                            };
                            Log.d(TAG, "进入订单查询成功的handler，开始送往后台");
                            Log.d(TAG, "**************************送往服务器的orderId：" + final_o_data.getResult().getOrderid() + ".........................");
                            sendTickestStatusAndDataToServiceMySQL(username, final_o_data);
                        } else {
                            Log.e(TAG, final_o_data.getReason());
                            Util.sendMsgToHandler(handler_GetNewOrderStatusForAllOrderFromJuHe, final_o_data.getReason(), false);
                        }

                        break;

                    case -1:
                        Log.e(TAG, msg.getData().getString("data"));

                        Util.sendMsgToHandler(handler_GetNewOrderStatusForAllOrderFromJuHe, msg.getData().getString("data"), false);
                        break;
                }
            }
        };
        XXHttpClient client = new XXHttpClient(Util.url_ticket5, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, byte[] bytes) {

                Log.d(TAG, "******************订单查询的返回：" + new String(bytes) + "...............");
                Log.d(TAG, "******************发出handler_serchOrderid,传入字符串：" + new String(bytes) + "...............");
                Util.sendMsgToHandler(handler_serchOrderid, new String(bytes), true);
            }

            @Override
            public void onError(int i, Throwable throwable) {
                Log.e(TAG, "网络异常");
                Util.sendMsgToHandler(handler_serchOrderid, "网络异常", false);
            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
        client.put("key", Util.appid_ticket);
        client.put("orderid", oerderId);
        client.doPost(15000);
//        Log.d(TAG, "查询订单状态提交的所有数据：" + client.getAllParams());
    }

    private OrderStatus_O_Result_Data result_data = null;
    private OrderStatus_O_Passengers_Data passengers = null;
    private OrderStatus_O_Passengers_Returntickets_Data returntickets_data = null;

    /**
     * 将最新数据送入后台服务器
     *
     * @param username
     */
    synchronized private void sendTickestStatusAndDataToServiceMySQL(String username, final OrderStatus_O_Data orderStatus_o_data) {
        result_data = orderStatus_o_data.getResult();
        passengers = result_data.getPassengers()[0];
        returntickets_data = passengers.getReturntickets();
        XXHttpClient client = new XXHttpClient(Util.url_my, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, byte[] bytes) {
                Log.d(TAG, "上送后台成功，返回：" + new String(bytes));
                Log.d(TAG, "**************************上送服务器成功，发出handler_saveToService:此处传入的参数为后台返回，无特定意义，只代表成功.........................");
                RespData respData = new Gson().fromJson(new String(bytes), RespData.class);
                if (respData.getCode() == 200) {
                    Util.sendMsgToHandler(handler_saveToService, orderStatus_o_data, true);
                } else {
                    Util.sendMsgToHandler(handler_saveToService, respData.getData(), false);
                }
            }

            @Override
            public void onError(int i, Throwable throwable) {
                Log.d(TAG, "上送后台失败，网络异常");
                Util.sendMsgToHandler(handler_saveToService, "网络异常", false);
            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
        client.put("type", 9);
        client.put("username", username);
        client.put("orderId", result_data.getOrderid());
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

    public void svpDismiss() {
        if (svProgressHUD.isShowing(getActivity())) {
            svProgressHUD.dismiss(getActivity());
        }
    }

    public void showSvp(String msg) {
        svpDismiss();
        svProgressHUD.showWithStatus(getActivity(), "正在" + msg + "...");
    }

    public void errorToast(String errorMsg) {
        if (FMT_Tickets_OrderMannager_AllOrder.this.isVisible())
            Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
    }

    public void showAlertView(String Msg, OnItemClickListener listener) {
        if (alertView != null) {

            if (alertView.isShowing()) {
                alertView.dismiss();
            }
        }

        alertView = new AlertView("提示", Msg, "取消", new String[]{"确定"},
                null, getActivity(), AlertView.Style.Alert, listener);
        alertView.show();


    }

    /**
     * 下拉刷新事件
     */
    @Override
    public void onRefresh() {
        adapter.removeAll();
        Util.getOrderIdFromService(username, 10, this);// 获取所有订单
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        showAlertView("是否删除该条记录", new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                Log.d(TAG, "position:" + position);
            }
        });
        return true;
    }
}
