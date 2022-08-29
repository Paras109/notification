package com.demo.notification.service;

import java.util.List;

import com.demo.notification.entity.Blacklist;

public interface BlacklistService {

	void save(Blacklist theBlacklist);

	List<Blacklist> findAll();

	List<Blacklist> findByNumber(String number);

	void deleteById(int id);

}
