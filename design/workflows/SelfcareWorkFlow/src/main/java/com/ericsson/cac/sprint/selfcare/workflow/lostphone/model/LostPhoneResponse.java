package com.ericsson.cac.sprint.selfcare.workflow.lostphone.model;

public class LostPhoneResponse {

	private String message;

	public LostPhoneResponse(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "LostPhoneResponse [message=" + message + "]";
	}

}
