package com.shanghai.listener.listener_tickets;

import com.shanghai.data.data_robtickets.GetSelectTicketsArrDdata;

import java.util.List;

/**
 * Created by Administrator on 2016/1/9.
 */
public interface GetSelectTicketsListener {
    void onSucc( List<GetSelectTicketsArrDdata> getSelectTicketsArrDdatas);
    void onError(String errorMsg);
}
