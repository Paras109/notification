package com.demo.notification.service;

import com.demo.notification.model.MessageElasticsearch;

public interface MessageElasticsearchService {

	void save(MessageElasticsearch theMessageElasticsearch);

	Iterable<MessageElasticsearch> findAll();

}
