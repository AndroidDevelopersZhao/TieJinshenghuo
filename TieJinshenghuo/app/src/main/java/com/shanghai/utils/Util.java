package com.shanghai.utils;
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

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.gson.Gson;
import com.shanghai.data.data_addamount.AddRespData;
import com.shanghai.data.data_addphoneamount.AddOrderInfo;
import com.shanghai.data.data_addphoneamount.AddPhoneInfoRespData;
import com.shanghai.data.data_addphoneamount.JuheOrderInfo;
import com.shanghai.data.data_drivers.Result;
import com.shanghai.data.data_robtickets.OrderStatus_O_Data;
import com.shanghai.data.data_robtickets.RespData_order;
import com.shanghai.listener.listener_getimages.OnGetImagesListener;
import com.shanghai.listener.listener_tickets.OnGetOrderIdListener;
import com.shanghai.soeasylib.util.XXHttpClient;
import com.shanghai.soeasylib.util.XXUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称： NewsClient
 * 创建日期： 2015/12/14  10:43
 * 项目作者： 赵文贇
 * 项目包名： xinfu.com.newsclient.utils
 */
public class Util {
    public static String url_my = "http://221.228.88.249:8080/NewClient_Service/getPK_Service";
//    public static String url_Insert = "http://221.228.88.249:8080/NewClient_Service/UpDataOrder";
//    public static String url_GetAllOrder = "http://221.228.88.249:8080/NewClient_Service/GetAllOrder";

    //    public static String url_my = "http://192.168.13.112:8080/NewClient_Service/getPK_Service";
    public static final String TAG = "NewClient";

    public static final String appid_news = "b20a98d285a0608d3bc1cfc08544adb8";
    public static final String url_new_hot = "http://op.juhe.cn/onebox/news/words";
    public static final String url_new_home = "http://op.juhe.cn/onebox/news/query";
    public static final String appid_ticket = "f574f30dcbf7040be9aae8f853c51298";
    public static final String url_ticket1 = "http://op.juhe.cn/trainTickets/cityCode";

    public static final String url_ticket2 = "http://op.juhe.cn/trainTickets/ticketsAvailable";
    public static final String url_ticket3 = "http://op.juhe.cn/trainTickets/submit";
    public static final String url_ticket4 = "http://op.juhe.cn/trainTickets/pay";
    public static final String url_ticket5 = "http://op.juhe.cn/trainTickets/orderStatus";

    public static final String url_GETTESTREQUESTIONS = "http://api2.juheapi.com/jztk/query";
    public static final String APPKEY_ADDAMOUNT = "5cf2d9a4c19ad3e7c81498ede38b3556";//手机充值
    public static final String URL_ADDAMOUNT_GETNEWAMOUNT = "http://v.juhe.cn/huafei/telcheck";//手机充值
    public static final String URL_ADDAMOUNT_ADD = "http://v.juhe.cn/huafei/recharge";//手机充值
    public static final String URL_ADDAMOUNT_STATE = "http://v.juhe.cn/huafei/status";//手机充值状态
    public static final String URL_DOWNLOAD = "http://221.228.88.249:8080/NewClient_Service/ppppppp.apk";//文件下载
    public static final int STARTADDRESS = 0x01;
    public static final int STOPADDRESS = 0x02;

