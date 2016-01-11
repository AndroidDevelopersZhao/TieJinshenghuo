package com.shanghai.data.data_robtickets;

/**
 * Created by Administrator on 2016/1/10.
 */
public class OrderStatus_O_Result_Data {
    private String orderid = null;
    private String user_orderid = null;/*您自定义的订单号*/
    private String msg = null;/*订单描述信息*/
    private String orderamount = null;/*订单金额，该订单正在处理，所以为null*/
    private String status = null;/*0表示正在处理*/
    private OrderStatus_O_Passengers_Data[] passengers = null;
    private String checi = null;/*车次*/
    private String ordernumber = null;/*12306的订单号，该订单正在处理，所以为null*/
    private String submit_time = null; /*您提交订单的时间*/
    private String deal_time = null;/*处理完占座的时间*/
    private String cancel_time = null; /*您主动取消订单的时间*/
    private String pay_time = null;/*您请求出票（支付）的时间*/
    private String finished_time = null; /*完成出票时间*/
    private String refund_time = null;/*此订单最后一次申请退票的时间（退票针对乘客不针对订单）*/
    private String juhe_refund_time = null;/*最后一次处理完退票的时间*/
    private String train_date = null; /*乘车日期*/
    private String from_station_name = null; /*出发站名字*/
    private String from_station_code = null;/*出发站简码*/
    private String to_station_name = null; /*到达站名字*/
    private String to_station_code = null; /*到达站简码*/
    private String refund_money = null;/*累积退款金额*/

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getUser_orderid() {
        return user_orderid;
    }

    public void setUser_orderid(String user_orderid) {
        this.user_orderid = user_orderid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getOrderamount() {
        return orderamount;
    }

    public void setOrderamount(String orderamount) {
        this.orderamount = orderamount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OrderStatus_O_Passengers_Data[] getPassengers() {
        return passengers;
    }

    public void setPassengers(OrderStatus_O_Passengers_Data[] passengers) {
        this.passengers = passengers;
    }

    public String getCheci() {
        return checi;
    }

    public void setCheci(String checi) {
        this.checi = checi;
    }

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getSubmit_time() {
        return submit_time;
    }

    public void setSubmit_time(String submit_time) {
        this.submit_time = submit_time;
    }

    public String getDeal_time() {
        return deal_time;
    }

    public void setDeal_time(String deal_time) {
        this.deal_time = deal_time;
    }

    public String getCancel_time() {
        return cancel_time;
    }

    public void setCancel_time(String cancel_time) {
        this.cancel_time = cancel_time;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getFinished_time() {
        return finished_time;
    }

    public void setFinished_time(String finished_time) {
        this.finished_time = finished_time;
    }

    public String getRefund_time() {
        return refund_time;
    }

    public void setRefund_time(String refund_time) {
        this.refund_time = refund_time;
    }

    public String getJuhe_refund_time() {
        return juhe_refund_time;
    }

    public void setJuhe_refund_time(String juhe_refund_time) {
        this.juhe_refund_time = juhe_refund_time;
    }

    public String getTrain_date() {
        return train_date;
    }

    public void setTrain_date(String train_date) {
        this.train_date = train_date;
    }

    public String getFrom_station_name() {
        return from_station_name;
    }

    public void setFrom_station_name(String from_station_name) {
        this.from_station_name = from_station_name;
    }

    public String getFrom_station_code() {
        return from_station_code;
    }

    public void setFrom_station_code(String from_station_code) {
        this.from_station_code = from_station_code;
    }

    public String getTo_station_name() {
        return to_station_name;
    }

    public void setTo_station_name(String to_station_name) {
        this.to_station_name = to_station_name;
    }

    public String getTo_station_code() {
        return to_station_code;
    }

    public void setTo_station_code(String to_station_code) {
        this.to_station_code = to_station_code;
    }

    public String getRefund_money() {
        return refund_money;
    }

    public void setRefund_money(String refund_money) {
        this.refund_money = refund_money;
    }
}
