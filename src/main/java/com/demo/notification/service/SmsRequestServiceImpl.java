package com.demo.notification.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.notification.dao.SmsRequestRepository;
import com.demo.notification.entity.SmsRequest;
import com.demo.notification.exception.IdNotFoundException;
@Service
public class SmsRequestServiceImpl implements SmsRequestService {

	private SmsRequestRepository smsRequestRepository;
	
	@Autowired
	public SmsRequestServiceImpl(SmsRequestRepository theSmsRequestRepository) {
		smsRequestRepository = theSmsRequestRepository;
	}
	
	
	public void save(SmsRequest theSmsRequest) {
		
		
		
		smsRequestRepository.save(theSmsRequest);
		
		
	}


	@Override
	public SmsRequest findById(int smsRequestId) {
		Optional<SmsRequest> result= smsRequestRepository.findById(smsRequestId);
		SmsRequest theSmsRequest=null;
		
		if(result.isPresent())
			theSmsRequest=result.get();
		else
		{
			throw new IdNotFoundException("Did not find Sms Request Id " + smsRequestId);
			
		}
		
		return theSmsRequest;
		
	}

	
	
}
