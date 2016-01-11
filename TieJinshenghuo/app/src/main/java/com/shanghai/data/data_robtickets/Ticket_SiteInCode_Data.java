package com.shanghai.data.data_robtickets;

/**
 * Created by Administrator on 2016/1/9.
 */
public class Ticket_SiteInCode_Data {
//    {"reason":"成功的返回","result":{"code":"SHH"},"error_code":0}
private String reason = null;
    private int error_code = -1;
    private Ticket_SiteInCode_Result_data result = null;

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

    public Ticket_SiteInCode_Result_data getResult() {
        return result;
    }

    public void setResult(Ticket_SiteInCode_Result_data result) {
        this.result = result;
    }
}
