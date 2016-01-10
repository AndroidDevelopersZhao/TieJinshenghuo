package com.shanghai.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
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
import com.shanghai.data.GetSelectTicketsArrDdata;
import com.shanghai.data.PassengersArrData;
import com.shanghai.data.PassengersData;
import com.shanghai.data.PassengersInsuranceData;
import com.shanghai.data.RespData_UserInfo;
import com.shanghai.soeasylib.util.XXHttpClient;
import com.shanghai.soeasylib.util.XXSharedPreferences;
import com.shanghai.utils.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import xinfu.com.pidanview.alerterview.alerterview.AlertView;
import xinfu.com.pidanview.alerterview.alerterview.OnItemClickListener;
import xinfu.com.pidanview.alerterview.progress.SVProgressHUD;

/**
 * Created by Administrator on 2016/1/9.
 */
public class RobTickets_Details extends Fragment implements View.OnClickListener {
    private View view;
    private String TAG = "NewClient";
    private TextView from_station_name;//始发站
    private TextView to_station_name;//到站
    private TextView swz_price;//商务座票价
    private TextView swz_num;//商务座余票数量
    private TextView ydz_price;//一等座票价
    private TextView ydz_num;//一等座余票数量
    private TextView yz_price;//硬座票价
    private TextView yz_num;//硬座的余票数量
    private TextView tdz_price;//特等座票价
    private TextView tdz_num;//特等座余票数量
    private TextView gjrw_price;//高级软卧票价
    private TextView gjrw_num;//高级软卧余票数量
    private TextView rz_price;//软座的票价
    private TextView rz_num;//软座的余票数量
    private TextView rw_price;//软卧（下）票价
    private TextView rw_num;//软卧（下）余票数量
    private TextView yw_price;//硬卧（中）票价
    private TextView yw_num;//硬卧（中）的余票数量
    private TextView wz_price;//无座票价
    private TextView wz_num;//无座的余票数量
    private TextView run_time;//历时（出发站到目的站）
    private TextView can_buy_now;//当前是否可以接受预定
    private TextView start_time;//出发时刻
    private TextView arrive_time;//到达时刻
    private GetSelectTicketsArrDdata arrDdata = null;
    private Button btn_pay_swz, btn_pay_ydz, btn_pay_yz, btn_pay_tdz, btn_pay_gjrw, btn_pay_rz,
            btn_pay_rwx, btn_pay_ywz, btn_pay_wz;
    private String train_date = null;

