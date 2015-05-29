package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class CoverageAreaRequest implements Serializable{
	private String zipCode;//Required = true,Zip code
	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}
	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
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
	private AsyncRequest async;//Required = false, A-sync request info. 


}
