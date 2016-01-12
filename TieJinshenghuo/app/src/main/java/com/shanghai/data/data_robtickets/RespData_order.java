package com.shanghai.data.data_robtickets;

import java.util.ArrayList;
import java.util.List;

public class RespData_order {
	private int code =-1;
	private String result = null;
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	private ArrayList<String> orders=null;
	
	public RespData_order(int code,String result,ArrayList<String> orders) {
		// TODO Auto-generated constructor stub
			this.code=code;
			this.orders=orders;
			this.result=result;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public ArrayList<String> getOrders() {
		return orders;
	}
	public void setOrders(ArrayList<String> orders) {
		this.orders = orders;
	}
	
}
