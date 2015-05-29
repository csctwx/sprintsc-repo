package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class EligibleServices implements Serializable {
	private Service[] services;//Required = false,Eligible services

	/**
	 * @return the services
	 */
	public Service[] getServices() {
		return services;
	}

	/**
	 * @param services the services to set
	 */
	public void setServices(Service[] services) {
		this.services = services;
	}

}
