package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class SecretQuestionsResponse implements Serializable {
	private SecretQuestions secretQuestions;//Required = true,Secret questions
	
	private StatusMessageResponse statusMessageResponse;

	public StatusMessageResponse getStatusMessageResponse() {
		return statusMessageResponse;
	}

	public void setStatusMessageResponse(
			StatusMessageResponse statusMessageResponse) {
		this.statusMessageResponse = statusMessageResponse;
	}
	
	/**
	 * @return the secretQuestions
	 */
	public SecretQuestions getSecretQuestions() {
		return secretQuestions;
	}
	/**
	 * @param secretQuestions the secretQuestions to set
	 */
	public void setSecretQuestions(SecretQuestions secretQuestions) {
		this.secretQuestions = secretQuestions;
	}
	/**
	 * @return the async
	 */
	public AsyncResponse getAsync() {
		return async;
	}
	/**
	 * @param async the async to set
	 */
	public void setAsync(AsyncResponse async) {
		this.async = async;
	}
	private AsyncResponse async;//Required = false,A-sync request info


}
