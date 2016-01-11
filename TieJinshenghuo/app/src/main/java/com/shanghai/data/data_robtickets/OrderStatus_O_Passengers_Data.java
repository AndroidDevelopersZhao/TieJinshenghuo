package com.shanghai.data.data_robtickets;

/**
 * Created by Administrator on 2016/1/10.
 */
public class OrderStatus_O_Passengers_Data {

    private String passengerid = null;/*您自定义的乘客编号*/
    private String passengersename = null;/*乘客姓名*/
    private String piaotype = null; /*票类型编码*/
    private String piaotypename = null; /*票类型*/
    private String passporttypeseid = null;/*证件类型编码*/
    private String passporttypeseidname = null;/*证件类型*/

    private String passportseno = null; /*证件号码*/
    private String price = null; /*票价，如果真实票价为100元，您将此字段设为1元，处理完订单会更正为100元*/
    private String zwcode = null;/*座次编码*/
    private String zwname = null;/*座次*/
    private String ticket_no = null;//票号
    private String cxin = null;//座位号
        private int reason = -1;
    private OrderStatus_O_Passengers_Returntickets_Data returntickets = null;//退票相关信息

    public OrderStatus_O_Passengers_Returntickets_Data getReturntickets() {
        return returntickets;
    }

    public void setReturntickets(OrderStatus_O_Passengers_Returntickets_Data returntickets) {
        this.returntickets = returntickets;
    }

    public int getReason() {
        return reason;
    }

    public void setReason(int reason) {
        this.reason = reason;
    }

    public String getCxin() {
        return cxin;
    }

    public void setCxin(String cxin) {
        this.cxin = cxin;
    }

    public void setTicket_no(String ticket_no) {
        this.ticket_no = ticket_no;
    }

    public String getTicket_no() {
        return ticket_no;
    }

    public String getPassengerid() {
        return passengerid;
    }

    public void setPassengerid(String passengerid) {
        this.passengerid = passengerid;
    }

    public String getPassengersename() {
        return passengersename;
    }

    public void setPassengersename(String passengersename) {
        this.passengersename = passengersename;
    }

    public String getPiaotype() {
        return piaotype;
    }

    public void setPiaotype(String piaotype) {
        this.piaotype = piaotype;
    }

    public String getPiaotypename() {
        return piaotypename;
    }

    public void setPiaotypename(String piaotypename) {
        this.piaotypename = piaotypename;
    }

    public String getPassporttypeseid() {
        return passporttypeseid;
    }

    public void setPassporttypeseid(String passporttypeseid) {
        this.passporttypeseid = passporttypeseid;
    }

    public String getPassporttypeseidname() {
        return passporttypeseidname;
    }

    public void setPassporttypeseidname(String passporttypeseidname) {
        this.passporttypeseidname = passporttypeseidname;
    }

    public String getPassportseno() {
        return passportseno;
    }

    public void setPassportseno(String passportseno) {
        this.passportseno = passportseno;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getZwcode() {
        return zwcode;
    }

    public void setZwcode(String zwcode) {
        this.zwcode = zwcode;
    }

    public String getZwname() {
        return zwname;
    }

    public void setZwname(String zwname) {
        this.zwname = zwname;
    }
}
