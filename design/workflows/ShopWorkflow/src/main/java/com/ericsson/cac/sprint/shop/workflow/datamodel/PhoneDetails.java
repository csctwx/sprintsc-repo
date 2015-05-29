package com.ericsson.cac.sprint.shop.workflow.datamodel;

import java.awt.Image;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PhoneDetails {
	@XmlElement
	private String assetId;
	@XmlElement
	private String phoneName;
	@XmlElement
	private String ownerAssetId;
	@XmlElement
	private String ownerId;
	@XmlElement
	private String serviceId;
	@XmlElement
	private String extendedDescription;
	@XmlElement
	private String minimumAdvPrice;
	@XmlElement
	private Boolean available;
	@XmlElement
	private String shortDescription;
	@XmlElement
	private Boolean isPreOwned;
	@XmlElement
	private Image pictures;
	@XmlElement
	private String generalAvailabilityDate;
	@XmlElement
	private Boolean defaultVariantId;
	@XmlElement
	private MemoryVariant memoryVariant;
	@XmlElement
	private Boolean featureBar;
	@XmlElement
	private Boolean featureSlider;
	@XmlElement
	private Boolean featureFlip;
	@XmlElement
	private Boolean featureTouchScreen;
	@XmlElement
	private Boolean featureQwerty;
	@XmlElement
	private Boolean featureCamera;
	@XmlElement
	private Boolean featureFrontFacingCamera;
	@XmlElement
	private Boolean featureGPS;
	@XmlElement
	private Boolean feature4GWiMax;
	@XmlElement
	private Boolean featureWifi;
	@XmlElement
	private Boolean featureBluetooth;
	@XmlElement
	private Boolean featureSpeakerPhone;
	@XmlElement
	private Boolean feature3G;
	@XmlElement
	private Boolean feature4GLTE;
	@XmlElement
	private Boolean feature4G;
	@XmlElement
	private Boolean featureText;
	@XmlElement
	private Boolean featureVideo;
	@XmlElement
	private Boolean featureHotSpot;
	@XmlElement
	private Boolean featureEmail;
	@XmlElement
	private Boolean featureHtmlBrowser;
	@XmlElement
	private Boolean featureUleCertified;
	@XmlElement
	private Boolean featureMusic;
	@XmlElement
	private Boolean featureVisualVoiceMail;
	@XmlElement
	private String disclaimerMini;
	@XmlElement
	private String disclaimerFull;
	@XmlElement
	private String footerLegal;
	@XmlElement
	private String colorVariant;
	@XmlElement
	private String colorId;
	@XmlElement
	private String gradientColor;
	@XmlElement
	private String associatedAccessoryId;
	
	public String getAssetId() {
		return assetId;
	}
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	public String getPhoneName() {
		return phoneName;
	}
	public void setPhoneName(String phoneName) {
		this.phoneName = phoneName;
	}
	public String getOwnerAssetId() {
		return ownerAssetId;
	}
	public void setOwnerAssetId(String ownerAssetId) {
		this.ownerAssetId = ownerAssetId;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getExtendedDescription() {
		return extendedDescription;
	}
	public void setExtendedDescription(String extendedDescription) {
		this.extendedDescription = extendedDescription;
	}
	public String getMinimumAdvPrice() {
		return minimumAdvPrice;
	}
	public void setMinimumAdvPrice(String minimumAdvPrice) {
		this.minimumAdvPrice = minimumAdvPrice;
	}
	public Boolean getAvailable() {
		return available;
	}
	public void setAvailable(Boolean available) {
		this.available = available;
	}
	public Boolean getFeature4G() {
		return feature4G;
	}
	public void setFeature4G(Boolean feature4g) {
		feature4G = feature4g;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public Boolean getIsPreOwned() {
		return isPreOwned;
	}
	public void setIsPreOwned(Boolean isPreOwned) {
		this.isPreOwned = isPreOwned;
	}
	public Image getPictures() {
		return pictures;
	}
	public void setPictures(Image pictures) {
		this.pictures = pictures;
	}
	public String getGeneralAvailabilityDate() {
		return generalAvailabilityDate;
	}
	public void setGeneralAvailabilityDate(String generalAvailabilityDate) {
		this.generalAvailabilityDate = generalAvailabilityDate;
	}
	public Boolean getDefaultVariantId() {
		return defaultVariantId;
	}
	public void setDefaultVariantId(Boolean defaultVariantId) {
		this.defaultVariantId = defaultVariantId;
	}
	public Boolean getFeatureBar() {
		return featureBar;
	}
	public void setFeatureBar(Boolean featureBar) {
		this.featureBar = featureBar;
	}
	public Boolean getFeatureSlider() {
		return featureSlider;
	}
	public void setFeatureSlider(Boolean featureSlider) {
		this.featureSlider = featureSlider;
	}
	public Boolean getFeatureFlip() {
		return featureFlip;
	}
	public void setFeatureFlip(Boolean featureFlip) {
		this.featureFlip = featureFlip;
	}
	public Boolean getFeatureTouchScreen() {
		return featureTouchScreen;
	}
	public void setFeatureTouchScreen(Boolean featureTouchScreen) {
		this.featureTouchScreen = featureTouchScreen;
	}
	public Boolean getFeatureQwerty() {
		return featureQwerty;
	}
	public void setFeatureQwerty(Boolean featureQwerty) {
		this.featureQwerty = featureQwerty;
	}
	public Boolean getFeatureCamera() {
		return featureCamera;
	}
	public void setFeatureCamera(Boolean featureCamera) {
		this.featureCamera = featureCamera;
	}
	public Boolean getFeatureFrontFacingCamera() {
		return featureFrontFacingCamera;
	}
	public void setFeatureFrontFacingCamera(Boolean featureFrontFacingCamera) {
		this.featureFrontFacingCamera = featureFrontFacingCamera;
	}
	public Boolean getFeatureGPS() {
		return featureGPS;
	}
	public void setFeatureGPS(Boolean featureGPS) {
		this.featureGPS = featureGPS;
	}
	public Boolean getFeature4GWiMax() {
		return feature4GWiMax;
	}
	public void setFeature4GWiMax(Boolean feature4gWiMax) {
		feature4GWiMax = feature4gWiMax;
	}
	public Boolean getFeatureWifi() {
		return featureWifi;
	}
	public void setFeatureWifi(Boolean featureWifi) {
		this.featureWifi = featureWifi;
	}
	public Boolean getFeatureBluetooth() {
		return featureBluetooth;
	}
	public void setFeatureBluetooth(Boolean featureBluetooth) {
		this.featureBluetooth = featureBluetooth;
	}
	public Boolean getFeatureSpeakerPhone() {
		return featureSpeakerPhone;
	}
	public void setFeatureSpeakerPhone(Boolean featureSpeakerPhone) {
		this.featureSpeakerPhone = featureSpeakerPhone;
	}
	public Boolean getFeature3G() {
		return feature3G;
	}
	public void setFeature3G(Boolean feature3g) {
		feature3G = feature3g;
	}
	public Boolean getFeature4GLTE() {
		return feature4GLTE;
	}
	public void setFeature4GLTE(Boolean feature4glte) {
		feature4GLTE = feature4glte;
	}
	public Boolean getFeatureText() {
		return featureText;
	}
	public void setFeatureText(Boolean featureText) {
		this.featureText = featureText;
	}
	public Boolean getFeatureVideo() {
		return featureVideo;
	}
	public void setFeatureVideo(Boolean featureVideo) {
		this.featureVideo = featureVideo;
	}
	public Boolean getFeatureHotSpot() {
		return featureHotSpot;
	}
	public void setFeatureHotSpot(Boolean featureHotSpot) {
		this.featureHotSpot = featureHotSpot;
	}
	public Boolean getFeatureEmail() {
		return featureEmail;
	}
	public void setFeatureEmail(Boolean featureEmail) {
		this.featureEmail = featureEmail;
	}
	public Boolean getFeatureHtmlBrowser() {
		return featureHtmlBrowser;
	}
	public void setFeatureHtmlBrowser(Boolean featureHtmlBrowser) {
		this.featureHtmlBrowser = featureHtmlBrowser;
	}
	public Boolean getFeatureUleCertified() {
		return featureUleCertified;
	}
	public void setFeatureUleCertified(Boolean featureUleCertified) {
		this.featureUleCertified = featureUleCertified;
	}
	public Boolean getFeatureMusic() {
		return featureMusic;
	}
	public void setFeatureMusic(Boolean featureMusic) {
		this.featureMusic = featureMusic;
	}
	public Boolean getFeatureVisualVoiceMail() {
		return featureVisualVoiceMail;
	}
	public void setFeatureVisualVoiceMail(Boolean featureVisualVoiceMail) {
		this.featureVisualVoiceMail = featureVisualVoiceMail;
	}
	public String getDisclaimerMini() {
		return disclaimerMini;
	}
	public void setDisclaimerMini(String disclaimerMini) {
		this.disclaimerMini = disclaimerMini;
	}
	public String getDisclaimerFull() {
		return disclaimerFull;
	}
	public void setDisclaimerFull(String disclaimerFull) {
		this.disclaimerFull = disclaimerFull;
	}
	public String getFooterLegal() {
		return footerLegal;
	}
	public void setFooterLegal(String footerLegal) {
		this.footerLegal = footerLegal;
	}
	public String getColorVariant() {
		return colorVariant;
	}
	public void setColorVariant(String colorVariant) {
		this.colorVariant = colorVariant;
	}
	public String getColorId() {
		return colorId;
	}
	public void setColorId(String colorId) {
		this.colorId = colorId;
	}
	public String getGradientColor() {
		return gradientColor;
	}
	public void setGradientColor(String gradientColor) {
		this.gradientColor = gradientColor;
	}
	public String getAssociatedAccessoryId() {
		return associatedAccessoryId;
	}
	public void setAssociatedAccessoryId(String associatedAccessoryId) {
		this.associatedAccessoryId = associatedAccessoryId;
	}
	public MemoryVariant getMemoryVariant(){
		return memoryVariant;
	}
	public void setMemoryVariant(MemoryVariant memoryVariant) {
		this.memoryVariant = memoryVariant;
	}

	
	
}
