package com.ericsson.cac.sprint.selfcare.workflow.lostphone.model;

import com.ericsson.cac.sprint.selfcare.workflow.commons.HeaderData;

public abstract class LostPhoneRequest {

	private HeaderData headerData;
	private String mdn;

	public LostPhoneRequest(HeaderData headerData, String mdn) {
		super();
		this.headerData = headerData;
		this.mdn = mdn;
	}

	public HeaderData getHeaderData() {
		return headerData;
	}

	public String getMdn() {
		return mdn;
	}

}
