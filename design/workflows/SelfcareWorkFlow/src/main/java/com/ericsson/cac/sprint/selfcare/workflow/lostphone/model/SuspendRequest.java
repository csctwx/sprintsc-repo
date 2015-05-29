package com.ericsson.cac.sprint.selfcare.workflow.lostphone.model;

import com.ericsson.cac.sprint.selfcare.workflow.commons.HeaderData;

public class SuspendRequest extends LostPhoneRequest{

	public SuspendRequest(HeaderData headerData, String mdn) {
		super(headerData, mdn);
	}

}
