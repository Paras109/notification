package com.demo.notification.utils;

public class SmsDetails {

	
	private String phoneNumber;
	private String message;
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public SmsDetails(String phoneNumber, String message) {
	
		this.phoneNumber = phoneNumber;
		this.message = message;
	}

	
	
	
}
