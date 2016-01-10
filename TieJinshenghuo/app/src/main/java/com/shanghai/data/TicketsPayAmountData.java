package com.shanghai.data;

/**
 * Created by Administrator on 2016/1/10.
 */
public class TicketsPayAmountData {
    private String reason=null;
    private String result=null;
    private int error_code=-1;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
}
