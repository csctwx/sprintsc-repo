package com.ericsson.cac.sprint.shop.workflow.datamodel;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GetBannerResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8009331117881869850L;
	@XmlElement
	private Banner banner;//Required = true,Banner Object
	@XmlElement
	private Integer status;//Required = true,Status 0 success
	@XmlElement
	private String description;//Required = Description
	
	public Banner getBanner() {
		return banner;
	}
	public void setBanner(Banner banner) {
		this.banner = banner;
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
