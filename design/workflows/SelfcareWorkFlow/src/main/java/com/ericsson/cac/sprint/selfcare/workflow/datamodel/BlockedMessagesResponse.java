package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class BlockedMessagesResponse implements Serializable {
	private Recipient[] recipients;// Required = true,Blocked senders

	private StatusMessageResponse statusMessageResponse;

	public StatusMessageResponse getStatusMessageResponse() {
		return statusMessageResponse;
	}

	public void setStatusMessageResponse(
			StatusMessageResponse statusMessageResponse) {
		this.statusMessageResponse = statusMessageResponse;
	}

	/**
	 * @return the recipients
	 */
	public Recipient[] getRecipients() {
		return recipients;
	}

	/**
	 * @param recipients
	 *            the recipients to set
	 */
	public void setRecipients(Recipient[] recipients) {
		this.recipients = recipients;
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

	private AsyncResponse async;// Required = false,A-sync request info.

}
