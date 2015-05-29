package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="feature")
@XmlAccessorType(XmlAccessType.FIELD)
public class FeatureProps {
	
	@XmlAttribute
	private String id;
	@XmlElement
	private String title;
	@XmlElement
	private String description;
	@XmlElement(name="compare-title")
	private String compareTitle;
	
}
