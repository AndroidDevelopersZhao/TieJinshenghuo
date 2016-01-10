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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.shanghai.R;
import com.shanghai.data.Data_News;
import com.shanghai.data.Data_News_Home_Code_JsonArray;
import com.shanghai.soeasylib.adapter.XXListViewAdapter;
import com.shanghai.utils.Util;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import xinfu.com.pidanview.alerterview.progress.SVProgressHUD;

/**
 * 项目名称： NewsClient
 * 创建日期： 2015/12/14  9:59
 * 项目作者： 赵文贇
 * 项目包名： xinfu.com.newsclient.fragment
 */
public class NewsWords extends Fragment implements AdapterView.OnItemClickListener {
    private View view;
    private ListView listView_NewHome_Words;
    private String hot = null;
    private Data_News_Home_Code_JsonArray data_code;
    private Data_News data_news;
    private XXListViewAdapter<Data_News> myAdapter = null;
    private WebView webView;
    private SVProgressHUD svProgressHUD;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.newswords, null);
        xLog("onCreateView");
        initView();
        initDataFromInternet();
        return view;
    }
    //
    private void initDataFromInternet() {
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);

        RequestParams params = new RequestParams();

        params.put("key", Util.appid_news);
        params.put("q", hot);

        client.post(Util.url_new_home, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                xLog("new String():" + new String(bytes));
//                data_code = new Gson().fromJson(new String(bytes), Data_News_Home_Code_JsonArray.class);
                initAdapter(new String(bytes));
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                xLog("error,networlk error");
            }
        });
    }

    private void initAdapter(String data) {

        myAdapter = new XXListViewAdapter<Data_News>(getActivity(), R.layout.item_data_newswords) {
            @Override
            public void initGetView(int position, View convertView, ViewGroup parent) {
                TextView item_tv_news_title = (TextView) convertView.findViewById(R.id.item_tv_news_title);
                TextView item_tv_news_content = (TextView) convertView.findViewById(R.id.item_tv_news_content);
                TextView item_tv_news_pdata = (TextView) convertView.findViewById(R.id.item_tv_news_pdata);
                ImageView imageView = (ImageView) convertView.findViewById(R.id.item_im_news);

                item_tv_news_title.setText(getItem(position).getTitle());
                item_tv_news_content.setText(getItem(position).getContent());
                item_tv_news_pdata.setText(getItem(position).getPdate());
//                try {
                imageView.setImageBitmap(getItem(position).getBitmap());
//
//                } catch (Throwable throwable) {
//
//                }
                if (svProgressHUD != null) {
                    if (svProgressHUD.isShowing(getActivity())) {

                        svProgressHUD.dismiss(getActivity());
                    }
                }
            }
        };
        initData(data);
        listView_NewHome_Words.setAdapter(myAdapter);
        listView_NewHome_Words.setOnItemClickListener(this);
    }

    private void initData(String data) {

        try {
            JSONObject jsonObject = new JSONObject(data);
            xLog("error_code" + jsonObject.getInt("error_code"));
            if (jsonObject.getInt("error_code") == 0) {

                xLog("success");
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                for (int q = 0; q < jsonArray.length(); q++) {
//                            xLog("a:"+((JSONObject)jsonArray.get(q)).getString("title"));
                    data_news = new Gson().fromJson(((JSONObject) jsonArray.get(q)).toString(), Data_News.class);
                    xLog("data_news.getTitle()：" + data_news.getTitle());
                    getImages(q, data_news.getImg());
                    myAdapter.addItem(data_news);
                }


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private ArrayList<Bitmap> bitmaps = null;

    private void getImages(int position, String img_url) {
        xLog("图片请求URL：(Position=)" + position + "img_url：" + img_url);
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);

        final int finalPosition = position;
        final int finalPosition1 = position;
        client.post(img_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                xLog("图片获取成功");

                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                bitmaps.add(bitmap);
                myAdapter.getList().get(finalPosition).setBitmap(bitmap);
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                xLog("图片获取超时");
            }
        });
        position++;
    }

    private void initView() {
        hot = getArguments().getString("hot");
        webView = new WebView();
        svProgressHUD = new SVProgressHUD();
        svProgressHUD.showWithStatus(getActivity(), "正在加载数据……");
        if (hot == null) {
            Toast.makeText(getActivity(), "hot null", Toast.LENGTH_LONG).show();
            return;
        }
        xLog(getArguments().getString("hot"));
        bitmaps = new ArrayList<>();
        listView_NewHome_Words = (ListView) view.findViewById(R.id.listView_NewHome_Words);

    }


    public void xLog(String msg) {

        Log.w("NewsClient------->>>>>>", msg);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        xLog(myAdapter.getItem(position).getUrl());
        String url = myAdapter.getItem(position).getUrl();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        webView.setArguments(bundle);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack("NewsWords");
        transaction.replace(R.id.mainView, webView).commit();
    }

    @Override
    public void onPause() {
        xLog("onPause");
        super.onPause();
    }

    @Override
    public void onResume() {
        xLog("onResume");
        super.onResume();
    }
}
