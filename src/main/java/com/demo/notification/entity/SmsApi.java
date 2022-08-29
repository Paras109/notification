package com.demo.notification.entity;

import java.util.List;

public class SmsApi {

	
	private String deliverychannel;
	
	private Channels channels;
	
	private List<Destination> destination;
	
	
	

	public String getDeliverychannel() {
		return deliverychannel;
	}

	public void setDeliverychannel(String deliverychannel) {
		this.deliverychannel = deliverychannel;
	}

	public Channels getChannels() {
		return channels;
	}

	public void setChannels(Channels channels) {
		this.channels = channels;
	}

	public List<Destination> getDestination() {
		return destination;
	}

	public void setDestination(List<Destination> destination) {
		this.destination = destination;
	}

	public SmsApi(String deliverychannel, Channels channels, List<Destination> destination) {
		super();
		this.deliverychannel = deliverychannel;
		this.channels = channels;
		this.destination = destination;
	}

	public SmsApi() {
		super();
	}

	

	
}
