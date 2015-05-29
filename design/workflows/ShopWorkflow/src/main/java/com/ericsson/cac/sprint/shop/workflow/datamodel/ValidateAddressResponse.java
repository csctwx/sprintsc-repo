package com.ericsson.cac.sprint.shop.workflow.datamodel;

public class ValidateAddressResponse {
	
	private String addressType;
	
	private Integer confidence;
	
	private Boolean addressExist;
	
	private Integer status;
	
	private String description;

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public Integer getConfidence() {
		return confidence;
	}

	public void setConfidence(Integer confidence) {
		this.confidence = confidence;
	}

	public Boolean getAddressExist() {
		return addressExist;
	}

	public void setAddressExist(Boolean addressExist) {
		this.addressExist = addressExist;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
