package com.shanghai.fragment.fmt_home_tv;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.shanghai.data.data_tv.Data_TV_Final;
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
public class TVTime_Final extends Fragment {
    private View view;
    private ListView listView_TV_Final;
    private String id;
    private XXListViewAdapter<Data_TV_Final> adapter;
    private SVProgressHUD svProgressHUD;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tvtimefinal, null);
        initVIew();
        initData();
        return view;
    }

    private void initData() {
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);

        RequestParams params = new RequestParams();

        params.put("key", "9273a0ef78e31f8428200cca1eb407fd");
        params.put("code", id);

        client.get("http://japi.juhe.cn/tv/getProgram", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                xLog(new String(bytes));
                try {
                    JSONObject jsonObject = new JSONObject(new String(bytes));
                    if (jsonObject.getString("error_code").equals("0")) {
                        JSONArray jsonArray = new JSONArray(jsonObject.get("result").toString());
                        for (int j = 0; j < jsonArray.length(); j++) {
                            xLog(jsonArray.get(j).toString());
                            Data_TV_Final data_tv_final = new Gson().fromJson(jsonArray.get(j).toString(), Data_TV_Final.class);
                            adapter.addItem(data_tv_final);
                            adapter.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("NewClient", "异常");
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.d("NewClient", "网络异常");
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (TVTime_Final.this.isVisible()){

                                if (svProgressHUD.isShowing(getActivity())) {
                                    svProgressHUD.dismiss(getActivity());
                                    Toast.makeText(getActivity(),"该频道暂无信息",Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });

                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //
    private void initVIew() {
        svProgressHUD=new SVProgressHUD();
        svProgressHUD.showWithStatus(getActivity(),"正在查询相关数据...");
        listView_TV_Final = (ListView) view.findViewById(R.id.listView_TV_Final);
        id = getArguments().getString("id");
        adapter = new XXListViewAdapter<Data_TV_Final>(getActivity(), R.layout.item_tv_tv_final) {
            @Override
            public void initGetView(int position, View convertView, ViewGroup parent) {
                TextView tv_cName = (TextView) convertView.findViewById(R.id.tv_cName);
                TextView tv_pName = (TextView) convertView.findViewById(R.id.tv_pName);
                TextView tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                android.webkit.WebView tv_webview = (android.webkit.WebView) convertView.findViewById(R.id.tv_webview);

                tv_cName.setText(getItem(position).getcName());
                tv_pName.setText(getItem(position).getpName());
                tv_time.setText(getItem(position).getTime());

                tv_webview.loadUrl(getItem(position).getpUrl());
                if (svProgressHUD.isShowing(getActivity())) {
                    svProgressHUD.dismiss(getActivity());
                }
            }
        };
        listView_TV_Final.setAdapter(adapter);
    }


    public void xLog(String msg) {

        Log.w("NewsClient------->>>>>>", msg);
    }
}
