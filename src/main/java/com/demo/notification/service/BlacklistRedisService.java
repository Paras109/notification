package com.demo.notification.service;


import java.util.List;

import com.demo.notification.entity.BlacklistRedis;

public interface BlacklistRedisService {

	void save(BlacklistRedis theBlacklistRedis);

	void deleteById(String string);

	List<BlacklistRedis>findAll();

	boolean findById(String phoneNumber);

}
 