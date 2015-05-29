package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD) 
public class Shipping {
	@XmlElement
	private String shippingMethod;
	@XmlElement
	private String shippingTypeCode;
	@XmlElement
	private String shippingFee;
	@XmlElement(name="default")
	private String isDefault;
	@XmlElement
	private String label;
	/**
	 * @return the shippingMethod
	 */
	public String getShippingMethod() {
		return shippingMethod;
	}
	/**
	 * @param shippingMethod the shippingMethod to set
	 */
	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}
	/**
	 * @return the shippingTypeCode
	 */
	public String getShippingTypeCode() {
		return shippingTypeCode;
	}
	/**
	 * @param shippingTypeCode the shippingTypeCode to set
	 */
	public void setShippingTypeCode(String shippingTypeCode) {
		this.shippingTypeCode = shippingTypeCode;
	}
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
	 * @return the isDefault
	 */
	public String getIsDefault() {
		return isDefault;
	}
	/**
	 * @param isDefault the isDefault to set
	 */
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
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
