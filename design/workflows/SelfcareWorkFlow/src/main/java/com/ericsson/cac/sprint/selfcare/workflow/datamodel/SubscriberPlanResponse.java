package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class SubscriberPlanResponse implements Serializable {
	private SubscriberPlan subscriberPlan;// Required = true,Subscriber plan

	private StatusMessageResponse statusMessageResponse;

	public StatusMessageResponse getStatusMessageResponse() {
		return statusMessageResponse;
	}

	public void setStatusMessageResponse(
			StatusMessageResponse statusMessageResponse) {
		this.statusMessageResponse = statusMessageResponse;
	}

	/**
	 * @return the subscriberPlan
	 */
	public SubscriberPlan getSubscriberPlan() {
		return subscriberPlan;
	}

	/**
	 * @param subscriberPlan
	 *            the subscriberPlan to set
	 */
	public void setSubscriberPlan(SubscriberPlan subscriberPlan) {
		this.subscriberPlan = subscriberPlan;
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
