package com.shanghai.fragment.fmt_home_robtickets;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.shanghai.R;
import com.shanghai.data.data_robtickets.GetSelectTickets;
import com.shanghai.data.data_robtickets.GetSelectTicketsArrDdata;
import com.shanghai.listener.GetSelectTicketsListener;
import com.shanghai.soeasylib.adapter.XXListViewAdapter;
import com.shanghai.view.CustomListView;

import java.util.List;

import xinfu.com.pidanview.alerterview.progress.SVProgressHUD;

/**
 * Created by Administrator on 2016/1/9.
 */
public class RobTickets_Selected extends Fragment implements CustomListView.OnRefreshListner, AdapterView.OnItemClickListener {
    private View view;
    private CustomListView lv_robtickets_Selected;
    private String str_startAdd = null;
    private String str_stopAdd = null;
    private String time = null;
    private XXListViewAdapter<GetSelectTicketsArrDdata> adapter;
    @Override
    public void onDestroy() {
        if (svProgressHUD.isShowing(getActivity())) {
            svProgressHUD.dismiss(getActivity());

        }
        super.onDestroy();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fmt_robtickets_selected, null);
        initView();
        get();
        initData();
        return view;

    }
    private SVProgressHUD svProgressHUD = new SVProgressHUD();
    private void initData() {
        svProgressHUD.showWithStatus(getActivity(),"正在查询最新车次信息...");
        getSelectTickets(str_startAdd, str_stopAdd, time);
    }

    private void get() {
        try {
            Bundle bundle = getArguments();
            str_startAdd = bundle.getString("str_startAdd");
            str_stopAdd = bundle.getString("str_stopAdd");
            time = bundle.getString("time");
        }catch (Throwable throwable){
            if (RobTickets_Selected.this.isVisible()){
                Toast.makeText(getActivity(),"页面意外关闭，请返回后重试一次",Toast.LENGTH_LONG).show();
            }
            return;
        }


    }

    private String TAG = "NewClient";

    private void getSelectTickets(String str_startAdd, String str_stopAdd, String time) {
        Log.d(TAG, str_startAdd);
        Log.d(TAG, str_stopAdd);
        Log.d(TAG, time);

        //TODO 记得发送Handel
        new GetSelectTickets(str_startAdd, str_stopAdd, time, new GetSelectTicketsListener() {

            @Override
            public void onSucc(List<GetSelectTicketsArrDdata> getSelectTicketsArrDdatas) {
                if (getActivity()!=null)
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (svProgressHUD.isShowing(getActivity())){
                            svProgressHUD.dismiss(getActivity());
                        }
                    }
                });
                if (getSelectTicketsArrDdatas.size()!=0){


                for (GetSelectTicketsArrDdata b : getSelectTicketsArrDdatas) {
                    Log.d(TAG, b.getEnd_station_name());
                    adapter.addItem(b);
                    adapter.notifyDataSetChanged();
                    lv_robtickets_Selected.onFootLoadingComplete();
//        lv_robtickets_Selected.removeFooterView(footer);
                    lv_robtickets_Selected.onRefreshComplete();
                }
                }else {
                    Toast.makeText(getActivity(),"返回数据为空，请稍后再试.",Toast.LENGTH_LONG).show();
                    return;
                }


            }

            @Override
            public void onError(final String errorMsg) {
                Log.e(TAG, errorMsg);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (svProgressHUD.isShowing(getActivity())) {
                            svProgressHUD.dismiss(getActivity());
                        }
                        Toast.makeText(getActivity(),errorMsg,Toast.LENGTH_LONG).show();
                        return;
                    }
                });
            }
        });
    }

    private void initView() {
        lv_robtickets_Selected = (CustomListView) view.findViewById(R.id.lv_robtickets_Selected);

        adapter = new XXListViewAdapter<GetSelectTicketsArrDdata>(getActivity(), R.layout.item_lv_robticketsselected) {
            @Override
            public void initGetView(int i, View view, ViewGroup viewGroup) {
                //车次
                TextView tv_robtickets_train_code = (TextView) view.findViewById(R.id.tv_robtickets_train_code);

                //历时时长
                TextView tv_robtickets_run_time = (TextView) view.findViewById(R.id.tv_robtickets_run_time);

                //始发站
                TextView tv_robtickets_from_station_name = (TextView) view.findViewById(R.id.tv_robtickets_from_station_name);

                //到站
                TextView tv_robtickets_to_station_name = (TextView) view.findViewById(R.id.tv_robtickets_to_station_name);

                //出发站时间
                TextView tv_robtickets_start_time = (TextView) view.findViewById(R.id.tv_robtickets_start_time);

                //硬座票价
                TextView tv_robtickets_yz_price = (TextView) view.findViewById(R.id.tv_robtickets_yz_price);

                //硬座剩余张数
                TextView tv_robtickets_yz_num = (TextView) view.findViewById(R.id.tv_robtickets_yz_num);

                tv_robtickets_train_code.setText(getItem(i).getTrain_code());
                tv_robtickets_run_time.setText(getItem(i).getRun_time());
                tv_robtickets_from_station_name.setText(getItem(i).getFrom_station_name());
                tv_robtickets_yz_price.setText(getItem(i).getYz_price()==0 ? "--" : getItem(i).getYz_price()+"");


                tv_robtickets_yz_num.setText(getItem(i).getYz_num().equals("--") ? "0张" : getItem(i).getYz_num()+"张");
                tv_robtickets_to_station_name.setText(getItem(i).getTo_station_name());
//                tv_robtickets_yz_price.setText(getItem(i).getYz_price()+"");
                tv_robtickets_start_time.setText(getItem(i).getStart_time());
//                tv_robtickets_yz_num.setText(getItem(i).getYz_num()+"张");


            }
        };
        lv_robtickets_Selected.setAdapter(adapter);
        //设置下拉刷新监听
        lv_robtickets_Selected.setOnRefreshListner(this);
        lv_robtickets_Selected.setOnItemClickListener(this);
    }

    @Override
    public void onRefresh() {
        adapter.removeAll();
        adapter.notifyDataSetChanged();
        getSelectTickets(str_startAdd, str_stopAdd, time);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG,""+adapter.getItem(position-1).getTrain_code());
        RobTickets_Details robTickets_details = new RobTickets_Details();
        Bundle bundle =new Bundle();
        GetSelectTicketsArrDdata arrDdata = adapter.getItem(position-1);
        bundle.putSerializable("data",arrDdata);
        bundle.putString("train_date",time);
        bundle.putString("username",getArguments().getString("username"));
        robTickets_details.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack("1");
        transaction.replace(R.id.mainView, robTickets_details).commit();
    }
}
