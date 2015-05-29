package com.ericsson.cac.sprint.selfcare.workflow.lostpin.model;

import javax.xml.datatype.XMLGregorianCalendar;

public class LoginAttemptResponse extends LostPinResponse {
	
	private static final String SUCCESS = "SUCCESS";

	protected XMLGregorianCalendar lockedUntil;

	public XMLGregorianCalendar getLockedUntil() {
		return lockedUntil;
	}

	public LoginAttemptResponse(String message) {
		super(message);
	}

	public LoginAttemptResponse(XMLGregorianCalendar lockedUntil) {
		super(SUCCESS);
		this.lockedUntil = lockedUntil;
	}

}
