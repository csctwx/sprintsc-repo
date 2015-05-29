package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class TextHistoryResponse implements Serializable {
	private TextHistory[] textHistory;// Required = true,Text history

	private StatusMessageResponse statusMessageResponse;

	public StatusMessageResponse getStatusMessageResponse() {
		return statusMessageResponse;
	}

	public void setStatusMessageResponse(
			StatusMessageResponse statusMessageResponse) {
		this.statusMessageResponse = statusMessageResponse;
	}

	/**
	 * @return the async
	 */
	public AsyncResponse getAsync() {
		return async;
	}

	/**
	 * @return the textHistory
	 */
	public TextHistory[] getTextHistory() {
		return textHistory;
	}

	/**
	 * @param textHistory
	 *            the textHistory to set
	 */
	public void setTextHistory(TextHistory[] textHistory) {
		this.textHistory = textHistory;
	}

	/**
	 * @param async
	 *            the async to set
	 */
	public void setAsync(AsyncResponse async) {
		this.async = async;
	}

	private AsyncResponse async;// Required = false,A-sync response info

}
