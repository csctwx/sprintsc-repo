package com.ericsson.cac.sprint.selfcare.workflow.lostpin.model;

public class LostPinResponse {

	private String message;

	public LostPinResponse(String message) {
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
