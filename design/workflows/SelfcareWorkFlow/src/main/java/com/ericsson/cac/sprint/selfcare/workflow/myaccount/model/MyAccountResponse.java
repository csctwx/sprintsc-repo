package com.ericsson.cac.sprint.selfcare.workflow.myaccount.model;

import com.sprint.integration.interfaces.queryaccountbasicinfo.v1.queryaccountbasicinfo.QueryAccountBasicInfoResponse;
import com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.QueryAvailableOptionsResponseType;
import com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.QueryAvailablePlansResponseType;
import com.sprint.integration.interfaces.querybalanceandcharges.v2.querybalanceandchargesv2.QueryBalanceAndChargesV2ResponseType;
import com.sprint.integration.interfaces.querydeviceinfo.v1.querydeviceinfo.QueryDeviceInfoResponseType;
import com.sprint.integration.interfaces.queryprepaidallowance.v1.queryprepaidallowance.QueryPrepaidAllowanceResponseType;
import com.sprint.integration.interfaces.queryprepaidbalanceandthresholdinfo.v1.queryprepaidbalanceandthresholdinfo.QueryPrepaidBalanceAndThresholdInfoResponseType;
import com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.QuerySubscriberPrepaidInfoResponseType;
import com.sprint.integration.interfaces.querysubscriberservices.v1.querysubscriberservices.QuerySubscriberServicesResponse;

public class MyAccountResponse {

	private QuerySubscriberPrepaidInfoResponseType prepaidInfoResponseType;
	
	private QueryAccountBasicInfoResponse queryAccountBasicInfoResponse;
	
	private QueryBalanceAndChargesV2ResponseType queryBalanceAndChargesV2ResponseType ;
	
	private QueryPrepaidAllowanceResponseType queryPrepaidAllowanceResponseType;
	
	private QuerySubscriberServicesResponse querySubscriberServicesResponse;
	
	private QueryDeviceInfoResponseType queryDeviceInfoResponseType;
	
	private QueryPrepaidBalanceAndThresholdInfoResponseType queryPrepaidBalanceAndThresholdInfoResponseType;
	
	private QueryAvailableOptionsResponseType queryAvailableOptionsResponseType;
	
	private QueryAvailablePlansResponseType queryAvailablePlansResponseType ;
	

	public QueryAvailablePlansResponseType getQueryAvailablePlansResponseType() {
		return queryAvailablePlansResponseType;
	}


	public void setQueryAvailablePlansResponseType(
			QueryAvailablePlansResponseType queryAvailablePlansResponseType) {
		this.queryAvailablePlansResponseType = queryAvailablePlansResponseType;
	}


	public QueryAvailableOptionsResponseType getQueryAvailableOptionsResponseType() {
		return queryAvailableOptionsResponseType;
	}


	public void setQueryAvailableOptionsResponseType(
			QueryAvailableOptionsResponseType queryAvailableOptionsResponseType) {
		this.queryAvailableOptionsResponseType = queryAvailableOptionsResponseType;
	}


	public QueryPrepaidBalanceAndThresholdInfoResponseType getQueryPrepaidBalanceAndThresholdInfoResponseType() {
		return queryPrepaidBalanceAndThresholdInfoResponseType;
	}


	public void setQueryPrepaidBalanceAndThresholdInfoResponseType(
			QueryPrepaidBalanceAndThresholdInfoResponseType queryPrepaidBalanceAndThresholdInfoResponseType) {
		this.queryPrepaidBalanceAndThresholdInfoResponseType = queryPrepaidBalanceAndThresholdInfoResponseType;
	}


	public QueryDeviceInfoResponseType getQueryDeviceInfoResponseType() {
		return queryDeviceInfoResponseType;
	}


	public void setQueryDeviceInfoResponseType(
			QueryDeviceInfoResponseType queryDeviceInfoResponseType) {
		this.queryDeviceInfoResponseType = queryDeviceInfoResponseType;
	}


	public QuerySubscriberServicesResponse getQuerySubscriberServicesResponse() {
		return querySubscriberServicesResponse;
	}


	public void setQuerySubscriberServicesResponse(
			QuerySubscriberServicesResponse querySubscriberServicesResponse) {
		this.querySubscriberServicesResponse = querySubscriberServicesResponse;
	}


	public QueryPrepaidAllowanceResponseType getQueryPrepaidAllowanceResponseType() {
		return queryPrepaidAllowanceResponseType;
	}


	public void setQueryPrepaidAllowanceResponseType(
			QueryPrepaidAllowanceResponseType queryPrepaidAllowanceResponseType) {
		this.queryPrepaidAllowanceResponseType = queryPrepaidAllowanceResponseType;
	}

	public QueryBalanceAndChargesV2ResponseType getQueryBalanceAndChargesV2ResponseType() {
		return queryBalanceAndChargesV2ResponseType;
	}


	public void setQueryBalanceAndChargesV2ResponseType(
			QueryBalanceAndChargesV2ResponseType queryBalanceAndChargesV2ResponseType) {
		this.queryBalanceAndChargesV2ResponseType = queryBalanceAndChargesV2ResponseType;
	}

	
	public QuerySubscriberPrepaidInfoResponseType getPrepaidInfoResponseType() {
		return prepaidInfoResponseType;
	}


	public void setPrepaidInfoResponseType(
			QuerySubscriberPrepaidInfoResponseType prepaidInfoResponseType) {
		this.prepaidInfoResponseType = prepaidInfoResponseType;
	}


	public QueryAccountBasicInfoResponse getQueryAccountBasicInfoResponse() {
		return queryAccountBasicInfoResponse;
	}

	public void setQueryAccountBasicInfoResponse(
			QueryAccountBasicInfoResponse queryAccountBasicInfoResponse) {
		this.queryAccountBasicInfoResponse = queryAccountBasicInfoResponse;
	}

	
	

}
