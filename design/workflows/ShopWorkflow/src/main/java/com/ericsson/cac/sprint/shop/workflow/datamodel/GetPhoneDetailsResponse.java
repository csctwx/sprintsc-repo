package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GetPhoneDetailsResponse {
	@XmlElementWrapper(name="phoneDetails")
	@XmlElement(name="phoneDetail")
	private PhoneDetail[] phoneDetail;
	@XmlElement
	private Integer status;
	@XmlElement
	private String description;
	
	public PhoneDetail[] getPhoneDetails() {
		return phoneDetail;
	}
	public void setPhoneDetail(PhoneDetail[] phoneDetail) {
		this.phoneDetail = phoneDetail;
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
