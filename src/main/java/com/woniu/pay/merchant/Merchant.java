package com.woniu.pay.merchant;

public class Merchant {

	private Integer merchantId;
	private Integer merchantGameId;
	private String merchantPwd;

	public Merchant() {
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public Integer getMerchantGameId() {
		return merchantGameId;
	}

	public void setMerchantGameId(Integer merchantGameId) {
		this.merchantGameId = merchantGameId;
	}

	public String getMerchantPwd() {
		return merchantPwd;
	}

	public void setMerchantPwd(String merchantPwd) {
		this.merchantPwd = merchantPwd;
	}

}
