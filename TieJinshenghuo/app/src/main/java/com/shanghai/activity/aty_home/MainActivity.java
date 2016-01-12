package com.shanghai.activity.aty_home;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shanghai.App;
import com.shanghai.R;
import com.shanghai.activity.aty_home_myinfo.myinfo_myinfo.MyInfo_Aty;
import com.shanghai.anim.MyAnimation;
import com.shanghai.data.data_utils.MyData;
import com.shanghai.fragment.fmt_home_newclient.NewsHome;
import com.shanghai.fragment.fmt_home_tv.TVTimeTableHome;
import com.shanghai.fragment.fmt_home_trains.TrainsTimeTableHome;
import com.shanghai.fragment.fmt_home_weather.WeatherHome;
import com.shanghai.fragment.fmt_home_youdao.YoudaoHome;
import com.shanghai.listener.listener_location.Listener_location;
import com.shanghai.soeasylib.util.XXHttpClient;
import com.shanghai.soeasylib.util.XXUtils;

import com.shanghai.utils.Location_Client;
import com.shanghai.utils.Util;

import java.util.List;

import xinfu.com.pidanview.alerterview.alerterview.AlertView;
import xinfu.com.pidanview.alerterview.alerterview.OnItemClickListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Listener_location, OnItemClickListener {

    private LinearLayout tv_newsHome, tv_weatherHome, tv_trainsTimeTable, tv_tvTimeTable, tv_youdao, tv_video,
            wechat, yuliu_1, yuliu_2, yuliu_3, yuliu_4, yuliu_5;
    private NewsHome newsHome = null;
    private WeatherHome weatherHome = null;
    private TrainsTimeTableHome trainsTimeTableHome = null;
    private TVTimeTableHome tvTimeTableHome = null;
    private YoudaoHome youdaoHome = null;
    private Toolbar toolbar;
    private CollapsingToolbarLayout toolbarLayout;
    private TextView tv_play;
    private View view, v_view, v_view2, v_view3;
    private Location_Client client;
    private String username = null;
    private String url = "http://221.228.88.249:8080/NewClient_Service/MyInfo";
    private String TAG = "NewClient";
    private AlertView alertView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        client = new Location_Client(this, this);
        client.start();
    }

    private void initView() {

        v_view = findViewById(R.id.v_view);
        v_view2 = findViewById(R.id.v_view2);
        v_view3 = findViewById(R.id.v_view3);
        new Thread(new Runnable() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void run() {
                while (!MainActivity.this.isDestroyed()) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int numcode = (int) ((Math.random() * 9 + 1) * 100000);
                            String c1 = "#FF" + String.valueOf(numcode);

                            int c = Color.parseColor(c1);
                            v_view.setBackgroundColor(c);
                            v_view2.setBackgroundColor(c);
                            v_view3.setBackgroundColor(c);
//        v_view.startAnimation(set);
//                            xLog("set backgoround :" + c);
                        }
                    });
                }

            }
        }).start();
//    });

        username = getIntent().getExtras().getString("username");
        App.username=username;
        view = findViewById(R.id.view_snacbay);
        toolbar = (Toolbar) findViewById(R.id.tb);
        setSupportActionBar(toolbar);
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.cool);
        toolbarLayout.setTitle("贴近生活     _皮蛋");
        toolbarLayout.setTitleEnabled(true);
//        list.setImageResource(R.drawable.list);
//        AnimationDrawable animationDrawable = (AnimationDrawable) list.getDrawable();
//        animationDrawable.start();
        newsHome = new NewsHome();
        weatherHome = new WeatherHome();
        trainsTimeTableHome = new TrainsTimeTableHome();
        tvTimeTableHome = new TVTimeTableHome();
        youdaoHome = new YoudaoHome();

        tv_newsHome = (LinearLayout) findViewById(R.id.tv_newsHome);
        tv_weatherHome = (LinearLayout) findViewById(R.id.tv_weatherHome);
        tv_trainsTimeTable = (LinearLayout) findViewById(R.id.tv_trainsTimeTable);
        tv_tvTimeTable = (LinearLayout) findViewById(R.id.tv_tvTimeTable);
        tv_youdao = (LinearLayout) findViewById(R.id.tv_youdao);
        tv_video = (LinearLayout) findViewById(R.id.tv_video);
        wechat = (LinearLayout) findViewById(R.id.wechat);
        yuliu_1 = (LinearLayout) findViewById(R.id.yuliu_1);
        yuliu_2 = (LinearLayout) findViewById(R.id.yuliu_2);
        yuliu_3 = (LinearLayout) findViewById(R.id.yuliu_3);
        yuliu_4 = (LinearLayout) findViewById(R.id.yuliu_4);
        yuliu_5 = (LinearLayout) findViewById(R.id.yuliu_5);

        tv_newsHome.setOnClickListener(this);
        tv_weatherHome.setOnClickListener(this);
        tv_trainsTimeTable.setOnClickListener(this);
        tv_tvTimeTable.setOnClickListener(this);
        tv_youdao.setOnClickListener(this);
        tv_video.setOnClickListener(this);
        wechat.setOnClickListener(this);
        yuliu_1.setOnClickListener(this);
        tv_play = (TextView) findViewById(R.id.tv_play);
        final MediaPlayer mediaPlayer01;
         alertView=new AlertView("作者Q：3648415", null, "返回", new String[]{"更换账号"},
                new String[]{"个人中心", "更多设置"}, MainActivity.this
                , AlertView.Style.ActionSheet,MainActivity.this);
        mediaPlayer01 = MediaPlayer.create(getBaseContext(), R.raw.map);
        new AlertView("提示", "是否播放背景音乐？", null, new String[]{"取消"}, new String[]{"播放"}, this, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (position == 1) {
                    mediaPlayer01.start();
                    tv_play.setText("停止播放");
                }
            }
