package com.shanghai.listener.listener_tickets;

/**
 * Created by Administrator on 2016/1/9.
 */
public interface GetTicktsSiteCodeListener {
    void OnSucc(String siteCode);
    void OnError(String error);
}
