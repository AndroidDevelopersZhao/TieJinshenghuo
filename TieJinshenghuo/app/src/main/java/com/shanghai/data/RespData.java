package com.shanghai.data;

public class RespData {
	private int code =-1;
	private String data=null;
	
	
	public RespData(int code,String data){
		this.code=code;
		this.data=data;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
}
