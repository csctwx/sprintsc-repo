package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class AddOnsRequest implements Serializable {
	private UserContext userContext;//Required = true,User context
	/**
	 * @return the userContext
	 */
	public UserContext getUserContext() {
		return userContext;
	}
	/**
	 * @param userContext the userContext to set
	 */
	public void setUserContext(UserContext userContext) {
		this.userContext = userContext;
	}
	/**
	 * @return the serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}
	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
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
	private String serviceType;//Required = true,Service type: data, voice
	private AsyncRequest async;//Required = false,A-sync request info


}
