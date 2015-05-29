package com.ericsson.cac.sprint.selfcare.workflow.lostpin.model;

import com.ericsson.cac.sprint.selfcare.workflow.commons.HeaderData;

public abstract class LostPinRequest {

	private HeaderData headerData;
	private String mdn;

	public LostPinRequest(HeaderData headerData, String mdn) {
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
