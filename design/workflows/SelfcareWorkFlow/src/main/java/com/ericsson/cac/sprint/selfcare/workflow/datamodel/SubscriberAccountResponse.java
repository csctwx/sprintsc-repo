package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class SubscriberAccountResponse implements Serializable {

	private SubscriberAccount subscriberAccount;// Required = true,Subscriber
												// account
	private AsyncResponse async;// Required = false,A-sync response info.
	private StatusMessageResponse statusMessageResponse;

	public StatusMessageResponse getStatusMessageResponse() {
		return statusMessageResponse;
	}

	public void setStatusMessageResponse(
			StatusMessageResponse statusMessageResponse) {
		this.statusMessageResponse = statusMessageResponse;
	}

	/**
	 * @return the subscriberAccount
	 */
	public SubscriberAccount getSubscriberAccount() {
		return subscriberAccount;
	}

	/**
	 * @param subscriberAccount
	 *            the subscriberAccount to set
	 */
	public void setSubscriberAccount(SubscriberAccount subscriberAccount) {
		this.subscriberAccount = subscriberAccount;
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
}
