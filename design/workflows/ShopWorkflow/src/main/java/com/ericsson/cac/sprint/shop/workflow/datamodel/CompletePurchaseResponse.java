package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CompletePurchaseResponse {
	@XmlElement
	private Boolean orderCompleted;
	@XmlElement
	private String fastOrderKey;
	@XmlElement
	private Integer status;
	@XmlElement
	private String description;
	
	/**
	 * @return the orderCompleted
	 */
	public Boolean getOrderCompleted() {
		return orderCompleted;
	}
	/**
	 * @param orderCompleted the orderCompleted to set
	 */
	public void setOrderCompleted(Boolean orderCompleted) {
		this.orderCompleted = orderCompleted;
	}
	/**
	 * @return the fastOrderKey
	 */
	public String getFastOrderKey() {
		return fastOrderKey;
	}
	/**
	 * @param fastOrderKey the fastOrderKey to set
	 */
	public void setFastOrderKey(String fastOrderKey) {
		this.fastOrderKey = fastOrderKey;
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
	
	public String toString()
	{
		return new Gson().toJson(this);
	}
	
}
