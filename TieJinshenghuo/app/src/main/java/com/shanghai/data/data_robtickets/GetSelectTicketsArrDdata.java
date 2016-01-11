package com.shanghai.data.data_robtickets;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/9.
 */
public class GetSelectTicketsArrDdata implements Serializable{

    private String end_station_name = null;/*终到站名*/
    private String swz_price = null;/*商务座票价*/
    private String swz_num = null;/*商务座余票数量*/
    private String to_station_name = null;/*到达车站名*/
    private String ydz_num = null;/*一等座余票数量*/
    private String yz_num = null;/*硬座的余票数量*/
    private String rw_num = null; /*软卧（下）余票数量*/
    private String arrive_days = null; /*列车从出发站到达目的站的运行天数 0:当日到达1,: 次日到达,2:三日到达,3:四日到达,依此类推*/
    private String rz_num = null;/*软座的余票数量*/
    private String access_byidcard = null;/*是否可凭二代身份证直接进出站*/
    private float yz_price =-1;/*硬座票价*/
    private String sale_date_time = null; /*车票开售时间*/

    private String from_station_code = null;/*出发车站简码*/
    private String rz_price = null;/*软座的票价*/
    private String gjrw_num = null; /*高级软卧余票数量*/
    private String to_station_code = null;/*到达车站简码*/
    private String ydz_price = null;/*一等座票价*/
    private String wz_price = null;/*无座票价*/
    private String tdz_price = null;  /*特等座票价*/
    private String run_time = null;/*历时（出发站到目的站）*/
    private String yw_num = null;/*硬卧（中）的余票数量*/
    private String edz_price = null; /*二等座票价*/


    private String qtxb_price = null;/*其他席别对应的票价*/
    private String can_buy_now = null;/*当前是否可以接受预定*/
    private String rw_price = null;/*软卧（下）票价*/
    private String train_type = null;/*列车类型*/
    private String yw_price = null; /*硬卧（中）票价*/
    private String note = null; /*备注*/
    private String train_no = null;/*列车号*/
    private String train_code = null; /*车次*/
    private String from_station_name = null; /*出发车站名*/
    private String run_time_minute = null;/*历时分钟合计*/
    private String arrive_time = null;/*到达时刻*/
    private String start_station_name = null;/*始发站名*/
    private String start_time = null;/*出发时刻*/
    private String edz_num = null;/*二等座的余票数量*/


    private String wz_num = null;/*无座的余票数量*/
    private String qtxb_num = null;/*其他席别余票数量*/
    private String train_start_date = null;/*列车从始发站出发的日期*/
    private String gjrw_price = null; /*高级软卧票价*/
    private String tdz_num = null;/*特等座的余票数量*/

    public String getEnd_station_name() {
        return end_station_name;
    }

    public void setEnd_station_name(String end_station_name) {
        this.end_station_name = end_station_name;
    }

    public String getSwz_price() {
        return swz_price;
    }

    public void setSwz_price(String swz_price) {
        this.swz_price = swz_price;
    }

    public String getSwz_num() {
        return swz_num;
    }

    public void setSwz_num(String swz_num) {
        this.swz_num = swz_num;
    }

    public String getTo_station_name() {
        return to_station_name;
    }

    public void setTo_station_name(String to_station_name) {
        this.to_station_name = to_station_name;
    }

    public String getYdz_num() {
        return ydz_num;
    }

    public void setYdz_num(String ydz_num) {
        this.ydz_num = ydz_num;
    }

    public String getYz_num() {
        return yz_num;
    }

    public void setYz_num(String yz_num) {
        this.yz_num = yz_num;
    }

    public String getRw_num() {
        return rw_num;
    }

    public void setRw_num(String rw_num) {
        this.rw_num = rw_num;
    }

    public String getArrive_days() {
        return arrive_days;
    }

    public void setArrive_days(String arrive_days) {
        this.arrive_days = arrive_days;
    }

    public String getRz_num() {
        return rz_num;
    }

    public void setRz_num(String rz_num) {
        this.rz_num = rz_num;
    }

    public String getAccess_byidcard() {
        return access_byidcard;
    }

    public void setAccess_byidcard(String access_byidcard) {
        this.access_byidcard = access_byidcard;
    }

    public float getYz_price() {
        return yz_price;
    }

    public void setYz_price(float yz_price) {
        this.yz_price = yz_price;
    }

