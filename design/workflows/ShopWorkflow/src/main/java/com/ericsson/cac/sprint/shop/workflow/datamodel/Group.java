package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringEscapeUtils;

import com.ericsson.cac.sprint.shop.workflow.util.CommonUtil;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Group {
	@XmlElement
	private	String	order;
	@XmlElement
	private	String	thumb;
	@XmlElementWrapper(name="specs")
	@XmlElement(name="spec")
	private	Spec[] specs;
	@XmlAttribute(name = "id")
	private String id;
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		if(!CommonUtil.isStringNullOfEmpty(order)){
			order=StringEscapeUtils.escapeHtml4(order);
		}
		this.order = order;
	}
	public String getThumb() {
		
		return thumb;
	}
	public void setThumb(String thumb) {
		if(!CommonUtil.isStringNullOfEmpty(thumb)){
			thumb=StringEscapeUtils.escapeHtml4(thumb);
		}
		this.thumb = thumb;
	}
	public Spec[] getSpecs() {
		return specs;
	}
	public void setSpecs(Spec[] specs) {
		this.specs = specs;
	}
	public String getId() {
		
		return id;
	}
/*	public Specs getSpecs() {
		return specs;
	}
	public void setSpecs(Specs specs) {
		this.specs = specs;
	}*/
	public void setId(String id) {
		if(!CommonUtil.isStringNullOfEmpty(id)){
			id=StringEscapeUtils.escapeHtml4(id);
		}
		this.id = id;
	}
	
	
	

}
