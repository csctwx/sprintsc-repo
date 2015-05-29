package com.ericsson.cac.sprint.selfcare.workflow.myaccount;

import com.ericsson.cac.sprint.selfcare.workflow.commons.HeaderData;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.AccountBalanceResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.DeviceResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberAccountResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberPlanResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.cac.sprint.selfcare.workflow.myaccount.model.MyAccountResponse;



/**
 * MyAccount is used to call the external adapters to produce and consume the web services
 * 
 * @author ericsson
 * @version $Revision: 1.0 $
 */
public interface MyAccount {
	
	/** Method getAccount
	 * @param request
	 * @return SubscriberAccountResponse
	 */
	SubscriberAccountResponse getAccount(UserContextRequest request);
	
	/** Method getAccountBalance
	 * @param request
	 * @return AccountBalanceResponse
	 */
	AccountBalanceResponse getAccountBalance(UserContextRequest request);
	
	/** Method getPlan
	 * @param request
	 * @return SubscriberPlanResponse
	 */
	SubscriberPlanResponse getPlan(UserContextRequest request);
	
	/** Method getDevice
	 * @param request
	 * @return DeviceResponse
	 */
	DeviceResponse getDevice(UserContextRequest request);
	
	/** Method getPlanUsage
	 * @param request
	 * @return DeviceResponse
	 */
	SubscriberPlanResponse getPlanUsage(UserContextRequest request);
	
	
	
	
	
	/**
	 * Method querySubscriberPrepaidInfo.
	 * @param data HeaderData
	 * @param mdn String
	 * @return MyAccountResponse
	 */
	MyAccountResponse querySubscriberPrepaidInfo(HeaderData data, String mdn);

	/**
	 * Method queryAccountBasicInfo.
	 * @param data HeaderData
	 * @param mdn String
	 * @return MyAccountResponse 
	 */
	MyAccountResponse queryAccountBasicInfo(HeaderData data, String mdn);

	/**
	 * Method queryBalanceAndChargesV2.
	 * @param data HeaderData
	 * @param mdn String
	 * @return MyAccountResponse
	 */
	MyAccountResponse queryBalanceAndChargesV2(HeaderData data, String mdn);

	/**
	 * Method querySubscriberServices.
	 * @param data HeaderData
	 * @param mdn String
	 * @return MyAccountResponse
     */
	MyAccountResponse querySubscriberServices(HeaderData data, String mdn);

	/**
	 * Method queryDeviceInfo.
	 * @param data HeaderData
	 * @param mdn String
	 * @return MyAccountResponse 
	 */
	MyAccountResponse queryDeviceInfo(HeaderData data, String mdn);

	/**
	 * Method queryPrepaidAllowance.
	 * @param data HeaderData
	 * @param mdn String
	 * @return MyAccountResponse 
	 */
	MyAccountResponse queryPrepaidAllowance(HeaderData data, String mdn);

	/**
	 * Method queryPrepaidBalanceAndThresholdInfo.
	 * @param data HeaderData
	 * @param mdn String
	 * @return MyAccountResponse
	 */
	MyAccountResponse queryPrepaidBalanceAndThresholdInfo(HeaderData data,
			String mdn);

	/**
	 * Method queryAvailableOptions.
	 * @param data HeaderData
	 * @param mdn String
	 * @return MyAccountResponse 
	 */
	MyAccountResponse queryAvailableOptions(HeaderData data, String mdn);

	/**
	 * Method queryAvailablePlans.
	 * @param data HeaderData
	 * @param mdn String
	 * @return MyAccountResponse
	 */
	MyAccountResponse queryAvailablePlans(HeaderData data, String mdn);
	
	
	 public MyAccountResponse createPrepaidPaymentSession(HeaderData
	 data, String mdn);
	 

}