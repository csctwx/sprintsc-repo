package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class DeviceInsurance  implements Serializable{
	private Boolean insured;//Required = true,True means phone is insured
	private String company;//Required = true,Insurance company
	private String link;//Required = true,URL to insurance claim
	private String phone;//Required = true,Insurance phone number
	private OpeningHours openingHours;//Required = false,Opening hours
	/**
	 * @return the insured
	 */
	public Boolean getInsured() {
		return insured;
	}
	/**
	 * @param insured the insured to set
	 */
	public void setInsured(Boolean insured) {
		this.insured = insured;
	}
	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}
	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}
	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}
	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the openingHours
	 */
	public OpeningHours getOpeningHours() {
		return openingHours;
	}
	/**
	 * @param openingHours the openingHours to set
	 */
	public void setOpeningHours(OpeningHours openingHours) {
		this.openingHours = openingHours;
	}


}
