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
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.shanghai.soeasylib.util.XXSharedPreferences;
import com.shanghai.R;

import cn.jpush.android.api.JPushInterface;

/**
 *  项目名称： NewsClient
 *  创建日期： 2015/12/25  16:48
 *  项目作者： 赵文贇
 *  项目包名： xinfu.com.newsclient.activity
 */
public class WaitAty extends Activity {
    private XXSharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waitactivity);

        final Handler handler =new Handler(){
            @Override
            public void handleMessage(Message msg) {
                sharedPreferences =new XXSharedPreferences("welcomeTime");
                int times = (int) sharedPreferences.get(WaitAty.this,"key",-1);
                if (times==0 || times>1){
                    startActivity(new Intent(WaitAty.this,Login.class));
                }else {
                    startActivity(new Intent(WaitAty.this,WelcomeAty.class));
                }
                finish();
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.w("NewClient:","开始休眠");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.w("NewClient:","休眠结束");
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
        Log.d("NewClient","Wait页面被销毁");
        super.onDestroy();
    }
}
