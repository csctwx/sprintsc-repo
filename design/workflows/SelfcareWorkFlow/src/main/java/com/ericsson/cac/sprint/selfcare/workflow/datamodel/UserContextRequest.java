package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class UserContextRequest implements Serializable {
	private UserContext userContext;//Required = true,User context
	private AsyncRequest async;//Required = false,A-sync request info. 
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
