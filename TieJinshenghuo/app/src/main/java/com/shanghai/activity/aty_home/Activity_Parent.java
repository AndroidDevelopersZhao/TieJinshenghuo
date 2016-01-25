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
import android.app.Fragment;
import android.os.Bundle;

import com.shanghai.R;
import com.shanghai.fragment.fmt_home_addphoneamount.AddAmountHome;
import com.shanghai.fragment.fmt_home_drivers.DriversTest_Select_Questions;
import com.shanghai.fragment.fmt_home_navigation.NavigationFirstView;
import com.shanghai.fragment.fmt_home_newclient.NewsHome;
import com.shanghai.fragment.fmt_home_robtickets.RobTickets;
import com.shanghai.fragment.fmt_home_tv.TVTimeTableHome;
import com.shanghai.fragment.fmt_home_trains.TrainsTimeTableHome;
import com.shanghai.fragment.fmt_home_video.Video;
import com.shanghai.fragment.fmt_home_wechat.WeChatSelection;
import com.shanghai.fragment.fmt_home_weather.WeatherHome;
import com.shanghai.fragment.fmt_home_youdao.YoudaoHome;

import java.util.ArrayList;
import java.util.List;


/**
 * 项目名称： NewsClient
 * 创建日期： 2015/12/15  14:44
 * 项目作者： 赵文贇
 * 项目包名： xinfu.com.newsclient.activity
 */
public class Activity_Parent extends Activity {
    private int mainView = 0;

    private List<Fragment> fragmentList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent);
        initData();
//        switch ()
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new NewsHome());
        fragmentList.add(new WeatherHome());
        fragmentList.add(new TrainsTimeTableHome());
        fragmentList.add(new TVTimeTableHome());
        fragmentList.add(new YoudaoHome());
        fragmentList.add(new Video());
        fragmentList.add(new WeChatSelection());
        fragmentList.add(new RobTickets());
        fragmentList.add(new NavigationFirstView());
        fragmentList.add(new DriversTest_Select_Questions());
        fragmentList.add(new AddAmountHome());

        mainView = R.id.mainView;
//        newsHome = ;
//        weatherHome = ;
//        trainsTimeTableHome = ;
//        tvTimeTableHome =;
//        youdaoHome =;
        switch (getIntent().getIntExtra("id", 0)) {
            case 1:
                if (!fragmentList.get(0).isVisible()) {
                    repleace(fragmentList.get(0));
                }
                break;
            case 2:
                if (!fragmentList.get(1).isVisible()) {
                    repleace(fragmentList.get(1));
                }
                break;
            case 3:
                if (!fragmentList.get(2).isVisible()) {
                    repleace(fragmentList.get(2));
                }
                break;
            case 4:
                if (!fragmentList.get(3).isVisible()) {
                    repleace(fragmentList.get(3));
                }
                break;
            case 5:
                if (!fragmentList.get(4).isVisible()) {
                    repleace(fragmentList.get(4));
                }
                break;
            case 6:
                if (!fragmentList.get(5).isVisible()) {
                    repleace(fragmentList.get(5));
                }
                break;
            case 7:
                if (!fragmentList.get(6).isVisible()) {
                    repleace(fragmentList.get(6));
                }
                break;
            case 8:
                if (!fragmentList.get(7).isVisible()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("username",getIntent().getExtras().getString("username"));
                    fragmentList.get(7).setArguments(bundle);
                    repleace(fragmentList.get(7));
                }
                break;
            case 9:
                if (!fragmentList.get(8).isVisible()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("username",getIntent().getExtras().getString("username"));
                    fragmentList.get(8).setArguments(bundle);
                    repleace(fragmentList.get(8));
                }
                break;
            case 10:
                if (!fragmentList.get(9).isVisible()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("username",getIntent().getExtras().getString("username"));
                    fragmentList.get(9).setArguments(bundle);
                    repleace(fragmentList.get(9));
                }
                break;
            case 11:
                if (!fragmentList.get(10).isVisible()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("username",getIntent().getExtras().getString("username"));
                    fragmentList.get(10).setArguments(bundle);
                    repleace(fragmentList.get(10));
                }
                break;
        }
    }

    private void repleace(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(mainView, fragment).commit();
    }

}
