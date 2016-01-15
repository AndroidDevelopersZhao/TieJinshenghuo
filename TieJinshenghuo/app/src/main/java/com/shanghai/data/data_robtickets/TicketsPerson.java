package com.shanghai.data.data_robtickets;

public class TicketsPerson {
	private String name =null;
	private String year =null;
	private String city =null;
	private String sex =null;
	private String cardNo =null;
	private String phoneNum =null;
	
	public TicketsPerson(String name, String year, String city, String sex,
			String cardNo, String phoneNum) {
		super();
		this.name = name;
		this.year = year;
		this.city = city;
		this.sex = sex;
		this.cardNo = cardNo;
		this.phoneNum = phoneNum;
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
