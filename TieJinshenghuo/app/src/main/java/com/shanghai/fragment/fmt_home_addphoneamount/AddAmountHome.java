package com.shanghai.fragment.fmt_home_addphoneamount;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shanghai.R;
import com.shanghai.data.data_addamount.AddRespData;
import com.shanghai.data.data_addamount.AddRespDataResult;
import com.shanghai.data.data_addamount.AllData;
import com.shanghai.data.data_addamount.RespData_UpdateUserAmount;
import com.shanghai.data.data_addamount.Result;
import com.shanghai.data.data_addphoneamount.AddPhoneInfoRespData;
import com.shanghai.data.data_utils.RespData;
import com.shanghai.soeasylib.util.XXHttpClient;
import com.shanghai.soeasylib.util.XXSharedPreferences;
import com.shanghai.soeasylib.util.XXUtils;
import com.shanghai.utils.Util;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import xinfu.com.pidanview.alerterview.alerterview.AlertView;
import xinfu.com.pidanview.alerterview.alerterview.OnItemClickListener;
import xinfu.com.pidanview.alerterview.progress.SVProgressHUD;

/**
 * Created by Administrator on 2016/1/25.
 */
public class AddAmountHome extends Fragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, TextWatcher {
    private View view;
    private String TAG = "NewClient";
    private RadioGroup group;
    private RadioButton amount_10, amount_20, amount_30, amount_50, amount_100, amount_200,
            amount_300, amount_500;
    private EditText et_phone;
    private Button btn_addamount;
    private SVProgressHUD svProgressHUD = new SVProgressHUD();
    private String Amount = null;
    private String username = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fmt_addamounthome, null);
        username = getArguments().getString("username");
        Log.d(TAG, getArguments().getString("username"));
        initView();
        return view;
    }

    private void initView() {
        group = (RadioGroup) view.findViewById(R.id.group);
        amount_10 = (RadioButton) view.findViewById(R.id.amount_10);
        amount_20 = (RadioButton) view.findViewById(R.id.amount_20);
        amount_30 = (RadioButton) view.findViewById(R.id.amount_30);
        amount_50 = (RadioButton) view.findViewById(R.id.amount_50);
        amount_100 = (RadioButton) view.findViewById(R.id.amount_100);
        amount_200 = (RadioButton) view.findViewById(R.id.amount_200);
        amount_300 = (RadioButton) view.findViewById(R.id.amount_300);
        amount_500 = (RadioButton) view.findViewById(R.id.amount_500);
        group.setOnCheckedChangeListener(this);
        et_phone = (EditText) view.findViewById(R.id.et_phone);
        btn_addamount = (Button) view.findViewById(R.id.btn_addamount);
        et_phone.addTextChangedListener(this);
        btn_addamount.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == amount_10.getId()) {
            Log.d(TAG, "充值10元");
            Amount = "10";
        } else if (checkedId == amount_20.getId()) {
            Log.d(TAG, "充值20元");
            Amount = "20";
        } else if (checkedId == amount_30.getId()) {
            Log.d(TAG, "充值30元");
            Amount = "30";
        } else if (checkedId == amount_50.getId()) {
            Amount = "50";
            Log.d(TAG, "充值50元");
        } else if (checkedId == amount_100.getId()) {
            Amount = "100";
            Log.d(TAG, "充值100元");
        } else if (checkedId == amount_200.getId()) {
            Log.d(TAG, "充值200元");
            Amount = "200";
        } else if (checkedId == amount_300.getId()) {
            Log.d(TAG, "充值300元");
            Amount = "300";
        } else if (checkedId == amount_500.getId()) {
            Log.d(TAG, "充值500元");
            Amount = "500";
        } else {
            Log.d(TAG, "充值100000元");
        }
        amount = Integer.valueOf(Amount);

    }

    private Handler handler_checkUserAmount = null;

    @Override
    public void onClick(View v) {
        svProgressHUD.showWithStatus(getActivity(), "正在充值，请稍后...");
        //检查账号余额
        checkUserAmount(username);
        //是否充值？
        handler_checkUserAmount = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                final String data = msg.getData().getString("data");

                switch (msg.what) {
                    case -1:
                        //不充值
                        Log.d(TAG, "不充值，原因：" + data);
                        if (svProgressHUD.isShowing(getActivity())) {
                            svProgressHUD.dismiss(getActivity());
                        }
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(600);
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            new AlertView("提示", data, null, new String[]{"取消"}, null, getActivity(), AlertView.Style.Alert, new OnItemClickListener() {
                                                @Override
                                                public void onItemClick(Object o, int position) {
                                                    Toast.makeText(getActivity(), "请至主页-账户中心-个人中心-账户余额页面充值金额后再试", Toast.LENGTH_LONG).show();
                                                }
                                            }).show();
                                        }
                                    });
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                        break;
                    case 1:
                        //充值
                        Log.d(TAG, "开始充值，充值号码：" + phonenumber + ",充值金额：" + amount + ",用户余额：" + data);
                        //查询该充值金额的优惠数据：

                        selectNewAmount(phonenumber, amount, data);

                        break;
                }
            }
        };
        //回调结果
    }

    private Handler handler_getNewAmount = null;

    /**
     * 查询该充值金额的优惠数据：
     *
     * @param phonenumber 充值的电话号码
     * @param amount      充值金额
     * @param yue         用户余额
     */
    private void selectNewAmount(String phonenumber, final int amount, final String yue) {
        handler_getNewAmount = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                final String data = msg.getData().getString("data");
                if (svProgressHUD.isShowing(getActivity())) {
                    svProgressHUD.dismiss(getActivity());
                }

                switch (msg.what) {
                    case -1:
                        Toast.makeText(getActivity(), data, Toast.LENGTH_LONG).show();
                        break;

                    case 1:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(600);
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            new AlertView("提示", "本次充值金额的优惠价：" + data + "\n账户余额：" + yue + "\n充值将从您的账户扣除相应金额，确定充值吗？", "充值", new String[]{"取消"}, null, getActivity(), AlertView.Style.Alert, new OnItemClickListener() {
                                                @Override
                                                public void onItemClick(Object o, int position) {
                                                    Log.d(TAG, "破：" + position);
                                                    if (position == -1) {
                                                        //充值
                                                        Log.e(TAG, "充值前操作，减值（更新数据库）---充值 充值结果-true--->ok（充值完成）  充值结果false----加值（更新数据库）--告知用户");
                                                        svProgressHUD.showWithStatus(getActivity(), "正在扣除金额...");
                                                        //减值
                                                        Log.e(TAG, "开始减值操作");

                                                        try {
                                                            Double yue_now = Double.valueOf(yue);
                                                            Double amount_now = Double.valueOf(data);
                                                            Log.d(TAG, "当前用户的余额（转换后）：" + yue_now);
                                                            Log.d(TAG, "当前充值的优惠价（转换后）：" + amount_now);
                                                            XXSharedPreferences preferences = new XXSharedPreferences("amount");
                                                            Double d = yue_now - amount_now;
                                                            preferences.put(getActivity(), "finalAmount", d);
                                                            Log.d(TAG, "相减后，当前账户剩余金额：" + d);

                                                            jianzhi(username, d);
                                                        } catch (Throwable throwable) {
                                                            Toast.makeText(getActivity(), "余额换算时出现异常", Toast.LENGTH_LONG).show();
                                                        }

                                                    } else {
                                                        //取消

                                                    }
                                                }
                                            }).show();
                                        }
                                    });
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                        break;
                }
            }
        };
        getNewAmountFromInternet(phonenumber, amount);
    }

    /**
     * 更新余额的handle
     */
    private Handler handler_updateuserAmount = null;

    /**
     * 更具充值金额扣除用户的账户余额
     *
     * @param username
     * @param data
     */
    private void jianzhi(String username, Double data) {
        if (svProgressHUD.isShowing(getActivity())) {
            svProgressHUD.dismiss(getActivity());
        }
        handler_updateuserAmount = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                String data = msg.getData().getString("data");
                switch (msg.what) {
                    case -1:
                        Toast.makeText(getActivity(), data, Toast.LENGTH_LONG).show();
                        break;

                    case 1:
                        //充值
                        finalAddAmount(phonenumber, amount);

                        break;
                }
            }
        };


        XXHttpClient client = new XXHttpClient(Util.url_my, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, byte[] bytes) {
                Log.e(TAG, "余额扣除的回调：" + new String(bytes));
                if (new String(bytes) != null) {
                    RespData_UpdateUserAmount userAmount = new Gson().fromJson(new String(bytes), RespData_UpdateUserAmount.class);
                    if (userAmount.getCode() == 200) {
                        Util.sendMsgToHandler(handler_updateuserAmount, userAmount.getData(), true);
                    } else {
                        Util.sendMsgToHandler(handler_updateuserAmount, userAmount.getData(), false);
                    }
                } else {
                    Util.sendMsgToHandler(handler_updateuserAmount, "数据异常", false);
                }
            }

            @Override
            public void onError(int i, Throwable throwable) {
                Log.e(TAG, "网络异常");
                Util.sendMsgToHandler(handler_updateuserAmount, "网络异常", false);
            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
        client.put("type", 14);
        client.put("username", username);
        client.put("amount", data);
        client.doPost(15000);
    }

    private Handler handler_add = null;

    /**
     * 为用户最后充值金额
     *
     * @param phonenumber
     * @param amount
     */
    private void finalAddAmount(String phonenumber, int amount) {
        //TODO -------------------------
        Log.d(TAG, "开始充值...");
        svProgressHUD.showWithStatus(getActivity(), "正在充值...");
        handler_add = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what) {
                    case -1:
                        String errorMsg = msg.getData().getString("data");
//                        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
                        Log.d(TAG, errorMsg);
                        //充值失败，发起全额退款
                        //TODO 预留
                        if (svProgressHUD.isShowing(getActivity())) {
                            svProgressHUD.dismiss(getActivity());
                        }
                        Toast.makeText(getActivity(), "只有管理才能解决的错误，请联系管理解决", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Log.d(TAG, "充值成功，开始存储订单到后台");
                        AddRespData respData = (AddRespData) msg.getData().getSerializable("data");
                        addOrderToService(respData);

                        break;
                }
            }
        };
        XXHttpClient client_add = new XXHttpClient(Util.URL_ADDAMOUNT_ADD, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, byte[] bytes) {
                Log.d(TAG, "充值返回：" + new String(bytes));
                if (new String(bytes) != null) {
                    AddRespData addRespData = new Gson().fromJson(new String(bytes), AddRespData.class);
                    if (addRespData.getError_code() == 0) {
                        Util.sendMsgToHandler(handler_add, addRespData, true);
                    } else {
                        Util.sendMsgToHandler(handler_add, addRespData.getReason(), false);
                    }
                } else {
                    Util.sendMsgToHandler(handler_add, "数据异常", false);
                }

            }

            @Override
            public void onError(int i, Throwable throwable) {
                Log.e(TAG, "网络异常");
                Util.sendMsgToHandler(handler_add, "网络异常", false);
            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
        //md5(OpenID+key+phone+pervalue+orderid)
        String orderid = String.valueOf(new Date().getTime());
        String md5 = (Util.MD5("JH6c8c56ceb0d8e5ab63a880369124900e" + Util.APPKEY_ADDAMOUNT + phonenumber + amount + orderid)).toLowerCase();
        client_add.put("phone", phonenumber);
        client_add.put("pervalue", amount);
        client_add.put("orderid", orderid);
        client_add.put("key", Util.APPKEY_ADDAMOUNT);
        client_add.put("sign", md5);
        Log.d(TAG, "上送数据：" + client_add.getAllParams());
        client_add.doPost(15000);
    }

    private Handler handler_addServiceSuccOrder = null;

    /**
     * 将充值成功的订单信息存入后台
     *
     * @param respData
     */
    private void addOrderToService(AddRespData respData) {
        AddRespDataResult dataResult = respData.getResult();
        handler_addServiceSuccOrder = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (svProgressHUD.isShowing(getActivity())) {
                    svProgressHUD.dismiss(getActivity());
                }
                String data = msg.getData().getString("data");
                switch (msg.what) {
                    case -1:
                        Toast.makeText(getActivity(), data, Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(500);
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            new AlertView("提示", "充值成功，请稍后查询余额，如果迟迟不到账请联系客服QQ3648415，Tel：15221340931",
                                                    "确定", null, null, getActivity(), AlertView.Style.Alert, new OnItemClickListener() {
                                                @Override
                                                public void onItemClick(Object o, int position) {
                                                    //跳入订单页面
                                                    PhoneOrder order = new PhoneOrder();
                                                    Bundle bundle = new Bundle();
                                                    bundle.putString("username", username);
                                                    order.setArguments(bundle);
                                                    getFragmentManager().beginTransaction().replace(R.id.mainView, order).commit();
                                                }
                                            }).show();
                                        }
                                    });

                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                        Log.d(TAG, "订单提交成功");
                        break;
                }
            }
        };
        XXHttpClient client = new XXHttpClient(Util.url_my, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, byte[] bytes) {
                Log.d(TAG, "订单插入返回：" + new String(bytes));
                if (new String(bytes)!=null){
                    AddPhoneInfoRespData respData1 = new Gson().fromJson(new String(bytes),AddPhoneInfoRespData.class);
                    if (respData1.getCode()==200){
                        Util.sendMsgToHandler(handler_addServiceSuccOrder,"成功",true);

                    }else {
                        Util.sendMsgToHandler(handler_addServiceSuccOrder,respData1.getResult(),false);
                    }
                }else {
                    Util.sendMsgToHandler(handler_addServiceSuccOrder,"数据异常",false);
                }
            }

            @Override
            public void onError(int i, Throwable throwable) {
                Log.d(TAG, "网络异常");
                Util.sendMsgToHandler(handler_addServiceSuccOrder, "网络异常", false);
            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
        client.put("type", 16);
        client.put("orderid", dataResult.getSporder_id());//聚合订单号
        client.put("price", dataResult.getOrdercash());//价格
        client.put("info", dataResult.getCardname());//订单说明
        client.put("mobphone", dataResult.getMobilephone());//充值号码
        client.put("username", username);//用户名
        client.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));//充值时间
        client.doPost(15000);
    }

    /**
     * 网络查询该充值金额的最新优惠金额
     *
     * @param phonenumber
     * @param amount
     */
    private void getNewAmountFromInternet(String phonenumber, int amount) {
        XXHttpClient client = new XXHttpClient(Util.URL_ADDAMOUNT_GETNEWAMOUNT, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, byte[] bytes) {
                Log.d(TAG, "查询成功：" + new String(bytes));
                if (new String(bytes) != null) {
                    AllData allData = new Gson().fromJson(new String(bytes), AllData.class);
                    if (allData.getError_code() == 0) {
                        Result result = allData.getResult();
                        if (result != null && result.getPrice() != null && !result.getPrice().equals("")) {
                            Util.sendMsgToHandler(handler_getNewAmount, result.getPrice(), true);
                        } else {
                            Util.sendMsgToHandler(handler_getNewAmount, "数据异常", false);
                        }
                    } else {
                        Util.sendMsgToHandler(handler_getNewAmount, allData.getReason(), false);
                    }
                } else {
                    Util.sendMsgToHandler(handler_getNewAmount, "数据异常", false);
                }
            }

            @Override
            public void onError(int i, Throwable throwable) {
                Log.e(TAG, "网络异常");
                Util.sendMsgToHandler(handler_getNewAmount, "网络异常", false);
            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
        client.put("key", Util.APPKEY_ADDAMOUNT);
        client.put("phone", phonenumber);
        client.put("pervalue", amount);
        client.doPost(15000);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!isNumeric(s.toString())) {
            Toast.makeText(getActivity(), "请不要输入数字以外的任何字符", Toast.LENGTH_LONG).show();
        }
    }

    private String phonenumber = null;
    int amount = -1;

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 11) {
            if (XXUtils.checkMobileNumberValid(s.toString()) && Amount != null) {

                amount = Integer.valueOf(Amount);
                if (amount >= 0) {
                    phonenumber = s.toString();
                    btn_addamount.setEnabled(true);
                }
            } else {
                btn_addamount.setEnabled(false);
                Toast.makeText(getActivity(), "金额未选择或手机号码有误", Toast.LENGTH_LONG).show();
            }
        } else {
            btn_addamount.setEnabled(false);
        }

    }


    //判断是否全是数字
    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    private void checkUserAmount(String username) {
        XXHttpClient client = new XXHttpClient(Util.url_my, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, byte[] bytes) {
                RespData respData = new Gson().fromJson(new String(bytes), RespData.class);
                Log.d(TAG, respData.getCode() + "");
                Log.d(TAG, respData.getData() + "");
                if (respData != null && respData.getCode() == 200) {
                    if (Double.valueOf(respData.getData()) == 0 || Double.valueOf(respData.getData()) < amount) {
                        Util.sendMsgToHandler(handler_checkUserAmount, "账户余额不足", false);
                    } else {
                        Util.sendMsgToHandler(handler_checkUserAmount, respData.getData(), true);
                    }
                } else {
                    Util.sendMsgToHandler(handler_checkUserAmount, respData.getData(), false);
                }
            }

            @Override
            public void onError(int i, Throwable throwable) {
                Log.e(TAG, "网络异常");
                Util.sendMsgToHandler(handler_checkUserAmount, "网络异常", false);
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
