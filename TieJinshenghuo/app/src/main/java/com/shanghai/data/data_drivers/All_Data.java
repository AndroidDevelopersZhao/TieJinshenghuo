package com.shanghai.data.data_drivers;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/20.
 */
public class All_Data implements Serializable{
    private int  error_code =-1;
    private String reason=null;
    private Result [] result=null;

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

    public Result[] getResult() {
        return result;
    }

    public void setResult(Result[] result) {
        this.result = result;
    }
}
