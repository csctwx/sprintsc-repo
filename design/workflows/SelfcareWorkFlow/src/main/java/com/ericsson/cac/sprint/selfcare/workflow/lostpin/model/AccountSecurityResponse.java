package com.ericsson.cac.sprint.selfcare.workflow.lostpin.model;

public class AccountSecurityResponse extends LostPinResponse {

	private boolean isAnswerValid;
	private static final String SUCCESS = "SUCCESS";

	public AccountSecurityResponse(String message) {
		super(message);
	}

	public boolean isAnswerValid() {
		return isAnswerValid;
	}

	public AccountSecurityResponse(boolean isAnswerValid) {
		super(SUCCESS);
		this.isAnswerValid = isAnswerValid;
	}
}

