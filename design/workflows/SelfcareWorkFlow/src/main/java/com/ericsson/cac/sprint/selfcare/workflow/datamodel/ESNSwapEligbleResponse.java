package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class ESNSwapEligbleResponse implements Serializable {
	private String serialNumber;//Required = true,New device serial number
	private Service[] eligiblePlans;//Required = false,Eligible plans
	private AsyncResponse async;//Required = false,A-sync response info.
	private String lockedInd;
	private StatusMessage statusMessage;
	
	public String getLockedInd() {
		return lockedInd;
	}
	public void setLockedInd(String lockedInd) {
		this.lockedInd = lockedInd;
	}
	public StatusMessage getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(StatusMessage statusMessage) {
		this.statusMessage = statusMessage;
	}
	
	/**
	 * @return the serialNumber
	 */
	public String getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * @return the eligiblePlans
	 */
	public Service[] getEligiblePlans() {
		return eligiblePlans;
	}
	/**
	 * @param eligiblePlans the eligiblePlans to set
	 */
	public void setEligiblePlans(Service[] eligiblePlans) {
		this.eligiblePlans = eligiblePlans;
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
