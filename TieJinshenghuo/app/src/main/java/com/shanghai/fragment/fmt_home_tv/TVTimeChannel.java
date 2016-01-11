package com.shanghai.fragment.fmt_home_tv;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.shanghai.data.data_tv.Data_TV_Channel;
import com.shanghai.soeasylib.adapter.XXListViewAdapter;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.shanghai.R;

import xinfu.com.pidanview.alerterview.progress.SVProgressHUD;

/**
 * Created by Administrator on 2015/12/14.
 */
public class TVTimeChannel extends Fragment implements AdapterView.OnItemClickListener {
    private View view;
    private ListView listVIew_TV_Channel;
    private String pId = null;
    private XXListViewAdapter<Data_TV_Channel> adapter;
    private TVTime_Final tvTime_final;
    private SVProgressHUD svProgressHUD; //
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tvtimechannel, null);
        initVIew();
        initData();
        return view;
    }

    private void initData() {
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);

        RequestParams params = new RequestParams();

        params.put("pId", pId);
        params.put("key", "9273a0ef78e31f8428200cca1eb407fd");

        client.get("http://japi.juhe.cn/tv/getChannel", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                xLog("================" + new String(bytes));
                try {
                    JSONObject jsonObject = new JSONObject(new String(bytes));
                    String error_code = jsonObject.getString("error_code");

                    if (error_code.equals("0")) {
                        JSONArray result = new JSONArray(jsonObject.get("result").toString());
                        for (int j = 0; j < result.length(); j++) {
                            xLog(result.get(j).toString());
                            Data_TV_Channel data_tv_channel = new Gson().fromJson(result.get(j).toString(), Data_TV_Channel.class);
                            adapter.addItem(data_tv_channel);
                            if (listVIew_TV_Channel.getAdapter() != null) {

                                adapter.notifyDataSetChanged();
                            } else {
                                listVIew_TV_Channel.setAdapter(adapter);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                xLog("error超时");

            }
        });
        if (adapter != null) {
            listVIew_TV_Channel.setAdapter(adapter);
        }
    }

    private void initVIew() {
        svProgressHUD=new SVProgressHUD();
        svProgressHUD.showWithStatus(getActivity(),"正在查询相关数据...");
        listVIew_TV_Channel = (ListView) view.findViewById(R.id.listVIew_TV_Channel);
        pId = getArguments().getString("id");
        adapter = new XXListViewAdapter<Data_TV_Channel>(getActivity(), R.layout.item_tv_channel) {
            @Override
            public void initGetView(int position, View convertView, ViewGroup parent) {
                TextView tv_tv_channel_tv1 = (TextView) convertView.findViewById(R.id.tv_tv_channel_tv1);
                TextView tv_tv_channel_tv2 = (TextView) convertView.findViewById(R.id.tv_tv_channel_tv2);

                tv_tv_channel_tv1.setText(getItem(position).getpId() + "".trim());
                tv_tv_channel_tv2.setText(getItem(position).getChannelName());
                if (svProgressHUD.isShowing(getActivity())) {
                    svProgressHUD.dismiss(getActivity());
                }
            }
        };
        listVIew_TV_Channel.setOnItemClickListener(this);
        tvTime_final = new TVTime_Final();

    }

    public void xLog(String msg) {
        Log.w("NewsClient------->>>>>>", msg);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();

        bundle.putString("id", adapter.getItem(position).getRel());
        tvTime_final.setArguments(bundle);
        transaction.addToBackStack("TV_Final");
        transaction.replace(R.id.mainView, tvTime_final).commit();
    }
}
