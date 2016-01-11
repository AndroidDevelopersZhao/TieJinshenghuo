package com.shanghai.fragment.fmt_home_wechat;
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
import android.app.FragmentTransaction;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shanghai.R;
import com.shanghai.data.data_wechat.Data_WeChatSelection;
import com.shanghai.listener.GetDataListener;
import com.shanghai.soeasylib.adapter.XXListViewAdapter;
import com.shanghai.view.CustomListView;
import com.shanghai.utils.GetDataFromService;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.Tencent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import xinfu.com.pidanview.alerterview.alerterview.AlertView;
import xinfu.com.pidanview.alerterview.alerterview.OnItemClickListener;
import xinfu.com.pidanview.alerterview.progress.SVProgressHUD;


/**
 * 项目名称： NewsClient
 * 创建日期： 2015/12/18  9:26
 * 项目作者： 赵文贇
 * 项目包名： xinfu.com.newsclient.fragment
 */
public class WeChatSelection extends Fragment implements AdapterView.OnItemClickListener, CustomListView.OnRefreshListner, CustomListView.OnFootLoadingListener, AdapterView.OnItemLongClickListener {
    private View view;
    private final String AppKey = "ba6a565b20139239ec4dd6f46f5540f8";
    private final String url = "http://v.juhe.cn/weixin/query";
    private CustomListView listView_WeChat_Selection;
    private XXListViewAdapter<Data_WeChatSelection> adapter;
    private SVProgressHUD svProgressHUD = null;
    private int position_images = 0;
    private int DATACOUNT = 5;
    private View footer;
    private int PNO = 1; //
    private Data_WeChatSelection data_WeChat;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.wechat, null);
        initTen();
        initView();
        initData();
        return view;
    }

    private Tencent mTencent;

    private void initTen() {
        mTencent = Tencent.createInstance("1104950333", getActivity());
    }

    private void initData() {

        GetDataFromService client = new GetDataFromService(url);
        client.put("pno", PNO);
        client.put("key", AppKey);
        client.put("ps", DATACOUNT);
//        if (svProgressHUD != null) {
//            svProgressHUD.dismiss(getActivity());
//        }
        client.doGet(new GetDataListener() {
            @Override
            public void onSucc(byte[] data) {
                xLog("Succ:" + new String(data));
                if (svProgressHUD != null) {

                    //
                    svProgressHUD.dismiss(getActivity());
                }

                try {
                    JSONObject all = new JSONObject(new String(data));
                    if (all.getInt("error_code") == 0) {
                        spiltsData(all.get("result").toString());
                    } else {

                        Toast.makeText(getActivity(), all.getString("reason"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "数据解析异常", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String errorMsg) {
                if (svProgressHUD != null) {
                    svProgressHUD.dismiss(getActivity());
                }
                xLog("Error," + errorMsg);
                if (WeChatSelection.this.isVisible()) {
                    Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void spiltsData(String re) {
        try {
            JSONObject result = new JSONObject(re);
            int ps = result.getInt("ps");
            JSONArray list = new JSONArray(result.get("list").toString());
            for (int i = 0; i < list.length(); i++) {
                xLog(list.get(i).toString());
                data_WeChat = new Gson().fromJson(list.get(i).toString(), Data_WeChatSelection.class);
                adapter.addItem(data_WeChat);
                adapter.notifyDataSetChanged();
                xLog("数据刷新完成，刷新适配器，关闭上下拉的layout");
                listView_WeChat_Selection.onFootLoadingComplete();
                listView_WeChat_Selection.removeFooterView(footer);
                listView_WeChat_Selection.onRefreshComplete();
                getImageFromInternet(position_images, data_WeChat.getFirstImg());

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getImageFromInternet(final int position_images, final String firstImg) {
        this.position_images++;
        GetDataFromService client = new GetDataFromService(firstImg);
        client.doGet(new GetDataListener() {
            @Override
            public void onSucc(byte[] data) {
                xLog("get Bitmap Succ");
                xLog("根据索引设置图片，当前索引参数：" + position_images);
                if (position_images < adapter.getCount()) {

                    adapter.getItem(position_images).setBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));
                } else {
                    Log.e("=========================", "索引错误，po:" + position_images + "size:" + adapter.getCount());
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String errorMsg) {
                xLog("Error,get bitmap time out," + errorMsg);
            }
        });
    }

    private void initView() {
        svProgressHUD = new SVProgressHUD();
        svProgressHUD.showWithStatus(getActivity(), "正在加载最新数据……");
        listView_WeChat_Selection = (CustomListView) view.findViewById(R.id.listView_WeChat_Selection);

        adapter = new XXListViewAdapter<Data_WeChatSelection>(getActivity(), R.layout.item_wechatselection) {
            @Override
            public void initGetView(int position, View convertView, ViewGroup parent) {
//                if (svProgressHUD != null) {
//                    svProgressHUD.dismiss(getActivity());
//                }
                ImageView imageView = (ImageView) convertView.findViewById(R.id.im_wechat_images);

                TextView tv_wechat_title = (TextView) convertView.findViewById(R.id.tv_wechat_title);
                TextView tv_wechat_source = (TextView) convertView.findViewById(R.id.tv_wechat_source);

                tv_wechat_title.setText(getItem(position).getTitle());
                tv_wechat_source.setText(getItem(position).getSource());

                imageView.setImageBitmap(getItem(position).getBitmap());

            }
        };
        listView_WeChat_Selection.setAdapter(adapter);
        listView_WeChat_Selection.setOnItemClickListener(this);
        listView_WeChat_Selection.setOnRefreshListner(this);
        footer = View.inflate(getActivity(), R.layout.footer, null);
        listView_WeChat_Selection.setOnAddFootListener(new CustomListView.OnAddFootListener() {
            @Override
            public void addFoot() {
                listView_WeChat_Selection.addFooterView(footer);
                footer.setVisibility(View.INVISIBLE);
            }
        });
        listView_WeChat_Selection.setOnFootLoadingListener(this);
        listView_WeChat_Selection.setOnItemLongClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        adapter.notifyDataSetChanged();
        xLog("当前点击的item position：" + position);
        WeChat_WebView weChat_webView = new
                WeChat_WebView();
        Bundle bundle = new Bundle();

        bundle.putString("url", adapter.getItem(position - 1).getUrl());
        weChat_webView.setArguments(bundle);
        FragmentTransaction y = getFragmentManager().beginTransaction();
        y.addToBackStack("WebView");
        y.replace(R.id.mainView, weChat_webView).commit();
        xLog(adapter.getItem(position).getUrl());
    }

    @Override
    public void onResume() {
        position_images = 0;
        super.onResume();
    }

    public void xLog(String msg) {
        Log.w("NewsClient------->>>>>>", msg);
    }

    @Override
    public void onRefresh() {
        xLog("下拉刷新");
        position_images = 0;
        DATACOUNT = 5;
        PNO = 1;
        data_WeChat = null;
        xLog("当前adapter里的对象的size：" + adapter.getList().size());
        adapter.removeAll();
        initData();


    }

    @Override
    public void onFootLoading() {
        xLog("上拉加载");
        PNO++;
        footer.setVisibility(View.VISIBLE);
//        position_images = 0;
//        DATACOUNT += 20;
//        for (int i = 0; i < adapter.getList().size(); i++) {
//            adapter.remove(i);
//        }
        initData();

    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        xLog("长按事件触发,positionL"+position);


        new AlertView("提示", "分享给谁呢？", "取消",null , new String[]{"QQ好友", "QQ空间"}, getActivity(), AlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int posi) {
                xLog("po:" + posi);
//
                switch (posi){
                    case -1:

                        break;

                    case 0:
                        toQQ(position-1, 0);
                        break;
                    case 1:
                        toQQ(position-1, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
                        break;
                }
            }
        }).show();
        return true;
    }

    private void toQQ(int position, int type) {
        final Bundle params = new Bundle();
        Log.e("AAAAAAAAAA",adapter.getItem(position).getUrl());
        Log.e("AAAAAAAAAA",adapter.getItem(position).getFirstImg());

        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, adapter.getItem(position).getTitle());
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "微信精选");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, adapter.getItem(position).getUrl());
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, adapter.getItem(position).getFirstImg());
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "贴近生活");
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, type);
        mTencent.shareToQQ(getActivity(), params, null);
    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(requestCode == Constants.REQUEST_API) {
//            if(resultCode == Constants.RESULT_LOGIN) {
//                mTencent.handleLoginData(data, loginListener);
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }

}
