/**
 * 
 */
package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

/**
 * @author emascol
 *
 */
public class PlanUsageResponse {
	
	private StatusMessageResponse statusMessageResponse;
	private Service talkUsage;
	private Service textUsage;
	private Service dataUsage;
	
	
	/**
	 * @return the statusMessageResponse
	 */
	public StatusMessageResponse getStatusMessageResponse() {
		return statusMessageResponse;
	}
	/**
	 * @param statusMessageResponse the statusMessageResponse to set
	 */
	public void setStatusMessageResponse(StatusMessageResponse statusMessageResponse) {
		this.statusMessageResponse = statusMessageResponse;
	}
	/**
	 * @return the talkUsage
	 */
	public Service getTalkUsage() {
		return talkUsage;
	}
	/**
	 * @param talkUsage the talkUsage to set
	 */
	public void setTalkUsage(Service talkUsage) {
		this.talkUsage = talkUsage;
	}
	/**
	 * @return the textUsage
	 */
	public Service getTextUsage() {
		return textUsage;
	}
	/**
	 * @param textUsage the textUsage to set
	 */
	public void setTextUsage(Service textUsage) {
		this.textUsage = textUsage;
	}
	/**
	 * @return the dataUsage
	 */
	public Service getDataUsage() {
		return dataUsage;
	}
	/**
	 * @param dataUsage the dataUsage to set
	 */
	public void setDataUsage(Service dataUsage) {
		this.dataUsage = dataUsage;
	}
	
	
	

}
