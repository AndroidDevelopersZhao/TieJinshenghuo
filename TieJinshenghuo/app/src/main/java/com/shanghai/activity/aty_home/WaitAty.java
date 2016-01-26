package com.shanghai.activity.aty_home;
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

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.shanghai.data.data_version.VersionRespData;
import com.shanghai.soeasylib.util.XXHttpClient;
import com.shanghai.soeasylib.util.XXSharedPreferences;
import com.shanghai.R;
import com.shanghai.soeasylib.util.XXUtils;
import com.shanghai.utils.Util;

import org.apache.http.Header;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.jpush.android.api.JPushInterface;
import xinfu.com.pidanview.alerterview.alerterview.AlertView;
import xinfu.com.pidanview.alerterview.alerterview.OnItemClickListener;
import xinfu.com.pidanview.alerterview.progress.SVProgressHUD;

/**
 * 项目名称： NewsClient
 * 创建日期： 2015/12/25  16:48
 * 项目作者： 赵文贇
 * 项目包名： xinfu.com.newsclient.activity
 */
public class WaitAty extends Activity {
    private XXSharedPreferences sharedPreferences;
    private Handler handler_version = null;
    private String TAG = "NewClient";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waitactivity);

        getNewVersion();
    }

    /**
     * 检测最新版本
     */
    private void getNewVersion() {
        handler_version = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String data = msg.getData().getString("data");
                switch (msg.what) {
                    case -1:
                        Toast.makeText(WaitAty.this, data, Toast.LENGTH_LONG).show();
                        //休眠----跳转
                        startNewAty();
                        break;

                    case 1:
                        //弹出窗口提示更新
                        showAlertView(data);
                        break;
                }
            }
        };
        Log.d(TAG, "开始检测最新版本");
        XXHttpClient client = new XXHttpClient(Util.url_my, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, byte[] bytes) {
                if (new String(bytes) != null) {
                    VersionRespData respData = new Gson().fromJson(new String(bytes), VersionRespData.class);

                    if (respData.getCode() == 200) {
//                        Util.sendMsgToHandler(handler_version,);
                        String version_service = respData.getVersion();
                        //查询本地版本号
                        try {
                            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
                            String version_location = info.versionName;
                            Log.d(TAG, "服务器版本：" + version_service);
                            Log.d(TAG, "本地版本：" + version_location);
                            Log.e(TAG, "本地版本==服务器版本？" + (version_location.equals(version_service)));
                            if (!version_location.equals(version_service)) {
                                Util.sendMsgToHandler(handler_version, respData.getResult(), true);
                            } else {
                                Util.sendMsgToHandler(handler_version, "当前已是最新版本", false);
                            }
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                            Util.sendMsgToHandler(handler_version, "本地版本查询失败", false);
                        }
                    } else {
                        Util.sendMsgToHandler(handler_version, respData.getResult(), false);
                    }
                } else {
                    Util.sendMsgToHandler(handler_version, "数据异常", false);
                }
            }

            @Override
            public void onError(int i, Throwable throwable) {
                Util.sendMsgToHandler(handler_version, "网络异常", false);
            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
        client.put("type", 15);
        client.doPost(15000);
    }

    private void showAlertView(String msg) {

        new AlertView("提示", "服务器版本有更新,新增功能如下：\n" + msg + "", "取消", new String[]{"更新"}, null, WaitAty.this, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                Log.d(TAG, "---------" + position);
                if (position == 0) {
                    //更新
                    updateSoft();


                } else {
                    finish();
                }
            }
        }).show();
    }
