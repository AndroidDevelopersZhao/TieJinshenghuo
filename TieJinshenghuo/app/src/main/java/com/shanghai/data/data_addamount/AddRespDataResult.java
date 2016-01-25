package com.shanghai.data.data_addamount;

/**
 * Created by Administrator on 2016/1/25.
 */
public class AddRespDataResult {
    private String ordercash=null;//价格
    private String cardname=null;//"10元话费充值",
    private String sporder_id=null;//"20150325162650234",/*聚合订单号*/
    private String orderid=null;/*自定义单号*/
    private String mobilephone=null;/*充值号码*/

    public String getOrdercash() {
        return ordercash;
    }

    public void setOrdercash(String ordercash) {
        this.ordercash = ordercash;
    }

    public String getCardname() {
        return cardname;
    }

    public void setCardname(String cardname) {
        this.cardname = cardname;
    }

    public String getSporder_id() {
        return sporder_id;
    }

    public void setSporder_id(String sporder_id) {
        this.sporder_id = sporder_id;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }
}
