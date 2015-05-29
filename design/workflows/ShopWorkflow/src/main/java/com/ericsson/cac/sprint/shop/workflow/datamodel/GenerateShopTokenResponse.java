package com.ericsson.cac.sprint.shop.workflow.datamodel;

public class GenerateShopTokenResponse {
	private String token;
	private Integer status;
	private String description;
	
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
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
