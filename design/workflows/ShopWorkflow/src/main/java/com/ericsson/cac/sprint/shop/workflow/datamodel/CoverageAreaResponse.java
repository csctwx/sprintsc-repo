package com.ericsson.cac.sprint.shop.workflow.datamodel;

public class CoverageAreaResponse {
	
	private String zipcode;
	private Boolean covered;
	private Integer status;
	private String description;
	/**
	 * @return the zipcode
	 */
	public String getZipcode() {
		return zipcode;
	}
	/**
	 * @param zipcode the zipcode to set
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	/**
	 * @return the covered
	 */
	public Boolean getCovered() {
		return covered;
	}
	/**
	 * @param covered the covered to set
	 */
	public void setCovered(Boolean covered) {
		this.covered = covered;
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
