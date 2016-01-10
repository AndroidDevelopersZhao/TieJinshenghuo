package com.shanghai.activity;
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
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.shanghai.soeasylib.util.XXSharedPreferences;

import java.util.ArrayList;
import java.util.List;

import com.shanghai.R;

/**
 * 项目名称： NewsClient
 * 创建日期： 2015/12/25  15:22
 * 项目作者： 赵文贇
 * 项目包名： xinfu.com.newsclient.activity
 */
public class WelcomeAty extends Activity implements ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private XXViewPagerAdapter adapter;
    private List<View> views;
    private ImageView[] dos = null;
    private int[] ids = {R.id.iv1, R.id.iv2, R.id.iv3};
    private XXSharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        initView();
        initDos();

    }

    private void initView() {
        sharedPreferences = new XXSharedPreferences("welcomeTime");
        sharedPreferences.put(this, "key", 10086);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        views = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(this);

        views.add(inflater.inflate(R.layout.item_welcome1, null));
        views.add(inflater.inflate(R.layout.item_welcome2, null));
        views.add(inflater.inflate(R.layout.item_welcome3, null));
        adapter = new XXViewPagerAdapter(views, this);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);
        Button button = (Button) views.get(2).findViewById(R.id.btn_start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeAty.this, Login.class));
                finish();
            }
        });


    }

    private void initDos() {
        dos = new ImageView[views.size()];
        for (int i = 0; i < ids.length; i++) {
            dos[i] = (ImageView) findViewById(ids[i]);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //滑动时
    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < ids.length; i++) {
            if (position == i) {
                dos[i].setImageResource(R.drawable.welcome_icon_true);
            } else {
                dos[i].setImageResource(R.drawable.welcome_icon_false);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state
    }


}
