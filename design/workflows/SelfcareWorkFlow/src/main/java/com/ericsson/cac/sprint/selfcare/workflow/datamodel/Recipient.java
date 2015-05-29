package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class Recipient implements Serializable {
	private String sender;//Required = true,Array of phone numbers or email addresses

	/**
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * @param sender the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}

}
