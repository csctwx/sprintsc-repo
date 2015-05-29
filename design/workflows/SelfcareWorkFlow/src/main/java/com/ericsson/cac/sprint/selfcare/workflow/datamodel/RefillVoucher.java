package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class RefillVoucher implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pin;
	private float amount;
	private String currency;
	private StatusMessage status;
	/**
	 * @return the pin
	 */
	public String getPin() {
		return pin;
	}
	/**
	 * @param pin the pin to set
	 */
	public void setPin(String pin) {
		this.pin = pin;
	}
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
	
	
}
