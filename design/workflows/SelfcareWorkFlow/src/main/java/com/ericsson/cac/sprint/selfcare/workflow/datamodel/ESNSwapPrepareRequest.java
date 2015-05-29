package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class ESNSwapPrepareRequest implements Serializable {
	private String serialNumber;//Required = true,New device serial number
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
	 * @return the iccid
	 */
	public String getIccid() {
		return iccid;
	}
	/**
	 * @param iccid the iccid to set
	 */
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	/**
	 * @return the plan
	 */
	public Service getPlan() {
		return plan;
	}
	/**
	 * @param plan the plan to set
	 */
	public void setPlan(Service plan) {
		this.plan = plan;
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
	private String iccid;//Required = false,ICCID
	private Service plan;//Required = false,Selected plan
	private AsyncRequest async;//Required = false,A-sync request info


}
