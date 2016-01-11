package com.shanghai.fragment.fmt_home_newclient;
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
import com.shanghai.R;
import com.shanghai.data.data_news.Data_News_Home;
import com.shanghai.data.data_news.Data_News_Home_Code;
import com.shanghai.soeasylib.adapter.XXListViewAdapter;
import com.shanghai.utils.Util;

import xinfu.com.pidanview.alerterview.progress.SVProgressHUD;

/**
 * 项目名称： NewsClient
 * 创建日期： 2015/12/14  9:59
 * 项目作者： 赵文贇
 * 项目包名： xinfu.com.newsclient.fragment
 */
public class  NewsHome extends Fragment implements AdapterView.OnItemClickListener {
    private View view;
    private ListView listView_NewHome;
    private XXListViewAdapter<Data_News_Home> myAdapter = null;
    private Data_News_Home_Code data_news_home_code;
    private Data_News_Home data_news_home;
    private SVProgressHUD svProgressHUD = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.newshome, null);
        svProgressHUD = new SVProgressHUD();
        svProgressHUD.showWithStatus(getActivity(), "正在获取热点信息……");
        initView();
        return view;
    }

    private void initView() {
        listView_NewHome = (ListView) view.findViewById(R.id.listView_NewHome);
        initDataFromInternet();
        listView_NewHome.setOnItemClickListener(this);
    }

    private void initDataFromInternet() {
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        RequestParams params = new RequestParams();

        params.put("key", Util.appid_news);

        client.post(Util.url_new_hot, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                xLog("data :" + new String(bytes));
                data_news_home_code = new Gson().fromJson(new String(bytes), Data_News_Home_Code.class);
                int code = data_news_home_code.getError_code();
                xLog("code :" + data_news_home_code.getError_code());
                xLog("reason:" + data_news_home_code.getReason());
                xLog("result:" + data_news_home_code.getResult());
                if (code == 0) {
                    initAdapter();
                }
            }

            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                xLog("error,Internet error");
            }
        });
    }

    private void initAdapter() {
        myAdapter = new XXListViewAdapter<Data_News_Home>(getActivity(), R.layout.item_data_newsclient) {
            @Override
            public void initGetView(int position, View convertView, ViewGroup parent) {
                TextView item_news_tv_home = (TextView) convertView.findViewById(R.id.item_news_tv_home);
                item_news_tv_home.setText(getItem(position).getTitle());
                svProgressHUD.dismiss(getActivity());
            }
        };
        for (int j = 0; j < data_news_home_code.getResult().length; j++) {
            data_news_home = new Data_News_Home();
            data_news_home.setTitle(data_news_home_code.getResult()[j]);
            myAdapter.addItem(data_news_home);
        }
        listView_NewHome.setAdapter(myAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        xLog("Title :" + myAdapter.getItem(position).getTitle());
        NewsWords newsWords = new NewsWords();
        Bundle bundle = new Bundle();

        bundle.putString("hot", myAdapter.getItem(position).getTitle());
        newsWords.setArguments(bundle);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack("NewsHome");

        transaction.replace(R.id.mainView, newsWords).commit();
    }

    public void xLog(String msg) {

        Log.w("NewsClient------->>>>>>", msg);
    }


}
