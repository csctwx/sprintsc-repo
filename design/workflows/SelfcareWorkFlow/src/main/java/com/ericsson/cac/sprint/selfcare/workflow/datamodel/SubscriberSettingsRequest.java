package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class SubscriberSettingsRequest implements Serializable{
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
	private AsyncRequest async;//Required = false, A-sync request info.


}
