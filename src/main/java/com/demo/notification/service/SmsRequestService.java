package com.demo.notification.service;

import com.demo.notification.entity.SmsRequest;

public interface SmsRequestService {

	void save(SmsRequest theSmsRequest);

	SmsRequest findById(int smsRequestId);

}
