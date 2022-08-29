package com.demo.notification.service;

import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class ProducerService {

	private static final Logger logger = LoggerFactory.getLogger(Producer.class);
    private static final String TOPIC = "notification.send_sms";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        logger.info(String.format("#### -> Producing message -> %s", message));
        System.out.println("producer message:-  " +message);
        this.kafkaTemplate.send(TOPIC, message);
    }
}
