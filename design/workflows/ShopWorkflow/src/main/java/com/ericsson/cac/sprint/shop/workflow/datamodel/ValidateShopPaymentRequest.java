package com.ericsson.cac.sprint.shop.workflow.datamodel;

public class ValidateShopPaymentRequest {
	private AddressInfo  billingInfo;
	private PaymentInfo paymentInfo;
	private String orderId;
	private String transactionId;
	
	public AddressInfo getBillingInfo() {
		return billingInfo;
	}
	public void setBillingInfo(AddressInfo billingInfo) {
		this.billingInfo = billingInfo;
	}
	public PaymentInfo getPaymentInfo() {
		return paymentInfo;
	}
	public void setPaymentInfo(PaymentInfo paymentInfo) {
		this.paymentInfo = paymentInfo;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
}
