package com.demo.notification.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.demo.notification.dao.BlacklistRedisRepository;

import com.demo.notification.entity.BlacklistRedis;

@Service
public class BlacklistRedisServiceImpl implements BlacklistRedisService {

private BlacklistRedisRepository blacklistRedisRepository;

public BlacklistRedisServiceImpl(BlacklistRedisRepository theBlacklistRedisRepository) {

blacklistRedisRepository=theBlacklistRedisRepository;
}

@Override
public void save(BlacklistRedis theBlacklistRedis) {
	// TODO Auto-generated method stub

	blacklistRedisRepository.save(theBlacklistRedis);
	
}

@Override
public void deleteById(String string) {
	// TODO Auto-generated method stub
	
	blacklistRedisRepository.deleteById(string);
	System.out.println("HOGAYA BHAI");
	
}

@Override
public List<BlacklistRedis> findAll() {
	
	List<BlacklistRedis> blacklistNumbers = new ArrayList<>();
	blacklistRedisRepository.findAll().forEach(blacklistNumbers::add);
	
	return blacklistNumbers;
}

@Override
public boolean findById(String phoneNumber) {
	// TODO Auto-generated method stub

	Optional<BlacklistRedis> result=blacklistRedisRepository.findById(phoneNumber);
	
	
	if(result.isPresent())
	{
		return true;
	}
	else
		return false;
}





}
