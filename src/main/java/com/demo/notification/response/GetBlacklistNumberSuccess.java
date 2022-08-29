package com.demo.notification.response;

import java.util.List;

public class GetBlacklistNumberSuccess {

	
	List<String> data;

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

	public GetBlacklistNumberSuccess(List<String> data) {
		
		this.data = data;
	}

	public GetBlacklistNumberSuccess() {
		
	}
	
	
}
