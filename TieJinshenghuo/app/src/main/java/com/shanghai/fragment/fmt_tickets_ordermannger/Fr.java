package com.shanghai.fragment.fmt_tickets_ordermannger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shanghai.R;

/**
 * Created by Administrator on 2016/1/12.
 */
public class Fr extends Fragment {
    private String ttt = null;
    int i=1;
    public Fr(String ttt) {
        Log.d("TAG","aaaaaa");
        this.ttt = ttt;
    }

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fmt_tickets_ordermannager_allorder, null);
        Log.d("TAG","b"+i);
//        i++;
        TextView v = (TextView) view.findViewById(R.id.aaaa);
        v.setText(i+"");
        i++;
        return view;
    }
}
