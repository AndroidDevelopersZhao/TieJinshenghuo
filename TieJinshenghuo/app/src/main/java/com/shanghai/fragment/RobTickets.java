package com.shanghai.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shanghai.R;
import com.shanghai.data.GetSelectTickets;
import com.shanghai.data.GetSelectTicketsArrDdata;
import com.shanghai.data.GetTicketsSiteCode;
import com.shanghai.listener.GetSelectTicketsListener;
import com.shanghai.listener.GetTicktsSiteCodeListener;
import com.shanghai.soeasylib.util.XXUtils;
import com.shanghai.utils.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import xinfu.com.pidanview.alerterview.progress.SVProgressHUD;

/**
 * 抢票神器
 * 项目创建时间：2015年12月29日14:26
 * Created by Administrator on 2015/12/29.
 */
public class RobTickets extends Fragment implements GetTicktsSiteCodeListener, View.OnClickListener {
    private View view;
    private String TAG = "NewClient";
    private Handler handler_Ticket_SiteCode = null;
    private Button btn_robtickets_select;
    private EditText et_robtickets_startAdd, et_robtickets_stopAdd, et_robtickets_time;
    private int type = -1;
    private String str_startAdd = null;
    private String str_stopAdd = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.robtickets, null);
        initView();
        Log.d(TAG, "进入抢票神器页面");
        selectTickets();

        return view;
    } //

    private void selectTickets() {

        handler_Ticket_SiteCode = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String data = msg.getData().getString("data");
                if (svProgressHUD.isShowing(getActivity())) {
                    svProgressHUD.dismiss(getActivity());
                }
                switch (msg.what) {
                    case 1:
                        Log.d(TAG, "请求成功：" + data);
                        switch (type) {
                            case Util.STARTADDRESS:
                                str_startAdd = data;
                                type = Util.STOPADDRESS;
                                new GetTicketsSiteCode(stopAdd, RobTickets.this);
                                break;

                            case Util.STOPADDRESS:
                                str_stopAdd = data;
                                RobTickets_Selected robTickets_selected = new RobTickets_Selected();
                                Bundle bundle = new Bundle();
                                bundle.putString("str_startAdd", str_startAdd);
                                bundle.putString("str_stopAdd", str_stopAdd);
                                bundle.putString("time", time);
                                bundle.putString("username",getArguments().getString("username"));
                                try {
                                    String[] s = time.split("-");
                                    int year = Integer.valueOf(s[0]);
                                    String mouth = s[1];
                                    String day = s[2];
                                    Log.d(TAG,year+"");
                                    Log.d(TAG,mouth+"");
                                    Log.d(TAG,day+"");

                                    Log.d(TAG,"月.int"+Integer.valueOf(mouth));
                                    Log.d(TAG,"日.int"+Integer.valueOf(day));


                                    if (year > 2016 || year < 2015
                                            || String.valueOf(year).length()!=4) {

                                        Toast.makeText(getActivity(), "年份输入错误，当前可查询2015年或2016年票价信息", Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                    if (Integer.valueOf(mouth) > 12 || Integer.valueOf(mouth) < 1 ||mouth.length()!=2) {

                                        Toast.makeText(getActivity(), "月份输入错误,1~9月前加0，例如09", Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                    if (Integer.valueOf(day) > 31 || Integer.valueOf(day) < 1 || day.length()!=2) {
                                        Toast.makeText(getActivity(), "天数输入错误,1~9日前加0，例如09", Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                    String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
                                    Log.d(TAG, "Date:" + date);
//                                    XXUtils.convertDays(2015)
                                    //:2016-01-09
//                                    int y=Integer.valueOf(date.split("-")[0]);
//                                    int m = Integer.valueOf(date.split("-")[1]);
//                                    int d = Integer.valueOf(date.split("-")[2]);
                                    StringBuffer sb = new StringBuffer();
                                    int newyear=Integer.valueOf(sb.append(year).append(mouth).append(day).toString());
                                        int td = Integer.valueOf(date);
                                    if (newyear<td){
                                        Toast.makeText(getActivity(), "不能查询过期车票信息", Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                } catch (Throwable throwable) {
                                    Toast.makeText(getActivity(), "时间格式不合法，标准格式：2016-01-01", Toast.LENGTH_LONG).show();
                                    return;
                                }


                                robTickets_selected.setArguments(bundle);
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.addToBackStack("");
                                transaction.replace(R.id.mainView, robTickets_selected).commit();

                                break;
                        }
                        break;
                    case -1:
                        Log.d(TAG, "请求失败：" + data);
                        Toast.makeText(getActivity(), data, Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };

    }


    private void initView() {
        btn_robtickets_select = (Button) view.findViewById(R.id.btn_robtickets_select);
        btn_robtickets_select.setOnClickListener(this);

        et_robtickets_startAdd = (EditText) view.findViewById(R.id.et_robtickets_startAdd);
        et_robtickets_stopAdd = (EditText) view.findViewById(R.id.et_robtickets_stopAdd);
        et_robtickets_time = (EditText) view.findViewById(R.id.et_robtickets_time);

    }

    @Override
    public void OnSucc(String siteCode) {
        Message message = handler_Ticket_SiteCode.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString("data", siteCode);
        message.setData(bundle);
        message.what = 1;
        handler_Ticket_SiteCode.sendMessage(message);
    }

    @Override
    public void OnError(String error) {
        Message message = handler_Ticket_SiteCode.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString("data", error);
        message.setData(bundle);
        message.what = -1;
        handler_Ticket_SiteCode.sendMessage(message);
    }

    private String stopAdd;
    private String time;
    private SVProgressHUD svProgressHUD = new SVProgressHUD();

    @Override
    public void onClick(View v) {
        svProgressHUD.showWithStatus(getActivity(), "正在查询...");
        String startAdd = et_robtickets_startAdd.getText().toString().trim();
        stopAdd = et_robtickets_stopAdd.getText().toString().trim();
        time = et_robtickets_time.getText().toString().trim();

        switch (v.getId()) {
            case R.id.btn_robtickets_select:

                type = Util.STARTADDRESS;
                new GetTicketsSiteCode(startAdd, this);
                break;
        }
    }
}
