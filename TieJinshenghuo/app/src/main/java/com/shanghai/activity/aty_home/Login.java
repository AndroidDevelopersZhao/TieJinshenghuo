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
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shanghai.R;
import com.shanghai.data.data_utils.RespData;
import com.shanghai.soeasylib.util.Base64Utils;
import com.shanghai.soeasylib.util.XXHttpClient;
import com.shanghai.soeasylib.util.XXRSAUtils;
import com.shanghai.soeasylib.util.XXSharedPreferences;

import xinfu.com.pidanview.alerterview.progress.SVProgressHUD;

/**
 * 项目名称： NewsClient
 * 创建日期： 2015/12/26  15:50
 * 项目作者： 赵文贇
 * 项目包名： xinfu.com.newsclient.activity
 */
public class Login extends Activity implements View.OnClickListener {
    private ImageView btn_signin;
    private EditText et_username, et_password;
    private XXSharedPreferences sharedPreferences;
    private String publicKey_service;
    private SVProgressHUD svProgressHUD = new SVProgressHUD();
    private final String TAG = "NewClient";
    private CheckBox cb;
    private String url = "http://221.228.88.249:8080/NewClient_Service/getPK_Service";
    private TextView et_register, tv_forgotpsw;
//    private String url = "http://192.168.51.112:8080/NewClient_Service/getPK_Service";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_aty);
//        Toast.makeText(this, "长按登陆按钮可以注册哦", Toast.LENGTH_LONG).show();

        initView();
        initData();
    }

    private void initData() {
        XXSharedPreferences sharedPreferences = new XXSharedPreferences("User_Num");
        if (sharedPreferences.get(this, "username", "").toString().equals("") ||
                sharedPreferences.get(this, "password", "").toString().equals("")) {
            return;
        }
        et_username.setText(sharedPreferences.get(this, "username", "").toString());
        et_password.setText(sharedPreferences.get(this, "password", "").toString());
    }

    private void initView() {
        sharedPreferences = new XXSharedPreferences("RSA_KEY");//

        btn_signin = (ImageView) findViewById(R.id.btn_signin);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_signin.setOnClickListener(this);
        cb = (CheckBox) findViewById(R.id.cb);
        cb.setChecked(true);
        et_register = (TextView) findViewById(R.id.et_register);
        tv_forgotpsw = (TextView) findViewById(R.id.tv_forgotpsw);
        et_register.setOnClickListener(this);
        tv_forgotpsw.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signin:
                svProgressHUD.showWithStatus(this, "正在登陆...");
                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                if (username == null || password == null || username.equals("") || password.equals("")) {
                    Toast.makeText(Login.this, "账号密码为空，请重新输入", Toast.LENGTH_SHORT).show();
                    if (svProgressHUD.isShowing(Login.this)) {
                        svProgressHUD.dismiss(Login.this);
                    }
                    return;
                }
                sigin(username, password);
                break;
            case R.id.et_register:
                startActivity(new Intent(Login.this, Register.class));
                break;
            case R.id.tv_forgotpsw:
                startActivity(new Intent(Login.this, ForgotPasswordAty.class));
                break;

        }
    }

    private void sigin(final String username, final String password) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.arg1 == 1) {
                    try {
                        publicKey_service = sharedPreferences.get(Login.this, "publicKey_service", "").toString();
                        String encreddata = Base64Utils.encode(XXRSAUtils.encryptByPublicKey(password.getBytes(), publicKey_service));
                        XXHttpClient client = new XXHttpClient(url, true, new XXHttpClient.XXHttpResponseListener() {
                            @Override
                            public void onSuccess(int i, byte[] bytes) {
                                RespData data = new Gson().fromJson(new String(bytes), RespData.class);
                                if (data.getCode() == 200) {
                                    if (cb.isChecked()) {
                                        XXSharedPreferences sharedPreferences = new XXSharedPreferences("User_Num");
                                        sharedPreferences.put(Login.this, "username", username);
                                        sharedPreferences.put(Login.this, "password", password);
                                        //
                                    }
                                    svProgressHUD.showSuccessWithStatus(Login.this, data.getData());
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    intent.putExtra("username", username);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(Login.this, data.getData(), Toast.LENGTH_SHORT).show();
                                    if (svProgressHUD.isShowing(Login.this)) {
                                        svProgressHUD.dismiss(Login.this);
                                    }
                                }
                            }

                            @Override
                            public void onError(int i, Throwable throwable) {
                                Log.e(TAG, "超时");
                                Toast.makeText(Login.this, "网络异常", Toast.LENGTH_SHORT).show();
                                if (svProgressHUD.isShowing(Login.this)) {
                                    svProgressHUD.dismiss(Login.this);
                                }
                            }

                            @Override
                            public void onProgress(long l, long l1) {

                            }
                        });
                        client.put("username", username);
                        client.put("password", encreddata);
                        client.put("type", 3);
                        client.doPost(15000);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG, "加密错误");
                        if (svProgressHUD.isShowing(Login.this)) {
                            svProgressHUD.dismiss(Login.this);
                        }
                    }
                } else {
                    if (svProgressHUD.isShowing(Login.this)) {
                        svProgressHUD.dismiss(Login.this);
                    }
                    Toast.makeText(Login.this, msg.getData().getString("data"), Toast.LENGTH_SHORT).show();
                }
            }
        };
        XXHttpClient getkey = new XXHttpClient(url, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, byte[] bytes) {
                RespData data = new Gson().fromJson(new String(bytes), RespData.class);
                if (data.getCode() == 200) {
                    sharedPreferences.put(Login.this, "publicKey_service", new String(XXRSAUtils.encrypt(data.getData().getBytes(), XXRSAUtils.MODE)));
                    Message message = handler.obtainMessage();
                    message.arg1 = 1;
                    handler.sendMessage(message);
                } else {
                    Message message = handler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putString("data", data.getData());
                    message.setData(bundle);
                    message.arg1 = -1;
                    handler.sendMessage(message);
                }

            }

            @Override
            public void onError(int i, Throwable throwable) {
                Log.e(TAG, "超时");
                Message message = handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("data", "连接服务器超时");
                message.setData(bundle);
                message.arg1 = -1;
                handler.sendMessage(message);
            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
        getkey.put("type", 1);
        getkey.doPost(9000);

    }

}
