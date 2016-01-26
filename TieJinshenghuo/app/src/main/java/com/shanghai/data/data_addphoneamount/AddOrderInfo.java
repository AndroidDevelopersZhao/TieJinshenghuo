package com.shanghai.data.data_addphoneamount;

import java.io.Serializable;

public class AddOrderInfo implements Serializable{
	private String time=null;
	private String orderid=null;
	private String price=null;
	private String info=null;
	private String mobnumber=null;
	private String state=null;
	private String stateinfo=null;
	public AddOrderInfo(String time, String orderid, String price, String info,
			String mobnumber, String state, String stateinfo) {
		super();
		this.time = time;
		this.orderid = orderid;
		this.price = price;
		this.info = info;
		this.mobnumber = mobnumber;
		this.state = state;
		this.stateinfo = stateinfo;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getMobnumber() {
		return mobnumber;
	}
	public void setMobnumber(String mobnumber) {
		this.mobnumber = mobnumber;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStateinfo() {
		return stateinfo;
	}
	public void setStateinfo(String stateinfo) {
		this.stateinfo = stateinfo;
	}
	
	
	
}
