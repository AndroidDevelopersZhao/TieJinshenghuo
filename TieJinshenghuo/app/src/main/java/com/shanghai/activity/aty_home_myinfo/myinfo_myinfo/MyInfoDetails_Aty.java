package com.shanghai.activity.aty_home_myinfo.myinfo_myinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shanghai.R;
import com.shanghai.data.data_robtickets.RespData_UserInfo;
import com.shanghai.soeasylib.adapter.XXListViewAdapter;
import com.shanghai.soeasylib.util.XXHttpClient;
import com.shanghai.view.CustomListView;
import com.shanghai.utils.Util;

import xinfu.com.pidanview.alerterview.progress.SVProgressHUD;

/**
 * Created by Administrator on 2016/1/9.
 */
public class MyInfoDetails_Aty extends Activity implements View.OnClickListener, CustomListView.OnRefreshListner {
    private String username = null;
    private Button btn_addInfo;
    private CustomListView listView;
    private XXListViewAdapter<RespData_UserInfo> adapter;
    private String TAG = "NewClient";
    private SVProgressHUD svProgressHUD = new SVProgressHUD();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_myinfo_details);
        initView();
        initData();


    }

    private void initData() {
        try {
            username = getIntent().getExtras().getString("username");
        } catch (Throwable throwable) {
            Toast.makeText(MyInfoDetails_Aty.this, "用户名获取异常，请退出软件重试", Toast.LENGTH_LONG).show();
            return;
        }
        //从服务器获取存储的购票信息，为空时隐藏listview，else现实listview+addBtn
        svProgressHUD.showWithStatus(MyInfoDetails_Aty.this, "正在从服务器获取您的数据...");
        getDataFromService(username);
//        Toast.makeText(MyInfoDetails_Aty.this, username, Toast.LENGTH_LONG).show();
    }

    private void getDataFromService(String username) {
        XXHttpClient client = new XXHttpClient(Util.url_my, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, final byte[] bytes) {
                Log.d(TAG, "服务器返回：" + new String(bytes));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listView.onRefreshComplete();
                        if (svProgressHUD.isShowing(MyInfoDetails_Aty.this)) {

                            svProgressHUD.dismiss(MyInfoDetails_Aty.this);
                        }
                        RespData_UserInfo respData_userInfo = new Gson().fromJson(new String(bytes), RespData_UserInfo.class);
                        if (respData_userInfo.getCode() == 200) {
                            adapter.addItem(respData_userInfo);
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(MyInfoDetails_Aty.this, "数据为空，请添加", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }

            @Override
            public void onError(int i, Throwable throwable) {
                Log.e(TAG, "网络异常");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listView.onRefreshComplete();
                        if (svProgressHUD.isShowing(MyInfoDetails_Aty.this)) {

                            svProgressHUD.dismiss(MyInfoDetails_Aty.this);
                        }
                    }
                });
            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
        client.put("type", "7");
        client.put("username", username);
        client.doPost(15000);
    }

    private void initView() {
        btn_addInfo = (Button) findViewById(R.id.btn_addInfo);
        btn_addInfo.setOnClickListener(this);
        listView = (CustomListView) findViewById(R.id.listview_aty_Myinfo_Details);
        adapter = new XXListViewAdapter<RespData_UserInfo>(MyInfoDetails_Aty.this, R.layout.item_lv_userinfo) {
            @Override
            public void initGetView(int i, View view, ViewGroup viewGroup) {
                TextView item_tv_name = (TextView) view.findViewById(R.id.item_tv_name);
                TextView item_tv_sex = (TextView) view.findViewById(R.id.item_tv_sex);
                TextView item_tv_year = (TextView) view.findViewById(R.id.item_tv_year);
                TextView item_tv_city = (TextView) view.findViewById(R.id.item_tv_city);
                TextView item_tv_myno = (TextView) view.findViewById(R.id.item_tv_myno);
                TextView item_tv_phoneNum = (TextView) view.findViewById(R.id.item_tv_phoneNum);

                item_tv_name.setText(getItem(i).getName());
                item_tv_sex.setText(getItem(i).getSex());
                item_tv_year.setText(getItem(i).getYear());
                item_tv_city.setText(getItem(i).getCity());
                item_tv_myno.setText(getItem(i).getCardNo());
                item_tv_phoneNum.setText(getItem(i).getPhoneNum());

            }
        };
        listView.setAdapter(adapter);
        listView.setOnRefreshListner(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MyInfoDetails_Aty.this, MyInfo_Details_Add.class);
        intent.putExtra("username", username);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_in_from_right);
    }

    @Override
    public void onRefresh() {
        adapter.removeAll();
        getDataFromService(username);
    }
}
