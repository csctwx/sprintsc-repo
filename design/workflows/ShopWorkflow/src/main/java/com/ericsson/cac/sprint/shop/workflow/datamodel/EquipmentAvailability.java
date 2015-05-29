package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class EquipmentAvailability {
	
	@XmlElement
	private boolean availableForSaleIndicator;
	@XmlElement
	private boolean preOrderIndicator;
	
	/**
	 * @return the availableForSaleIndicator
	 */
	public Boolean getAvailableForSaleIndicator() {
		return availableForSaleIndicator;
	}
	/**
	 * @param availableForSaleIndicator the availableForSaleIndicator to set
	 */
	public void setAvailableForSaleIndicator(boolean availableForSaleIndicator) {
		this.availableForSaleIndicator = availableForSaleIndicator;
	}
	/**
	 * @return the preOrderIndicator
	 */
	public Boolean getPreOrderIndicator() {
		return preOrderIndicator;
	}
	/**
	 * @param preOrderIndicator the preOrderIndicator to set
	 */
	public void setPreOrderIndicator(boolean preOrderIndicator) {
		this.preOrderIndicator = preOrderIndicator;
	}	

	
}
