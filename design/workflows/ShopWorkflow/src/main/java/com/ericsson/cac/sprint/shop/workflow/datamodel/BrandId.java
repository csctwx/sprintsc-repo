package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum BrandId {
	
	@XmlEnumValue("sprint")
	SPRINT("sprint"),
	
	@XmlEnumValue("boost")
	BOOST("boost"),
	
	@XmlEnumValue("virgin")
	VIRGIN("virgin");
	
	
	private String brandID;
	
	public String getBrandID() {
		return brandID;
	}
	
	private BrandId(String brandID) {
		this.brandID = brandID;
	}
	
	public static BrandId getBrand (String brandID) {
		switch (brandID) {
			case "sprint"	: return SPRINT;
			case "boost"	: return BOOST;
			case "virgin"	: return VIRGIN;
			default : return null;
		}
	}   
}