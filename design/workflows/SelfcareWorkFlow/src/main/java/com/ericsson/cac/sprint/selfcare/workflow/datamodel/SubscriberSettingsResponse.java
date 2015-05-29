package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class SubscriberSettingsResponse implements Serializable {
	private SubscriberSetting[] subscriberSettings;//Required = true,Subscriber settings
	/**
	 * @return the subscriberSettings
	 */
	public SubscriberSetting[] getSubscriberSettings() {
		return subscriberSettings;
	}
	/**
	 * @param subscriberSettings the subscriberSettings to set
	 */
	public void setSubscriberSettings(SubscriberSetting[] subscriberSettings) {
		this.subscriberSettings = subscriberSettings;
	}
	/**
	 * @return the statusMessage
	 */
	public StatusMessage getStatusMessage() {
		return statusMessage;
	}
	/**
	 * @param statusMessage the statusMessage to set
	 */
	public void setStatusMessage(StatusMessage statusMessage) {
		this.statusMessage = statusMessage;
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
	private StatusMessage statusMessage;//Required = false,Status message
	private AsyncResponse async;//Required = false,A-sync response info.


}
