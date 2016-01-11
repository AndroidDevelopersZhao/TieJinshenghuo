package com.shanghai.activity.aty_home;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shanghai.R;
import com.shanghai.data.data_utils.RespData;
import com.shanghai.soeasylib.util.Base64Utils;
import com.shanghai.soeasylib.util.XXHttpClient;
import com.shanghai.soeasylib.util.XXRSAUtils;
import com.shanghai.soeasylib.util.XXSharedPreferences;
import com.shanghai.soeasylib.util.XXUtils;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import xinfu.com.pidanview.alerterview.progress.SVProgressHUD;

/**
 * //TODO 找回密码页面
 * Created by Administrator on 2016/1/8.
 */
public class ForgotPasswordAty extends Activity implements View.OnClickListener, TextWatcher {
    private final String TAG = "NewClient";
    private EditText et_forgotpsw_phonenumber, et_forgotpsw_newpsw1, et_forgotpsw_newpsw2;
    private Button btn_forgotpsw_sendMsg, btn_forgotpsw_updatapsw;
    private TextView tv_forgot_qq;
    private LinearLayout view_setNewPsw, view_setMsg;
    private SVProgressHUD svProgressHUD = new SVProgressHUD();
    private String url = "http://221.228.88.249:8080/NewClient_Service/getPK_Service";
    private XXSharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpswaty);
        initView();
    }

    private void initView() {
        et_forgotpsw_phonenumber = (EditText) findViewById(R.id.et_forgotpsw_phonenumber);
        et_forgotpsw_newpsw1 = (EditText) findViewById(R.id.et_forgotpsw_newpsw1);
        et_forgotpsw_newpsw2 = (EditText) findViewById(R.id.et_forgotpsw_newpsw2);
        et_forgotpsw_phonenumber.addTextChangedListener(this);
        btn_forgotpsw_sendMsg = (Button) findViewById(R.id.btn_forgotpsw_sendMsg);
        btn_forgotpsw_updatapsw = (Button) findViewById(R.id.btn_forgotpsw_updatapsw);

        tv_forgot_qq = (TextView) findViewById(R.id.tv_forgot_qq);
        view_setNewPsw = (LinearLayout) findViewById(R.id.view_setNewPsw);
        view_setMsg = (LinearLayout) findViewById(R.id.view_setMsg);
        view_setMsg.setVisibility(View.VISIBLE);
        view_setNewPsw.setVisibility(View.INVISIBLE);
        btn_forgotpsw_sendMsg.setOnClickListener(this);
        btn_forgotpsw_updatapsw.setOnClickListener(this);
        tv_forgot_qq.setOnClickListener(this);
        sharedPreferences = new XXSharedPreferences("RSA_KEY");
        SMS();
    }

    String str_et_forgotpsw_phonenumber;
    String number = "";

    @Override
    public void onClick(View v) {

        String str_btn_forgotpsw_sendMsg = btn_forgotpsw_sendMsg.getText().toString();
        str_et_forgotpsw_phonenumber = et_forgotpsw_phonenumber.getText().toString().trim();
        String str_et_forgotpsw_newpsw1 = et_forgotpsw_newpsw1.getText().toString().trim();
        String str_et_forgotpsw_newpsw2 = et_forgotpsw_newpsw2.getText().toString().trim();

        switch (v.getId()) {
            case R.id.btn_forgotpsw_sendMsg:
                if (str_btn_forgotpsw_sendMsg.equals("发送验证码")) {
                    Log.d(TAG, "发送验证码");
                    svProgressHUD.showWithStatus(ForgotPasswordAty.this, "正在发送验证码");
                    if (!str_et_forgotpsw_phonenumber.equals("")
                            && XXUtils.checkMobileNumberValid(str_et_forgotpsw_phonenumber)) {
                        SMSSDK.getVerificationCode("+86", str_et_forgotpsw_phonenumber);
                        btn_forgotpsw_sendMsg.setEnabled(false);
                    } else {
                        if (svProgressHUD.isShowing(ForgotPasswordAty.this)) {
                            svProgressHUD.dismiss(ForgotPasswordAty.this);
                        }
                        Toast.makeText(ForgotPasswordAty.this, "电话号码格式错误", Toast.LENGTH_SHORT).show();
                    }

                } else if (str_btn_forgotpsw_sendMsg.equals("提交验证码")) {
                    svProgressHUD.showWithStatus(ForgotPasswordAty.this, "正在提交验证码");
                    btn_forgotpsw_sendMsg.setEnabled(false);
                    Log.d(TAG, "提交验证码");
                    SMSSDK.submitVerificationCode("+86", number, str_et_forgotpsw_phonenumber);
                }
                break;
            case R.id.btn_forgotpsw_updatapsw:
                Log.d(TAG, "提交新密码");

                if (!str_et_forgotpsw_newpsw1.equals("") && !str_et_forgotpsw_newpsw2.equals("")
                        && str_et_forgotpsw_newpsw1.equals(str_et_forgotpsw_newpsw2)) {
                    //后台待编写（交换秘钥，加密数据传输、Service端摘要后存入数据库，与注册页面代码雷同，晚上完成）
                    svProgressHUD.showWithStatus(ForgotPasswordAty.this, "正在设置新密码，请稍后...");
                    updataPsw(number, str_et_forgotpsw_newpsw1);
                } else {
                    Toast.makeText(ForgotPasswordAty.this, "两次输入不一致", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_forgot_qq:
                Log.d(TAG, "联系客服找回");
                //掉起QQ聊天窗口联系客服。前期我就是客服。- -、

                Intent intent= new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://wpa.qq.com/msgrd?v=3&uin=3648415&site=qq&menu=yes");
                intent.setData(content_url);
                startActivity(intent);
//                Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(""));
//                it.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
//                startActivity(it);
                break;
        }
    }

    private android.os.Handler handler = null;

    private void updataPsw(final String number, final String newpassword) {
        handler = new android.os.Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.arg1) {
                    case 1:
                        updataPassword(number, newpassword);
                        break;
                    case -1:
                        if (svProgressHUD.isShowing(ForgotPasswordAty.this)) {
                            svProgressHUD.dismiss(ForgotPasswordAty.this);
                        }
                        Toast.makeText(ForgotPasswordAty.this, msg.getData().getString("data"), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        getServicePublicKey();


    }

    private void updataPassword(String number, String newpassword) {
        String publicKey_Service = sharedPreferences.get(ForgotPasswordAty.this, "publicKey_service", "").toString();
        if (publicKey_Service == null||publicKey_Service.equals("")) {
            if (svProgressHUD.isShowing(ForgotPasswordAty.this)) {
                svProgressHUD.dismiss(ForgotPasswordAty.this);
            }
            Toast.makeText(ForgotPasswordAty.this, "检测到您还没有注册过账号", Toast.LENGTH_SHORT).show();
            return;
        }
        XXHttpClient client = new XXHttpClient(url, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, byte[] bytes) {
                Log.d(TAG, "返回：" + new String(bytes));
                final RespData respData = new Gson().fromJson(new String(bytes), RespData.class);
                if (respData.getCode() == 200 && respData.getData() != null && !respData.getData().equals("")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (svProgressHUD.isShowing(ForgotPasswordAty.this)) {
                                svProgressHUD.dismiss(ForgotPasswordAty.this);
                            }
                            svProgressHUD.showSuccessWithStatus(ForgotPasswordAty.this, respData.getData());
                            et_forgotpsw_newpsw1.setText("");
                            et_forgotpsw_newpsw2.setText("");
                            et_forgotpsw_phonenumber.setText("");

                            et_forgotpsw_phonenumber.setEnabled(false);
                            et_forgotpsw_newpsw2.setEnabled(false);
                            et_forgotpsw_newpsw1.setEnabled(false);
                            btn_forgotpsw_sendMsg.setEnabled(false);
                            btn_forgotpsw_updatapsw.setEnabled(false);

                        }
                    });
                } else {
                    Message message = handler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putString("data", respData.getData());
                    message.setData(bundle);
                    message.arg1 = -1;
                    handler.sendMessage(message);
                }

            }

            @Override
            public void onError(int i, Throwable throwable) {
                Log.e(TAG, "网络异常");
            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
        client.put("type", 4);
        client.put("username", number);
//        client.put("username", "admin");
        try {
            String encreddata = Base64Utils.encode(XXRSAUtils.encryptByPublicKey(newpassword.getBytes(), publicKey_Service));
            client.put("newpassword", encreddata);

            client.doPost(15000);

        } catch (Exception e) {
            e.printStackTrace();
            if (svProgressHUD.isShowing(ForgotPasswordAty.this)) {
                svProgressHUD.dismiss(ForgotPasswordAty.this);
            }
            Toast.makeText(ForgotPasswordAty.this, "加密失败，请稍后再试。", Toast.LENGTH_SHORT).show();
        }
    }

    private void getServicePublicKey() {
        XXHttpClient client = new XXHttpClient(url, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, byte[] bytes) {
                RespData respData = new Gson().fromJson(new String(bytes), RespData.class);
                //请求服务器秘钥的返回
                if (bytes.length > 0 && respData.getCode() == 200) {

                    sharedPreferences.put(ForgotPasswordAty.this, "publicKey_service", new String(XXRSAUtils.encrypt(respData.getData().getBytes(), XXRSAUtils.MODE)));
                    Log.d(TAG, "服务器公钥存储成功");
                    Message message = handler.obtainMessage();
                    message.arg1 = 1;//秘钥交换成功
                    handler.sendMessage(message);
                } else {
                    Log.e(TAG, "秘钥请求错误");
                    Message message = handler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putString("data", "秘钥请求错误");
                    message.setData(bundle);
                    message.arg1 = -1;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onError(int i, Throwable throwable) {
                Message message = handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("data", "网络异常");
                message.setData(bundle);
                message.arg1 = -1;
                handler.sendMessage(message);
            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
        client.put("type", 1);
        client.doPost(15000);
    }


    private void SMS() {
        EventHandler eventHandler = new EventHandler() {

            @Override
            public void onRegister() {
                Log.d(TAG, "短信验证监听设置成功");
            }

            @Override
            public void afterEvent(int event, int result, final Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {

                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        Log.d(TAG, data + "短信验证成功");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (svProgressHUD.isShowing(ForgotPasswordAty.this)) {
                                    svProgressHUD.dismiss(ForgotPasswordAty.this);
                                }
                                svProgressHUD.showSuccessWithStatus(ForgotPasswordAty.this, "验证成功");
                                view_setMsg.setEnabled(false);
                                view_setNewPsw.setVisibility(View.VISIBLE);
                            }
                        });

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Log.d(TAG, data + "短信已发送");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (svProgressHUD.isShowing(ForgotPasswordAty.this)) {
                                    svProgressHUD.dismiss(ForgotPasswordAty.this);
                                }
                                svProgressHUD.showSuccessWithStatus(ForgotPasswordAty.this, "发送成功");
                                number = str_et_forgotpsw_phonenumber;
                                et_forgotpsw_phonenumber.setText("");
                                et_forgotpsw_phonenumber.setHint("请输入四位验证码");
                                btn_forgotpsw_sendMsg.setEnabled(true);
                                btn_forgotpsw_sendMsg.setText("提交验证码");
                            }
                        });
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                        Log.e(TAG, data + "");
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                    Log.e(TAG, "错误：" + ((Throwable) data).getMessage());


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (svProgressHUD.isShowing(ForgotPasswordAty.this)) {
                                svProgressHUD.dismiss(ForgotPasswordAty.this);
                            }
                            try {

                                Toast.makeText(ForgotPasswordAty.this, new JSONObject(((Throwable) data).getMessage().toString()).getString("description"), Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e(TAG, "toast异常," + ((Throwable) data).getMessage());
                                Toast.makeText(ForgotPasswordAty.this, ((Throwable) data).getMessage(), Toast.LENGTH_SHORT).show();

                            } finally {
                                if (svProgressHUD.isShowing(ForgotPasswordAty.this)) {
                                    svProgressHUD.dismiss(ForgotPasswordAty.this);
                                }
                            }
                        }
                    });

                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length()>0){
            tv_forgot_qq.setVisibility(View.INVISIBLE);
        }else {
            tv_forgot_qq.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
