package com.ericsson.cac.sprint.selfcare.workflow.lostpin.model;

import com.ericsson.cac.sprint.selfcare.workflow.commons.HeaderData;

public class ResendSecurityRequest extends LostPinRequest{

	public ResendSecurityRequest(HeaderData headerData, String mdn) {
		super(headerData, mdn);
	}

}