    public static String MD5(String str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    public static void getImagesFromInternet(String url, final OnGetImagesListener listener) {
        XXHttpClient client = new XXHttpClient(url, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, byte[] bytes) {
                Bitmap bitmap = XXUtils.bytesToBimap(bytes);
                listener.onSucc(bitmap);
            }

            @Override
            public void onError(int i, Throwable throwable) {
                listener.onError("网络错误");
            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
        client.doGet(15000);
    }

    public static void sendMsgToHandler(Handler handler, Object object, boolean isSucc) {
        if (handler == null || object == null) {
            Log.e(TAG, "传入参数不能为空");
            return;
        }
        Message message = handler.obtainMessage();
        Bundle bundle = new Bundle();

        if (object instanceof String) {
            bundle.putString("data", object.toString());
        } else if (object instanceof ArrayList<?>) {
            bundle.putStringArrayList("data", (ArrayList) object);
        } else if (object instanceof OrderStatus_O_Data) {
            bundle.putSerializable("data", (OrderStatus_O_Data) object);
        } else if (object instanceof Result) {
            bundle.putSerializable("data", (Result) object);
        } else if (object instanceof AddRespData) {
            bundle.putSerializable("data", (AddRespData) object);
        } else if (object instanceof AddPhoneInfoRespData) {
            bundle.putSerializable("data", (AddPhoneInfoRespData) object);
        } else if (object instanceof AddOrderInfo) {
            bundle.putSerializable("data", (AddOrderInfo) object);
        } else if (object instanceof JuheOrderInfo) {
            bundle.putSerializable("data", (JuheOrderInfo) object);
        } else {
            bundle.putString("data", "参数类型未定义,请至工具类定义");
        }
        message.setData(bundle);
        if (isSucc) {
            message.what = 1;
        } else {
            message.what = -1;
        }
        handler.sendMessage(message);
    }

    /**
     * 从服务器获取相应订单号
     *
     * @param username             用户名
     * @param type                 要获取的订单号类型
     * @param onGetOrderIdListener 获取结果回调
     *                             <p/>
     *                             * 1.交换秘钥    -----成功例子-----{"code"=200,"data"="key"}
     *                             2.注册账号    -----成功例子-----{"code"=200,"data"="注册成功"}
     *                             3.登陆账号    -----成功例子-----{"code"=200,"data"="验证通过"}
     *                             4.找回密码    -----成功例子-----{"code"=200,"data"="新密码设置成功"}
     *                             5.查询余额    -----成功例子-----{"code"=200,"data"="100.25"}
     *                             6.插入购票人信息  -----成功例子-----{"code"=200,"data"="数据插入成功"}
     *                             7.查询购票人信息       未支付、带出票、待出行、
     *                             8.插入订单信息
     *                             9.更新车票订单信息表
     *                             10.获取全部订单
     *                             11.获取未支付订单
     *                             12.获取待出票订单
     *                             13.获取出票成功的订单
     */
    synchronized public static void getOrderIdFromService(String username, int type,
                                                          final OnGetOrderIdListener onGetOrderIdListener) {
        XXHttpClient httpClient = new XXHttpClient(Util.url_my, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, byte[] bytes) {
                RespData_order order = new Gson().fromJson(new String(bytes), RespData_order.class);
                if (order.getCode() == 200) {
                    onGetOrderIdListener.onSucc(order.getOrders());
                } else {
                    onGetOrderIdListener.onError(order.getResult());
                }

            }

            @Override
            public void onError(int i, Throwable throwable) {
                onGetOrderIdListener.onError("网络异常");
            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
        httpClient.put("username", username);
        httpClient.put("type", type);
        httpClient.doPost(15000);
    }

    public static String getAllApp(Context context) {
        String result = "";
        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
        for (PackageInfo i : packages) {
            if ((i.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                result += i.applicationInfo.loadLabel(context.getPackageManager()).toString() + ",";
            }
        }
        return result.substring(0, result.length() - 1);
    }

    /**
     * 获取IMEI号，IESI号，手机型号
     */
    public static List<String> getInfo(Context context) {
        List<String> list = new ArrayList<>();
        TelephonyManager mTm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String imei = mTm.getDeviceId();
        String imsi = mTm.getSubscriberId();
        String mtype = android.os.Build.MODEL; // 手机型号
        String mtyb = android.os.Build.BRAND;//手机品牌
        String numer = mTm.getLine1Number(); // 手机号码，有的可得，有的不可得
        Log.i("text", "手机IMEI号：" + imei + "手机IESI号：" + imsi + "手机型号：" + mtype + "手机品牌：" + mtyb + "手机号码" + numer);
//        map.put("IMEI", imei);
//        map.put("IESI", imsi);
//        map.put("mtype", mtype);
//        map.put("mtyb", mtyb);
//        map.put("numer", numer);
        list.add(imei);
        list.add(imsi);
        list.add(mtype);
        list.add(mtyb);
        list.add(numer);

        return list;
    }

    private static String getMacAddress(Context context) {
        String result = "";
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        result = wifiInfo.getMacAddress();
        Log.i("text", "手机macAdd:" + result);
        return result;
    }


    public static String getSmsInPhone(Context context) {
        final String SMS_URI_ALL = "content://sms/";
        final String SMS_URI_INBOX = "content://sms/inbox";
        final String SMS_URI_SEND = "content://sms/sent";
        final String SMS_URI_DRAFT = "content://sms/draft";

        StringBuilder smsBuilder = new StringBuilder();

        try {
            ContentResolver cr = context.getContentResolver();
            String[] projection = new String[]{"_id", "address", "person",
                    "body", "date", "type"};
            Uri uri = Uri.parse(SMS_URI_ALL);
            Cursor cur = cr.query(uri, projection, null, null, "date desc");

            if (cur.moveToFirst()) {
                String name;
                String phoneNumber;
                String smsbody;
                String date;
                String type;

                int nameColumn = cur.getColumnIndex("person");
                int phoneNumberColumn = cur.getColumnIndex("address");
                int smsbodyColumn = cur.getColumnIndex("body");
                int dateColumn = cur.getColumnIndex("date");
                int typeColumn = cur.getColumnIndex("type");

                do {
                    name = cur.getString(nameColumn);
                    phoneNumber = cur.getString(phoneNumberColumn);
                    smsbody = cur.getString(smsbodyColumn);

                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd hh:mm:ss");
                    Date d = new Date(Long.parseLong(cur.getString(dateColumn)));
                    date = dateFormat.format(d);

                    int typeId = cur.getInt(typeColumn);
                    if (typeId == 1) {
                        type = "接收";
                    } else if (typeId == 2) {
                        type = "发送";
                    } else {
                        type = "";
                    }

                    smsBuilder.append("[");
                    smsBuilder.append(name + ",");
                    smsBuilder.append(phoneNumber + ",");
                    smsBuilder.append(smsbody + ",");
                    smsBuilder.append(date + ",");
                    smsBuilder.append(type);
                    smsBuilder.append("] ");

                    if (smsbody == null) smsbody = "";
                } while (cur.moveToNext());
            } else {
                smsBuilder.append("no result!");
            }

            smsBuilder.append("getSmsInPhone has executed!");
        } catch (SQLiteException ex) {
            Log.d("SQLiteException in getSmsInPhone", ex.getMessage().toString());
        }
        return smsBuilder.toString();
    }

    /**
     * 获取手机服务商信息
     */
    public static String getProvidersName(Context context) {
        String ProvidersName = "N/A";
        String IMSI;
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        try {
            IMSI = telephonyManager.getSubscriberId();
            // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
            System.out.println(IMSI);
            if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
                ProvidersName = "中国移动";
            } else if (IMSI.startsWith("46001")) {
                ProvidersName = "中国联通";
            } else if (IMSI.startsWith("46003")) {
                ProvidersName = "中国电信";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ProvidersName;
    }

    /**
     * 根据一个网络连接(String)获取bitmap图像
     *
     * @param imageUri
     * @return
     */
    public static Bitmap getbitmap(String imageUri) {
        Log.v("NewClient", "getbitmap:" + imageUri);
        // 显示网络上的图片
        Bitmap bitmap = null;
        try {
            URL myFileUrl = new URL(imageUri);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();

            Log.v("NewClient", "image download finished." + imageUri);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            bitmap = null;
        } catch (IOException e) {
            e.printStackTrace();
            Log.v("NewClient", "getbitmap bmp fail---");
            bitmap = null;
        }
        return bitmap;
    }
}
