package com.shanghai.fragment.fmt_home_addphoneamount;

import android.app.Fragment;
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
import com.shanghai.R;
import com.shanghai.data.data_addphoneamount.AddOrderInfo;
import com.shanghai.data.data_addphoneamount.AddPhoneInfoRespData;
import com.shanghai.data.data_addphoneamount.JuheOrderInfo;
import com.shanghai.soeasylib.adapter.XXListViewAdapter;
import com.shanghai.soeasylib.util.XXHttpClient;
import com.shanghai.utils.Util;
import com.shanghai.view.CustomListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import xinfu.com.pidanview.alerterview.progress.SVProgressHUD;

/**
 * Created by Administrator on 2016/1/26.
 */
public class PhoneOrder extends Fragment implements CustomListView.OnRefreshListner {
    private View view;
    private CustomListView lv_phoneOrder;
    private XXListViewAdapter<AddOrderInfo> adapter;
    private String username = null;
    private final String TAG = "NewClient";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fmt_phoneorder, null);

        initView();

        return view;
    }

    private void initView() {
        username = getArguments().getString("username");
        lv_phoneOrder = (CustomListView) view.findViewById(R.id.lv_phoneOrder);
        adapter = new XXListViewAdapter<AddOrderInfo>(getActivity(), R.layout.item_lv_phoneorder) {
            @Override
            public void initGetView(int i, View view, ViewGroup viewGroup) {
                TextView tv_phoneorder_monphone = (TextView) view.findViewById(R.id.tv_phoneorder_monphone);
                TextView tv_phoneorder_price = (TextView) view.findViewById(R.id.tv_phoneorder_price);
                TextView tv_phoneorder_orderid = (TextView) view.findViewById(R.id.tv_phoneorder_orderid);
                TextView tv_phoneorder_time = (TextView) view.findViewById(R.id.tv_phoneorder_time);
                TextView tv_phoneorder_state = (TextView) view.findViewById(R.id.tv_phoneorder_state);
                tv_phoneorder_monphone.setText(getItem(i).getMobnumber());
                tv_phoneorder_price.setText(getItem(i).getPrice());
                tv_phoneorder_orderid.setText(getItem(i).getOrderid());
                tv_phoneorder_time.setText(getItem(i).getTime());
                tv_phoneorder_state.setText(getItem(i).getStateinfo());

            }
        };
        lv_phoneOrder.setAdapter(adapter);
        lv_phoneOrder.setOnRefreshListner(this);
        //向服务器请求订单号
        getOrder(username);
    }

    private Handler handler_getOrder = null;

    private void getOrder(String username) {
        SVProgressHUD.showWithStatus(getActivity(), "正在查询您的所有订单...");
        handler_getOrder = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case -1:
                        if (SVProgressHUD.isShowing(getActivity())) {
                            SVProgressHUD.dismiss(getActivity());
                        }
                        lv_phoneOrder.onRefreshComplete();
                        Toast.makeText(getActivity(), msg.getData().getString("data"), Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        AddPhoneInfoRespData info = (AddPhoneInfoRespData) msg.getData().getSerializable("data");
                        for (int i = 0; i < info.getData().size(); i++) {
                            Log.e(TAG, "查询成功，订单号：" + info.getData().get(i).getOrderid());
                        }
                        //将订单集合发给聚合后台，查询该订单的最新状态
                        getOrderInfoFromJuHe(info.getData());

                        break;
                }
            }
        };

        XXHttpClient client = new XXHttpClient(Util.url_my, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, byte[] bytes) {
                Log.d(TAG, "返回：" + new String(bytes));
                if (new String(bytes) != null) {
                    AddPhoneInfoRespData respData = new Gson().fromJson(new String(bytes), AddPhoneInfoRespData.class);
                    if (respData.getCode() == 200) {
                        Util.sendMsgToHandler(handler_getOrder, respData, true);
                    } else {
                        Util.sendMsgToHandler(handler_getOrder, respData.getResult(), false);
                    }
                } else {
                    Util.sendMsgToHandler(handler_getOrder, "数据异常", false);
                }
            }

            @Override
            public void onError(int i, Throwable throwable) {
                Log.e(TAG, "网络异常");
                Util.sendMsgToHandler(handler_getOrder, "网络异常", false);
            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
        client.put("type", 17);
        client.put("username", username);
        client.doPost(15000);
    }

    private Handler handler_getOrderInfoFromJuHe = null;

    /**
     * zai 聚合后台，查询该订单的最新状态
     *
     * @param info
     */
    private void getOrderInfoFromJuHe(List<AddOrderInfo> info) {
        handler_getOrderInfoFromJuHe = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case -1:
                        if (SVProgressHUD.isShowing(getActivity())) {
                            SVProgressHUD.dismiss(getActivity());
                        }
                        lv_phoneOrder.onRefreshComplete();
                        String ms = msg.getData().getString("data");
                        Toast.makeText(getActivity(), ms, Toast.LENGTH_LONG).show();
                        break;

                    case 1:
                        JuheOrderInfo juhe = (JuheOrderInfo) msg.getData().getSerializable("data");
                        //发送最新订单信息到后台
                        sendNewInfoToService(username, juhe);
                        break;
                }
            }
        };
        for (int i = 0; i < info.size(); i++) {
            final String sporder_id = info.get(i).getOrderid();
            getInfo(sporder_id);
        }
    }
    synchronized private void getInfo(final String sporder_id){
        XXHttpClient client = new XXHttpClient(Util.URL_ADDAMOUNT_STATE, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, byte[] bytes) {
                Log.d(TAG, "查询聚合订单的返回：" + new String(bytes));
                try {
                    JSONObject o = new JSONObject(new String(bytes));
                    if (o.getInt("error_code") == 0) {
                        JSONObject o1 = new JSONObject(o.get("result").toString());
                        int sta = o1.getInt("sta");
                        String info = o1.getString("info");
                        JuheOrderInfo juhe = new JuheOrderInfo();
                        juhe.setSta(sta);
                        juhe.setInfo(info);
                        juhe.setOrderid(sporder_id);
                        Util.sendMsgToHandler(handler_getOrderInfoFromJuHe, juhe, true);

                    } else {
                        Util.sendMsgToHandler(handler_getOrderInfoFromJuHe, o.getString("reason"), false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Util.sendMsgToHandler(handler_getOrderInfoFromJuHe, "数据异常", false);
                }
            }

            @Override
            public void onError(int i, Throwable throwable) {
                Log.e(TAG, "网络异常");
                Util.sendMsgToHandler(handler_getOrderInfoFromJuHe, "网络异常", false);
            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
        client.put("key", Util.APPKEY_ADDAMOUNT);
        client.put("sporder_id", sporder_id);
        client.doPost(15000);
    }
    private Handler handler_sendNewInfoToService = null;

    /**
     * 发送最新的订单信息到后台，后台更新后返回最新数据
     *
     * @param username
     * @param juhe
     */
    private void sendNewInfoToService(String username, JuheOrderInfo juhe) {
        handler_sendNewInfoToService = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (SVProgressHUD.isShowing(getActivity())) {
                    SVProgressHUD.dismiss(getActivity());
                }
                lv_phoneOrder.onRefreshComplete();
                switch (msg.what) {
                    case -1:
                    Toast.makeText(getActivity(),msg.getData().getString("data"),Toast.LENGTH_LONG).show();
                        break;

                    case 1:
                        AddOrderInfo info = (AddOrderInfo) msg.getData().getSerializable("data");
                        Log.d(TAG,"最后结果确认："+info.getOrderid());
                        Log.d(TAG,"最后结果确认："+info.getInfo());
                        Log.d(TAG,"最后结果确认："+info.getMobnumber());
                        Log.d(TAG,"最后结果确认："+info.getPrice());
                        Log.d(TAG,"最后结果确认："+info.getState());
                        Log.d(TAG,"最后结果确认："+info.getStateinfo());
                        Log.d(TAG,"最后结果确认："+info.getTime());

                        adapter.addItem(info);
                        adapter.notifyDataSetChanged();
                        break;
                }
            }
        };

        XXHttpClient client = new XXHttpClient(Util.url_my, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, byte[] bytes) {
                Log.d(TAG, "后台返回：" + new String(bytes));
                AddOrderInfo info = new Gson().fromJson(new String(bytes), AddOrderInfo.class);
                Util.sendMsgToHandler(handler_sendNewInfoToService, info, true);
            }

            @Override
            public void onError(int i, Throwable throwable) {
                Log.e(TAG, "网络异常");
                Util.sendMsgToHandler(handler_sendNewInfoToService, "网络异常", false);
            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
        client.put("type", 18);
        client.put("username", username);
        client.put("sporder_id", juhe.getOrderid());
        client.put("sta", juhe.getSta());
        client.put("info", juhe.getInfo());
        client.doPost(15000);
        Log.e(TAG,"请求更新订单的数据："+client.getAllParams());

    }

    @Override
    public void onRefresh() {
        adapter.removeAll();
        adapter.notifyDataSetChanged();
        getOrder(username);

    }
}
