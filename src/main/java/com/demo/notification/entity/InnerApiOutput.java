package com.demo.notification.entity;

public class InnerApiOutput {

	private String code;
	private String transid;
	private String description;
	public InnerApiOutput(String code, String transid, String description) {
		super();
		this.code = code;
		this.transid = transid;
		this.description = description;
	}
	public InnerApiOutput() {
		super();
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTransid() {
		return transid;
	}
	public void setTransid(String transid) {
		this.transid = transid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
