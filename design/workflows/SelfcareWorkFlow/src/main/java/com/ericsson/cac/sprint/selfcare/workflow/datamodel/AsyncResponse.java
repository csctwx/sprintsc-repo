package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class AsyncResponse  implements Serializable  {
	private Boolean async; //Required = true,Values: true means that the request is executed asynchronously
	private String requestId;//Required = false,RequestId to be used to retrieve execution data/status in subsequent call
	/**
	 * @return the async
	 */
	public Boolean getAsync() {
		return async;
	}
	/**
	 * @param async the async to set
	 */
	public void setAsync(Boolean async) {
		this.async = async;
	}
	/**
	 * @return the requestId
	 */
	public String getRequestId() {
		return requestId;
	}
	/**
	 * @param requestId the requestId to set
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	private String status;//Required = false,A-sync execution status
}
