package com.demo.notification.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.notification.dao.BlacklistRepository;

import com.demo.notification.entity.Blacklist;

@Service
public class BlacklistServiceImpl implements BlacklistService {
	
	
	
private BlacklistRepository blacklistRepository;
	
	@Autowired
	public BlacklistServiceImpl(BlacklistRepository theBlacklistRepository) {
		blacklistRepository = theBlacklistRepository;
	}
	

	@Override
	public void save(Blacklist theBlacklist) {
		blacklistRepository.save(theBlacklist);
		
	}


	@Override
	public List<Blacklist> findAll() {
		return blacklistRepository.findAll();
	}


	@Override
	public List<Blacklist> findByNumber(String number) {
		
	return 	blacklistRepository.findByBlacklistNumber(number);
		
		
	
	}


	@Override
	public void deleteById(int id) {
		blacklistRepository.deleteById(id);
		
	}

}
