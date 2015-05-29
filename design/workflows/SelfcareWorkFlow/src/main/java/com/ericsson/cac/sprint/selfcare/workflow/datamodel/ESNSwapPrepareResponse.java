package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class ESNSwapPrepareResponse implements Serializable{
	private Device oldDevice;//Required = true,oldDevice
	private String newDevice;//Required = false,ICCID
	private Service[] changedServices;//Required = false,Changed plan
	/**
	 * @return the oldDevice
	 */
	public Device getOldDevice() {
		return oldDevice;
	}
	/**
	 * @param oldDevice the oldDevice to set
	 */
	public void setOldDevice(Device oldDevice) {
		this.oldDevice = oldDevice;
	}
	/**
	 * @return the newDevice
	 */
	public String getNewDevice() {
		return newDevice;
	}
	/**
	 * @param newDevice the newDevice to set
	 */
	public void setNewDevice(String newDevice) {
		this.newDevice = newDevice;
	}
	/**
	 * @return the changedServices
	 */
	public Service[] getChangedServices() {
		return changedServices;
	}
	/**
	 * @param changedServices the changedServices to set
	 */
	public void setChangedServices(Service[] changedServices) {
		this.changedServices = changedServices;
	}
	/**
	 * @return the unchangedServices
	 */
	public Service[] getUnchangedServices() {
		return unchangedServices;
	}
	/**
	 * @param unchangedServices the unchangedServices to set
	 */
	public void setUnchangedServices(Service[] unchangedServices) {
		this.unchangedServices = unchangedServices;
	}
	/**
	 * @return the removedServices
	 */
	public Service[] getRemovedServices() {
		return removedServices;
	}
	/**
	 * @param removedServices the removedServices to set
	 */
	public void setRemovedServices(Service[] removedServices) {
		this.removedServices = removedServices;
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
	private Service[] unchangedServices;//Required = false,Unchanged services
	private Service[] removedServices;//Required = false,Removed services
	private AsyncResponse async;//Required = false,A-sync response info


}
