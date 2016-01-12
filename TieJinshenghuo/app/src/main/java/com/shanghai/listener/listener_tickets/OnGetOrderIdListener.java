package com.shanghai.listener.listener_tickets;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/12.
 */
public interface OnGetOrderIdListener {
    void onSucc(ArrayList<String> orders);
    void onError(String errorMsg);
}
