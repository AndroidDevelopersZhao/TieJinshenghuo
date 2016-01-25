package com.shanghai.data.data_addamount;

/**
 * Created by Administrator on 2016/1/25.
 */
public class AllData {
    private int error_code=-1;
    private String reason=null;
    private Result result=null;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
