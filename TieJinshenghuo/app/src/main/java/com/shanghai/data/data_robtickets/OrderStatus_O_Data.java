package com.shanghai.data.data_robtickets;

/**
 * Created by Administrator on 2016/1/10.
 */
public class OrderStatus_O_Data {
    private String reason=null;
    private int error_code =-1;
    private OrderStatus_O_Result_Data result=null;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public OrderStatus_O_Result_Data getResult() {
        return result;
    }

    public void setResult(OrderStatus_O_Result_Data result) {
        this.result = result;
    }
}
