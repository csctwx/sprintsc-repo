package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;
import java.util.Date;

public class Service implements Serializable {
	private String id;//Required = true,Service ID
	private String name;//Required = true,Service name
	private String description;//Required = false,Service description
	private String capacity;//Required = false,Values: 300, Unlimited, 3.5, 
	private String usage;//Required = false,Values: 100, Unlimited, 3.5,
	private String unit;//Required = false,Values: mins, GB, etc. capacity and usage should have same normalized unit 
	private String type;//Required = true,Service type: Talk, Text, Data, Hot Spot
	private float rate;//Required = true,Service rate
	private String currency;//Required = false,Currency for above values. If not specified $ would be the default value
	private Boolean addOn;//Required = true,True means this service is an add-on 
	private Date expiry;//Required = true,Service expiry date
	private String recurrence;//Required = false,Values: monthly, daily
	private Boolean startingNow;//Required = true,True means service starts now otherwise service starts on MRC+1
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	/**
	 * @return the capacity
	 */
	public String getCapacity() {
		return capacity;
	}
	/**
	 * @param capacity the capacity to set
	 */
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	/**
	 * @return the usage
	 */
	public String getUsage() {
		return usage;
	}
	/**
	 * @param usage the usage to set
	 */
	public void setUsage(String usage) {
		this.usage = usage;
	}
	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the rate
	 */
	public float getRate() {
		return rate;
	}
	/**
	 * @param rate the rate to set
	 */
	public void setRate(float rate) {
		this.rate = rate;
	}
	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	/**
	 * @return the addOn
	 */
	public Boolean getAddOn() {
		return addOn;
	}
	/**
	 * @param addOn the addOn to set
	 */
	public void setAddOn(Boolean addOn) {
		this.addOn = addOn;
	}
	/**
	 * @return the expiry
	 */
	public Date getExpiry() {
		return expiry;
	}
	/**
	 * @param expiry the expiry to set
	 */
	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}
	/**
	 * @return the recurrence
	 */
	public String getRecurrence() {
		return recurrence;
	}
	/**
	 * @param recurrence the recurrence to set
	 */
	public void setRecurrence(String recurrence) {
		this.recurrence = recurrence;
	}
	/**
	 * @return the startingNow
	 */
	public Boolean getStartingNow() {
		return startingNow;
	}
	/**
	 * @param startingNow the startingNow to set
	 */
	public void setStartingNow(Boolean startingNow) {
		this.startingNow = startingNow;
	}



}
