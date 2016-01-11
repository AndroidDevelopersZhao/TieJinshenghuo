package com.shanghai.activity.aty_home;
/**
 * .   如果你认为你败了，那你就一败涂地；
 * .   如果你认为你不敢，那你就会退缩；
 * .   如果你想赢但是认为你不能；
 * .   那么毫无疑问你就会失利；
 * .   如果你认为你输了，你就输了；
 * .   我们发现成功是从一个人的意志开始的；
 * .   成功是一种心态。
 * .   生活之战中，
 * .   胜利并不属于那些更强和更快的人，
 * .   胜利者终究是认为自己能行的人。
 * .
 * .   If you think you are beaten,you are;
 * .   If you think you dare not,you don't;
 * .   If you can to win but think you can't;
 * .   It's almost a cinch you won't.
 * .   If you think you'll lose,you're lost;
 * .   For out of the world we find Success begins with a fellow's will;
 * .   It's all in a state of mind.
 * .   Life's battles don't always go to the stronger and faster man,But sooner or later the man who wins Is the man who thinks he can.
 * .
 * .   You can you do.  No can no bb.
 * .
 */

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shanghai.data.data_utils.RespData;
import com.shanghai.soeasylib.util.Base64Utils;
import com.shanghai.soeasylib.util.XXHttpClient;
import com.shanghai.soeasylib.util.XXRSAUtils;
import com.shanghai.soeasylib.util.XXSharedPreferences;
import com.shanghai.soeasylib.util.XXUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import xinfu.com.pidanview.alerterview.progress.SVProgressHUD;
import com.shanghai.R;
/**
 * 项目名称： NewsClient
 * 创建日期： 2015/12/27  0:12
 * 项目作者： 赵文贇
 * 项目包名： xinfu.com.newsclient.activity
 */
