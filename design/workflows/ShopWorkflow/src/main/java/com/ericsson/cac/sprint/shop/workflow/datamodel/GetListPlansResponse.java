package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GetListPlansResponse {

	@XmlElement
	private Plan[] plans;
	@XmlElement
	private Integer status;
	@XmlElement
	private String description;

	/**
	 * @return the plans
	 */
	public Plan[] getPlans() {
		return plans;
	}

	/**
	 * @param plans
	 *            the plans to set
	 */
	public void setPlans(Plan[] plans) {
		if (plans.length == 0) {
			this.plans = new Plan[0];
		} else {
			this.plans = plans.clone();
		}
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
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
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
