package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class StatusMessageResponse implements Serializable {
	private  StatusMessage statusMessage;//Required = true,Status message
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
	private AsyncResponse async;//Required = false,A-sync response info. 


}
