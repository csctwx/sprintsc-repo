package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class SubscriberPlan implements Serializable {

	private Service[] baseplan;//Required = false,Base plan services
	private Service[] addOns;//Required = false,Add-on services
	/**
	 * @return the baseplan
	 */
	public Service[] getBaseplan() {
		return baseplan;
	}
	/**
	 * @param baseplan the baseplan to set
	 */
	public void setBaseplan(Service[] baseplan) {
		this.baseplan = baseplan;
	}
	/**
	 * @return the addOns
	 */
	public Service[] getAddOns() {
		return addOns;
	}
	/**
	 * @param addOns the addOns to set
	 */
	public void setAddOns(Service[] addOns) {
		this.addOns = addOns;
	}

}
