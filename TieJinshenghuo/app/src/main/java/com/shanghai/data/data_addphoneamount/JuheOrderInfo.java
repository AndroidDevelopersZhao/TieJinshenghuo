package com.shanghai.data.data_addphoneamount;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/26.
 */
public class JuheOrderInfo implements Serializable{
    private int sta = -1;
    private String info = null;
    private String orderid=null;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public int getSta() {
        return sta;
    }

    public void setSta(int sta) {
        this.sta = sta;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
