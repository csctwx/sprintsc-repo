package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ComparePhoneResponse {
	@XmlElementWrapper(name="comparePhoneList")
	@XmlElement(name="comparePhone")
	private ComparePhone[] comparePhones;
	@XmlElement
	private Integer status;
	@XmlElement
	private String description;
	/**
	 * @return the comparePhones
	 */
	public ComparePhone[] getComparePhones() {
		return comparePhones;
	}
	/**
	 * @param comparePhones the comparePhones to set
	 */
	public void setComparePhones(ComparePhone[] comparePhones) {
		this.comparePhones = comparePhones;
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
