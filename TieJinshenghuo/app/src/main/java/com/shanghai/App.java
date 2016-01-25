package com.shanghai;
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

import android.app.Application;
import android.nfc.Tag;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.umeng.socialize.PlatformConfig;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import cn.smssdk.gui.SMSReceiver;

/**
 * 项目名称： NewsClient
 * 创建日期： 2015/12/27  0:55
 * 项目作者： 赵文贇
 * 项目包名： xinfu.com.newsclient
 */
public class App extends Application {
    private final String appkey = "dad75592a660";
    private final String acept = "1ea97da166f863b12540a8ef9737855d";
    private ArrayList<Map<String, Object>> list = new ArrayList<>();
    //这是一句废话
    @Override
    public void onCreate() {
        super.onCreate();
        SMSSDK.initSDK(getApplicationContext(), appkey, acept);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        {
            //微信    wx12342956d1cab4f9,a5ae111de7d9ea137e88a5e02c07c94d
            PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
            //豆瓣RENREN平台目前只能在服务器端配置
            //新浪微博
            PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");
            //易信
            PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
            PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
            PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
            PlatformConfig.setAlipay("2015111700822536");
            PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
            PlatformConfig.setPinterest("1439206");

        }
    }
    public static String username = null;



}
