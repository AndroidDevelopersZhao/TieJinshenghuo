package com.shanghai.data;

import java.io.Serializable;

public class RespData_UserInfo implements Serializable{
	private String name =null;
	private String year =null;
	private String city =null;
	private String sex =null;
	private String cardNo =null;
	private String phoneNum =null;
	private int code =-1;
	
	
	public RespData_UserInfo(int code,String name, String year, String city, String sex,
		String cardNo, String phoneNum) {
	
	this.code=code;
	this.name = name;
	this.year = year;
	this.city = city;
	this.sex = sex;
	this.cardNo = cardNo;
	this.phoneNum = phoneNum;
}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
	
}
