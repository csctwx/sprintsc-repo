package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class SecretQuestions implements Serializable {
	private String[] questions;// Required = true,Secret questions

	/**
	 * @return the questions
	 */
	public String[] getQuestions() {
		return questions;
	}

	/**
	 * @param questions
	 *            the questions to set
	 */
	public void setQuestions(String[] questions) {
		this.questions = questions;
	}

	private String[] questionCodes;

	public String[] getQuestionCodes() {
		return questionCodes;
	}

	public void setQuestionCodes(String[] questionCodes) {
		this.questionCodes = questionCodes;
	}

}
