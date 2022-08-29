package com.demo.notification.response;

import com.demo.notification.entity.SmsRequest;

public class GetSmsDetailsSuccess {

	
	
private SmsRequest data;

public GetSmsDetailsSuccess(SmsRequest data) {
	super();
	this.data = data;
}

public GetSmsDetailsSuccess() {
	super();
}

public SmsRequest getData() {
	return data;
}

public void setData(SmsRequest data) {
	this.data = data;
}

	
}
