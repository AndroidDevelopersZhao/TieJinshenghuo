package com.shanghai.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shanghai.R;
import com.shanghai.soeasylib.adapter.XXListViewAdapter;

/**
 * Created by Administrator on 2016/1/9.
 */
public class MyInfo_Aty extends Activity implements AdapterView.OnItemClickListener {
    private ListView lv_myinfo;
    private XXListViewAdapter<String> adapter;
    private String TAG = "NewClient";
    private String username=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_myinfo);
        try {
            username=getIntent().getExtras().get("username").toString();
        }catch (Throwable throwable){
            Toast.makeText(MyInfo_Aty.this,"登陆状态失效，请退出重新登陆",Toast.LENGTH_LONG).show();
            return;
        }
        lv_myinfo = (ListView) findViewById(R.id.lv_myinfo);
        adapter = new XXListViewAdapter<String>(this, R.layout.item_lv_myinfo) {
            @Override
            public void initGetView(int i, View view, ViewGroup viewGroup) {
                TextView tv_item_myinfo = (TextView) view.findViewById(R.id.tv_item_myinfo);
                tv_item_myinfo.setText(getItem(i).toString());
            }
        };
        adapter.addItem("我的信息");
        adapter.addItem("账户余额");
        adapter.addItem("修改密码");
        adapter.addItem("反馈意见");
        adapter.addItem("在线升级");
        adapter.addItem("联系我们");
        adapter.notifyDataSetChanged();
        lv_myinfo.setAdapter(adapter);
        lv_myinfo.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent =null;
        switch (adapter.getItem(position)) {
            case "我的信息":
                Log.d(TAG, "我的信息");
                intent =new Intent(MyInfo_Aty.this, MyInfoDetails_Aty.class);
                intent.putExtra("username", username);
                break;
            case "账户余额":
                Log.d(TAG, "账户余额");
                 intent =new Intent(MyInfo_Aty.this, Amount_Aty.class);
                intent.putExtra("username", username);

                break;
            case "修改密码":

                break;
            case "反馈意见":

                break;
            case "在线升级":

                break;
            case "联系我们":

                break;
        }
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_in_from_right);
    }
}
