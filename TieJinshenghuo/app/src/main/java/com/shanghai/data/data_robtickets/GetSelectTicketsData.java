package com.shanghai.data.data_robtickets;

/**
 * Created by Administrator on 2016/1/9.
 */
public class GetSelectTicketsData {
    private String reason=null;
    private int error_code=-1;
    private GetSelectTicketsResultData result=null;





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

    public GetSelectTicketsResultData getResult() {
        return result;
    }

    public void setResult(GetSelectTicketsResultData result) {
        this.result = result;
    }
}
