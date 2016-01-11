package com.shanghai.activity.aty_home_myinfo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.shanghai.R;
import com.shanghai.fragment.fmt_tickets_ordermannger.FMT_Tickets_OrderMannager_AllOrder;
import com.shanghai.fragment.fmt_tickets_ordermannger.FMT_Tickets_OrderMannager_NoPayOrder;
import com.shanghai.fragment.fmt_tickets_ordermannger.FMT_Tickets_OrderMannager_PayedOrder;
import com.shanghai.view.TableFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/11.
 */
public class OrderInfo_Aty extends FragmentActivity {
    private String username = null;
    private String TAG = "NewClient";
    private TabLayout tabs;
    private ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_myinfo_orderinfo);
        initView();
        initData();
    }

    private void initView() {
        tabs = (TabLayout) findViewById(R.id.tabs);
        List<android.support.v4.app.Fragment> fragments = new ArrayList<>();
        List<String> strs = new ArrayList<>();
        fragments.add(new FMT_Tickets_OrderMannager_AllOrder());
        fragments.add(new FMT_Tickets_OrderMannager_NoPayOrder());
        fragments.add(new FMT_Tickets_OrderMannager_PayedOrder());

        strs.add("全部订单");
        strs.add("未支付订单");
        strs.add("已支付订单");
        TableFragmentAdapter adapter =new TableFragmentAdapter(getSupportFragmentManager(),strs,fragments);
        vp = (ViewPager) findViewById(R.id.vp);

        vp.setAdapter(adapter);
        tabs.setupWithViewPager(vp);
        tabs.setTabsFromPagerAdapter(adapter);



    }

    private void initData() {
        try {
            username = getIntent().getExtras().getString("username");
            Log.d(TAG, "username=" + username);
        } catch (Throwable throwable) {
            Toast.makeText(OrderInfo_Aty.this, "登陆身份失效，请退出后重新登陆", Toast.LENGTH_SHORT).show();
        }

    }


}