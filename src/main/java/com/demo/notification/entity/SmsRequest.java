package com.demo.notification.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sms_requests")

public class SmsRequest {
	

		// define fields
		
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		@Column(name="id")
		private int id;
		

		
		@Column(name="phone_number")
		private String phoneNumber;
		@Column(name="message")
		private String message;
		@Column(name="status")
		private String status;
		@Column(name="failure_code")
		private int failureCode;
		@Column(name="failure_comments")
		private String failureComments;
		@Column(name="created_at")
		private Date createdAt;
		@Column(name="updated_at")
		private Date updatedAt;
		
		
		
		
		
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public int getFailureCode() {
			return failureCode;
		}
		public void setFailureCode(int failureCode) {
			this.failureCode = failureCode;
		}
		public String getFailureComments() {
			return failureComments;
		}
		public void setFailureComments(String failureComments) {
			this.failureComments = failureComments;
		}
		public Date getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(Date createdAt) {
			this.createdAt = createdAt;
		}
		public Date getUpdatedAt() {
			return updatedAt;
		}
		public void setUpdatedAt(Date updatedAt) {
			this.updatedAt = updatedAt;
		}
		
		
		public SmsRequest(int id,String phoneNumber, String message, String status,
				Date createdAt, Date updatedAt) {
			this.id=id;
			this.phoneNumber = phoneNumber;
			this.message = message;
			this.status = status;
			this.createdAt = createdAt;
			this.updatedAt = updatedAt;
		}
		
		
		
		public SmsRequest(String phoneNumber, String message, String status,
				Date createdAt, Date updatedAt) {
			
			this.phoneNumber = phoneNumber;
			this.message = message;
			this.status = status;
			this.createdAt = createdAt;
			this.updatedAt = updatedAt;
		}
		
			

		public SmsRequest(int id,String phoneNumber, String message, String status,int failureCode,
				Date createdAt, Date updatedAt) {
			this.id=id;
			this.phoneNumber = phoneNumber;
			this.failureCode=failureCode;
			this.message = message;
			this.status = status;
			this.createdAt = createdAt;
			this.updatedAt = updatedAt;
		}
		public SmsRequest() {
			super();
		}

	



	
}
