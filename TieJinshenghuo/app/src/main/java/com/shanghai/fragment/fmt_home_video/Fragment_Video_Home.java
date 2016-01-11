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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shanghai.R;
import com.shanghai.data.data_video.Data_Video_Home;
import com.shanghai.listener.GetDataListener;
import com.shanghai.listener.Listener_location;
import com.shanghai.soeasylib.adapter.XXListViewAdapter;
import com.shanghai.utils.GetDataFromService;
import com.shanghai.utils.Location_Client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import xinfu.com.pidanview.alerterview.progress.SVProgressHUD;

/**
 * 项目名称： NewsClient
 * 创建日期： 2015/12/16  12:26
 * 项目作者： 赵文贇
 * 项目包名： xinfu.com.newsclient.fragment
 */
public class Fragment_Video_Home extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private View view;
    private String city;
    private GetDataFromService getDataFromService;
    private ListView listView_video_home;
    private XXListViewAdapter<Data_Video_Home> adapter;
    private int positions = 0;
    private SVProgressHUD svProgressHUD = null;
    private LinearLayout layout_find = null;
    private Button btn_video_get;
    private Fragment_Video_Details video_details;
    private EditText et_videi_select;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_video_home, null);
        initView();
        initData();
        return view;
    }

    private void initView() {
        svProgressHUD = new SVProgressHUD();
        svProgressHUD.showWithStatus(getActivity(), "正在获取最新热点数据");
        layout_find = (LinearLayout) view.findViewById(R.id.layout_find);
        et_videi_select = (EditText) view.findViewById(R.id.et_videi_select);
        btn_video_get = (Button) view.findViewById(R.id.btn_video_get);

        listView_video_home = (ListView) view.findViewById(R.id.listView_video_home);
        adapter = new XXListViewAdapter<Data_Video_Home>(getActivity(), R.layout.item_listview_video_home) {
            @Override
            public void initGetView(int position, View convertView, ViewGroup parent) {
                ImageView imageView = (ImageView) convertView.findViewById(R.id.im_video_home);

                TextView tv_video_title = (TextView) convertView.findViewById(R.id.tv_video_title);
                TextView tv_video_grade = (TextView) convertView.findViewById(R.id.tv_video_grade);
                TextView tv_video_starring_1 = (TextView) convertView.findViewById(R.id.tv_video_starring_1);
                TextView tv_video_starring_2 = (TextView) convertView.findViewById(R.id.tv_video_starring_2);
                TextView tv_video_starring_3 = (TextView) convertView.findViewById(R.id.tv_video_starring_3);
                TextView tv_video_starring_4 = (TextView) convertView.findViewById(R.id.tv_video_starring_4);
                TextView tv_video_type_1 = (TextView) convertView.findViewById(R.id.tv_video_type_1);
                TextView tv_video_type_2 = (TextView) convertView.findViewById(R.id.tv_video_type_2);
                TextView tv_video_date = (TextView) convertView.findViewById(R.id.tv_video_date);
                List<TextView> textViewList = new ArrayList<>();

                textViewList.add(tv_video_title);
                textViewList.add(tv_video_grade);
                textViewList.add(tv_video_starring_1);
                textViewList.add(tv_video_starring_2);
                textViewList.add(tv_video_starring_3);
                textViewList.add(tv_video_starring_4);
                textViewList.add(tv_video_type_1);
                textViewList.add(tv_video_type_2);
                textViewList.add(tv_video_date);
                for (int i = 0; i < textViewList.size(); i++) {
                    textViewList.get(0).setText(getItem(position).getTvTitle());
                    textViewList.get(1).setText(getItem(position).getGrade());
                    textViewList.get(2).setText(getItem(position).getStarring_1());
                    textViewList.get(3).setText(getItem(position).getStarring_2());
                    textViewList.get(4).setText(getItem(position).getStarring_3());
                    textViewList.get(5).setText(getItem(position).getStarring_4());
                    textViewList.get(6).setText(getItem(position).getType_1());
                    textViewList.get(7).setText(getItem(position).getType_2());
                    textViewList.get(8).setText(getItem(position).getDate());
                }
                imageView.setImageBitmap(getItem(position).getImages());

                if (svProgressHUD.isShowing(getActivity())) {
                    svProgressHUD.dismiss(getActivity());
                }
            }
        };
        listView_video_home.setAdapter(adapter);
        layout_find.setVisibility(View.VISIBLE);
        btn_video_get.setOnClickListener(this);
        listView_video_home.setOnItemClickListener(this);
    }


    private void initData() {
        xLog("开始定位操作...");
        new Location_Client(getActivity(), new Listener_location() {
            @Override
            public void Location(String... strings) {
                city = strings[0]; //
                xLog("city"+city);
                if (city != null) {
                    xLog("开始请求数据...");
                    getDataFromService = new GetDataFromService("http://op.juhe.cn/onebox/movie/pmovie");
                    getDataFromService.put("key", "4ab501671c1f160f5d7fdd198fce86f4");
                    getDataFromService.put("city", city);
                    getDataFromService.doGet(new GetDataListener() {
                        @Override
                        public void onSucc(byte[] data) {
                            xLog("成功回调？：" + new String(data));
                            try {
                                JSONObject jo = new JSONObject(new String(data));
                                if (jo.getInt("error_code") == 0) {
                                    spliteData(jo.getJSONObject("result").toString());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(String errorMsg) {
                            xLog("失败回调：" + errorMsg);
                        }
                    });
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                xLog("定位倒计时开始");
                for (int i = 0; i < 15; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                if (city == null) {
                    try {
                        getActivity().runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                xLog("定位失败，请开启定位相关权限后再试");
                                Toast.makeText(getActivity(), "定位失败，请开启定位相关权限后再试", Toast.LENGTH_LONG).show();
                            }
                        });
                    } catch (Throwable throwable) {
                        xLog("e--定位失败，请开启定位相关权限后再试");
                    }


                }
            }
        }).start();
    }

    private void spliteData(String result) {
        xLog("result:" + result);
        try {
            JSONArray data_1 = new JSONArray(new JSONObject(result).get("data").toString());

            for (int i = 0; i < data_1.length(); i++) {

                xLog("data_1,第" + i + "个下标的值：" + data_1.get(i).toString());

                JSONArray data_2 = new JSONArray(new JSONObject(data_1.get(i).toString()).get("data").toString());
                for (int j = 0; j < data_2.length(); j++) {
                    xLog("***************************************开始解析**************************************");
                    Data_Video_Home data_video_home = new Data_Video_Home();
                    xLog("对象创建成功，当前positions：" + positions);
                    xLog("data_2,第" + j + "个下标的值：" + data_2.get(j).toString());
                    JSONObject all = new JSONObject(data_2.get(j).toString());
                    try {
                        JSONObject story = new JSONObject(all.get("story").toString());
                        xLog("story  ----showname :" + story.get("showname"));
                        xLog("story  ---- data :" + new JSONObject(story.get("data").toString()).getString("storyBrief"));
                        xLog("tvTitle ------标题：" + all.get("tvTitle"));
                    } catch (Throwable throwable) {
                        xLog("subHead 异常一次");
                    }


                    data_video_home.setTvTitle(all.get("tvTitle").toString());//将标题加入对象方便填充，下雷同
                    try {
                        xLog("subHead ------热门度：" + all.get("subHead"));
                    } catch (Throwable throwable) {
                        xLog("subHead 异常一次");
                    }
//
                    JSONObject star = new JSONObject(all.get("star").toString());
                    xLog("star -------showname：" + star.getString("showname"));
                    JSONObject star_data = new JSONObject(star.get("data").toString());
                    xLog("-----------------:" + new JSONObject(star_data.get("1").toString()).getString("name"));
                    try {

                        xLog("-----------------:" + new JSONObject(star_data.get("2").toString()).getString("name"));
                        xLog("-----------------:" + new JSONObject(star_data.get("3").toString()).getString("name"));
                        xLog("-----------------:" + new JSONObject(star_data.get("4").toString()).getString("name"));

                        data_video_home.setStarring_1(new JSONObject(star_data.get("1").toString()).getString("name"));
                        data_video_home.setStarring_2(new JSONObject(star_data.get("2").toString()).getString("name"));
                        data_video_home.setStarring_3(new JSONObject(star_data.get("3").toString()).getString("name"));
                        data_video_home.setStarring_4(new JSONObject(star_data.get("4").toString()).getString("name"));
                    }catch (Throwable thread){
                        xLog("----------------- 异常一次");
                    }

                    try {
                        xLog("grade  ---------评分：" + all.getString("grade"));
                        data_video_home.setGrade(all.getString("grade"));
                    } catch (Throwable throwable) {
                        xLog("grade 异常一次");
                    }
                    try {
                        xLog("iconaddress  ---------iconaddress：" + all.getString("iconaddress"));
                    } catch (Throwable throwable) {
                        xLog("iconaddress 异常一次");
                    }

                    JSONObject type = new JSONObject(all.get("type").toString());
                    JSONObject type_data = new JSONObject(type.get("data").toString());
                    xLog("type -----------影片类型：" + new JSONObject(type_data.get("1").toString()).getString("name"));
                    data_video_home.setType_1(new JSONObject(type_data.get("1").toString()).getString("name"));
                    try {
                        xLog("type -----------影片类型：" + new JSONObject(type_data.get("2").toString()).getString("name"));
                        data_video_home.setType_2(new JSONObject(type_data.get("2").toString()).getString("name"));
                    } catch (Throwable throwable) {
                        xLog("type -----------影片类型 异常一次");
                    }

                    JSONObject playDate = new JSONObject(all.get("playDate").toString());
                    xLog("showname--------showname:" + playDate.getString("showname"));
                    xLog("data-----------data:" + playDate.getString("data"));
                    data_video_home.setDate(playDate.getString("data"));
                    xLog("data2-----------data2:" + playDate.getString("data2"));
                    xLog("Images ---------iconaddress:" + all.getString("iconaddress"));
                    //TODO 开线程去请求图片，后设置到adapter
                    if (all.getString("iconaddress") != null && !all.getString("iconaddress").equals("")) {
                        getImagesFromService(all.getString("iconaddress"), positions);
                    }
                    JSONObject director = new JSONObject(all.get("director").toString());
                    xLog("showname-------------showname:" + director.getString("showname"));
                    JSONObject director_data = new JSONObject(director.get("data").toString());
                    xLog("name--------------name:" + new JSONObject(director_data.get("1").toString()).getString("name"));
                    xLog("iconlinkUrl---------iconlinkUrl：" + all.getString("iconlinkUrl"));
                    xLog("***************************************解析完成**************************************");
                    xLog("添加对象到适配器...,");
                    adapter.addItem(data_video_home);
                    adapter.notifyDataSetChanged();
                    xLog("添加到适配器成功，posititons自增1，");

                    positions++;
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
            if (svProgressHUD.isShowing(getActivity())) {
                svProgressHUD.dismiss(getActivity());
            }
        }
    }

    private void getImagesFromService(String iconaddress, final int posit) {
//
        GetDataFromService getDataFromService = new GetDataFromService(iconaddress);
        getDataFromService.doGet(new GetDataListener() {
            @Override
            public void onSucc(byte[] data) {
                if (svProgressHUD.isShowing(getActivity())) {
                    svProgressHUD.dismiss(getActivity());
                }
                xLog("positons为：" + posit + "的图片请求成功");
                try {
                    adapter.getList().get(posit).setImages(BitmapFactory.decodeByteArray(data, 0, data.length));
                    adapter.notifyDataSetChanged();
                } catch (Throwable throwable) {
                    xLog("99999999999999999999999999999999999999999999999999999999999");
                }

            }

            @Override
            public void onError(String errorMsg) {
                xLog("positons为：" + posit + "的图片请求失败");
                if (svProgressHUD.isShowing(getActivity())) {
                    svProgressHUD.dismiss(getActivity());
                }
            }
        });
    }

    public void xLog(String msg) {
        Log.w("NewsClient------->>>>>>", msg);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        layout_find.setVisibility(View.INVISIBLE);//点击后隐藏
        Bundle bundle = new Bundle();
        bundle.putString("name", adapter.getItem(position).getTvTitle());
        xLog("传值前的title:" + adapter.getItem(position).getTvTitle());
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack("1");
        video_details = new Fragment_Video_Details();

        video_details.setArguments(bundle);
        transaction.replace(R.id.view_video, video_details).commit();
    }

    @Override
    public void onClick(View v) {
        svProgressHUD.showWithStatus(getActivity(), "正在搜索...");
        String videoName = et_videi_select.getText().toString().trim();
        selectVideo(videoName);

    }

    private void selectVideo(String videoName) {
        GetDataFromService client = new GetDataFromService("http://op.juhe.cn/onebox/movie/video");

        client.put("key", "4ab501671c1f160f5d7fdd198fce86f4");
        client.put("q", videoName);

        client.doGet(new GetDataListener() {
            @Override
            public void onSucc(byte[] data) {
                if (svProgressHUD.isShowing(getActivity())) {
                    svProgressHUD.dismiss(getActivity());
                }
                xLog(new String(data));
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(new String(data));
                    if (jsonObject.getInt("error_code") == 0) {
                        layout_find.setVisibility(View.INVISIBLE);//在查找到结果后影藏
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.addToBackStack("2");
                        video_details = new Fragment_Video_Details();
                        Bundle bundle = new Bundle();
                        bundle.putByteArray("data", data);
                        video_details.setArguments(bundle);
                        xLog("传值前data：" + new String(data));
                        transaction.replace(R.id.view_video, video_details).commit();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(String errorMsg) {
                if (svProgressHUD.isShowing(getActivity())) {
                    svProgressHUD.dismiss(getActivity());
                }
                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        positions = 0;
        super.onResume();
    }
}
