package com.demo.notification.entity;

import java.util.List;

public class ApiOutput {

	
	private List<InnerApiOutput> response;

	public ApiOutput(List<InnerApiOutput> response) {
		super();
		this.response = response;
	}

	public List<InnerApiOutput> getResponse() {
		return response;
	}

	public void setResponse(List<InnerApiOutput> response) {
		this.response = response;
	}

	public ApiOutput() {
		super();
	}
	
}
