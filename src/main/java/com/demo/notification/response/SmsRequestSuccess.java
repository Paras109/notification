package com.demo.notification.response;

public class SmsRequestSuccess {

	
	private InnerSmsRequestSuccess data;

	public InnerSmsRequestSuccess getData() {
		return data;
	}

	public void setData(InnerSmsRequestSuccess data) {
		this.data = data;
	}

	public SmsRequestSuccess(InnerSmsRequestSuccess data) {
		super();
		this.data = data;
	}

	public SmsRequestSuccess() {
	
	}
	
	
	
}
