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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.shanghai.data.Data_TrainsTime_1;
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
public class TrainsTimeTableHome extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private View view;
    private EditText et_trains_shifa, et_trains_daozhan;
    private Button btn_tranis_select;
    private ListView listView_trains_1;
    private XXListViewAdapter<Data_TrainsTime_1> adapter;
    private SVProgressHUD svProgressHUD;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.trainstimetablehome, null);
        initView();
        return view;
    }

    private void initView() {
        svProgressHUD=new SVProgressHUD();
        et_trains_shifa = (EditText) view.findViewById(R.id.et_trains_shifa);
        et_trains_daozhan = (EditText) view.findViewById(R.id.et_trains_daozhan);
        trainsTime_tickets=new TrainsTime_tickets();
        btn_tranis_select = (Button) view.findViewById(R.id.btn_tranis_select);
        listView_trains_1 = (ListView) view.findViewById(R.id.listView_trains_1);

        btn_tranis_select.setOnClickListener(this);
        listView_trains_1.setOnItemClickListener(this);
        adapter = new XXListViewAdapter<Data_TrainsTime_1>(getActivity(), R.layout.item_trains_1) {
            @Override
            public void initGetView(int position, View convertView, ViewGroup parent) {
                TextView tv_train_no = (TextView) convertView.findViewById(R.id.tv_train_no);
                TextView tv_train_type = (TextView) convertView.findViewById(R.id.tv_train_type);
                TextView tv_start_station = (TextView) convertView.findViewById(R.id.tv_start_station);
                TextView tv_start_station_type = (TextView) convertView.findViewById(R.id.tv_start_station_type);
                TextView tv_run_time = (TextView) convertView.findViewById(R.id.tv_run_time);

                TextView tv_end_station = (TextView) convertView.findViewById(R.id.tv_end_station);
                TextView tv_end_station_type = (TextView) convertView.findViewById(R.id.tv_end_station_type);
                TextView tv_start_time = (TextView) convertView.findViewById(R.id.tv_start_time);
                TextView tv_end_time = (TextView) convertView.findViewById(R.id.tv_end_time);
                TextView tv_run_distance = (TextView) convertView.findViewById(R.id.tv_run_distance);

                tv_train_no.setText(getItem(position).getTrain_no());
                tv_train_type.setText(getItem(position).getTrain_type());
                tv_start_station.setText(getItem(position).getStart_station());
                tv_start_station_type.setText(getItem(position).getStart_station_type());
                tv_run_time.setText(getItem(position).getRun_time());

                tv_end_station.setText(getItem(position).getEnd_station());
                tv_end_station_type.setText(getItem(position).getEnd_station_type());
                tv_start_time.setText(getItem(position).getStart_time());
                tv_end_time.setText(getItem(position).getEnd_time());
                tv_run_distance.setText(getItem(position).getRun_distance());

            }
        };
        listView_trains_1.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        svProgressHUD.showWithStatus(getActivity(),"正在查询");
        //查询
        String shifa = et_trains_shifa.getText().toString().trim();
        String daozhan = et_trains_daozhan.getText().toString().trim();
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);

        RequestParams params = new RequestParams();

        params.put("from", shifa);
        params.put("to", daozhan);
        params.put("key", "f5d1931874a7c5f686edac873014e735");

        client.get("http://op.juhe.cn/onebox/train/query_ab", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                xLog(new String(bytes));
                if (svProgressHUD.isShowing(getActivity())) {
                    svProgressHUD.dismiss(getActivity());
                }
                try {
                    JSONObject jsonObject = new JSONObject(new String(bytes));
                    String code = jsonObject.getString("error_code");

                    if (code.equals("0")) {
                        JSONObject jsonObject1 = new JSONObject(jsonObject.get("result").toString());
                        String title = jsonObject1.getString("title");
                        JSONArray array = new JSONArray(jsonObject1.get("list").toString());
                        for (int j = 0; j < array.length(); j++) {

                            xLog(array.get(j).toString());
                            Data_TrainsTime_1 data_trainsTime_1 = new Gson().fromJson(array.get(j).toString(), Data_TrainsTime_1.class);
                            adapter.addItem(data_trainsTime_1);
                            adapter.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                if (svProgressHUD.isShowing(getActivity())) {
                    svProgressHUD.dismiss(getActivity()); //
                }
                xLog("error net error");
            }
        });

    }
    private TrainsTime_tickets trainsTime_tickets=null;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentManager fragmentManager =getFragmentManager();
        FragmentTransaction transaction =fragmentManager.beginTransaction();
        trainsTime_tickets = new TrainsTime_tickets();
        transaction.addToBackStack("a");
        Bundle bundle =new Bundle();
        bundle.putString("id",adapter.getItem(position).getTrain_no());
        trainsTime_tickets.setArguments(bundle);
        transaction.replace(R.id.mainView,trainsTime_tickets).commit();
    }


    public void xLog(String msg) {

        Log.w("NewsClient------->>>>>>", msg);
    }
}
