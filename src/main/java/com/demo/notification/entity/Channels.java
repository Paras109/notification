package com.demo.notification.entity;

public class Channels {

	
	private Sms sms;

	public Channels(Sms sms) {
		super();
		this.sms = sms;
	}

	public Sms getSms() {
		return sms;
	}

	public void setSms(Sms sms) {
		this.sms = sms;
	}

	public Channels() {
		super();
	}
	
}