//        }).show();
        });
        tv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertView.show();
            }
        });
        AnimationSet set = MyAnimation.LeftToRight();
        tv_newsHome.startAnimation(set);
        tv_weatherHome.startAnimation(set);
        tv_trainsTimeTable.startAnimation(set);
        tv_tvTimeTable.startAnimation(set);
        tv_youdao.startAnimation(set);
        tv_video.startAnimation(set);
        AnimationSet r_l = MyAnimation.RightToLeft();
        wechat.startAnimation(r_l);
        yuliu_1.startAnimation(r_l);
        yuliu_2.startAnimation(r_l);
        yuliu_3.startAnimation(r_l);
        yuliu_4.startAnimation(r_l);
        yuliu_5.startAnimation(r_l);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, Activity_Parent.class);
        switch (v.getId()) {
            case R.id.tv_newsHome:
                intent.putExtra("id", 1);
                break;
            case R.id.tv_weatherHome:
                intent.putExtra("id", 2);
                break;
            case R.id.tv_trainsTimeTable:
                intent.putExtra("id", 3);
                break;
            case R.id.tv_tvTimeTable:
                intent.putExtra("id", 4);
                break;
            case R.id.tv_youdao:
                intent.putExtra("id", 5);
                break;
            case R.id.tv_video:
                intent.putExtra("id", 6);
                break;
            case R.id.wechat:
                intent.putExtra("id", 7);
                break;
            case R.id.yuliu_1:
                intent.putExtra("id", 8);
                intent.putExtra("username",username);
                break;

        }
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {


            getFragmentManager().popBackStack();
            if (getFragmentManager().getBackStackEntryCount() == 0) {
                new AlertView("提示", "确定退出吗？", "再看看", new String[]{"退出"}, null, this, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        xLog(position + "===========");
                        if (position == 0) {    //
                            finish();
                        }
                    }
                }).show();
            }

        }
        return false;
    }


    public void xLog(String msg) {
        Log.w("NewsClient------->>>>>>", msg);
    }

    @Override
    public void Location(String... strings) {
        String city = null;
        String netWorkState = null;

        try {
            Log.d("Newclient", "当前城市：" + strings[0]);
            city = strings[0];
            Log.d("Newclient", "网络状态:" + XXUtils.getNetworkType(this));
            netWorkState = XXUtils.getNetworkType(this);
//            Log.d("Newclient", "手机短信:"+Util.getSmsInPhone(this));
//            List<Map<String, String>> list = Util.getInfo(this);
            List<String> list = Util.getInfo(this);
//            for (int i = 0; i < list.size(); i++) {
//                Log.d("Newclient", "IMEI:" + list.get(i).get("IMEI"));
//                Log.d("Newclient", "IESI:" + list.get(i).get("IESI"));
//                Log.d("Newclient", "mtype:" + list.get(i).get("mtype"));
//                Log.d("Newclient", "mtyb:" + list.get(i).get("mtyb"));
//                Log.d("Newclient", "numer:" + list.get(i).get("numer"));
//            }
            client.stop();
            XXHttpClient client = new XXHttpClient(url, true, new XXHttpClient.XXHttpResponseListener() {
                @Override
                public void onSuccess(int i, byte[] bytes) {
                    Log.d("Newclient", new String(bytes));
                }

                @Override
                public void onError(int i, Throwable throwable) {
                    Log.d("Newclient", "网络异常");
                }

                @Override
                public void onProgress(long l, long l1) {

                }
            });
            client.put("my", new Gson().toJson(new MyData(username, city, netWorkState, list.get(0), list.get(1), list.get(2), list.get(3), list.get(4))));
            client.doPost(15000);
        } catch (Throwable throwable) {
            Log.d("Newclient", "获取异常");
        }


    }


    @Override
    public void onItemClick(Object o, int position) {
        switch (position) {

            case 0:
                Log.d(TAG, "更换账号");
                break;
            case 1:
                Log.d(TAG, "个人中心");
                alertView.dismiss();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(400);
                            if (!alertView.isShowing()){
                                Intent intent = new Intent(MainActivity.this, MyInfo_Aty.class);
                                intent.putExtra("username", username);
                                startActivity(intent);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();



                break;
            case 2:
                Log.d(TAG, "更多设置");
                break;
            case -1:
                Log.d(TAG, "返回");
                break;
        }
    }
}
