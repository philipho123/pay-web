package com.woniu.pay.support;


import java.util.Collections;
import java.util.Map;

public class PaymentForm {
	
	private String payUrl;
	private String targetId;
	private Map<String, String> members = null;
	
	@SuppressWarnings("unchecked")
	public PaymentForm(){
		members = (Map<String, String>) Collections.EMPTY_MAP;
	}

	public PaymentForm(Map<String, String> members, String payUrl, String targetId) {
		this.members = (Map<String, String>) Collections.unmodifiableMap(members);
		this.payUrl = payUrl;
		this.targetId = targetId;
	}

	public String getPayUrl() {
		return payUrl;
	}

	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}

	public Map<String, String> getMembers() {
		return members;
	}

	public void setMembers(Map<String, String> members) {
		this.members = members;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

}

