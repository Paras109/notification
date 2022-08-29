package com.demo.notification.response;

public class InnerSmsRequestError {

	
	private String code;
	private String message;
	public InnerSmsRequestError(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	public InnerSmsRequestError() {
		super();
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
