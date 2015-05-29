package com.ericsson.cac.sprint.shop.workflow.datamodel;

public class EquipmentAvailabilityRequest {
	
	private String brandId;
	private boolean ispreorder;
	private String modelId;
	
	/**
	 * @return the brandId
	 */
	public String getBrandId() {
		return brandId;
	}
	/**
	 * @param brandId the brandId to set
	 */
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}	
	/**
	 * @return the modelId
	 */
	public String getModelId() {
		return modelId;
	}
	/**
	 * @param modelId the modelId to set
	 */
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	/**
	 * @return the ispreorder
	 */
	public boolean isIspreorder() {
		return ispreorder;
	}
	/**
	 * @param ispreorder the ispreorder to set
	 */
	public void setIspreorder(boolean ispreorder) {
		this.ispreorder = ispreorder;
	}
	
	
	
}
