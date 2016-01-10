package com.shanghai.listener;

import com.shanghai.data.GetSelectTicketsArrDdata;

import java.util.List;

/**
 * Created by Administrator on 2016/1/9.
 */
public interface GetSelectTicketsListener {
    void onSucc( List<GetSelectTicketsArrDdata> getSelectTicketsArrDdatas);
    void onError(String errorMsg);
}
