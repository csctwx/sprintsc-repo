package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="GetAccessoryResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetAccessoryResponse {
	
	@XmlElement
	 Accessory accessory;	
	@XmlElement
	 Integer status;	
	@XmlElement
	 String description;

	/**
	 * @return the accessory
	 */
	public Accessory getAccessory() {
		return accessory;
	}

	/**
	 * @param accessory the accessory to set
	 */
	public void setAccessory(Accessory accessory) {
		this.accessory = accessory;
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
