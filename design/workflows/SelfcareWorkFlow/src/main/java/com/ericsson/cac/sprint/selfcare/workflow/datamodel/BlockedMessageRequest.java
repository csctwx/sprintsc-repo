package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class BlockedMessageRequest implements Serializable {
	private Recipient recipients;//Required = true,Blocked senders
	private AsyncRequest async;//Required = false,A-sync request info.
	/**
	 * @return the recipients
	 */
	public Recipient getRecipients() {
		return recipients;
	}
	/**
	 * @param recipients the recipients to set
	 */
	public void setRecipients(Recipient recipients) {
		this.recipients = recipients;
	}
	/**
	 * @return the async
	 */
	public AsyncRequest getAsync() {
		return async;
	}
	/**
	 * @param async the async to set
	 */
	public void setAsync(AsyncRequest async) {
		this.async = async;
	}

}
