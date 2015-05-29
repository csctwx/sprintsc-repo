package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class RefillVoucherRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] refillPins;
	private AsyncRequest async;
	/**
	 * @return the refillPins
	 */
	public String[] getRefillPins() {
		return refillPins;
	}
	/**
	 * @param refillPins the refillPins to set
	 */
	public void setRefillPins(String[] refillPins) {
		this.refillPins = refillPins;
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
