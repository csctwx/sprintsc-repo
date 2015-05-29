package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;
import java.util.Date;

public class TextHistory implements Serializable{
	private Date date;//Required = true,Order date
	private String from;//Required = false,Incoming call
	private String to;//Required = false,Outgoing call
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}
	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}
	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}
	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}

}
