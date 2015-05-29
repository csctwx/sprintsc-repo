package com.ericsson.cac.sprint.shop.workflow.datamodel;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PhoneVariant {
	
	@XmlElement
	private String sku;
	
	
	
	@XmlElement
	private String memoryVariant;
	
	@XmlElement
	private String colorVariant;
	
	@XmlElement
	private String gradientColor;
	
	@XmlElement(name = "msrp")
	private String retailPrice;	
	
	@XmlElement
	private String discount;
	
	@XmlElement
	private String price;
	
	@XmlElement
	private String inventory;
	
	@XmlElement
	private boolean isDefault;
	
	@XmlElement
	private Picture shopGridPicture;
	
	@XmlElement
	private Picture heroImage;
	
	@XmlElement
	private Picture overlayImage;
	
	@XmlElement
	private Integer purchaseLimit;
	
	@XmlElement
	private boolean hiddenPrice;
	
	public String getMemoryVariant() {
		return memoryVariant;
	}
	public void setMemoryVariant(String memoryVariant) {
		this.memoryVariant = memoryVariant;
	}
	
	public String getColorVariant() {
		return colorVariant;
	}
	public void setColorVariant(String colorVariant) {
		this.colorVariant = colorVariant;
	}
	
	/**
	 * @return the retailPrice
	 */
	public String getRetailPrice() {
		return retailPrice;
	}
	/**
	 * @param retailPrice the retailPrice to set
	 */
	public void setRetailPrice(String retailPrice) {
		this.retailPrice = retailPrice;
	}
	public String getDiscount() {
		return discount;
	}
	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	/**
	 * @return the inventory
	 */
	public String getInventory() {
		return inventory;
	}
	/**
	 * @param inventory the inventory to set
	 */
	public void setInventory(String inventory) {
		this.inventory = inventory;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public Picture getShopGridPicture() {
		return shopGridPicture;
	}
	public void setShopGridPicture(Picture shopGridPicture) {
		this.shopGridPicture = shopGridPicture;
	}
	public boolean isDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	public String getGradientColor() {
		return gradientColor;
	}
	public void setGradientColor(String gradientColor) {
		this.gradientColor = gradientColor;
	}
	public Picture getHeroImage() {
		return heroImage;
	}
	public void setHeroImage(Picture heroImage) {
		this.heroImage = heroImage;
	}
	public Picture getOverlayImage() {
		return overlayImage;
	}
	public void setOverlayImage(Picture overlayImage) {
		this.overlayImage = overlayImage;
	}
	public Integer getPurchaseLimit() {
		return purchaseLimit;
	}
	public void setPurchaseLimit(Integer purchaseLimit) {
		this.purchaseLimit = purchaseLimit;
	}
	public boolean isHiddenPrice() {
		return hiddenPrice;
	}
	public void setHiddenPrice(boolean hiddenPrice) {
		this.hiddenPrice = hiddenPrice;
	}	
	
}
