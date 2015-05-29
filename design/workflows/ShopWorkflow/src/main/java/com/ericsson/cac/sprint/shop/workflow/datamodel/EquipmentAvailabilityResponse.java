package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class EquipmentAvailabilityResponse {

	@XmlElement
	private EquipmentAvailability equipmentAvailability;
	@XmlElement
	private Integer status;
	@XmlElement
	private String description;
	/**
	 * @return the equipmentAvailability
	 */
	public EquipmentAvailability getEquipmentAvailability() {
		return equipmentAvailability;
	}
	/**
	 * @param equipmentAvailability the equipmentAvailability to set
	 */
	public void setEquipmentAvailability(EquipmentAvailability equipmentAvailability) {
		this.equipmentAvailability = equipmentAvailability;
	}
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public String toString()
	{
		return new Gson().toJson(this);
	}

}
