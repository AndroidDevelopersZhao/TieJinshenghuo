package com.shanghai.fragment.fmt_home_trains;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.shanghai.data.data_trains.Data_tickets;
import com.shanghai.soeasylib.adapter.XXListViewAdapter;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.shanghai.R;

import xinfu.com.pidanview.alerterview.alerterview.AlertView;
import xinfu.com.pidanview.alerterview.alerterview.OnItemClickListener;

/**
 * Created by Administrator on 2015/12/15.
 */
public class TrainsTime_tickets extends Fragment {
    private View view;
    private ListView listView_tickets;
    private String id;
    private XXListViewAdapter<Data_tickets> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tickets, null);
        initView();
        initData();
        return view;
    }

    private void initData() {
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        //
        params.put("train", id);
        params.put("key", "f5d1931874a7c5f686edac873014e735");
        client.get("http://op.juhe.cn/onebox/train/query", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                xLog(new String(bytes));
                try {
                    JSONObject jsonObject = new JSONObject(new String(bytes));
                    if (jsonObject.getString("error_code").equals("0")) {
                        JSONObject jsonObject1 = new JSONObject(jsonObject.get("result").toString());
                        JSONObject list = new JSONObject(jsonObject1.get("list").toString());
                        JSONObject price_list = new JSONObject(list.get("price_list").toString());
                        JSONArray item = new JSONArray(price_list.get("item").toString());
                        for (int j = 0; j < item.length(); j++) {
                            xLog(item.get(j).toString());
                            Data_tickets data_tickets = new Gson().fromJson(item.get(j).toString(), Data_tickets.class);
                            adapter.addItem(data_tickets);
                            adapter.notifyDataSetChanged();
                        }
                    }else {
                        new AlertView("提示", jsonObject.getString("reason"), null, new String[]{"返回"}, null, getActivity(), AlertView.Style.Alert, new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                getFragmentManager().beginTransaction().replace(R.id.mainView,new TrainsTimeTableHome()).commit();
                                getFragmentManager().beginTransaction().remove(TrainsTime_tickets.this);
                            }
                        }).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                new AlertView("提示","网络异常", null, new String[]{"返回"}, null, getActivity(), AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        getFragmentManager().beginTransaction().replace(R.id.mainView,new TrainsTimeTableHome()).commit();
                        getFragmentManager().beginTransaction().remove(TrainsTime_tickets.this);
                    }
                }).show();
            }
        });
    }

    private void initView() {
        id = getArguments().getString("id");
        listView_tickets = (ListView) view.findViewById(R.id.listView_tickets);
        adapter = new XXListViewAdapter<Data_tickets>(getActivity(), R.layout.item_tickets) {
            @Override
            public void initGetView(int position, View convertView, ViewGroup parent) {
                TextView tv_price_type = (TextView) convertView.findViewById(R.id.tv_price_type);
                TextView tv_price = (TextView) convertView.findViewById(R.id.tv_price);

                tv_price_type.setText(getItem(position).getPrice_type());
                tv_price.setText(getItem(position).getPrice());


            }
        };
        listView_tickets.setAdapter(adapter);
    }

    public void xLog(String msg) {

        Log.w("NewsClient------->>>>>>", msg);
    }
}
