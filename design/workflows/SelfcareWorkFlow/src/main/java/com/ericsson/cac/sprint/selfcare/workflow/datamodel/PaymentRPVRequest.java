package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class PaymentRPVRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float amount;
	private String currency;
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
