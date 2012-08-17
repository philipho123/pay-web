package com.woniu.pay.base.payment;
import static com.woniu.pay.constants.PaymentConstants.PAYMENT_RETURN_NOTIFY_ERROR;
import static com.woniu.pay.constants.PaymentConstants.PAYMENT_RETURN_NOTIFY_FAILED;
import static com.woniu.pay.constants.PaymentConstants.PAYMENT_RETURN_NOTIFY_REVERSAL;
import static com.woniu.pay.constants.PaymentConstants.PAYMENT_RETURN_NOTIFY_SUCCESS;
import static com.woniu.pay.constants.TopConstants.TOP_ORDER_OPERATOR_FAILED;
import static com.woniu.pay.constants.TopConstants.TOP_ORDER_OPERATOR_PENDING;
import static com.woniu.pay.constants.TopConstants.TOP_ORDER_OPERATOR_REVERSAL;
import static com.woniu.pay.constants.TopConstants.TOP_ORDER_OPERATOR_SUCCEESS;

public class PaymentOrder extends BasePayOrder {

	private static final long serialVersionUID = 6939097630101958085L;

	private String payState;
	
	private String errMessage;
	
	/**
	 * 支付币种（国际标准符号）
	 * */
	private String payCurrency;
		
	/**
	 * 支付金额
	 * */
	private Double payAmount;
	
	/**
	 * 支付金额
	 * */
	private Double fee;
	/**
	 * 支付人邮箱
	 * */
	private String payerEmail;
	
	/**
	 * 支付人在支付平台的帐号或者id
	 * */
	private String payerIDs;
	

	/**
	 * 支付平台交易的备注说明
	 * */
	private String notes;
	
	
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public String getPayerEmail() {
		return payerEmail;
	}

	public void setPayerEmail(String payerEmail) {
		this.payerEmail = payerEmail;
	}

	public String getPayerIDs() {
		return payerIDs;
	}

	public void setPayerIDs(String payerIDs) {
		this.payerIDs = payerIDs;
	}


	public String getPayCurrency() {
		return payCurrency;
	}

	public void setPayCurrency(String payCurrency) {
		this.payCurrency = payCurrency;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public String getPayState() {
		return payState;
	}

	public void setPayState(String payState) {
		this.payState = payState;
	}

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
	
	public boolean isNotifyFailed(){
		return PAYMENT_RETURN_NOTIFY_FAILED.equals(getPayState());
	}
	
	public boolean isNotifySuccess(){
		return PAYMENT_RETURN_NOTIFY_SUCCESS.equals(getPayState());
	}
	
	public boolean isNotifyReversal(){
		return PAYMENT_RETURN_NOTIFY_REVERSAL.equals(getPayState());
	}
	
	public boolean isNotifyError(){
		return PAYMENT_RETURN_NOTIFY_ERROR.equals(getPayState());
	}
	
	public String getNotifyType(){
		if(isNotifyFailed()){
			return TOP_ORDER_OPERATOR_FAILED;
		}
		if(isNotifySuccess()){
			return TOP_ORDER_OPERATOR_SUCCEESS;
		}
		if(isNotifyReversal()){
			return TOP_ORDER_OPERATOR_REVERSAL;
		}
		if(isNotifyError()){
			return PAYMENT_RETURN_NOTIFY_ERROR;
		}
		return TOP_ORDER_OPERATOR_PENDING;
	}
	
}
