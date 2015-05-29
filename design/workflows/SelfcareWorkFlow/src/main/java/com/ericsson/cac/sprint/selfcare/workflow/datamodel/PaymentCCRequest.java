package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class PaymentCCRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float amount;
	private String currency;
	private CreditCard creditCard;
	private Address billingAddress;
	private Boolean autoPay;
	private AsyncRequest async;
	/**
	 * @return the amount
	 */
	public float getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(float amount) {
		this.amount = amount;
	}
	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	/**
	 * @return the creditCard
	 */
	public CreditCard getCreditCard() {
		return creditCard;
	}
	/**
	 * @param creditCard the creditCard to set
	 */
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	/**
	 * @return the billingAddress
	 */
	public Address getBillingAddress() {
		return billingAddress;
	}
	/**
	 * @param billingAddress the billingAddress to set
	 */
	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}
	/**
	 * @return the autoPay
	 */
	public Boolean getAutoPay() {
		return autoPay;
	}
	/**
	 * @param autoPay the autoPay to set
	 */
	public void setAutoPay(Boolean autoPay) {
		this.autoPay = autoPay;
	}
	/**
	 * @return the async
	 */
	public AsyncRequest getAsync() {
		return async;
	}
	/**
	 * @param async the async to set
	 */
	public void setAsync(AsyncRequest async) {
		this.async = async;
	}
	
	
}
