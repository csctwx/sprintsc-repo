package com.ericsson.cac.sprint.selfcare.workflow.lostpin.model;

import com.ericsson.cac.sprint.selfcare.workflow.commons.HeaderData;

public class AccountSecurityRequest extends LostPinRequest{
	
	private String answer;
	private String question;

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public AccountSecurityRequest(HeaderData headerData, String mdn) {
		super(headerData, mdn);
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

}
