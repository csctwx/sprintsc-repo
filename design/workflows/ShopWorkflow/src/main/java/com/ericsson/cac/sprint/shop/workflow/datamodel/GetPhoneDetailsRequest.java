package com.ericsson.cac.sprint.shop.workflow.datamodel;

public class GetPhoneDetailsRequest {
	
	private String phoneId;
	
	private String brandId;
	
	
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	
	public String getPhoneId() {
		return phoneId;
	}
	public void setPhoneId(String phoneId) {
		this.phoneId = phoneId;
	}
	
	
}
