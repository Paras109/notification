package com.demo.notification.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.demo.notification.entity.Blacklist;

public interface BlacklistRepository extends JpaRepository<Blacklist, Integer> {

	
	List<Blacklist> findByBlacklistNumber(String blacklistNumber);
	

}
