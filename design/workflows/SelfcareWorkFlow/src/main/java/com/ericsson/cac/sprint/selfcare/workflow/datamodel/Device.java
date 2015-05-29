package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class Device implements Serializable{
	private String msisdn;//Required = true,MDN
	private String meid;//Required = true,Device MEID
	private String name;//Required = false,Device name
	private String brand;//Required = false,Device brand
	private String model;//Required = false,Device model
	private String picture;//Required = false,URL to device image
	private DeviceInsurance insurance;//Required = false,Device insurance. Null means device is not insured (default).
	/**
	 * @return the msisdn
	 */
	public String getMsisdn() {
		return msisdn;
	}
	/**
	 * @param msisdn the msisdn to set
	 */
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	/**
	 * @return the meid
	 */
	public String getMeid() {
		return meid;
	}
	/**
	 * @param meid the meid to set
	 */
	public void setMeid(String meid) {
		this.meid = meid;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}
	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}
	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}
	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}
	/**
	 * @return the picture
	 */
	public String getPicture() {
		return picture;
	}
	/**
	 * @param picture the picture to set
	 */
	public void setPicture(String picture) {
		this.picture = picture;
	}
	/**
	 * @return the insurance
	 */
	public DeviceInsurance getInsurance() {
		return insurance;
	}
	/**
	 * @param insurance the insurance to set
	 */
	public void setInsurance(DeviceInsurance insurance) {
		this.insurance = insurance;
	}


}
