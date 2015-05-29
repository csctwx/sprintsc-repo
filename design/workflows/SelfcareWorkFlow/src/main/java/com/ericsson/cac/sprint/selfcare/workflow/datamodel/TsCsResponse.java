package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class TsCsResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TsCs tscs;
	private AsyncResponse async;
	/**
	 * @return the tscs
	 */
	public TsCs getTscs() {
		return tscs;
	}
	/**
	 * @param tscs the tscs to set
	 */
	public void setTscs(TsCs tscs) {
		this.tscs = tscs;
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
