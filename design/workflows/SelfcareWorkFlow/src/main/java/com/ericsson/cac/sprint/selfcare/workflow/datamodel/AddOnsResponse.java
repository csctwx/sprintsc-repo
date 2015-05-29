package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class AddOnsResponse  implements Serializable{
	private Service[] current;//Required = false,Current add-ons
	private Service[] eligible;//Required = false,Eligible add-ons
	
	private StatusMessageResponse statusMessageResponse;

	public StatusMessageResponse getStatusMessageResponse() {
		return statusMessageResponse;
	}

	public void setStatusMessageResponse(
			StatusMessageResponse statusMessageResponse) {
		this.statusMessageResponse = statusMessageResponse;
	}
	
	/**
	 * @return the current
	 */
	public Service[] getCurrent() {
		return current;
	}
	/**
	 * @param current the current to set
	 */
	public void setCurrent(Service[] current) {
		this.current = current;
	}
	/**
	 * @return the eligible
	 */
	public Service[] getEligible() {
		return eligible;
	}
	/**
	 * @param eligible the eligible to set
	 */
	public void setEligible(Service[] eligible) {
		this.eligible = eligible;
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
	private AsyncResponse async;//Required = false,A-sync request info


}
