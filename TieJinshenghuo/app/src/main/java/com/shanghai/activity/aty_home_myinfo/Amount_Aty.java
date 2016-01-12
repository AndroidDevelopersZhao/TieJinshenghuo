package com.shanghai.activity.aty_home_myinfo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shanghai.R;
import com.shanghai.data.data_utils.RespData;
import com.shanghai.soeasylib.util.XXHttpClient;
import com.shanghai.utils.Util;

/**
 * Created by Administrator on 2016/1/9.
 */
public class Amount_Aty extends Activity implements View.OnClickListener {
    private final String TAG = "NewClient";
    private String username = null;
    private TextView tv_amount_username, tv_amount_amount;
    private Button brn_amount_pay;
    private Handler handler = null;
    private String url = Util.url_my;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_amount);
        initVIew();
        initData();


    }

    private void initData() {
        try {
            username = getIntent().getExtras().getString("username");
        } catch (Throwable throwable) {
            Toast.makeText(Amount_Aty.this, "登陆状态失效，请退出重新登陆", Toast.LENGTH_LONG).show();
            return;
        }
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String data=msg.getData().getString("data");
                switch (msg.what) {
                    case -1:
                        Log.d(TAG,"错误信息："+data);
                        tv_amount_username.setText(username);
                        tv_amount_amount.setText("");
                        break;
                    case 1:
                        Log.d(TAG,"请求成功，用户余额："+data);
                        tv_amount_username.setText(username);
                        tv_amount_amount.setText(data);
                        break;
                    case 2:

                        break;
                    case 3:

                        break;

                }
            }
        };
        getAmount(username);
    }

    private void getAmount(String username) {
        XXHttpClient client = new XXHttpClient(url, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, byte[] bytes) {
                RespData respData = new Gson().fromJson(new String(bytes), RespData.class);
                Message message = handler.obtainMessage();
                Bundle bundle = new Bundle();
                if (respData.getCode()==200){
                    message.what=1;
                }else {
                    message.what=-1;
                }
                bundle.putString("data",respData.getData());
                message.setData(bundle);
                handler.sendMessage(message);
            }

            @Override
            public void onError(int i, Throwable throwable) {
            Log.e(TAG,"网络异常");
            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
        client.put("type", 5);
        client.put("username", username);
        client.doPost(15000);
    }

    private void initVIew() {
        tv_amount_username = (TextView) findViewById(R.id.tv_amount_username);
        tv_amount_amount = (TextView) findViewById(R.id.tv_amount_amount);
        brn_amount_pay = (Button) findViewById(R.id.brn_amount_pay);
        brn_amount_pay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent= new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse("http://wpa.qq.com/msgrd?v=3&uin=3648415&site=qq&menu=yes");
        intent.setData(content_url);
        startActivity(intent);
    }
}
