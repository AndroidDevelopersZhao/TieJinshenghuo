package com.shanghai.activity.aty_home_myinfo.myinfo_myinfo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shanghai.R;
import com.shanghai.data.data_utils.RespData;
import com.shanghai.soeasylib.util.XXHttpClient;
import com.shanghai.utils.Util;

import xinfu.com.pidanview.alerterview.progress.SVProgressHUD;

/**
 * Created by Administrator on 2016/1/9.
 */
public class MyInfo_Details_Add extends Activity implements View.OnClickListener {
    private String username = null;
    private EditText et_myinfo_add_name, et_myinfo_add_year,
            et_myinfo_add_city, et_myinfo_add_myCardNo, et_myinfo_add_myPhoneNum;
    private RadioGroup sex;
    private Button btn_submit;
    private String name, year, city, cardNo, phoneNum;
    private int selectedId = -1;
    private String Sex = null;
    private String TAG = "NewClient";
    private SVProgressHUD svProgressHUD = new SVProgressHUD();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_myinfo_details_add);
        initView();
        initData();
    }

    private void initView() {
        et_myinfo_add_name = (EditText) findViewById(R.id.et_myinfo_add_name);
        et_myinfo_add_year = (EditText) findViewById(R.id.et_myinfo_add_year);
        et_myinfo_add_city = (EditText) findViewById(R.id.et_myinfo_add_city);
        et_myinfo_add_myCardNo = (EditText) findViewById(R.id.et_myinfo_add_myCardNo);
        et_myinfo_add_myPhoneNum = (EditText) findViewById(R.id.et_myinfo_add_myPhoneNum);
        sex = (RadioGroup) findViewById(R.id.sex);

        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
    }

    private void initData() {
        try {
            username = getIntent().getExtras().getString("username");
        } catch (Throwable throwable) {
            Toast.makeText(MyInfo_Details_Add.this, "用户名获取异常，请退出软件重试", Toast.LENGTH_LONG).show();
            return;
        }
        //从服务器获取存储的购票信息，为空时隐藏listview，else现实listview+addBtn
        Toast.makeText(MyInfo_Details_Add.this, username, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {

        name = et_myinfo_add_name.getText().toString().trim();
        year = et_myinfo_add_year.getText().toString().trim();
        city = et_myinfo_add_city.getText().toString().trim();
        cardNo = et_myinfo_add_myCardNo.getText().toString().trim();
        phoneNum = et_myinfo_add_myPhoneNum.getText().toString().trim();
        selectedId = sex.getCheckedRadioButtonId();
        if (name == null || name.equals("") || year == null || year.equals("")
                || city == null || city.equals("") || cardNo == null || cardNo.equals("")
                || phoneNum == null || phoneNum.equals("") || selectedId == -1) {
            Toast.makeText(MyInfo_Details_Add.this, "至少由一项为空", Toast.LENGTH_LONG).show();
            return;
        }
        if (selectedId == R.id.male) {
            Sex = "男";
        } else if (selectedId == R.id.female) {
            Sex = "女";
        }
        addMyInfoToService(name, year, city, cardNo, phoneNum, Sex);
    }

    private void addMyInfoToService(String name, String year, String city, String cardNo, String phoneNum, String sex) {
        XXHttpClient client = new XXHttpClient(Util.url_my, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, byte[] bytes) {
                Log.d(TAG, "服务器返回：" + new String(bytes));
                final RespData data = new Gson().fromJson(new String(bytes), RespData.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (svProgressHUD.isShowing(MyInfo_Details_Add.this)) {
                            svProgressHUD.dismiss(MyInfo_Details_Add.this);
                        }
                        if (data.getCode() == 200) {
                            svProgressHUD.showSuccessWithStatus(MyInfo_Details_Add.this, "添加成功");
                            et_myinfo_add_name.setText("");
                            et_myinfo_add_year.setText("");
                            et_myinfo_add_city.setText("");
                            et_myinfo_add_myCardNo.setText("");
                            et_myinfo_add_myPhoneNum.setText("");
                        } else {
                            Toast.makeText(MyInfo_Details_Add.this, data.getData(), Toast.LENGTH_LONG).show();
                        }

                    }
                });


            }

            @Override
            public void onError(int i, Throwable throwable) {
                Log.e(TAG, "网络异常");
            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
        client.put("type", "6");
        client.put("username", username);
        client.put("name", name);
        client.put("year", year);
        client.put("city", city);
        client.put("cardNo", cardNo);
        client.put("phoneNum", phoneNum);
        client.put("sex", sex);
        svProgressHUD.showWithStatus(MyInfo_Details_Add.this, "正在添加数据到个人中心...");
        client.doPost(15000);
    }
}
