package com.ericsson.cac.sprint.selfcare.workflow.lostpin.model;

import com.ericsson.cac.sprint.selfcare.workflow.commons.HeaderData;

public class LoginAttemptRequest extends LostPinRequest{
	
	public String getNumberOfFailedAttempts() {
		return numberOfFailedAttempts;
	}

	public void setNumberOfFailedAttempts(String numberOfFailedAttempts) {
		this.numberOfFailedAttempts = numberOfFailedAttempts;
	}

	private String numberOfFailedAttempts;

	public LoginAttemptRequest(HeaderData headerData, String mdn) {
		super(headerData, mdn);
	}

}
