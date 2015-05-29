package com.ericsson.cac.sprint.shop.workflow.datamodel;

public class GetFeatureRequest {
	
	private String phoneId;
	private String brandId;

	

	/**
	 * @return the phoneId
	 */
	public String getPhoneId() {
		return phoneId;
	}

	/**
	 * @param phoneId the phoneId to set
	 */
	public void setPhoneId(String phoneId) {
		this.phoneId = phoneId;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	
	
}	
