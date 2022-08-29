package com.demo.notification.response;

public class SmsRequestError {

	
	private InnerSmsRequestError error;

	public SmsRequestError(InnerSmsRequestError error) {
		super();
		this.error = error;
	}

	public SmsRequestError() {
		super();
	}

	public InnerSmsRequestError getError() {
		return error;
	}

	public void setError(InnerSmsRequestError error) {
		this.error = error;
	}
	
}
