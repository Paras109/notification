package com.demo.notification.dao;

import org.springframework.data.repository.CrudRepository;

import com.demo.notification.entity.BlacklistRedis;

public interface BlacklistRedisRepository extends CrudRepository<BlacklistRedis,String>{

}
