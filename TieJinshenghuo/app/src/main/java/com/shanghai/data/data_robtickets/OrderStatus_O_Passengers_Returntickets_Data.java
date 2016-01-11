package com.shanghai.data.data_robtickets;

/**
 * Created by Administrator on 2016/1/11.
 */
public class OrderStatus_O_Passengers_Returntickets_Data {
    private boolean returnsuccess = false;/*退票是否成功*/
    private String returnmoney = null;/*退票（改签差价）金额，退票要收手续费，所以此金额小于票价*/
    private String returntime = null;/**/
    private String returnfailid = null;/*退票失败原因ID*/
    private String returnfailmsg = null;/*退票失败原因描述*/
    private String returntype = null;/*1:线上退票；0:线下退票，即用户持票去火车站窗口退票*/

    public boolean isReturnsuccess() {
        return returnsuccess;
    }

    public void setReturnsuccess(boolean returnsuccess) {
        this.returnsuccess = returnsuccess;
    }

    public String getReturnmoney() {
        return returnmoney;
    }

    public void setReturnmoney(String returnmoney) {
        this.returnmoney = returnmoney;
    }

    public String getReturntime() {
        return returntime;
    }

    public void setReturntime(String returntime) {
        this.returntime = returntime;
    }

    public String getReturnfailid() {
        return returnfailid;
    }

    public void setReturnfailid(String returnfailid) {
        this.returnfailid = returnfailid;
    }

    public String getReturnfailmsg() {
        return returnfailmsg;
    }

    public void setReturnfailmsg(String returnfailmsg) {
        this.returnfailmsg = returnfailmsg;
    }

    public String getReturntype() {
        return returntype;
    }

    public void setReturntype(String returntype) {
        this.returntype = returntype;
    }
}
