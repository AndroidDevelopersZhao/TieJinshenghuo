package com.shanghai.data.data_robtickets;

import android.util.Log;

import com.google.gson.Gson;
import com.shanghai.listener.GetTicktsSiteCodeListener;
import com.shanghai.soeasylib.util.XXHttpClient;
import com.shanghai.utils.Util;

/**
 * Created by Administrator on 2016/1/9.
 */
public class GetTicketsSiteCode {
    private String TAG = "NewClient";
    private  GetTicketsSiteCode(){
    }
    public  GetTicketsSiteCode(String ticketAddress, final GetTicktsSiteCodeListener codeListener){

        XXHttpClient client = new XXHttpClient(Util.url_ticket1, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, byte[] bytes) {
                Ticket_SiteInCode_Data ticket_siteInCode_data =
                        new Gson().fromJson(new String(bytes), Ticket_SiteInCode_Data.class);
                if (ticket_siteInCode_data.getError_code() == 0) {
                    codeListener.OnSucc(ticket_siteInCode_data.getResult().getCode());
                } else {
//                    Ticket_SiteInCode_Result_data Ticket_SiteInCode_Result_data=new Ticket_SiteInCode_Result_data();
//                    Ticket_SiteInCode_Result_data.setCode(ticket_siteInCode_data.getReason());
//                    ticket_siteInCode_data.setResult(Ticket_SiteInCode_Result_data);
                    codeListener.OnError(ticket_siteInCode_data.getReason());
                }
            }

            @Override
            public void onError(int i, Throwable throwable) {
                Log.e(TAG, "网络异常");
                codeListener.OnError("网络异常");
            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
        client.put("key", Util.appid_ticket);
        client.put("stationName", ticketAddress);
        client.doPost(15000);
    }
}
