package com.shanghai.data.data_robtickets;

/**
 * Created by Administrator on 2016/1/9.
 */
public class PassengersArrData {

        private String passengerid = null;
    private String passengersename = null;
    private String piaotype = null;
    private String piaotypename = null;
    private String passporttypeseid = null;
    private String passporttypeseidname = null;
    private String passportseno = null;
    private String price = null;
    private String zwcode = null;
    private String zwname = null;
    private PassengersInsuranceData insurance=null;

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

    public PassengersInsuranceData getInsurance() {
        return insurance;
    }

    public void setInsurance(PassengersInsuranceData insurance) {
        this.insurance = insurance;
    }
    String data =null;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return data;
    }
}
