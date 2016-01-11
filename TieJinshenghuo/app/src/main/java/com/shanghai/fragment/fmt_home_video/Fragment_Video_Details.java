package com.shanghai.fragment.fmt_home_video;
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
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.shanghai.data.data_video.Data_Video_Details;
import com.shanghai.listener.listener_util.GetDataListener;
import com.shanghai.soeasylib.adapter.XXListViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import xinfu.com.pidanview.alerterview.progress.SVProgressHUD;
import com.shanghai.R;
import com.shanghai.utils.GetDataFromService;

/**
 * 项目名称： NewsClient
 * 创建日期： 2015/12/16  14:05
 * 项目作者： 赵文贇
 * 项目包名： xinfu.com.newsclient.fragment
 */
public class Fragment_Video_Details extends Fragment implements AdapterView.OnItemClickListener {
     private View view;
    private byte[] data;
    private boolean isStartGetData = false;
    private SVProgressHUD svProgressHUD;
    private String name;
    private ListView listView_video_details;
    private XXListViewAdapter<Data_Video_Details> adapter;
    private List<TextView> textViewList;
    private TextView tv_video_tatails_title, tv_video_tatails_tag, tv_video_tatails_act,
            tv_video_tatails_rating, tv_video_tatails_area, tv_video_tatails_dir, tv_video_tatails_desc;
    private ImageView im_video_tatails_images;
    private ScrollView scrollView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_video_details, null);
        initView();
        iniData();
        return view;
    }

    private void initView() {
        svProgressHUD.showWithStatus(getActivity(), "正在加载数据");
        textViewList = new ArrayList<>();
        scrollView = (ScrollView) view.findViewById(R.id.scview);
        tv_video_tatails_title = (TextView) view.findViewById(R.id.tv_video_tatails_title);
        tv_video_tatails_tag = (TextView) view.findViewById(R.id.tv_video_tatails_tag);
        tv_video_tatails_act = (TextView) view.findViewById(R.id.tv_video_tatails_act);
        tv_video_tatails_rating = (TextView) view.findViewById(R.id.tv_video_tatails_rating);
        tv_video_tatails_area = (TextView) view.findViewById(R.id.tv_video_tatails_area);
        tv_video_tatails_dir = (TextView) view.findViewById(R.id.tv_video_tatails_dir);
        tv_video_tatails_desc = (TextView) view.findViewById(R.id.tv_video_tatails_desc);
        im_video_tatails_images = (ImageView) view.findViewById(R.id.im_video_tatails_images);

        textViewList.add(tv_video_tatails_title);
        textViewList.add(tv_video_tatails_tag);
        textViewList.add(tv_video_tatails_act);
        textViewList.add(tv_video_tatails_rating);
        textViewList.add(tv_video_tatails_area);
        textViewList.add(tv_video_tatails_dir);
        textViewList.add(tv_video_tatails_desc);

        svProgressHUD = new SVProgressHUD();
        try {
            data = getArguments().getByteArray("data");
        } catch (Throwable throwable) {
            data = null;
        }
        try {
            name = getArguments().getString("name");
        } catch (Throwable throwable) {
            name = null;
        }

        if (data == null || data.equals("")) {
            isStartGetData = true;
        }
        xLog("传值后,data:" + data);
        xLog("传值后,name:" + name);
        listView_video_details = (ListView) view.findViewById(R.id.listView_video_details);

        adapter = new XXListViewAdapter<Data_Video_Details>(getActivity(), R.layout.item_listview_tuijian) {
            @Override
            public void initGetView(int position, View convertView, ViewGroup parent) {
                TextView tv_tv_tv_title = (TextView) convertView.findViewById(R.id.tv_tv_tv_title);
                ImageView im_im_im = (ImageView) convertView.findViewById(R.id.im_im_im);


                tv_tv_tv_title.setText(getItem(position).getTitle());
                im_im_im.setImageBitmap(getItem(position).getBitmap());
//                setListViewHigh();
            }
        };
        if (adapter != null) {
            listView_video_details.setAdapter(adapter);
        }
        listView_video_details.setOnItemClickListener(this);
        setListViewHigh();
        scrollView.scrollTo(0, 0);
    }

    private void iniData() {
        //从服务器请求
        if (isStartGetData) {
            xLog("网络请求");
            svProgressHUD.showWithStatus(getActivity(), "获取数据……");
            selectVideo(name);
        } else {//填充本地
            xLog("填充本地");
            svProgressHUD.showWithStatus(getActivity(), "正在填充数据……");
            spliteData(new String(data));
        }
    }

    private void setListViewHigh() {
        int totalHeight = 0;

        for (int i = 0; i < adapter.getCount(); i++) {

            View listItem = adapter.getView(i, null, listView_video_details);

            listItem.measure(0, 0);

            totalHeight += listItem.getMeasuredHeight();

        }


        ViewGroup.LayoutParams params = listView_video_details.getLayoutParams();

        params.height = totalHeight + (listView_video_details.getDividerHeight() * (listView_video_details.getCount() - 1));

        listView_video_details.setLayoutParams(params);
    }

    private void selectVideo(String videoName) {
        GetDataFromService client = new GetDataFromService("http://op.juhe.cn/onebox/movie/video");

        client.put("key", "4ab501671c1f160f5d7fdd198fce86f4");
        client.put("q", videoName);

        client.doGet(new GetDataListener() {
            @Override
            public void onSucc(byte[] data) {
                svProgressHUD.showWithStatus(getActivity(), "解析数据中……");
                xLog(new String(data));
                try {
                    JSONObject jsonObject = new JSONObject(new String(data));
                    if (jsonObject.getInt("error_code") == 0) {
                        spliteData(new String(data));
                    } else {
                        if (svProgressHUD != null) {
                            svProgressHUD.dismiss(getActivity());
                        }
                        Toast.makeText(getActivity(), jsonObject.getString("reason"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (svProgressHUD != null) {
                        svProgressHUD.dismiss(getActivity());
                    }
                }
            }

            @Override
            public void onError(String errorMsg) {
                if (svProgressHUD != null) {
                    svProgressHUD.dismiss(getActivity());
                }
                if (Fragment_Video_Details.this.isVisible()) {

                    Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void spliteData(String data) {
        try {
            JSONObject all = new JSONObject(data);
            if (all.getInt("error_code") == 0) {
                JSONObject result = new JSONObject(all.get("result").toString());
                xLog("title:" + result.getString("title"));

                xLog("tag:" + result.getString("tag"));
                xLog("act:" + result.getString("act"));
                xLog("year:" + result.getString("year"));
                xLog("rating:" + result.getString("rating"));
                xLog("area:" + result.getString("area"));
                xLog("dir:" + result.getString("dir"));
                xLog("desc:" + result.getString("desc"));
                xLog("cover:" + result.getString("cover"));
                xLog("vdo_status:" + result.getString("vdo_status"));
                textViewList.get(0).setText(result.getString("title"));
                textViewList.get(1).setText(result.getString("tag"));
                textViewList.get(2).setText(result.getString("act"));
                textViewList.get(3).setText(result.getString("rating"));
                textViewList.get(4).setText(result.getString("area"));
                textViewList.get(5).setText(result.getString("dir"));
                textViewList.get(6).setText(result.getString("desc"));
                if (svProgressHUD != null) {
                    svProgressHUD.dismiss(getActivity());
                }
                GetDataFromService client = new GetDataFromService(result.getString("cover"));
                client.doPost(new GetDataListener() {
                    @Override
                    public void onSucc(final byte[] data) {

                        xLog("图片获取成功");
                        im_video_tatails_images.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (Fragment_Video_Details.this.isVisible()){
//
//                                    im_video_tatails_images.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));
//                                }
//                            }
//                        });

                    }

                    @Override
                    public void onError(String errorMsg) {
                        xLog("图片获取失败");
                    }
                });
                JSONArray video_rec = new JSONArray(result.get("video_rec").toString());
                for (int i = 0; i < video_rec.length(); i++) {
                    xLog("video_rec." + i + ":" + video_rec.get(i).toString());
                    JSONObject jsonObject = new JSONObject(video_rec.get(i).toString());
                    Data_Video_Details data_video_details = new Data_Video_Details();
                    data_video_details.setTitle(jsonObject.getString("title"));
                    adapter.addItem(data_video_details);
                    adapter.notifyDataSetChanged();
                    setListViewHigh();
                    scrollView.scrollTo(0, 0);
                    getImagesFromService(jsonObject.getString("cover"), i);
                }
            } else {
                if (svProgressHUD != null) {
                    svProgressHUD.dismiss(getActivity());
                }
                Toast.makeText(getActivity(), all.getString("reason"), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "数据处理异常", Toast.LENGTH_SHORT).show();
            if (svProgressHUD != null) {
                svProgressHUD.dismiss(getActivity());
            }
        }
    }

    private void getImagesFromService(String url, final int i) {
        GetDataFromService c = new GetDataFromService(url);
        c.doGet(new GetDataListener() {
            @Override
            public void onSucc(byte[] data) {
                xLog("图片请求成功");
                try {

                    adapter.getList().get(i).setBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));
                    adapter.notifyDataSetChanged();
                }catch (Throwable t){
                    xLog("99999999999999999999999999999999999999999999999999999999999");
                }
                setListViewHigh();
                scrollView.scrollTo(0, 0);
            }

            @Override
            public void onError(String errorMsg) {
                xLog("图片请求失败");
            }
        });
    }

    public void xLog(String msg) {
        Log.w("NewsClient------->>>>>>", msg);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Fragment_Video_Details details = new Fragment_Video_Details();
        Bundle bundle = new Bundle();
        bundle.putString("name", adapter.getItem(position).getTitle());
        details.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack("12");
        transaction.replace(R.id.view_video, details).commit();
    }

    @Override
    public void onResume() {
        xLog("onResume");
        super.onResume();
    }
}
