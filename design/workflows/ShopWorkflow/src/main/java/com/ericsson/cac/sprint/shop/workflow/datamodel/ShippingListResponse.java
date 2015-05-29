/**
 * 
 */
package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author eashich
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD) 
public class ShippingListResponse {
	@XmlElementWrapper(name="shippingList")
	@XmlElement(name="shipping")
	private Shipping[] shipping;
	@XmlElement
	private Integer status;
	@XmlElement
	private String description;
	/**
	 * @return the shipping
	 */
	public Shipping[] getShipping() {
		return shipping;
	}
	/**
	 * @param shipping the shipping to set
	 */
	public void setShipping(Shipping[] shipping) {
		this.shipping = shipping;
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
	
	

}
