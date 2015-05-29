package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PhoneFeature {
	@XmlElement
	private GeneralFeature[] generalFeatures;
	@XmlElement
	private TechnicalFeatures[] technicalFeatures;
	@XmlElement
	private SpecialFeatures[] specialFeatures;
	
	@XmlElement
	private Picture specifationImage;
	
	@XmlElement
	private Picture iiboxImage;
	
	public GeneralFeature[] getGeneralFeatures() {
		return generalFeatures;
	}

	public void setGeneralFeatures(GeneralFeature[] generalFeatures) {
		this.generalFeatures = generalFeatures;
	}

	public TechnicalFeatures[] getTechnicalFeatures() {
		return technicalFeatures;
	}

	public void setTechnicalFeatures(TechnicalFeatures[] technicalFeatures) {
		this.technicalFeatures = technicalFeatures;
	}

	public SpecialFeatures[] getSpecialFeatures() {
		return specialFeatures;
	}

	public void setSpecialFeatures(SpecialFeatures[] specialFeatures) {
		this.specialFeatures = specialFeatures;
	}

	public Picture getSpecifationImage() {
		return specifationImage;
	}

	public void setSpecifationImage(Picture specifationImage) {
		this.specifationImage = specifationImage;
	}

	public Picture getIiboxImage() {
		return iiboxImage;
	}

	public void setIiboxImage(Picture iiboxImage) {
		this.iiboxImage = iiboxImage;
	}
	
	
}
