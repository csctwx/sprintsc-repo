package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PhoneDetail {
	
	@XmlElement
	private String phoneName;
	
	@XmlElement
	private String manufacturerName;
	
	@XmlElement
	private String extendedDescription;
	
	@XmlElement
	private String associatedAccessoryId;
	
	@XmlElement
	private boolean isRedVentures;
	
	@XmlElement
	private String disclaimerMini;
	
	@XmlElement
	private String disclaimerFull;
	
	@XmlElementWrapper(name="phoneViewImages")
	@XmlElement(name="phoneViewImage")
	private Picture[] phoneViewImages;
	
	@XmlElementWrapper(name="phoneVariants")
	@XmlElement(name="phoneVariant")
	private PhoneVariant[] phoneVariants;
	
	@XmlElement(name="ReviewStatistics")
	private ReviewStatistics reviewStatistics;
	
	@XmlTransient
	private String sku;
	
	@XmlElement
	private Keywords keywords;
	
	@XmlElement
	private Metas meta;
			
	/**
	 * @return the meta
	 */
	public Metas getMeta() {
		return meta;
	}
	/**
	 * @param meta the meta to set
	 */
	public void setMeta(Metas meta) {
		this.meta = meta;
	}
	/**
	 * @return the keywords
	 */
	public Keywords getKeywords() {
		return keywords;
	}
	/**
	 * @param keywords the keywords to set
	 */
	public void setKeywords(Keywords keywords) {
		this.keywords = keywords;
	}
	public String getPhoneName() {
		return phoneName;
	}
	public void setPhoneName(String phoneName) {
		this.phoneName = phoneName;
	}
	public String getManufacturerName() {
		return manufacturerName;
	}
	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}
	public String getAssociatedAccessoryId() {
		return associatedAccessoryId;
	}
	public void setAssociatedAccessoryId(String associatedAccessoryId) {
		this.associatedAccessoryId = associatedAccessoryId;
	}
	public boolean isRedVentures() {
		return isRedVentures;
	}
	public void setRedVentures(boolean isRedVentures) {
		this.isRedVentures = isRedVentures;
	}
	public Picture[] getPhoneViewImages() {
		return phoneViewImages;
	}
	public void setPhoneViewImages(Picture[] phoneViewImages) {
		this.phoneViewImages = phoneViewImages;
	}
	public PhoneVariant[] getPhoneVariants() {
		return phoneVariants;
	}
	public void setPhoneVariants(PhoneVariant[] phoneVariants) {
		this.phoneVariants = phoneVariants;
	}
	/**
	 * @return the extendedDescription
	 */
	public String getExtendedDescription() {
		return extendedDescription;
	}
	/**
	 * @param extendedDescription the extendedDescription to set
	 */
	public void setExtendedDescription(String extendedDescription) {
		this.extendedDescription = extendedDescription;
	}
	/**
	 * @return the disclaimerMini
	 */
	public String getDisclaimerMini() {
		return disclaimerMini;
	}
	/**
	 * @param disclaimerMini the disclaimerMini to set
	 */
	public void setDisclaimerMini(String disclaimerMini) {
		this.disclaimerMini = disclaimerMini;
	}
	/**
	 * @return the disclaimerFull
	 */
	public String getDisclaimerFull() {
		return disclaimerFull;
	}
	/**
	 * @param disclaimerFull the disclaimerFull to set
	 */
	public void setDisclaimerFull(String disclaimerFull) {
		this.disclaimerFull = disclaimerFull;
	}
	public ReviewStatistics getReviewStatistics() {
		return reviewStatistics;
	}
	public void setReviewStatistics(ReviewStatistics reviewStatistics) {
		this.reviewStatistics = reviewStatistics;
	}
	
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	
}
