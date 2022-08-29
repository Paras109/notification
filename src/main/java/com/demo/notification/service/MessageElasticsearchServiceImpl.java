package com.demo.notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.notification.dao.MessageElasticsearchRepository;
import com.demo.notification.model.MessageElasticsearch;
@Service
public class MessageElasticsearchServiceImpl implements MessageElasticsearchService {

	
	
	@Autowired
	private MessageElasticsearchRepository messageElasticsearchRepository;
	@Override
	public void save(MessageElasticsearch theMessageElasticsearch) {
		
		messageElasticsearchRepository.save(theMessageElasticsearch);
		
	}
	@Override
	public Iterable<MessageElasticsearch> findAll() {
		
return messageElasticsearchRepository.findAll();
	}

	
	
}
