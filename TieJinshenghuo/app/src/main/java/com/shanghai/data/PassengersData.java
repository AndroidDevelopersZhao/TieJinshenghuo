package com.shanghai.data;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2016/1/9.
 */
public class PassengersData {
    PassengersArrData [] passengersArrDatas = null;

    public PassengersArrData[] getPassengersArrDatas() {
        return passengersArrDatas;
    }

    public void setPassengersArrDatas(PassengersArrData[] passengersArrDatas) {
        this.passengersArrDatas = passengersArrDatas;
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
        PassengersInsuranceData insuranceDatas=passengersArrDatas[0].getInsurance();
        PassengersArrData arrData = passengersArrDatas[0];
        return data;
    }
}
