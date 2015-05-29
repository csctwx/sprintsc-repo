package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;
import java.util.Date;

public class SearchCriteriaRequest implements Serializable{
	
	private Date fromDate;//XMLGregorianCalendar
	private int pageNumber;
	private int numberOfItems;
	private AsyncRequest async;
	
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public AsyncRequest getAsync() {
		return async;
	}
	public void setAsync(AsyncRequest async) {
		this.async = async;
	}
	/**
	 * @return the numberOfItems
	 */
	public int getNumberOfItems() {
		return numberOfItems;
	}
	/**
	 * @param numberOfItems the numberOfItems to set
	 */
	public void setNumberOfItems(int numberOfItems) {
		this.numberOfItems = numberOfItems;
	}

}
