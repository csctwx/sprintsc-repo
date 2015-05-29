package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD) 
public class GetCompareDeviceResponse {
	@XmlElement
	private PhoneCompare[] phoneCompare;
	@XmlElement
	private Integer status;
	@XmlElement
	private String description;
	public PhoneCompare[] getPhoneCompare() {
		return phoneCompare;
	}
	public void setPhoneCompare(PhoneCompare[] phoneCompare) {
		this.phoneCompare = phoneCompare;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
