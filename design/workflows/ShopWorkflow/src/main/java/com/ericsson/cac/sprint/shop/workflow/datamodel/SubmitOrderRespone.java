package com.ericsson.cac.sprint.shop.workflow.datamodel;

public class SubmitOrderRespone {
	
	private String fastOrderkey;
	private Integer status;
	private String description;
	
	public String getFastOrderkey() {
		return fastOrderkey;
	}
	public void setFastOrderkey(String fastOrderkey) {
		this.fastOrderkey = fastOrderkey;
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
