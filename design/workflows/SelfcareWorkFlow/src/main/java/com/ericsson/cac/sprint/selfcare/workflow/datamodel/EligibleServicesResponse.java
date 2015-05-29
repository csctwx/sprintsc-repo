package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class EligibleServicesResponse implements Serializable{
	private EligibleServices eligibleServices;//Required = true,Eligible services
	
	private StatusMessageResponse statusMessageResponse;

	public StatusMessageResponse getStatusMessageResponse() {
		return statusMessageResponse;
	}

	public void setStatusMessageResponse(
			StatusMessageResponse statusMessageResponse) {
		this.statusMessageResponse = statusMessageResponse;
	}
	
	/**
	 * @return the eligibleServices
	 */
	public EligibleServices getEligibleServices() {
		return eligibleServices;
	}
	/**
	 * @param eligibleServices the eligibleServices to set
	 */
	public void setEligibleServices(EligibleServices eligibleServices) {
		this.eligibleServices = eligibleServices;
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
	private AsyncResponse async;//Required = false,A-sync response info


}
