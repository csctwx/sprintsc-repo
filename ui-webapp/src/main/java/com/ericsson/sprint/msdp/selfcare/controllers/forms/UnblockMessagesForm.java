package com.ericsson.sprint.msdp.selfcare.controllers.forms;

import java.util.ArrayList;
import java.util.List;

import com.ericsson.cac.sprint.selfcare.workflow.datamodel.Recipient;

public class UnblockMessagesForm {
	private List<Recipient> recipients = new ArrayList<Recipient>();

	public List<Recipient> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<Recipient> recipients) {
		this.recipients = recipients;
	}
}
