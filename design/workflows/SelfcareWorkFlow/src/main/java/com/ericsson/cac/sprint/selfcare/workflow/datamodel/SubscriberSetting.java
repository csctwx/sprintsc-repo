package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class SubscriberSetting implements Serializable{
	private String id;//Required = true,Setting ID
	private String title;//Required = true,Setting title: Content Filter, Caller ID blocking
	private String description;//Required = true,Status message
	private String value;//Required = true,A-sync response info.
	private boolean settingEnabled;//Required = true, true = enabled, false = disabled
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the settingEnabled
	 */
	public boolean isSettingEnabled() {
		return settingEnabled;
	}
	/**
	 * @param settingEnabled the settingEnabled to set
	 */
	public void setSettingEnabled(boolean settingEnabled) {
		this.settingEnabled = settingEnabled;
	}
	


}
