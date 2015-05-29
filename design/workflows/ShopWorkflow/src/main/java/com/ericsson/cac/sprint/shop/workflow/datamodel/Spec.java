package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import org.apache.commons.lang3.StringEscapeUtils;

import com.ericsson.cac.sprint.shop.workflow.util.CommonUtil;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="spec")
public class Spec {
	@XmlAttribute(name="order")
	private	String	order;
	@XmlAttribute(name="type")
	private	String	type;
	@XmlValue
	private	String	description;
	
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		if(!CommonUtil.isStringNullOfEmpty(order)){
			order=StringEscapeUtils.escapeHtml4(order);
		}
		this.order = order;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		if(!CommonUtil.isStringNullOfEmpty(type)){
			order=StringEscapeUtils.escapeHtml4(type);
		}
		this.type = type;
	}
/*	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}*/
	
	
}
