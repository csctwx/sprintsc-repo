package com.ericsson.cac.sprint.selfcare.workflow.lostphone.model;

import com.ericsson.cac.sprint.selfcare.workflow.commons.HeaderData;

public class RestoreRequest extends LostPhoneRequest {

	public RestoreRequest(HeaderData headerData, String mdn) {
		super(headerData, mdn);
	}

}
