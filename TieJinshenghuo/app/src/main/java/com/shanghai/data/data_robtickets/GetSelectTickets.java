package com.shanghai.data.data_robtickets;

import android.util.Log;

import com.google.gson.Gson;
import com.shanghai.listener.GetSelectTicketsListener;
import com.shanghai.soeasylib.util.XXHttpClient;
import com.shanghai.utils.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/9.
 */
public class GetSelectTickets {
    private String TAG = "NewClient";
    private List<GetSelectTicketsArrDdata> data =new ArrayList<>();
    private GetSelectTickets() {//siyouh

    }

    public GetSelectTickets(String startAdd, String stopAdd, String time,
                            final GetSelectTicketsListener getSelectTicketsListener) {
        XXHttpClient client = new XXHttpClient(Util.url_ticket2, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, byte[] bytes) {
                Log.d(TAG,new String(bytes));



                GetSelectTicketsData getSelectTicketsData = new Gson().fromJson(new String(bytes),GetSelectTicketsData.class);
                if (getSelectTicketsData.getError_code()==0){
                    for (int j = 0; j < getSelectTicketsData.getResult().getList().length; j++) {
//                    Log.d(TAG,getSelectTicketsData.getResult().getList()[j].getEnd_station_name());
                        data.add(getSelectTicketsData.getResult().getList()[j]);
//                    for (GetSelectTicketsArrDdata b:((getSelectTicketsData.getResult().getList())[j])){
//
//                    }
                    }
                }
                if (getSelectTicketsData.getError_code()==0){
                    getSelectTicketsListener.onSucc(data);
                }else {
                    getSelectTicketsListener.onError(getSelectTicketsData.getReason());
                }



            }

            @Override
            public void onError(int i, Throwable throwable) {
                Log.e(TAG, "网络异常");
                getSelectTicketsListener.onError("网络错误");
            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
        client.put("key", Util.appid_ticket);
        client.put("train_date", time);
        client.put("from_station", startAdd);
        client.put("to_station", stopAdd);
        client.doPost(15000);


    }
}
