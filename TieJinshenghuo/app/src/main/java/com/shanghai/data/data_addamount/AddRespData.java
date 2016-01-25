package com.shanghai.data.data_addamount;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/25.
 */
public class AddRespData implements Serializable{
    private int error_code=-1;
    private String reason=null;
    private AddRespDataResult result=null;

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

    public AddRespDataResult getResult() {
        return result;
    }

    public void setResult(AddRespDataResult result) {
        this.result = result;
    }
}