public class Register extends Activity implements View.OnClickListener {
    private EditText et_phone_no;
    private TextView sendMsg;
    private SVProgressHUD svProgressHUD = new SVProgressHUD();
    private int i = 60;
    private EditText psw1, psw2;
    private Button btn_register;
    private String phoneNum = null;
    private Handler handler = null;
    private int NetWorkType = -1;
    private final String TAG = "NewClient";
    private XXSharedPreferences sharedPreferences;
    private String url = "http://221.228.88.249:8080/NewClient_Service/getPK_Service";
//private String url = "http://192.168.51.112:8080/NewClient_Service/getPK_Service";
    private boolean isRegister = false;//验证是否通过

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        initView();
        SMS();

    }

    private void initView() {
        sharedPreferences = new XXSharedPreferences("RSA_KEY");
        et_phone_no = (EditText) findViewById(R.id.et_phone_no);
        sendMsg = (TextView) findViewById(R.id.auth_sms);
        sendMsg.setOnClickListener(this);
        psw1 = (EditText) findViewById(R.id.psw1);
        psw2 = (EditText) findViewById(R.id.psw2);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
    }

    private void SMS() {
        EventHandler eventHandler = new EventHandler() {

            @Override
            public void onRegister() {
                Log.d(TAG, "注册成功");
            }

            @Override
            public void afterEvent(int event, int result, final Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {

                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        Log.e("NewClient---1", data + "短信验证成功");
//                        isRegister = true;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                register();
                            }
                        });

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
//                        svProgressHUD.showSuccessWithStatus(Register.this, "发送成功");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Register.this, "发送成功，请耐心等待...", Toast.LENGTH_SHORT).show();
                            }
                        });
                        //获取验证码成功
                        Log.e("NewClient---2", data + "短信已发送");

                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                        Log.e("NewClient---3", data + "");
                    }
                } else {

                    ((Throwable) data).printStackTrace();
                    Log.e(TAG, "错误：" + ((Throwable) data).getMessage());


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (svProgressHUD.isShowing(Register.this)) {
                                svProgressHUD.dismiss(Register.this);
                            }
                            try {

                                Toast.makeText(Register.this, new JSONObject(((Throwable) data).getMessage().toString()).getString("description"), Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e(TAG, "toast异常");
                                Toast.makeText(Register.this,((Throwable) data).getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
//        SMSSDK.getSupportedCountries();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                if (phoneNum != null && psw1.getText().toString().trim() != null
                        && psw2.getText().toString().trim() != null) {

                    svProgressHUD.showWithStatus(Register.this, "正在注册...");
                    SMSSDK.submitVerificationCode("+86", phoneNum, et_phone_no.getText().toString().trim());
                } else {
                    if (phoneNum == null) {
                        Toast.makeText(getApplicationContext(), "请获取验证码后再试", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(getApplicationContext(), "数据不能为空", Toast.LENGTH_SHORT).show();
                    }
                }
//                if (isRegister) {
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "验证未通过", Toast.LENGTH_SHORT).show();
//                }

                break;


            case R.id.auth_sms:
                sendMSM();
                break;
        }


//        Log.d("NewClient", "正在提交");
//

    }

    private void register() {
//        svProgressHUD.showWithStatus(Register.this, "正在注册...");
        final String p1 = psw1.getText().toString().trim();
        String p2 = psw2.getText().toString().trim();

        if (p1.equals(p2)) {

            XXHttpClient client = new XXHttpClient(url, true, new listener());
            //定义类型为1表示请求服务器公钥

            client.put("type", "1");

            client.doPost(10000);

            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.arg1 == 1) {
//                        //短信验证码验证成功
                    } else if (msg.arg1 == 2) {
                        //公钥请求成功，开始注册
                        XXHttpClient client = new XXHttpClient(url, true, new XXHttpClient.XXHttpResponseListener() {
                            @Override
                            public void onSuccess(int i, byte[] bytes) {
                                Log.e(TAG, "服务器返回：" + new String(bytes));
                                RespData respData = new Gson().fromJson(new String(bytes), RespData.class);
                                if (respData.getCode() == 200) {
                                    //注册成功
                                    svProgressHUD.showSuccessWithStatus(Register.this, respData.getData());
                                    et_phone_no.setText("");
                                    psw1.setText("");
                                    psw2.setText("");
                                    phoneNum = null;
                                    Log.d(TAG, respData.getData());
                                } else {
                                    if (svProgressHUD.isShowing(Register.this)) {
                                        svProgressHUD.dismiss(Register.this);
                                    }
                                    Toast.makeText(getApplicationContext(), "注册失败," + respData.getData(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(int i, Throwable throwable) {
                                if (svProgressHUD.isShowing(Register.this)) {
                                    svProgressHUD.dismiss(Register.this);
                                }
                                Toast.makeText(getApplicationContext(), "注册失败,网络异常", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onProgress(long l, long l1) {

                            }
                        });
                        client.put("type", 2);
                        client.put("username", phoneNum);
                        Log.d(TAG, "上送账号：" + phoneNum);
                        try {
                            client.put("password", Base64Utils.encode(XXRSAUtils.encryptByPublicKey(p1.getBytes(),
                                    sharedPreferences.get(Register.this, "publicKey_service", "").toString())));
                            client.doPost(10000);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(TAG, "加密失败");
                            if (svProgressHUD.isShowing(Register.this)) {
                                svProgressHUD.dismiss(Register.this);
                            }
                            Toast.makeText(getApplicationContext(), "环境不安全，无法注册，请稍后再试", Toast.LENGTH_SHORT).show();
                        }
                    }else {

                        if (svProgressHUD.isShowing(Register.this)) {
                            svProgressHUD.dismiss(Register.this);
                        }
                        Toast.makeText(getApplicationContext(),msg.getData().getString("data"), Toast.LENGTH_SHORT).show();
                    }
                }
            };

        } else {
            if (svProgressHUD.isShowing(Register.this)) {
                svProgressHUD.dismiss(Register.this);
            }
            psw1.setText("");
            psw2.setText("");
            Toast.makeText(getApplicationContext(), "两次密码输入不一致", Toast.LENGTH_SHORT).show();
        }


    }

    private void sendMSM() {
        XXUtils.closeKeybord(et_phone_no, Register.this);
        if (sendMsg.getText().equals("发送验证码")) {
            Log.d(TAG,"正在发送验证码");
//            svProgressHUD.showWithStatus(Register.this, "正在发送验证码至您的手机...");
            if (XXUtils.checkMobileNumberValid(et_phone_no.getText().toString().trim())) {
                phoneNum = et_phone_no.getText().toString().trim();
                et_phone_no.setText("");
                et_phone_no.setHint("请输入验证码");

                SMSSDK.getVerificationCode("+86", phoneNum);

            } else {
                Toast.makeText(Register.this, "电话号码格式不正确", Toast.LENGTH_SHORT).show();
                return;

            }
//
            Log.d(TAG, "Cli");
            sendMsg.setEnabled(false);
            final Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (i < 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                i = 5;
                                timer.cancel();
                                sendMsg.setEnabled(true);
                                sendMsg.setText("发送验证码");
                            }
                        });

                        return;
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (i >= 0) {
                                sendMsg.setText(i + "秒后可以再次发送");
                            }
                        }
                    });
                    Log.d(TAG, "i:" + i);
                    i--;
                }
            }, 0, 1000);

        }
    }

    class listener implements XXHttpClient.XXHttpResponseListener {

        @Override
        public void onSuccess(int i, byte[] bytes) {
            Log.d(TAG, "服务器返回秘钥大小:" + bytes.length);

//            Log.d(TAG, "code:" + respData.getCode());
//            Log.d(TAG, "type:" + NetWorkType);
            RespData respData = new Gson().fromJson(new String(bytes), RespData.class);
            //请求服务器秘钥的返回
            if (bytes.length > 0 && respData.getCode() == 200) {

                Log.d(TAG, "服务器返回公钥：" + new String(XXRSAUtils.encrypt(respData.getData().getBytes(), XXRSAUtils.MODE)));
                sharedPreferences.put(Register.this, "publicKey_service", new String(XXRSAUtils.encrypt(respData.getData().getBytes(), XXRSAUtils.MODE)));
                Log.d(TAG, "服务器公钥存储成功");
                NetWorkType = 0x02;
                Log.d(TAG, "类型设置为0x02，开始上送注册数据（加密后）");
                Message message = handler.obtainMessage();
                message.arg1 = 2;
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
            Log.e(TAG, "网络错误");
            Message message = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("data", "网络错误");
            message.setData(bundle);
            message.arg1 = -1;
            handler.sendMessage(message);
        }


        @Override
        public void onProgress(long l, long l1) {


        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
