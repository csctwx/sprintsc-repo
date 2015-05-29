package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
	@XmlRootElement
public class CompareDeviceRequest {
	@XmlElement
	private String externalId1;
	@XmlElement
	private String externalId2;
	@XmlElement
	private String externalId3;
	@XmlElement
	private String externalId4;
	public String getExternalId1() {
		return externalId1;
	}
	public void setExternalId1(String externalId1) {
		this.externalId1 = externalId1;
	}
	public String getExternalId2() {
		return externalId2;
	}
	public void setExternalId2(String externalId2) {
		this.externalId2 = externalId2;
	}
	public String getExternalId3() {
		return externalId3;
	}
	public void setExternalId3(String externalId3) {
		this.externalId3 = externalId3;
	}
	public String getExternalId4() {
		return externalId4;
	}
	public void setExternalId4(String externalId4) {
		this.externalId4 = externalId4;
	}
	
	
}

