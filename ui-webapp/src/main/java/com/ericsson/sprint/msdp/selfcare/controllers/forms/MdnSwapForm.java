package com.ericsson.sprint.msdp.selfcare.controllers.forms;

import com.ericsson.cac.sprint.selfcare.workflow.datamodel.CoverageAreaResponse;

public class MdnSwapForm {
	private String zipCode;
	private CoverageAreaResponse coverageAreaResponse;

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public CoverageAreaResponse getCoverageAreaResponse() {
		return coverageAreaResponse;
	}

	public void setCoverageAreaResponse(CoverageAreaResponse coverageAreaResponse) {
		this.coverageAreaResponse = coverageAreaResponse;
	}
}
