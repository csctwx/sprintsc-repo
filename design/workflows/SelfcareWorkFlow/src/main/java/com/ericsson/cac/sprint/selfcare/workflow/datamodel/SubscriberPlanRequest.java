package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class SubscriberPlanRequest implements Serializable{
	private SubscriberAccount subscriberAccount;//Required = true,Subscriber account
	/**
	 * @return the subscriberAccount
	 */
	public SubscriberAccount getSubscriberAccount() {
		return subscriberAccount;
	}
	/**
	 * @param subscriberAccount the subscriberAccount to set
	 */
	public void setSubscriberAccount(SubscriberAccount subscriberAccount) {
		this.subscriberAccount = subscriberAccount;
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
	private AsyncRequest async;//Required = false,A-sync response info


}