//    private

    /**
     * 软件在线更新
     */
    private void updateSoft() {
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.get(Util.URL_DOWNLOAD, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d(TAG, "开始存储");
                SVProgressHUD.dismiss(WaitAty.this);
                SVProgressHUD.getProgressBar(WaitAty.this).setProgress(0);
                SVProgressHUD.showSuccessWithStatus(WaitAty.this, "下载完成，将在保存后引导您完成安装");
                save(bytes);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.e(TAG, "网络异常");
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {

                long all = totalSize;
                long thiss = bytesWritten;
                float ff = (float) ((((double) thiss) / (double) all) * 100);
                float b = (float) (Math.round(ff * 100)) / 100;
                Log.d(TAG, ((((double) thiss) / (double) all) * 100) + "/100");
                Log.e(TAG, "long written---" + bytesWritten);
                Log.e(TAG, "long totalSize---" + totalSize);
                if (SVProgressHUD.isShowing(WaitAty.this)) {
                    if (SVProgressHUD.getProgressBar(WaitAty.this).getMax() != SVProgressHUD.getProgressBar(WaitAty.this).getProgress()) {


                        SVProgressHUD.getProgressBar(WaitAty.this).setProgress((int) ((((double) thiss) / (double) all) * 100));
                        SVProgressHUD.setText(WaitAty.this, b + "/100");
                    }
//                    else {
//                        SVProgressHUD.dismiss(WaitAty.this);
//                        SVProgressHUD.getProgressBar(WaitAty.this).setProgress(0);
//                        SVProgressHUD.showSuccessWithStatus(WaitAty.this, "下载完成，将在保存后引导您完成安装");
//                    }
                } else {
                    SVProgressHUD.getProgressBar(WaitAty.this).setMax(100);

                    SVProgressHUD.showWithProgress(WaitAty.this, "当前进度 " + b + "/100", SVProgressHUD.SVProgressHUDMaskType.Black);

                }

            }
        });
    }

    public void save(byte[] bytes) {
        try {

            FileOutputStream outStream = this.openFileOutput("ppppppp.apk", Context.MODE_WORLD_READABLE);
            outStream.write(bytes);
            outStream.close();
            Log.d(TAG, "存储成功，开始安装");
            //卸载
//            Uri packageURI = Uri.parse("com.shanghai");
//            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
//            startActivity(uninstallIntent);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(getFilesDir() + "/ppppppp.apk")), "application/vnd.android.package-archive");
            startActivity(intent);
//            AutoInstall.setUrl(Environment.getExternalStorageDirectory()
//                    + "/Download/Spore.apk");
//            AutoInstall.install(ApkAutoInstallActivity.this);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "存储失败");
            return;
        } catch (IOException e) {
            Log.e(TAG, "存储失败");
            return;
        }
    }
//
//    public void load() {
//        try {
//            FileInputStream inStream = this.openFileInput("ppppppp.apk");
//
////            ByteArrayOutputStream stream=new ByteArrayOutputStream();
////            byte[] buffer=new byte[1024];
////            int length=-1;
////            while((length=inStream.read(buffer))!=-1)   {
////                stream.write(buffer,0,length);
////            }
////            stream.close();
////            inStream.close();
//            File file = new File(inStream);
////            text.setText(stream.toString());
////            Toast.makeText(MyActivity.this,”Loaded”,Toast.LENGTH_LONG).show();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            return;
//        }
//    }

    private void startNewAty() {

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                sharedPreferences = new XXSharedPreferences("welcomeTime");
                int times = (int) sharedPreferences.get(WaitAty.this, "key", -1);
                if (times == 0 || times > 1) {
                    startActivity(new Intent(WaitAty.this, Login.class));
                } else {
                    startActivity(new Intent(WaitAty.this, WelcomeAty.class));
                }
                finish();
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.w("NewClient:", "开始休眠");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.w("NewClient:", "休眠结束");
                Message m
                        = handler.obtainMessage();
                handler.sendMessage(m);

            }
        }).start();


    }

    @Override
    protected void onResume() {
        JPushInterface.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        JPushInterface.onPause(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.d("NewClient", "Wait页面被销毁");
        super.onDestroy();
    }

    public class WaitReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "收到广播，ac=" + intent.getAction() + "name=" + intent.getDataString());
            if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
                if (intent.getDataString().equals("com.shanghai")) {
                    Intent intenta = new Intent(Intent.ACTION_MAIN);
                    intenta.setComponent(new ComponentName("com.package.address", "com.shanghai.WaitAty"));
                   startActivity(intenta);
                }
            }
        }
    }

}
/**
 * 开服务  检测最新版本   ----发现新版本   弹出对话框更新-----------做进度条---------更新完成后卸载当前版本
 * <p/>
 * 安装新版本-------进入软件--------ok
 */
