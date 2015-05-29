package com.ericsson.cac.sprint.shop.workflow.datamodel;

public class SubmitOrderResponse {
	
	private String fastOrderkey;	
	private Integer status;	
	private String description;
	/**
	 * @return the fastOrderkey
	 */
	public String getFastOrderkey() {
		return fastOrderkey;
	}
	/**
	 * @param fastOrderkey the fastOrderkey to set
	 */
	public void setFastOrderkey(String fastOrderkey) {
		this.fastOrderkey = fastOrderkey;
	}
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
