package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class CartResponse implements Serializable {
	private Service[] changedServices;//Required = false,Changed plan
	private Service[] unchangedServices;//Required = false,Unchanged services
	private Service[] removedServices;//Required = false,Removed services
	private float total;//Required = true,Total cart charges
	private String currency;//Required = false,Currency for total charges. If not specified $ would be the default value.
	private AsyncResponse async;//Required = false,A-sync request info.
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
