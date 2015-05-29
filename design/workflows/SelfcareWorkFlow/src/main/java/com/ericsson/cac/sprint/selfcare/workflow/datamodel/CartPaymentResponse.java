package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class CartPaymentResponse implements Serializable{
	private Service[] changedServices;//Required = false,Changed plan
	
	private StatusMessageResponse statusMessageResponse;

	public StatusMessageResponse getStatusMessageResponse() {
		return statusMessageResponse;
	}

	public void setStatusMessageResponse(
			StatusMessageResponse statusMessageResponse) {
		this.statusMessageResponse = statusMessageResponse;
	}
	
	/**
	 * @return the changedServices
	 */
	public Service[] getChangedServices() {
		return changedServices;
	}
	/**
	 * @param changedServices the changedServices to set
	 */
	public void setChangedServices(Service[] changedServices) {
		this.changedServices = changedServices;
	}
	/**
	 * @return the unchangedServices
	 */
	public Service[] getUnchangedServices() {
		return unchangedServices;
	}
	/**
	 * @param unchangedServices the unchangedServices to set
	 */
	public void setUnchangedServices(Service[] unchangedServices) {
		this.unchangedServices = unchangedServices;
	}
	/**
	 * @return the removedServices
	 */
	public Service[] getRemovedServices() {
		return removedServices;
	}
	/**
	 * @param removedServices the removedServices to set
	 */
	public void setRemovedServices(Service[] removedServices) {
		this.removedServices = removedServices;
	}
	/**
	 * @return the total
	 */
	public float getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(float total) {
		this.total = total;
	}
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
	private Service[]unchangedServices;//Required = false,Unchanged services
	private Service[] removedServices;//Required = false,Removed services
	private float total;//Required = true,Total cart charges
	private float charged;//Required = true,Amount charged
	private float balance;//Required = true,Amount remaining
	private String currency;//Required = false,Currency for total charges. If not specified $ would be the default value
	private StatusMessage status;//Required = true,Unchanged services
	private AsyncResponse async;//Required = false,Unchanged services


}
