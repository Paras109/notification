package com.demo.notification.response;

import java.util.List;

public class ElasticsearchSuccess {

	
	private List<String> data;

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

	public ElasticsearchSuccess(List<String> data) {
		super();
		this.data = data;
	}

	public ElasticsearchSuccess() {
		super();
	}
	
}
