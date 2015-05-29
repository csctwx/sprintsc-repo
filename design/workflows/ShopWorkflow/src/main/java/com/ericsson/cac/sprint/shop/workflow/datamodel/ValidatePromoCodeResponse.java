package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ValidatePromoCodeResponse {
	
	@XmlElement
	protected String shippingFee;	
	@XmlElementWrapper(name="items")	
	@XmlElement(name="item")
	private DiscountItem[] discounts;
	@XmlElement
	protected String totalOriginalPrice;	
	@XmlElement
	protected String totalDiscountedPrice;	
	@XmlElement
	protected String promoLegalese;	
	@XmlElement
	protected String successMessage;	
	@XmlElement
	protected Integer status;	
	@XmlElement
	protected String description;
	/**
	 * @return the shippingFee
	 */
	public String getShippingFee() {
		return shippingFee;
	}
	/**
	 * @param shippingFee the shippingFee to set
	 */
	public void setShippingFee(String shippingFee) {
		this.shippingFee = shippingFee;
	}
	/**
	 * @return the totalOriginalPrice
	 */
	public String getTotalOriginalPrice() {
		return totalOriginalPrice;
	}
	/**
	 * @param totalOriginalPrice the totalOriginalPrice to set
	 */
	public void setTotalOriginalPrice(String totalOriginalPrice) {
		this.totalOriginalPrice = totalOriginalPrice;
	}
	/**
	 * @return the totalDiscountedPrice
	 */
	public String getTotalDiscountedPrice() {
		return totalDiscountedPrice;
	}
	/**
	 * @param totalDiscountedPrice the totalDiscountedPrice to set
	 */
	public void setTotalDiscountedPrice(String totalDiscountedPrice) {
		this.totalDiscountedPrice = totalDiscountedPrice;
	}
	/**
	 * @return the promoLegalese
	 */
	public String getPromoLegalese() {
		return promoLegalese;
	}
	/**
	 * @param promoLegalese the promoLegalese to set
	 */
	public void setPromoLegalese(String promoLegalese) {
		this.promoLegalese = promoLegalese;
	}
	/**
	 * @return the successMessage
	 */
	public String getSuccessMessage() {
		return successMessage;
	}
	/**
	 * @param successMessage the successMessage to set
	 */
	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
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
	/**
	 * @return the discounts
	 */
	public DiscountItem[] getDiscounts() {
		return discounts;
	}
	/**
	 * @param discounts the discounts to set
	 */
	public void setDiscounts(DiscountItem[] discounts) {
		this.discounts = discounts;
	}	
	
}
