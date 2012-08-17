package com.woniu.pay.base.payment;



/**
 * @version 1.0
 */
public class SnailPayOrder extends BasePayOrder {

	private static final long serialVersionUID = 6052542916960283752L;

	private String clientUrl;
	
	private String cancelUrl;
	
	private String snailLogoUrl;
	
	private  boolean limitCreditCard;
	
	public boolean isLimitCreditCard() {
		return limitCreditCard;
	}

	public void setLimitCreditCard(boolean limitCreditCard) {
		this.limitCreditCard = limitCreditCard;
	}


	public String getClientUrl() {
		return clientUrl;
	}

	public void setClientUrl(String clientUrl) {
		this.clientUrl = clientUrl;
	}

	public String getCancelUrl() {
		return cancelUrl;
	}

	public void setCancelUrl(String cancelUrl) {
		this.cancelUrl = cancelUrl;
	}

	public String getSnailLogoUrl() {
		return snailLogoUrl;
	}

	public void setSnailLogoUrl(String snailLogoUrl) {
		this.snailLogoUrl = snailLogoUrl;
	}
	
}

