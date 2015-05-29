package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GetFeaturesResponse {
	@XmlElement
	private PhoneFeature phoneFeatures;
	@XmlElement
	private Integer status;
	@XmlElement
	private String description;
	
	public PhoneFeature getPhoneFeatures() {
		return phoneFeatures;
	}

	public void setPhoneFeatures(PhoneFeature phoneFeatures) {
		this.phoneFeatures = phoneFeatures;
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
