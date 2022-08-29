package com.demo.notification.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "message-details")
public class MessageElasticsearch {

	
	@Id
    private String id;
	
	private String number;
	private String message;
	
	private long sentAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getSentAt() {
		return sentAt;
	}

	public void setSentAt(long sentAt) {
		this.sentAt = sentAt;
	}

	public MessageElasticsearch(String id, String number, String message, long sentAt) {
		
		this.id = id;
		this.number = number;
		this.message = message;
		this.sentAt = sentAt;
	}

	public MessageElasticsearch(String number, String message, long sentAt) {
		
		this.number = number;
		this.message = message;
		this.sentAt = sentAt;
	}

	public MessageElasticsearch() {
		
	}
	
	
}
