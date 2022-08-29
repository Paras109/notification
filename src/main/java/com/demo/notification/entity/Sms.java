package com.demo.notification.entity;

public class Sms {

	
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Sms(String text) {
		super();
		this.text = text;
	}

	public Sms() {
		super();
	}

	
}
