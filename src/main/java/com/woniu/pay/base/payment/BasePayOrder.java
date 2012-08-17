package com.woniu.pay.base.payment;


import java.io.Serializable;

/**
 * @version 1.0
 */
public abstract class BasePayOrder implements Serializable{
	private static final long serialVersionUID = 9128114729655908123L;


	/**
	 * 蜗牛订单号
	 * */
	protected String snailOrderNo;
	
	
	/**
	 * 蜗牛订单号
	 * */
	protected String serviceName;
	/**
	 * 玩家帐号
	 * */
	protected String userAccount;
	
	/**
	 * 玩家帐号ID
	 * */
	protected String userId;
	
	/**
	 * 购买人信息 名字
	 * */
	protected String givenName;
	
	/**
	 * 购买人信息 姓氏
	 * */
	protected String surnName;
	/**
	 * 购买人信息 邮件
	 * */
	protected String useremail;
	
	/**
	 * 支付平台订单号
	 * */
	protected String paymentOrderNo;
	
	/**
	 * 支付币种（国际标准符号）
	 * */
	protected String currency;
		
	/**
	 * 支付金额
	 * */
	protected Double amount;
	
	/**
	 * 商品名称
	 * */
	protected String itemName;
	
	
	/**
	 * 商品编码
	 * */
	protected String itemCode;
	
	/**
	 * 支付金额
	 * */
	protected Double price;
	
	/**
	 * 商品数量
	 * */
	protected int itemNumber;
	
	/**
	 * 支付平台定义的支付类型
	 * */
	protected String payType;
	
	protected String serverUrl;
	
	/**
	 * 游戏点数
	 * */
	protected int points;
	
	/**
	 * 游戏服务器ID
	 * */
	protected int serverId;
	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getSnailOrderNo() {
		return snailOrderNo;
	}

	public void setSnailOrderNo(String snailOrderNo) {
		this.snailOrderNo = snailOrderNo;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getUseremail() {
		return useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getSurnName() {
		return surnName;
	}

	public void setSurnName(String surnName) {
		this.surnName = surnName;
	}

	public String getPaymentOrderNo() {
		return paymentOrderNo;
	}

	public void setPaymentOrderNo(String paymentOrderNo) {
		this.paymentOrderNo = paymentOrderNo;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public int getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

}