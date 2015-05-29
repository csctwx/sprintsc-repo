package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class DeviceInsuranceResponse implements Serializable{
	private DeviceInsurance deviceInsurance;//Required = true,insurance
	private AsyncResponse async;//Required = false,A-sync response info.
	private StatusMessageResponse statusMessageResponse;
	
	public StatusMessageResponse getStatusMessageResponse() {
		return statusMessageResponse;
	}
	
	public void setStatusMessageResponse(StatusMessageResponse statusMessageResponse) {
		this.statusMessageResponse = statusMessageResponse;
	}
	/**
	 * @return the deviceInsurance
	 */
	public DeviceInsurance getDeviceInsurance() {
		return deviceInsurance;
	}
	/**
	 * @param deviceInsurance the deviceInsurance to set
	 */
	public void setDeviceInsurance(DeviceInsurance deviceInsurance) {
		this.deviceInsurance = deviceInsurance;
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
