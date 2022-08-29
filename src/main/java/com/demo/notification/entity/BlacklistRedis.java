package com.demo.notification.entity;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;


@RedisHash("blacklist-numbers")
public class BlacklistRedis implements Serializable{

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	
	public BlacklistRedis() {
		
	}

	public BlacklistRedis(String id) {
	
		this.id = id;
	}
	
}
