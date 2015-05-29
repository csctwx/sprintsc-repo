package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class OpeningHour implements Serializable {
	private String day; //Required = true,Day of the week
	private Integer opening; //Required = false,Opening hour in 24 hours format.
	private Integer closing; //Required = false,Closing hour in 24 hours format
	/**
	 * @return the day
	 */
	public String getDay() {
		return day;
	}
	/**
	 * @param day the day to set
	 */
	public void setDay(String day) {
		this.day = day;
	}
	/**
	 * @return the opening
	 */
	public Integer getOpening() {
		return opening;
	}
	/**
	 * @param opening the opening to set
	 */
	public void setOpening(Integer opening) {
		this.opening = opening;
	}
	/**
	 * @return the closing
	 */
	public Integer getClosing() {
		return closing;
	}
	/**
	 * @param closing the closing to set
	 */
	public void setClosing(Integer closing) {
		this.closing = closing;
	}


}
