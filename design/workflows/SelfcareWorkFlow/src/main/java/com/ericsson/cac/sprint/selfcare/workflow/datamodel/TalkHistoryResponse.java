package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class TalkHistoryResponse implements Serializable {
	private TalkHistory[] talkHistory;// Required = true,Talk history

	private StatusMessageResponse statusMessageResponse;

	public StatusMessageResponse getStatusMessageResponse() {
		return statusMessageResponse;
	}

	public void setStatusMessageResponse(
			StatusMessageResponse statusMessageResponse) {
		this.statusMessageResponse = statusMessageResponse;
	}

	public TalkHistory[] getTalkHistory() {
		return talkHistory;
	}

	public void setTalkHistory(TalkHistory[] talkHistory) {
		this.talkHistory = talkHistory;
	}

	/**
	 * @return the async
	 */
	public AsyncResponse getAsync() {
		return async;
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