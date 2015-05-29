package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class StatusMessage implements Serializable{
	private Integer status; //Required = true , Values: 0 for success, error code otherwise.
	private String reason; //Required = true ,Error reason (short title)
	private String warning;//Required = false, Critical/Warning message
	private String description;//Required = false, General error message description
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}
	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
	/**
	 * @return the warning
	 */
	public String getWarning() {
		return warning;
	}
	/**
	 * @param warning the warning to set
	 */
	public void setWarning(String warning) {
		this.warning = warning;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
