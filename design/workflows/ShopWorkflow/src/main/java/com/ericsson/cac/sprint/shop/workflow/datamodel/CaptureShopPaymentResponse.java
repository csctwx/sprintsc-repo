package com.ericsson.cac.sprint.shop.workflow.datamodel;

public class CaptureShopPaymentResponse {
	private Boolean captured;
	private Integer status;
	private String description;
	
	public Boolean getCaptured() {
		return captured;
	}
	public void setCaptured(Boolean captured) {
		this.captured = captured;
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
