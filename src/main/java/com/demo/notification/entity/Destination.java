package com.demo.notification.entity;

import java.util.List;

public class Destination {

	
//	
//	private List<InnerDestination>innerdestination;
//
//	public List<InnerDestination> getInnerdestination() {
//		return innerdestination;
//	}
//
//	public void setInnerdestination(List<InnerDestination> innerdestination) {
//		this.innerdestination = innerdestination;
//	}
//
//	public Destination(List<InnerDestination> innerdestination) {
//		super();
//		this.innerdestination = innerdestination;
//	}
//
//	public Destination() {
//		super();
//	}
	

	private List<String> msisdn;
	private String correlationid;
	public List<String> getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(List<String> msisdn) {
		this.msisdn = msisdn;
	}
	public String getCorrelationid() {
		return correlationid;
	}
	public void setCorrelationid(String correlationid) {
		this.correlationid = correlationid;
	}
	public Destination(List<String> msisdn, String correlationid) {
		super();
		this.msisdn = msisdn;
		this.correlationid = correlationid;
	}
	public Destination() {
		super();
	}
	

	
	
}