    private String strt_code = null;
    private String strt_name = null;
    private String stop_code = null;
    private String stop_name = null;
    private String trade_code = null;
    private SVProgressHUD svProgressHUD = new SVProgressHUD();
    private Handler handler_pay = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fmt_robtickets_details, null);
        initView();

        return view;
    }

    private void initView() {
        from_station_name = (TextView) view.findViewById(R.id.from_station_name);
        to_station_name = (TextView) view.findViewById(R.id.to_station_name);
        swz_price = (TextView) view.findViewById(R.id.swz_price);
        swz_num = (TextView) view.findViewById(R.id.swz_num);
        ydz_price = (TextView) view.findViewById(R.id.ydz_price);
        ydz_num = (TextView) view.findViewById(R.id.ydz_num);

        yz_price = (TextView) view.findViewById(R.id.yz_price);
        yz_num = (TextView) view.findViewById(R.id.yz_num);
        tdz_price = (TextView) view.findViewById(R.id.tdz_price);
        tdz_num = (TextView) view.findViewById(R.id.tdz_num);
        gjrw_price = (TextView) view.findViewById(R.id.gjrw_price);
        gjrw_num = (TextView) view.findViewById(R.id.gjrw_num);
        rz_price = (TextView) view.findViewById(R.id.rz_price);
        rz_num = (TextView) view.findViewById(R.id.rz_num);
        rw_price = (TextView) view.findViewById(R.id.rw_price);
        rw_num = (TextView) view.findViewById(R.id.rw_num);
        yw_price = (TextView) view.findViewById(R.id.yw_price);
        yw_num = (TextView) view.findViewById(R.id.yw_num);
        wz_price = (TextView) view.findViewById(R.id.wz_price);
        wz_num = (TextView) view.findViewById(R.id.wz_num);
        run_time = (TextView) view.findViewById(R.id.run_time);
        can_buy_now = (TextView) view.findViewById(R.id.can_buy_now);
        start_time = (TextView) view.findViewById(R.id.start_time);
        arrive_time = (TextView) view.findViewById(R.id.arrive_time);
        GetSelectTicketsArrDdata arrDdata = (GetSelectTicketsArrDdata) getArguments().getSerializable("data");
        train_date = getArguments().getString("train_date");
        if (arrDdata == null) {
            Log.e(TAG, "对象接收失败");
            return;
        } else {
            this.strt_name = arrDdata.getFrom_station_name();
            this.strt_code = arrDdata.getFrom_station_code();
            this.stop_code = arrDdata.getTo_station_code();
            this.stop_name = arrDdata.getTo_station_name();
            this.trade_code = arrDdata.getTrain_code();
            Log.d(TAG, "对象接收成功," + arrDdata.getFrom_station_name());
        }
        from_station_name.setText(arrDdata.getFrom_station_name());
        to_station_name.setText(arrDdata.getTo_station_name());

        swz_price.setText(arrDdata.getSwz_price().equals("0") ? "--" : arrDdata.getSwz_price());

        swz_num.setText(arrDdata.getSwz_num().equals("--") ? "0" : arrDdata.getSwz_num());


        ydz_price.setText(arrDdata.getYdz_price().equals("0") ? "--" : arrDdata.getYdz_price());


        ydz_num.setText(arrDdata.getYdz_num().equals("--") ? "0" : arrDdata.getYdz_num());


        yz_price.setText(arrDdata.getYz_price()==0 ? "--" : arrDdata.getYz_price()+"");


        yz_num.setText(arrDdata.getYz_num().equals("--") ? "0" : arrDdata.getYz_num());


        tdz_price.setText(arrDdata.getTdz_price().equals("0") ? "--" : arrDdata.getTdz_price());


        tdz_num.setText(arrDdata.getTdz_num().equals("--") ? "0" : arrDdata.getTdz_num());


        gjrw_price.setText(arrDdata.getGjrw_price().equals("0") ? "--" : arrDdata.getGjrw_price());

        gjrw_num.setText(arrDdata.getGjrw_num().equals("--") ? "0" : arrDdata.getGjrw_num());

        rz_price.setText(arrDdata.getRz_price().equals("0") ? "--" : arrDdata.getRz_price());

        rz_num.setText(arrDdata.getRz_num().equals("--") ? "0" : arrDdata.getRz_num());

        rw_price.setText(arrDdata.getRw_price().equals("0") ? "--" : arrDdata.getRw_price());

        rw_num.setText(arrDdata.getRw_num().equals("--") ? "0" : arrDdata.getRw_num());

        yw_price.setText(arrDdata.getYw_price().equals("0") ? "--" : arrDdata.getYw_price());

        yw_num.setText(arrDdata.getYw_num().equals("--") ? "0" : arrDdata.getYw_num());

        wz_price.setText(arrDdata.getWz_price().equals("0") ? "--" : arrDdata.getWz_price());

        wz_num.setText(arrDdata.getWz_num().equals("--") ? "0" : arrDdata.getWz_num());

        run_time.setText(arrDdata.getRun_time());

        can_buy_now.setText(arrDdata.getCan_buy_now());

        start_time.setText(arrDdata.getStart_time());

        arrive_time.setText(arrDdata.getArrive_time());


        btn_pay_swz = (Button) view.findViewById(R.id.btn_pay_swz);
        btn_pay_ydz = (Button) view.findViewById(R.id.btn_pay_ydz);
        btn_pay_yz = (Button) view.findViewById(R.id.btn_pay_yz);
        btn_pay_tdz = (Button) view.findViewById(R.id.btn_pay_tdz);
        btn_pay_gjrw = (Button) view.findViewById(R.id.btn_pay_gjrw);
        btn_pay_rz = (Button) view.findViewById(R.id.btn_pay_rz);
        btn_pay_rwx = (Button) view.findViewById(R.id.btn_pay_rwx);
        btn_pay_ywz = (Button) view.findViewById(R.id.btn_pay_ywz);
        btn_pay_wz = (Button) view.findViewById(R.id.btn_pay_wz);


        //对按钮是否点亮做判断
        try {


            if (Integer.valueOf(swz_num.getText().toString()) > 0) {
                swz_num.append(" 张");
                btn_pay_swz.setEnabled(true);
                btn_pay_swz.setOnClickListener(this);
            }
            if (Integer.valueOf(ydz_num.getText().toString()) > 0) {
                ydz_num.append(" 张");
                btn_pay_ydz.setEnabled(true);
                btn_pay_ydz.setOnClickListener(this);
            }
            if (Integer.valueOf(yz_num.getText().toString()) > 0) {
                yz_num.append(" 张");
                btn_pay_yz.setEnabled(true);
                btn_pay_yz.setOnClickListener(this);
            }
            if (Integer.valueOf(tdz_num.getText().toString()) > 0) {
                tdz_num.append(" 张");
                btn_pay_tdz.setEnabled(true);
                btn_pay_tdz.setOnClickListener(this);
            }
            if (Integer.valueOf(gjrw_num.getText().toString()) > 0) {
                gjrw_num.append(" 张");
                btn_pay_gjrw.setEnabled(true);
                btn_pay_gjrw.setOnClickListener(this);
            }
            if (Integer.valueOf(rz_num.getText().toString()) > 0) {
                rz_num.append(" 张");
                btn_pay_rz.setEnabled(true);
                btn_pay_rz.setOnClickListener(this);
            }
            if (Integer.valueOf(rw_num.getText().toString()) > 0) {
                rw_num.append(" 张");
                btn_pay_rwx.setEnabled(true);
                btn_pay_rwx.setOnClickListener(this);
            }
            if (Integer.valueOf(yw_num.getText().toString()) > 0) {
                yw_num.append(" 张");
                btn_pay_ywz.setEnabled(true);
                btn_pay_ywz.setOnClickListener(this);
            }
            if (Integer.valueOf(wz_num.getText().toString()) > 0) {
                wz_num.append(" 张");

                btn_pay_wz.setEnabled(true);
                btn_pay_wz.setOnClickListener(this);
            }
        } catch (Throwable throwable) {
            Toast.makeText(getActivity(), "页面意外关闭，联系作者", Toast.LENGTH_LONG).show();
            return;
        }


    }

    @Override
    public void onClick(View v) {

        String str = "";
        String amount = null;
        String cc_code = null;
        String cc_name = null;
        switch (v.getId()) {
            case R.id.btn_pay_swz:
                str = "商务座购票";
                amount = swz_price.getText().toString();
                cc_code = "9";
                cc_name = "商务座";
                break;
            case R.id.btn_pay_ydz:
                str = "一等座购票";
                amount = ydz_price.getText().toString();
                cc_code = "M";
                cc_name = "一等座";
                break;

            case R.id.btn_pay_yz:
                str = "硬座购票";
                amount = yz_price.getText().toString();
                cc_code = "1";
                cc_name = "硬座";
                break;

            case R.id.btn_pay_tdz:
                str = "特等座购票";
                amount = tdz_price.getText().toString();
                cc_code = "P";
                cc_name = "特等座";
                break;
            case R.id.btn_pay_gjrw:
                str = "高级软卧购票";
                amount = gjrw_price.getText().toString();
                cc_code = "6";
                cc_name = "高级软卧";
                break;

            case R.id.btn_pay_rz:
                str = "软座购票";
                amount = rz_price.getText().toString();
                cc_code = "2";
                cc_name = "软座";
                break;
            case R.id.btn_pay_rwx:
                str = "软卧下购票";
                amount = rw_price.getText().toString();
                cc_code = "4";
                cc_name = "软卧";
                break;
            case R.id.btn_pay_ywz:
                str = "硬卧中购票";
                amount = yw_price.getText().toString();
                cc_code = "3";
                cc_name = "硬卧";
                break;
            case R.id.btn_pay_wz:
                str = "无座购票";
                amount = wz_price.getText().toString();

                break;

        }
        Log.d(TAG, str);
        Log.d(TAG, "票价：" + amount);
        Log.d(TAG, "座位编号：" + cc_code);
        Log.d(TAG, "座位名称：" + cc_name);

        pay_qid(amount, cc_code, cc_name);
    }

    private String amount = null;
    private String cc_code = null;
    private String cc_name = null;

    private Handler handler = null;

    private void pay_qid(String amount, String cc_code, String cc_name) {
        this.amount = amount;
        this.cc_code = cc_code;
        this.cc_name = cc_name;

        svProgressHUD.showWithStatus(getActivity(), "正在为您下订单..");
        //获取个人信息
        XXHttpClient client_getuserinfo = new XXHttpClient(Util.url_my, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, final byte[] bytes) {
                Log.d(TAG, "服务器返回：" + new String(bytes));
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (svProgressHUD.isShowing(getActivity())) {

                            svProgressHUD.dismiss(getActivity());
                        }
                        RespData_UserInfo respData_userInfo = new Gson().fromJson(new String(bytes), RespData_UserInfo.class);
                        Message message = handler.obtainMessage();
                        Bundle bundle = new Bundle();
                        if (respData_userInfo.getCode() == 200) {
                            message.what = 1;
                            Log.d(TAG, "请求成功，发出handle");
                            bundle.putSerializable("data", respData_userInfo);

                        } else {
                            message.what = -1;
                            bundle.putString("data", "数据为空，请至个人中心添加个人信息");
                            Toast.makeText(getActivity(), "数据为空，请至个人中心添加个人信息", Toast.LENGTH_LONG).show();

                        }
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                });

            }

            @Override
            public void onError(int i, Throwable throwable) {
                Log.e(TAG, "网络异常");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (svProgressHUD.isShowing(getActivity())) {

                            svProgressHUD.dismiss(getActivity());
                        }
                    }
                });
            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
        client_getuserinfo.put("type", "7");
        client_getuserinfo.put("username", getArguments().getString("username"));
        client_getuserinfo.doPost(15000);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                final RespData_UserInfo userInfo = (RespData_UserInfo) msg.getData().getSerializable("data");
                switch (msg.what) {
                    case 1:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(500);
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (userInfo.getName() != null || userInfo.getYear() != null
                                                    || userInfo.getCardNo() != null || userInfo.getSex() != null
                                                    || userInfo.getCity() != null || userInfo.getPhoneNum() != null) {


                                                new AlertView("提示", "请确认您的订票信息:\n乘客姓名：" + userInfo.getName() + "" +
                                                        "\n性别：" + userInfo.getSex() + "" +
                                                        "\n身份证号：" + userInfo.getCardNo() + "" +
                                                        "\n当前城市：" + userInfo.getCity() + "(用来赠送保险)" +
                                                        "\n电话号码：" + userInfo.getPhoneNum() + "" +
                                                        "\n出生年月：" + userInfo.getYear() + "(用来赠送保险)" +
                                                        "\n请确保如上信息真实准确后点击确定", null, new String[]{"取消"}, new String[]{"确定"},
                                                        getActivity(), AlertView.Style.Alert, new OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(Object o, int position) {
                                                        if (position == 0) {
                                                            //取消
                                                        } else {
                                                            //确定下单
                                                            svProgressHUD.showWithStatus(getActivity(), "开始下单...");
                                                            Log.d(TAG, "接收到handle:" + userInfo);
                                                            pay(userInfo);
                                                        }
                                                        Log.d(TAG, "" + position);
                                                    }
                                                }).show();
                                            } else {
                                                new AlertView("提示", "您的个人信息为空，无法购票。请至\n主页更多功能-个人中心-我的信息页面添加信\n息后再试", null, new String[]{"取消"}, null,
                                                        getActivity(), AlertView.Style.Alert, new OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(Object o, int position) {

                                                    }
                                                }).show();
                                            }
                                        }
                                    });
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

