package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Accessory {
	
	@XmlElement
	 String assetId;
	@XmlElement
	 BrandId brandId;
	@XmlElement
	 Boolean available;
	@XmlElement
	 Boolean available4preorder;
	@XmlElement
	 String generalAvailabilityDate;
	@XmlElement
	 Boolean defaultVariantId;
	@XmlElement
	 String assetDescription;
	@XmlElement
	 String label;
	
	
	
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
	 * @return the available
	 */
	public Boolean getAvailable() {
		return available;
	}
	/**
	 * @param available the available to set
	 */
	public void setAvailable(Boolean available) {
		this.available = available;
	}
	/**
	 * @return the available4preorder
	 */
	public Boolean getAvailable4preorder() {
		return available4preorder;
	}
	/**
	 * @param available4preorder the available4preorder to set
	 */
	public void setAvailable4preorder(Boolean available4preorder) {
		this.available4preorder = available4preorder;
	}
	/**
	 * @return the generalAvailabilityDate
	 */
	public String getGeneralAvailabilityDate() {
		return generalAvailabilityDate;
	}
	/**
	 * @param generalAvailabilityDate the generalAvailabilityDate to set
	 */
	public void setGeneralAvailabilityDate(String generalAvailabilityDate) {
		this.generalAvailabilityDate = generalAvailabilityDate;
	}
	/**
	 * @return the defaultVariantId
	 */
	public Boolean getDefaultVariantId() {
		return defaultVariantId;
	}
	/**
	 * @param defaultVariantId the defaultVariantId to set
	 */
	public void setDefaultVariantId(Boolean defaultVariantId) {
		this.defaultVariantId = defaultVariantId;
	}
	/**
	 * @return the assetDescription
	 */
	public String getAssetDescription() {
		return assetDescription;
	}
	/**
	 * @param assetDescription the assetDescription to set
	 */
	public void setAssetDescription(String assetDescription) {
		this.assetDescription = assetDescription;
	}
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}	
	
}