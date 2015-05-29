package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class CoverageAreaResponse implements Serializable {
	private String zipCode;//Required = true,Zip code
	private AsyncResponse async;//Required = false, A-sync request info.
	private String npa;
	private String csa;
	private String nxx;
	private int statusmessage;
		
	
	/**
	 * @return
	 */
	public int getStatusmessage() {
		return statusmessage;
	}
	
	/**
	 * @param statusmessage
	 */
	public void setStatusmessage(int statusmessage) {
		this.statusmessage = statusmessage;
	}
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
	public AsyncResponse getAsync() {
		return async;
	}
	/**
	 * @param async the async to set
	 */
	public void setAsync(AsyncResponse async) {
		this.async = async;
	}

	public String getNpa() {
		return npa;
	}

	public void setNpa(String npa) {
		this.npa = npa;
	}

	public String getCsa() {
		return csa;
	}

	public void setCsa(String csa) {
		this.csa = csa;
	}

	public String getNxx() {
		return nxx;
	}

	public void setNxx(String nxx) {
		this.nxx = nxx;
	}


}
