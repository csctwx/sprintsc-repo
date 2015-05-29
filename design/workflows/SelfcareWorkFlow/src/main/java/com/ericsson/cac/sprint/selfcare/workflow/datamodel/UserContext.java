package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class UserContext implements Serializable{
	private String subscriberId ; //Required = true , Subscriber ID
	private String language; //Required = true , ISO Language code: en, fr, etc
	private String msisdn; //Required = false , Mobile phone
	public String getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

}
