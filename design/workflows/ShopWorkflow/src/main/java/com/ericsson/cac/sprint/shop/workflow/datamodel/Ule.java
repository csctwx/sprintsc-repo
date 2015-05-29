package com.ericsson.cac.sprint.shop.workflow.datamodel;

import java.util.Map;

public class Ule {
	private String average;
	private Map<String,String> items;
	/**
	 * @return the average
	 */
	public String getAverage() {
		return average;
	}
	/**
	 * @param average the average to set
	 */
	public void setAverage(String average) {
		this.average = average;
	}
	/**
	 * @return the items
	 */
	public Map<String, String> getItems() {
		return items;
	}
	/**
	 * @param items the items to set
	 */
	public void setItems(Map<String, String> items) {
		this.items = items;
	}
	
	
	
}
