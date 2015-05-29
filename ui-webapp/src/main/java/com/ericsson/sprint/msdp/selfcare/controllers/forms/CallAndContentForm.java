package com.ericsson.sprint.msdp.selfcare.controllers.forms;

import java.util.ArrayList;
import java.util.List;

import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberSetting;

public class CallAndContentForm {
	private List<SubscriberSetting> subscriberSettings = new ArrayList<SubscriberSetting>();

	public List<SubscriberSetting> getSubscriberSettings() {
		return subscriberSettings;
	}

	public void setSubscriberSettings(List<SubscriberSetting> subscriberSettings) {
		this.subscriberSettings = subscriberSettings;
	}

}
