package com.shanghai.data.data_aty_model;

import com.shanghai.listener.listener_aty_moudel.OnGetRobTicketsOrderListener;
import com.shanghai.soeasylib.util.XXHttpClient;
import com.shanghai.utils.Util;

/**
 * Created by Administrator on 2016/1/11.
 */
public class GetRobTicketsOrder {
    private String username = null;
    private OnGetRobTicketsOrderListener allRobTicketsOrderListener = null;
    private int type = -1;

    /**
     * 查询订单的相关数据
     * 0：刚提交，待处理；1：失败／失效／取消的订单；2：占座成功待支付（此时可取消订单，超时不支付将失效）；3：支付成功待出票；4：出票成功；
     * 5：出票失败；6：正在处理线上退票请求；7：有乘客退票（改签）成功（status保存的是最后一次操作该订单后的状态，先有乘客退票失败，
     * 然后有乘客退票成功，那么status为7）；8：有乘客退票失败
     *
     * @param type
     * @param username
     * @param orderId
     * @param status                     要查询的订单的状态（为1时表示查询该用户所有的失败订单，为2时表示占座成功待支付的全部订单 3：支付成功待出票 4：出票成功；）
     * @param allRobTicketsOrderListener 通过此接口返回，返回对象
     */
    public GetRobTicketsOrder(int type, String username, String orderId, String status, OnGetRobTicketsOrderListener allRobTicketsOrderListener) {
        this.username = username;
        this.allRobTicketsOrderListener = allRobTicketsOrderListener;
        this.type = type;
        XXHttpClient client = new XXHttpClient(Util.url_my, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, byte[] bytes) {

            }

            @Override
            public void onError(int i, Throwable throwable) {

            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
        client.put("type", type);
        client.put("username", username);

    }
}
