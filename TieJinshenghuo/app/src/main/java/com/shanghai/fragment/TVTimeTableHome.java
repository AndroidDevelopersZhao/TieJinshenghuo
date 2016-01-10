package com.shanghai.fragment;
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

import android.app.Fragment;
import android.app.FragmentManager;
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
import com.shanghai.data.Data_TV_1;
import com.shanghai.soeasylib.adapter.XXListViewAdapter;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.shanghai.R;

import xinfu.com.pidanview.alerterview.progress.SVProgressHUD;

/**
 * 项目名称： NewsClient
 * 创建日期： 2015/12/14  9:59
 * 项目作者： 赵文贇
 * 项目包名： xinfu.com.newsclient.fragment
 */
public class TVTimeTableHome extends Fragment implements AdapterView.OnItemClickListener {
    private View view;
    private ListView listview_TV_tvType;
    private XXListViewAdapter<Data_TV_1> adapter;
    private TVTimeChannel tvTimeChannel;
    private SVProgressHUD svProgressHUD;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tvtimetablehome, null);
        initView();
        initData();
        return view;

    }

    private void initData() {
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);

        RequestParams params = new RequestParams();

        params.put("key", "9273a0ef78e31f8428200cca1eb407fd");

        client.get("http://japi.juhe.cn/tv/getCategory", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                xLog(new String(bytes));
                try {
                    JSONObject jsonObject = new JSONObject(new String(bytes));
                    String code = jsonObject.getString("error_code");
                    if (code.equals("0")) {
//                        xLog(jsonObject.getString("result"));
                        JSONArray result = new JSONArray(jsonObject.get("result").toString());
                        for (int j = 0; j < result.length(); j++) {
                            xLog("=============" + result.get(j));
                            Data_TV_1 data_tv_1 = new Gson().fromJson(result.get(j).toString(), Data_TV_1.class);
                            adapter.addItem(data_tv_1);
                            adapter.notifyDataSetChanged();
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
            listview_TV_tvType.setAdapter(adapter);
        }
    }

    private void initView() {
        svProgressHUD=new SVProgressHUD();
        svProgressHUD.showWithStatus(getActivity(),"正在查询相关数据...");
        listview_TV_tvType = (ListView) view.findViewById(R.id.listview_TV_tvType);
        adapter = new XXListViewAdapter<Data_TV_1>(getActivity(), R.layout.item_tv_1) {
            @Override
            public void initGetView(int position, View convertView, ViewGroup parent) {
                TextView tv_tv_1 = (TextView) convertView.findViewById(R.id.tv_tv_1);
                tv_tv_1.setText(getItem(position).getName());
                if (svProgressHUD.isShowing(getActivity())) {
                    svProgressHUD.dismiss(getActivity());
                }
            } //
        };
        listview_TV_tvType.setOnItemClickListener(this);
        tvTimeChannel = new TVTimeChannel();
    }


    public void xLog(String msg) {

        Log.w("NewsClient------->>>>>>", msg);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Bundle bundle = new Bundle();
        bundle.putString("id", adapter.getItem(position).getId() + "".trim());
        tvTimeChannel.setArguments(bundle);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack("TV_1");
        transaction.replace(R.id.mainView, tvTimeChannel).commit();
    }
}
