package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class PaymentResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float charged;
	private float balance;
	private String currency;
	private StatusMessage status;
	private AsyncResponse async;
	/**
	 * @return the charged
	 */
	public float getCharged() {
		return charged;
	}
	/**
	 * @param charged the charged to set
	 */
	public void setCharged(float charged) {
		this.charged = charged;
	}
	/**
	 * @return the balance
	 */
	public float getBalance() {
		return balance;
	}
	/**
	 * @param balance the balance to set
	 */
	public void setBalance(float balance) {
		this.balance = balance;
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
	 * @return the status
	 */
	public StatusMessage getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(StatusMessage status) {
		this.status = status;
	}
	/**
	 * @return the async
	 */
	public AsyncResponse getAsync() {
		return async;
	}
	/**
	 * @param async the async to set
	 */
	public void setAsync(AsyncResponse async) {
		this.async = async;
	}
	
	
}
