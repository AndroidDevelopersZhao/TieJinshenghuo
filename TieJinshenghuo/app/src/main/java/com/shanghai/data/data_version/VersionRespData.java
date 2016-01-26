package com.shanghai.data.data_version;

public class VersionRespData {
	private int code =-1;
	private String version = null;
	private String result =null;
	
	
	
	public VersionRespData(int code, String version, String result) {
		super();
		this.code = code;
		this.version = version;
		this.result = result;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
}