    public String getSale_date_time() {
        return sale_date_time;
    }

    public void setSale_date_time(String sale_date_time) {
        this.sale_date_time = sale_date_time;
    }

    public String getFrom_station_code() {
        return from_station_code;
    }

    public void setFrom_station_code(String from_station_code) {
        this.from_station_code = from_station_code;
    }

    public String getRz_price() {
        return rz_price;
    }

    public void setRz_price(String rz_price) {
        this.rz_price = rz_price;
    }

    public String getGjrw_num() {
        return gjrw_num;
    }

    public void setGjrw_num(String gjrw_num) {
        this.gjrw_num = gjrw_num;
    }

    public String getTo_station_code() {
        return to_station_code;
    }

    public void setTo_station_code(String to_station_code) {
        this.to_station_code = to_station_code;
    }

    public String getYdz_price() {
        return ydz_price;
    }

    public void setYdz_price(String ydz_price) {
        this.ydz_price = ydz_price;
    }

    public String getWz_price() {
        return wz_price;
    }

    public void setWz_price(String wz_price) {
        this.wz_price = wz_price;
    }

    public String getTdz_price() {
        return tdz_price;
    }

    public void setTdz_price(String tdz_price) {
        this.tdz_price = tdz_price;
    }

    public String getRun_time() {
        return run_time;
    }

    public void setRun_time(String run_time) {
        this.run_time = run_time;
    }

    public String getYw_num() {
        return yw_num;
    }

    public void setYw_num(String yw_num) {
        this.yw_num = yw_num;
    }

    public String getEdz_price() {
        return edz_price;
    }

    public void setEdz_price(String edz_price) {
        this.edz_price = edz_price;
    }

    public String getQtxb_price() {
        return qtxb_price;
    }

    public void setQtxb_price(String qtxb_price) {
        this.qtxb_price = qtxb_price;
    }

    public String getCan_buy_now() {
        return can_buy_now;
    }

    public void setCan_buy_now(String can_buy_now) {
        this.can_buy_now = can_buy_now;
    }

    public String getRw_price() {
        return rw_price;
    }

    public void setRw_price(String rw_price) {
        this.rw_price = rw_price;
    }

    public String getTrain_type() {
        return train_type;
    }

    public void setTrain_type(String train_type) {
        this.train_type = train_type;
    }

    public String getYw_price() {
        return yw_price;
    }

    public void setYw_price(String yw_price) {
        this.yw_price = yw_price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTrain_no() {
        return train_no;
    }

    public void setTrain_no(String train_no) {
        this.train_no = train_no;
    }

    public String getTrain_code() {
        return train_code;
    }

    public void setTrain_code(String train_code) {
        this.train_code = train_code;
    }

    public String getFrom_station_name() {
        return from_station_name;
    }

    public void setFrom_station_name(String from_station_name) {
        this.from_station_name = from_station_name;
    }

    public String getRun_time_minute() {
        return run_time_minute;
    }

    public void setRun_time_minute(String run_time_minute) {
        this.run_time_minute = run_time_minute;
    }

    public String getArrive_time() {
        return arrive_time;
    }

    public void setArrive_time(String arrive_time) {
        this.arrive_time = arrive_time;
    }

    public String getStart_station_name() {
        return start_station_name;
    }

    public void setStart_station_name(String start_station_name) {
        this.start_station_name = start_station_name;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEdz_num() {
        return edz_num;
    }

    public void setEdz_num(String edz_num) {
        this.edz_num = edz_num;
    }

    public String getWz_num() {
        return wz_num;
    }

    public void setWz_num(String wz_num) {
        this.wz_num = wz_num;
    }

    public String getQtxb_num() {
        return qtxb_num;
    }

    public void setQtxb_num(String qtxb_num) {
        this.qtxb_num = qtxb_num;
    }

    public String getTrain_start_date() {
        return train_start_date;
    }

    public void setTrain_start_date(String train_start_date) {
        this.train_start_date = train_start_date;
    }

    public String getGjrw_price() {
        return gjrw_price;
    }

    public void setGjrw_price(String gjrw_price) {
        this.gjrw_price = gjrw_price;
    }

    public String getTdz_num() {
        return tdz_num;
    }

    public void setTdz_num(String tdz_num) {
        this.tdz_num = tdz_num;
    }
}
