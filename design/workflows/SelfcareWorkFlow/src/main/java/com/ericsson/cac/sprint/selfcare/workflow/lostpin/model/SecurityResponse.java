package com.ericsson.cac.sprint.selfcare.workflow.lostpin.model;

import java.util.List;

public class SecurityResponse {

	private List<QuestionInfo> securityQuestionList;
	private String message;

	public List<QuestionInfo> getSecurityQuestionList() {
		return securityQuestionList;
	}

	public SecurityResponse(List<QuestionInfo> questionListDataModel) {
		this.securityQuestionList = questionListDataModel;
	}

	public SecurityResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
