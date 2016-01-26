package com.shanghai.data.data_addphoneamount;

import java.io.Serializable;
import java.util.List;

public class AddPhoneInfoRespData implements Serializable {
	private int code =-1;
	private String result =null;
	private List<AddOrderInfo> data = null;
	public AddPhoneInfoRespData(int code, String result,  List<AddOrderInfo> data) {
		super();
		this.code = code;
		this.result = result;
		this.data = data;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public  List<AddOrderInfo> getData() {
		return data;
	}
	public void setData( List<AddOrderInfo> data) {
		this.data = data;
	}
	
}
