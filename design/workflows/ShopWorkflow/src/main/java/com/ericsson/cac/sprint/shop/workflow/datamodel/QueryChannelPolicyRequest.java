package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlElement;

import com.amdocs.mfs.api.epp.v1.sprint.walletcommontypes.ActorApp;

public class QueryChannelPolicyRequest {
	
	@XmlElement
	private ActorApp applicationInfo;

	/**
	 * @return the applicationInfo
	 */
	public ActorApp getApplicationInfo() {
		return applicationInfo;
	}

	/**
	 * @param applicationInfo the applicationInfo to set
	 */
	public void setApplicationInfo(ActorApp applicationInfo) {
		this.applicationInfo = applicationInfo;
	}	

}
