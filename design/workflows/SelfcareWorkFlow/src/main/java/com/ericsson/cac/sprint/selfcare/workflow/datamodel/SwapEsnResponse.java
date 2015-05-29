
package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class SwapEsnResponse implements Serializable{
	private String msisdn;//Required = true,MDN
	private String action;//Required = true,UI needs to start next workflow based on this value
	private StatusMessage status;//Required = false,Execution status message
	private AsyncResponse async;//Required = false,A-sync response info.
	private StatusMessageResponse statusMessageResponse;
	
	public StatusMessageResponse getStatusMessageResponse() {
		return statusMessageResponse;
	}
	public void setStatusMessageResponse(StatusMessageResponse statusMessageResponse) {
		this.statusMessageResponse = statusMessageResponse;
	}
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
	 * @return the action
	 */
	public String getAction() {
		return action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
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
