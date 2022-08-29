package com.demo.notification.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="blacklist_numbers")
public class Blacklist {



	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="blacklist_number")
	private String blacklistNumber;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBlacklistNumber() {
		return blacklistNumber;
	}

	public void setBlacklistNumber(String blacklistNumber) {
		this.blacklistNumber = blacklistNumber;
	}

	
	public Blacklist() {
		
	}

	public Blacklist(String blacklistNumber) {
		
		this.blacklistNumber = blacklistNumber;
	}
	
	
	
	
	
	
	
	
}
