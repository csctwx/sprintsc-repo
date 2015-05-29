package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Plan {
	@XmlElement
	private String assetId;
	@XmlElement
	private BrandId brandId;
	@XmlElement
	private String titleShort;
	@XmlElement
	private String description;
	@XmlElement
	private String toolTip;
	@XmlElement
	private String planRating;
	@XmlElement
	private String usageRestartTreshold;
	/**
	 * @return the assetId
	 */
	public String getAssetId() {
		return assetId;
	}
	/**
	 * @param assetId the assetId to set
	 */
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	/**
	 * @return the brandId
	 */
	public BrandId getBrandId() {
		return brandId;
	}
	/**
	 * @param brandId the brandId to set
	 */
	public void setBrandId(BrandId brandId) {
		this.brandId = brandId;
	}
	/**
	 * @return the titleShort
	 */
	public String getTitleShort() {
		return titleShort;
	}
	/**
	 * @param titleShort the titleShort to set
	 */
	public void setTitleShort(String titleShort) {
		this.titleShort = titleShort;
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
	/**
	 * @return the toolTip
	 */
	public String getToolTip() {
		return toolTip;
	}
	/**
	 * @param toolTip the toolTip to set
	 */
	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}
	/**
	 * @return the planRating
	 */
	public String getPlanRating() {
		return planRating;
	}
	/**
	 * @param planRating the planRating to set
	 */
	public void setPlanRating(String planRating) {
		this.planRating = planRating;
	}
	/**
	 * @return the usageRestartTreshold
	 */
	public String getUsageRestartTreshold() {
		return usageRestartTreshold;
	}
	/**
	 * @param usageRestartTreshold the usageRestartTreshold to set
	 */
	public void setUsageRestartTreshold(String usageRestartTreshold) {
		this.usageRestartTreshold = usageRestartTreshold;
	}

}
