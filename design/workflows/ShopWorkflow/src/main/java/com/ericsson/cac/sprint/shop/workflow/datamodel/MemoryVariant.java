package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum MemoryVariant {
	//TODO need to confirm values
	@XmlEnumValue("16GB")
	GB16("GB16"),
	
	@XmlEnumValue("8GB")
	GB8("GB8"),
	
	@XmlEnumValue("64GB")
	GB64("GB64");
	
	
	private String memeryVariant;
	
	public String getMemoryVariant() {
		return memeryVariant;
	}
	
	private MemoryVariant(String brandID) {
		this.memeryVariant = brandID;
	}
	
	public static  MemoryVariant getMemory(String memeryVariant) {
		switch (memeryVariant) {
			case "8GB"	: return GB8;
			case "16GB"	: return GB16;
			case "64GB"	: return GB64;
			default : return null;
		}
	}   

}