//
                        break;

                    case -1:
                        Toast.makeText(getActivity(), msg.getData().getString("data"), Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };

    }

    /**
     * 下单
     *
     * @param data
     */
    private void pay(RespData_UserInfo data) {
        String name = data.getName();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(data.getYear().substring(0, 4)).append("-").append(data.getYear().substring(4, 6)).append("-")
                .append(data.getYear().substring(6, data.getYear().length()));
        String year = stringBuffer.toString();
        String city = data.getCity();
        String sex = data.getSex();
        String cardNo = data.getCardNo();
        String phoneNum = data.getPhoneNum();

        XXHttpClient client = new XXHttpClient(Util.url_ticket3, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, final byte[] bytes) {
                Log.d(TAG, "下单的返回：" + new String(bytes));
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (svProgressHUD.isShowing(getActivity())) {

                            svProgressHUD.dismiss(getActivity());
                        }
                        try {
                            JSONObject reason = new JSONObject(new String(bytes));
                            if (reason.getInt("error_code") == 0) {

                                //{"reason":"成功的返回","result":{"orderid":"1452362046945"},"error_code":0}
                                JSONObject o = new JSONObject(reason.getString("result"));
                                RobTickets_OerderId oerderId = new RobTickets_OerderId();
                                Bundle bundle = new Bundle();
                                bundle.putString("oerderId", o.getString("orderid"));
                                bundle.putString("username",getArguments().getString("username"));
                                oerderId.setArguments(bundle);
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.addToBackStack("22");
                                transaction.replace(R.id.mainView, oerderId).commit();
//                                Toast.makeText(getActivity(), "订单号：" + o.getString("orderid"), Toast.LENGTH_LONG).show();
//                                serchOrderid(o.getString("orderid"));//查询订单状态

//                                payAmount(o.getString("orderid"));
                            } else {

                                Toast.makeText(getActivity(), reason.getString("reason"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }

            @Override
            public void onError(int i, Throwable throwable) {
                Log.d(TAG, "网络异常");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (svProgressHUD.isShowing(getActivity())) {

                            svProgressHUD.dismiss(getActivity());
                        }

                    }
                });
            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
        PassengersInsuranceData passengersInsuranceData = new PassengersInsuranceData();

        passengersInsuranceData.setName(name);
        passengersInsuranceData.setBirth(year);
        passengersInsuranceData.setCity(city);
        if (sex.equals("男")) {
            passengersInsuranceData.setGender("M");//性别 M男
        } else {
            passengersInsuranceData.setGender("F");//性别 F女
        }

        passengersInsuranceData.setIdcard(cardNo);
        passengersInsuranceData.setMobile(phoneNum);

        PassengersArrData passengersArrData = new PassengersArrData();
        XXSharedPreferences userid = new XXSharedPreferences("UserId");
        int a = Integer.valueOf(userid.get(getActivity(), "ID", -1) + "".trim());
        if (a == -1) {
            userid.put(getActivity(), "ID", 1);
            a = Integer.valueOf(userid.get(getActivity(), "ID", -1) + "".trim());
            passengersArrData.setPassengerid(String.valueOf(a));
        } else {

            passengersArrData.setPassengerid(String.valueOf((a++)));
        }


        passengersArrData.setPassengersename(name);
        passengersArrData.setPiaotype("1");//1。其中，1 :成人票,2 :儿童票,4 :残军票
        passengersArrData.setPiaotypename("成人票");//成人票。票种名称，和上面的piaotype对应
        passengersArrData.setPassporttypeseid("1");//1。其中，1:二代身份证,2:一代身份证,C:港澳通行证,B:护照,G:台湾通行证
        passengersArrData.setPassporttypeseidname("二代身份证");//二代身份证。证件类型名称，和上面的passporttypeseid对应
        passengersArrData.setPassportseno(cardNo);//420205199207231234。乘客证件号码
        passengersArrData.setPrice(amount);//票价
        if (cc_code != null) {
            passengersArrData.setZwcode(cc_code);//表示座位编码，其中9:商务座,P:特等座, M:一等座, O（大写字母O，不是数字0）:二等座, //6:高级软卧, 4:软卧,3:硬卧, 2:软座,1:硬座。
        }
        if (cc_name != null) {
            passengersArrData.setZwname(cc_name);//如：硬座。表示座位名称，和上面的座位编码对应，注意：无座没有zwnam
        }


        passengersArrData.setInsurance(passengersInsuranceData);
        passengersArrData.setData(new Gson().toJson(passengersArrData));
        PassengersData passengersData = new PassengersData();
        passengersData.setPassengersArrDatas(new PassengersArrData[]{passengersArrData});

        passengersData.setData(new Gson().toJson(passengersData));
        client.put("key", Util.appid_ticket);
        String qid = new Date().getTime() + "".trim();
        client.put("user_orderid", qid);//订单号
        Log.d(TAG, "当前生成订单号：" + qid);
        client.put("train_date", train_date);
        client.put("from_station_name", strt_name);
        client.put("from_station_code", strt_code);
        client.put("to_station_code", stop_code);
        client.put("to_station_name", stop_name);
        client.put("checi", trade_code);
        client.put("passengers", "[" + passengersArrData.toString() + "]");

        Log.d(TAG, client.getAllParams() + "");
        client.doGet(15000);

    }

    private Handler hhh = null;


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

                switch (msg.what) {
                    case 1:
                        svProgressHUD.showSuccessWithStatus(getActivity(), "付款成功，等待出票");
                        break;

                    case -1:
                        Toast.makeText(getActivity(), msg.getData().getString("data"), Toast.LENGTH_LONG).show();
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

}
