package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD) 
public class QueryTotalOrderAmountResponse {
	
	@XmlElement
	private String queryTotalOrderAmount;

	/**
	 * @return the queryTotalOrderAmount
	 */
	public String getQueryTotalOrderAmount() {
		return queryTotalOrderAmount;
	}

	/**
	 * @param queryTotalOrderAmount the queryTotalOrderAmount to set
	 */
	public void setQueryTotalOrderAmount(String queryTotalOrderAmount) {
		this.queryTotalOrderAmount = queryTotalOrderAmount;
	}

}
