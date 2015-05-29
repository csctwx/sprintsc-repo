package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class ChangeNumberResponse implements Serializable{
	private String msisdn;//Required = true,New phone number. Must be null in case of error
	/**
	 * @return the msisdn
	 */
	public String getMsisdn() {
		return msisdn;
	}
	/**
	 * @param msisdn the msisdn to set
	 */
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	/**
	 * @return the statusMessage
	 */
	public StatusMessage getStatusMessage() {
		return statusMessage;
	}
	/**
	 * @param statusMessage the statusMessage to set
	 */
	public void setStatusMessage(StatusMessage statusMessage) {
		this.statusMessage = statusMessage;
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
	private StatusMessage statusMessage;//Required = true,Status message
	private AsyncResponse async;//Required = false, A-sync request info.
	
	private StatusMessageResponse statusMessageResponse;
	
	public StatusMessageResponse getStatusMessageResponse() {
		return statusMessageResponse;
	}
	public void setStatusMessageResponse(StatusMessageResponse statusMessageResponse) {
		this.statusMessageResponse = statusMessageResponse;
	}

}
