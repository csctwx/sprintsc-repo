package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Phone {
	
	@XmlElement
	private String phoneName;
	
	@XmlElement
	private String externalUrl;
	
	@XmlElement
	private String manufacturerName;
	
	@XmlElement
	private String shortDescription;
	
	@XmlElementWrapper(name="filters")
	@XmlElement(name="feature")
	private String[] filter;
	
	@XmlElement
	private String associatedAccessoryId;	
	
	
	@XmlElement
	private String generalAvailabilityDate;
	
	@XmlElement
	private String genieOrder;
	
	@XmlElement
	private String phoneOS;

	@XmlElement
	private String disclaimerMini;

	@XmlElement
	private String phoneType;

	@XmlElementWrapper(name="phoneVariants")	
	@XmlElement(name="phoneVariant")
	private PhoneVariant[] phoneVariants;
	
	@XmlElement(name="ReviewStatistics")
	private ReviewStatistics reviewStatistics;
	
	@XmlElementWrapper(name="compareImages")
	@XmlElement(name="compareImage")
	private Picture[] compareImage;
	
	@XmlTransient
	private String sku;
	
	public Picture[] getCompareImage() {
		return compareImage;
	}
	public void setCompareImage(Picture[] compareImage) {
		this.compareImage = compareImage;
	}
	public String getPhoneType() {
		return phoneType;
	}
	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}
	public String getPhoneOS() {
		return phoneOS;
	}
	public void setPhoneOS(String phoneOS) {
		this.phoneOS = phoneOS;
	}
	
	public String getGeneralAvailabilityDate() {
		return generalAvailabilityDate;
	}
	public void setGeneralAvailabilityDate(String generalAvailabilityDate) {
		this.generalAvailabilityDate = generalAvailabilityDate;
	}
	public String getManufacturerName() {
		return manufacturerName;
	}
	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}
	public String getDisclaimerMini() {
		return disclaimerMini;
	}
	public void setDisclaimerMini(String disclaimerMini) {
		this.disclaimerMini = disclaimerMini;
	}
	public String getAssociatedAccessoryId() {
		return associatedAccessoryId;
	}
	public void setAssociatedAccessoryId(String associatedAccessoryId) {
		this.associatedAccessoryId = associatedAccessoryId;
	}
	public String getPhoneName() {
		return phoneName;
	}
	public void setPhoneName(String phoneName) {
		this.phoneName = phoneName;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getGenieOrder() {
		return genieOrder;
	}
	public void setGenieOrder(String genieOrder) {
		this.genieOrder = genieOrder;
	}
	public PhoneVariant[] getPhoneVariants() {
		return phoneVariants;
	}
	public void setPhoneVariants(PhoneVariant[] phoneVariants) {
		this.phoneVariants = phoneVariants;
	}
	
	/**
	 * @return the filter
	 */
	public String[] getFilter() {
		return filter;
	}
	/**
	 * @param filter the filter to set
	 */
	public void setFilter(String[] filter) {
		this.filter = filter;
	}
	/**
	 * @return the reviewStatistics
	 */
	public ReviewStatistics getReviewStatistics() {
		return reviewStatistics;
	}
	/**
	 * @param reviewStatistics the reviewStatistics to set
	 */
	public void setReviewStatistics(ReviewStatistics reviewStatistics) {
		this.reviewStatistics = reviewStatistics;
	}
	/**
	 * @return the sku
	 */
	public String getSku() {
		return sku;
	}
	/**
	 * @param sku the sku to set
	 */
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getExternalUrl() {
		return externalUrl;
	}
	public void setExternalUrl(String externalUrl) {
		this.externalUrl = externalUrl;
	}	
	
}