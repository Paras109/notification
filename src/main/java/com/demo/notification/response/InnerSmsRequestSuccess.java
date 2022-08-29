package com.demo.notification.response;

public class InnerSmsRequestSuccess {

	
	private int requestId;
	private String comments;
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public InnerSmsRequestSuccess(int requestId, String comments) {
		
		this.requestId = requestId;
		this.comments = comments;
	}
	public InnerSmsRequestSuccess() {
		
	}
	
}
