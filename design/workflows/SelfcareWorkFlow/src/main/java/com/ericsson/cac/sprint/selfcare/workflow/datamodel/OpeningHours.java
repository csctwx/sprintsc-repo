package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class OpeningHours implements Serializable {
	private String description;//Required = false,Opening hours description
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
	/**
	 * @return the hours
	 */
	public OpeningHour[] getHours() {
		return hours;
	}
	/**
	 * @param hours the hours to set
	 */
	public void setHours(OpeningHour[] hours) {
		this.hours = hours;
	}
	private OpeningHour[] hours;//Required = false,Details per day


}
