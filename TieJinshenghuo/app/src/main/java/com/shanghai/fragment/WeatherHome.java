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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.shanghai.R;
import com.shanghai.data.Data_Weather;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import xinfu.com.pidanview.alerterview.progress.SVProgressHUD;


/**
 * 项目名称： NewsClient
 * 创建日期： 2015/12/14  9:59
 * 项目作者： 赵文贇
 * 项目包名： xinfu.com.newsclient.fragment
 */
public class WeatherHome extends Fragment {
    private View view;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private LocationClient locationClient = null;
    private String Address = null;
    private Handler handler = null;
    private TextView tv_temp, tv_city, tv_temperature;
    private ImageView im_weather;
    private List<TextView> weeks;
    private List<ImageView> imgs;
    private List<TextView> tv1s;
    private List<TextView> tv2s;
    private SVProgressHUD svProgressHUD;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.weatherhome, null);
        svProgressHUD=new SVProgressHUD();
        svProgressHUD.showWithStatus(getActivity(),"正在获取当前城市...");
        BDLocationListener listener = new MyLocationListener();
        locationClient = new LocationClient(getActivity());     //声明LocationClient类
        locationClient.registerLocationListener(listener);    //注册监听函数
        initLocation();
        locationClient.start();
        initView();
        busness();
        return view; //
    }

    private TextView tv_week_1, tv_week_2, tv_week_3, tv_week_4, tv_week_5, tv_week_6, tv_week_7;
    private TextView tv_week_1_tv1, tv_week_1_tv2, tv_week_2_tv1, tv_week_2_tv2, tv_week_3_tv1,
            tv_week_3_tv2, tv_week_4_tv1, tv_week_4_tv2, tv_week_5_tv1, tv_week_5_tv2,
            tv_week_6_tv1, tv_week_6_tv2, tv_week_7_tv1, tv_week_7_tv2;
    private ImageView im_week_1_1, im_week_2_2, im_week_3_3, im_week_4_4, im_week_5_5, im_week_6_6, im_week_7_7;
    private TextView tv_travel_index, tv_wash_index, tv_exercise_index, tv_dressing_index, tv_dressing_advice;

    private void initView() {
        weathers = new ArrayList<>();
        weeks = new ArrayList<>();
        imgs = new ArrayList<>();
        tv1s = new ArrayList<>();
        tv2s = new ArrayList<>();

        tv_travel_index = (TextView) view.findViewById(R.id.tv_travel_index);
        tv_wash_index = (TextView) view.findViewById(R.id.tv_wash_index);
        tv_exercise_index = (TextView) view.findViewById(R.id.tv_exercise_index);
        tv_dressing_index = (TextView) view.findViewById(R.id.tv_dressing_index);
        tv_dressing_advice = (TextView) view.findViewById(R.id.tv_dressing_advice);

        tv_temp = (TextView) view.findViewById(R.id.tv_temp);
        tv_city = (TextView) view.findViewById(R.id.tv_city);

        tv_temperature = (TextView) view.findViewById(R.id.tv_temperature);


        tv_week_1_tv1 = (TextView) view.findViewById(R.id.tv_week_1_tv1);
        tv_week_1_tv2 = (TextView) view.findViewById(R.id.tv_week_1_tv2);

        tv_week_2_tv1 = (TextView) view.findViewById(R.id.tv_week_2_tv1);
        tv_week_2_tv2 = (TextView) view.findViewById(R.id.tv_week_2_tv2);

        tv_week_3_tv1 = (TextView) view.findViewById(R.id.tv_week_3_tv1);
        tv_week_3_tv2 = (TextView) view.findViewById(R.id.tv_week_3_tv2);

        tv_week_4_tv1 = (TextView) view.findViewById(R.id.tv_week_4_tv1);
        tv_week_4_tv2 = (TextView) view.findViewById(R.id.tv_week_4_tv2);

        tv_week_5_tv1 = (TextView) view.findViewById(R.id.tv_week_5_tv1);
        tv_week_5_tv2 = (TextView) view.findViewById(R.id.tv_week_5_tv2);

        tv_week_6_tv1 = (TextView) view.findViewById(R.id.tv_week_6_tv1);
        tv_week_6_tv2 = (TextView) view.findViewById(R.id.tv_week_6_tv2);

        tv_week_7_tv1 = (TextView) view.findViewById(R.id.tv_week_7_tv1);
        tv_week_7_tv2 = (TextView) view.findViewById(R.id.tv_week_7_tv2);
        tv1s.add(tv_week_1_tv1);
        tv1s.add(tv_week_2_tv1);
        tv1s.add(tv_week_3_tv1);
        tv1s.add(tv_week_4_tv1);
        tv1s.add(tv_week_5_tv1);
        tv1s.add(tv_week_6_tv1);
        tv1s.add(tv_week_7_tv1);

        tv2s.add(tv_week_1_tv2);
        tv2s.add(tv_week_2_tv2);
        tv2s.add(tv_week_3_tv2);
        tv2s.add(tv_week_4_tv2);
        tv2s.add(tv_week_5_tv2);
        tv2s.add(tv_week_6_tv2);
        tv2s.add(tv_week_7_tv2);

//
        tv_week_1 = (TextView) view.findViewById(R.id.tv_week_1);
        tv_week_2 = (TextView) view.findViewById(R.id.tv_week_2);
        tv_week_3 = (TextView) view.findViewById(R.id.tv_week_3);
        tv_week_4 = (TextView) view.findViewById(R.id.tv_week_4);
        tv_week_5 = (TextView) view.findViewById(R.id.tv_week_5);
        tv_week_6 = (TextView) view.findViewById(R.id.tv_week_6);
        tv_week_7 = (TextView) view.findViewById(R.id.tv_week_7);
        weeks.add(tv_week_1);
        weeks.add(tv_week_2);
        weeks.add(tv_week_3);
        weeks.add(tv_week_4);
        weeks.add(tv_week_5);
        weeks.add(tv_week_6);
        weeks.add(tv_week_7);

        im_week_1_1 = (ImageView) view.findViewById(R.id.im_week_1_1);
        im_week_2_2 = (ImageView) view.findViewById(R.id.im_week_2_2);
        im_week_3_3 = (ImageView) view.findViewById(R.id.im_week_3_3);
        im_week_4_4 = (ImageView) view.findViewById(R.id.im_week_4_4);
        im_week_5_5 = (ImageView) view.findViewById(R.id.im_week_5_5);
        im_week_6_6 = (ImageView) view.findViewById(R.id.im_week_6_6);
        im_week_7_7 = (ImageView) view.findViewById(R.id.im_week_7_7);
        imgs.add(im_week_1_1);
        imgs.add(im_week_2_2);
        imgs.add(im_week_3_3);
        imgs.add(im_week_4_4);
        imgs.add(im_week_5_5);
        imgs.add(im_week_6_6);
        imgs.add(im_week_7_7);


        im_weather = (ImageView) view.findViewById(R.id.im_weather);
    }

    private void busness() {

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.arg1 == 1) {
                    AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);

                    RequestParams params = new RequestParams();
                    params.put("key", "a4c7df74726697b4e00ffd9b12aa0a41");
                    params.put("format", 2);
                    params.put("cityname", changeCharset(Address, "UTF-8"));
                    xLog(Address);
                    client.get("http://v.juhe.cn/weather/index", params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int i, Header[] headers, byte[] bytes) {
                            xLog(new String(bytes));
                            try {
                                JSONObject jsonObject = new JSONObject(new String(bytes));
                                if (jsonObject.getString("resultcode").equals("200")) {
                                    splitData(jsonObject.toString());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                            xLog("error,超时");
                        }
                    });
                }
            }
        };
    }

    private String temp = null, temperature = null, city = null, weather = null;//上部分填充
    private List<Data_Weather> weathers = null;

    private void splitData(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject result = jsonObject.getJSONObject("result");
            xLog("result:" + result);
            JSONObject sk = result.getJSONObject("sk");
            temp = sk.getString("temp");
            final JSONObject today = result.getJSONObject("today");
            temperature = today.getString("temperature");
            city = today.getString("city");
            weather = today.getString("weather");
            xLog("temperature=======>>>>>" + temperature);
            xLog("city=======>>>>>" + city);
            xLog("weather=======>>>>>" + weather);

            JSONArray future = result.getJSONArray("future");
            xLog("sk" + sk);
            xLog("today" + today);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        tv_travel_index.setText(today.getString("travel_index"));
                        tv_wash_index.setText(today.getString("wash_index"));
                        tv_exercise_index.setText(today.getString("exercise_index"));
                        tv_dressing_index.setText(today.getString("dressing_index"));
                        tv_dressing_advice.setText(today.getString("dressing_advice"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            for (int i = 0; i < future.length(); i++) {
                xLog("future" + future.getJSONObject(i).toString());
                Data_Weather data_weather = new Gson().fromJson(future.getJSONObject(i).toString(), Data_Weather.class);
                weathers.add(data_weather);
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_temp.setText(temp);
                    tv_city.setText(city);
                    tv_temperature.setText(temperature);
                    if (weather.equals("多云")) {
                        im_weather.setImageResource(R.drawable.weather_qingzhuanduoyun);
                    }
                    for (int i = 0; i < weathers.size(); i++) {
                        weeks.get(i).setText(weathers.get(i).getWeek());
                        if (weathers.get(i).getWeather().equals("多云")) {
                            imgs.get(i).setImageResource(R.drawable.weather_qingzhuanduoyun);
                        } else if (weathers.get(i).getWeather().equals("晴")) {
                            imgs.get(i).setImageResource(R.drawable.weather_qing);
                        } else if (weathers.get(i).getWeather().equals("阴")) {
                            imgs.get(i).setImageResource(R.drawable.weather_yin);
                        } else if (weathers.get(i).getWeather().equals("多云转阴")) {
                            imgs.get(i).setImageResource(R.drawable.weather_yin);
                        } else if (weathers.get(i).getWeather().equals("小雨")) {
                            imgs.get(i).setImageResource(R.drawable.weather_yu);
                        }
                        tv1s.get(i).setText(weathers.get(i).getWind());
                        tv2s.get(i).setText(weathers.get(i).getTemperature());

                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 字符串编码转换的实现方法
     *
     * @param str        待转换的字符串
     * @param newCharset 目标编码
     */
    public String changeCharset(String str, String newCharset) {
        if (str != null) {
            //用默认字符编码解码字符串。与系统相关，中文windows默认为GB2312
            byte[] bs = str.getBytes();
            try {
                return new String(bs, newCharset);    //用新的字符编码生成字符串
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        locationClient.setLocOption(option);
    }

    /**
     * 内部类 用于实现位置的获取
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            xLog(location.getCity() + "");
            if (location.getCity()!=null){
                if (location.getCity().substring(location.getCity().length() - 1, location.getCity().length()).equals("市")) {
                    Address = location.getCity().substring(0, location.getCity().length() - 1);
                } else {
                    Address = location.getCity();
                }
                locationClient.stop();
                if (WeatherHome.this.isVisible()) {
                    if (svProgressHUD.isShowing(getActivity())){
                        svProgressHUD.dismiss(getActivity());
                    }
                    Message message = handler.obtainMessage();
                    message.arg1 = 1;
                    handler.sendMessage(message);
                    Toast.makeText(getActivity(), "当前城市：" + Address, Toast.LENGTH_LONG).show();
                }
            }

            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.i("BaiduLocationApiDem", sb.toString());

        }
    }

    public void xLog(String msg) {
        Log.w("NewsClient------->>>>>>", msg);
    }
}
