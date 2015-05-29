/**
 * Sprint Template
 */
package com.ericsson.cac.sprint.selfcare.workflow.commons;

/**
 * HeaderData is used to transfer the objects from one layer to another layer
 * 
 * @author Ericsson
 * @version $Revision: 1.0 $
 */
public class HeaderData {
	private String url;
	private String ipAddress;
	private String device;
	private String sessionInfo;
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}
	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	/**
	 * @return the device
	 */
	public String getDevice() {
		return device;
	}
	/**
	 * @param device the device to set
	 */
	public void setDevice(String device) {
		this.device = device;
	}
	/**
	 * @return the sessionInfo
	 */
	public String getSessionInfo() {
		return sessionInfo;
	}
	/**
	 * @param sessionInfo the sessionInfo to set
	 */
	public void setSessionInfo(String sessionInfo) {
		this.sessionInfo = sessionInfo;
	}

	
}
