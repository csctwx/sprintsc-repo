package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ComparePhone {
	@XmlElement
	private String id;
	@XmlElement
	private GeneralFeature[] generalFeatures;
	@XmlElement
	private TechnicalFeatures[] technicalFeatures;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the generalFeatures
	 */
	public GeneralFeature[] getGeneralFeatures() {
		return generalFeatures;
	}
	/**
	 * @param generalFeatures the generalFeatures to set
	 */
	public void setGeneralFeatures(GeneralFeature[] generalFeatures) {
		this.generalFeatures = generalFeatures;
	}
	/**
	 * @return the technicalFeatures
	 */
	public TechnicalFeatures[] getTechnicalFeatures() {
		return technicalFeatures;
	}
	/**
	 * @param technicalFeatures the technicalFeatures to set
	 */
	public void setTechnicalFeatures(TechnicalFeatures[] technicalFeatures) {
		this.technicalFeatures = technicalFeatures;
	}
	
	
}
