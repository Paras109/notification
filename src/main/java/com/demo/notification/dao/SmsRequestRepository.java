package com.demo.notification.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.notification.entity.SmsRequest;

public interface SmsRequestRepository extends JpaRepository<SmsRequest, Integer> {

}
