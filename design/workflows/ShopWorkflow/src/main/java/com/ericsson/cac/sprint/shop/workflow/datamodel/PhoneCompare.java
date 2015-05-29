package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD) 
public class PhoneCompare {
	@XmlElement
	private String  assetId;
	@XmlElement
	private String  phoneName;
	@XmlElement
	private String  minimumAdvPrice;
	@XmlElement
	private	String	picture	;
	@XmlElement
	private	String	compareItemOS;
	@XmlElement
	private	String	compareItemDisplay;
	@XmlElement
	private	String	compareItemCamera;
	@XmlElement
	private	String	compareItemWifi;
	@XmlElement
	private	String	compareItem4G;
	@XmlElement
	private	String	compareItemHotspot;
	@XmlElement
	private	String	compareItemQWERTYKeyboard;
	@XmlElement
	private	String	compareItemWebBrowser;
	@XmlElement
	private	String	compareItemFlashPlayer;
	@XmlElement
	private	String	compareItemEmail;
	@XmlElement
	private	String	compareItemVideo;
	@XmlElement
	private	String	compareItemMusicPlayer;
	@XmlElement
	private	String	compareItemGPS;
	@XmlElement
	private	String	compareItemSpeakerphone;
	@XmlElement
	private	String	compareItemMemory;
	@XmlElement
	private	String	compareItemProcessor;
	@XmlElement
	private	String	compareItemCalendar;
	@XmlElement
	private	String	compareItemVisualVoicemail;
	@XmlElement
	private	String	compareItem3G;
	@XmlElement
	private	String	compareItemBluetooth;
	@XmlElement
	private	String	featureMemory;
	@XmlElement
	private	Boolean	featureBar;
	@XmlElement
	private	Boolean	featureSlider;
	@XmlElement
	private	Boolean	featureFlip;
	@XmlElement
	private	Boolean	featureTouchScreen;
	@XmlElement
	private	Boolean	featureQwerty;
	@XmlElement
	private	Boolean	featureCamera;
	@XmlElement
	private	Boolean	featureFrontFacingCamera;
	@XmlElement
	private	Boolean	featureGPS;
	@XmlElement
	private	Boolean	feature4GWiMax;
	@XmlElement
	private	Boolean	featureWifi;
	@XmlElement
	private	Boolean	featureBluetooth;
	@XmlElement
	private	Boolean	featureSpeakerPhone;
	@XmlElement
	private	Boolean	feature3G;
	@XmlElement
	private	Boolean	feature4GLTE;
	@XmlElement
	private	Boolean	feature4G;
	@XmlElement
	private	Boolean	featureText;
	@XmlElement
	private	Boolean	featureVideo;
	@XmlElement
	private	Boolean	featureHotSpot;
	@XmlElement
	private	Boolean	featureEmail;
	@XmlElement
	private	Boolean	featureHtmlBrowser;
	@XmlElement
	private	Boolean	featureUleCertified;
	@XmlElement
	private	Boolean	featureMusic;
	@XmlElement
	private	Boolean	featureVisualVoiceMail;
	@XmlElement
	private	Boolean	isPremium;
	@XmlElement
	private String  synonym;
	
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
	public String getMinimumAdvPrice() {
		return minimumAdvPrice;
	}
	public void setMinimumAdvPrice(String minimumAdvPrice) {
		this.minimumAdvPrice = minimumAdvPrice;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getCompareItemOS() {
		return compareItemOS;
	}
	public void setCompareItemOS(String compareItemOS) {
		this.compareItemOS = compareItemOS;
	}
	public String getCompareItemDisplay() {
		return compareItemDisplay;
	}
	public void setCompareItemDisplay(String compareItemDisplay) {
		this.compareItemDisplay = compareItemDisplay;
	}
	public String getCompareItemCamera() {
		return compareItemCamera;
	}
	public void setCompareItemCamera(String compareItemCamera) {
		this.compareItemCamera = compareItemCamera;
	}
	public String getCompareItemWifi() {
		return compareItemWifi;
	}
	public void setCompareItemWifi(String compareItemWifi) {
		this.compareItemWifi = compareItemWifi;
	}
	public String getCompareItem4G() {
		return compareItem4G;
	}
	public void setCompareItem4G(String compareItem4G) {
		this.compareItem4G = compareItem4G;
	}
	public String getCompareItemHotspot() {
		return compareItemHotspot;
	}
	public void setCompareItemHotspot(String compareItemHotspot) {
		this.compareItemHotspot = compareItemHotspot;
	}
	public String getCompareItemQWERTYKeyboard() {
		return compareItemQWERTYKeyboard;
	}
	public void setCompareItemQWERTYKeyboard(String compareItemQWERTYKeyboard) {
		this.compareItemQWERTYKeyboard = compareItemQWERTYKeyboard;
	}
	public String getCompareItemWebBrowser() {
		return compareItemWebBrowser;
	}
	public void setCompareItemWebBrowser(String compareItemWebBrowser) {
		this.compareItemWebBrowser = compareItemWebBrowser;
	}
	public String getCompareItemFlashPlayer() {
		return compareItemFlashPlayer;
	}
	public void setCompareItemFlashPlayer(String compareItemFlashPlayer) {
		this.compareItemFlashPlayer = compareItemFlashPlayer;
	}
	public String getCompareItemEmail() {
		return compareItemEmail;
	}
	public void setCompareItemEmail(String compareItemEmail) {
		this.compareItemEmail = compareItemEmail;
	}
	public String getCompareItemVideo() {
		return compareItemVideo;
	}
	public void setCompareItemVideo(String compareItemVideo) {
		this.compareItemVideo = compareItemVideo;
	}
	public String getCompareItemMusicPlayer() {
		return compareItemMusicPlayer;
	}
	public void setCompareItemMusicPlayer(String compareItemMusicPlayer) {
		this.compareItemMusicPlayer = compareItemMusicPlayer;
	}
	public String getCompareItemGPS() {
		return compareItemGPS;
	}
	public void setCompareItemGPS(String compareItemGPS) {
		this.compareItemGPS = compareItemGPS;
	}
	public String getCompareItemSpeakerphone() {
		return compareItemSpeakerphone;
	}
	public void setCompareItemSpeakerphone(String compareItemSpeakerphone) {
		this.compareItemSpeakerphone = compareItemSpeakerphone;
	}
	public String getCompareItemMemory() {
		return compareItemMemory;
	}
	public void setCompareItemMemory(String compareItemMemory) {
		this.compareItemMemory = compareItemMemory;
	}
	public String getCompareItemProcessor() {
		return compareItemProcessor;
	}
	public void setCompareItemProcessor(String compareItemProcessor) {
		this.compareItemProcessor = compareItemProcessor;
	}
	public String getCompareItemCalendar() {
		return compareItemCalendar;
	}
	public void setCompareItemCalendar(String compareItemCalendar) {
		this.compareItemCalendar = compareItemCalendar;
	}
	public String getCompareItemVisualVoicemail() {
		return compareItemVisualVoicemail;
	}
	public void setCompareItemVisualVoicemail(String compareItemVisualVoicemail) {
		this.compareItemVisualVoicemail = compareItemVisualVoicemail;
	}
	public String getCompareItem3G() {
		return compareItem3G;
	}
	public void setCompareItem3G(String compareItem3G) {
		this.compareItem3G = compareItem3G;
	}
	public String getCompareItemBluetooth() {
		return compareItemBluetooth;
	}
	public void setCompareItemBluetooth(String compareItemBluetooth) {
		this.compareItemBluetooth = compareItemBluetooth;
	}
	public String getFeatureMemory() {
		return featureMemory;
	}
	public void setFeatureMemory(String featureMemory) {
		this.featureMemory = featureMemory;
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
	public Boolean getFeature4G() {
		return feature4G;
	}
	public void setFeature4G(Boolean feature4g) {
		feature4G = feature4g;
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
	public Boolean getIsPremium() {
		return isPremium;
	}
	public void setIsPremium(Boolean isPremium) {
		this.isPremium = isPremium;
	}
	public String getSynonym() {
		return synonym;
	}
	public void setSynonym(String synonym) {
		this.synonym = synonym;
	}
	
	

	
	
}
