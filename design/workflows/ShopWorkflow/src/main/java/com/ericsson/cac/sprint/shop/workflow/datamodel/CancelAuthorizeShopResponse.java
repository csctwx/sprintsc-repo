package com.ericsson.cac.sprint.shop.workflow.datamodel;

public class CancelAuthorizeShopResponse {
	private Boolean cancelled;
	private Integer status;
	private String description;
	public Boolean getCancelled() {
		return cancelled;
	}
	public void setCancelled(Boolean cancelled) {
		this.cancelled = cancelled;
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
