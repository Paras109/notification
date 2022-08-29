package com.demo.notification.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.demo.notification.model.MessageElasticsearch;

public interface MessageElasticsearchRepository extends ElasticsearchRepository<MessageElasticsearch, String> {

}
