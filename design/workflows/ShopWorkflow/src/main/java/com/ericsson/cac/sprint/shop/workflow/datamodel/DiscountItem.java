package com.ericsson.cac.sprint.shop.workflow.datamodel;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DiscountItem {
	@XmlElement
	protected String itemId;
	@XmlElement
	protected BigInteger quantity;
	@XmlElement
	protected BigInteger eligibleQuantity;
	@XmlElement
	protected BigDecimal originalPricePerItemId;
	@XmlElement
	protected BigDecimal discountAmount;
	@XmlElement
	protected String promotionName;
	/**
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}
	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	/**
	 * @return the quantity
	 */
	public BigInteger getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(BigInteger quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the eligibleQuantity
	 */
	public BigInteger getEligibleQuantity() {
		return eligibleQuantity;
	}
	/**
	 * @param eligibleQuantity the eligibleQuantity to set
	 */
	public void setEligibleQuantity(BigInteger eligibleQuantity) {
		this.eligibleQuantity = eligibleQuantity;
	}
	/**
	 * @return the originalPricePerItemId
	 */
	public BigDecimal getOriginalPricePerItemId() {
		return originalPricePerItemId;
	}
	/**
	 * @param originalPricePerItemId the originalPricePerItemId to set
	 */
	public void setOriginalPricePerItemId(BigDecimal originalPricePerItemId) {
		this.originalPricePerItemId = originalPricePerItemId;
	}
	/**
	 * @return the discountAmount
	 */
	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}
	/**
	 * @param discountAmount the discountAmount to set
	 */
	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}
	/**
	 * @return the promotionName
	 */
	public String getPromotionName() {
		return promotionName;
	}
	/**
	 * @param promotionName the promotionName to set
	 */
	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}
    
    
}
