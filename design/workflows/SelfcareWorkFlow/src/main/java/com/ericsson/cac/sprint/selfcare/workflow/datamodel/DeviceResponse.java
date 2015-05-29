package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class DeviceResponse implements Serializable {
	private Device device;//Required = true,Device info
	private StatusMessageResponse statusMessageResponse;
	/**
	 * @return the device
	 */
	public Device getDevice() {
		return device;
	}
	/**
	 * @param device the device to set
	 */
	public void setDevice(Device device) {
		this.device = device;
	}
	public StatusMessageResponse getStatusMessageResponse() {
		return statusMessageResponse;
	}
	public void setStatusMessageResponse(StatusMessageResponse statusMessageResponse) {
		this.statusMessageResponse = statusMessageResponse;
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
