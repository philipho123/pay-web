package com.woniu.pay.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.woniu.pay.base.SingleKeyIdLongPojo;

@Entity
@Table(name = "INTERFACE_TOP_ORDER")
public class AgencyOrder extends SingleKeyIdLongPojo {

	private static final long serialVersionUID = 4307398004867673796L;

	@Column(name = "N_SYS_ID")
	private Integer merchantId;
	
	@Column(name = "N_SYS_GAME_ID")
	private Integer merchantGameId;

	@Column(name = "N_SYS_AID")
	private Long accountId;

	@Column(name = "S_SYS_TRAN_ID")
	private String merchantTranId;

	@Column(name = "S_SYS_ITEM_INFO")
	private String itemInfo;
	
	@Column(name = "N_SYS_POINTS")
	private Integer merchantPoints;

	@Column(name = "N_SYS_AMOUNT")
	private BigDecimal merchantAmount;

	@Column(name = "S_SYS_CURRENCY")
	private String merchantCurrency;

	@Column(name = "S_SYS_TRAN_STATE")
	private String merchantTranState;

	@Column(name = "N_PAYMENT_ID")
	private Integer paymentId;

	@Column(name = "S_PAYMENT_TYPE")
	private String paymentType;

	@Column(name = "S_PAYMENT_ORDER_NO")
	private String paymentOrderNo;

	@Column(name = "N_PAYMENT_AMOUNT")
	private BigDecimal paymentAmount;

	@Column(name = "S_PAYMENT_CURRENCY")
	private String paymentCurrency;

	@Column(name = "S_PAYMENT_STATE")
	private String paymentState;

	@Column(name = "N_CREATOR_IP")
	private Long clientIp;

	@Column(name = "S_STATE", nullable = false, length = 1)
	private String state;

	@Column(name = "D_CREATE")
	private transient Date createDate;

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getMerchantTranId() {
		return merchantTranId;
	}

	public void setMerchantTranId(String merchantTranId) {
		this.merchantTranId = merchantTranId;
	}

	public String getItemInfo() {
		return itemInfo;
	}

	public void setItemInfo(String itemInfo) {
		this.itemInfo = itemInfo;
	}

	public BigDecimal getMerchantAmount() {
		return merchantAmount;
	}

	public Integer getMerchantGameId() {
		return merchantGameId;
	}

	public void setMerchantGameId(Integer merchantGameId) {
		this.merchantGameId = merchantGameId;
	}

	public Integer getMerchantPoints() {
		return merchantPoints;
	}

	public void setMerchantPoints(Integer merchantPoints) {
		this.merchantPoints = merchantPoints;
	}

	public void setMerchantAmount(BigDecimal merchantAmount) {
		this.merchantAmount = merchantAmount;
	}

	public String getMerchantCurrency() {
		return merchantCurrency;
	}

	public void setMerchantCurrency(String merchantCurrency) {
		this.merchantCurrency = merchantCurrency;
	}

	public String getMerchantTranState() {
		return merchantTranState;
	}

	public void setMerchantTranState(String merchantTranState) {
		this.merchantTranState = merchantTranState;
	}

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPaymentOrderNo() {
		return paymentOrderNo;
	}

	public void setPaymentOrderNo(String paymentOrderNo) {
		this.paymentOrderNo = paymentOrderNo;
	}

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getPaymentCurrency() {
		return paymentCurrency;
	}

	public void setPaymentCurrency(String paymentCurrency) {
		this.paymentCurrency = paymentCurrency;
	}

	public String getPaymentState() {
		return paymentState;
	}

	public void setPaymentState(String paymentState) {
		this.paymentState = paymentState;
	}

	public Long getClientIp() {
		return clientIp;
	}

	public void setClientIp(Long clientIp) {
		this.clientIp = clientIp;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
