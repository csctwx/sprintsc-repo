package com.ericsson.cac.sprint.selfcare.workflow.lostpin.model;

import com.ericsson.cac.sprint.selfcare.workflow.commons.HeaderData;

public class BrandCodeRequest extends LostPinRequest{

	private String brandCode;
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public BrandCodeRequest(HeaderData headerData, String mdn) {
		super(headerData, mdn);
	}

}
