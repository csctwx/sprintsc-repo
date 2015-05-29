package com.ericsson.cac.sprint.selfcare.workflow.myaccount;

import java.math.BigInteger;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.Holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ericsson.cac.sprint.adapters.QueryAccountInfoServiceProxyService;
import com.ericsson.cac.sprint.adapters.QueryDeviceInfoProxyService;
import com.ericsson.cac.sprint.adapters.QueryPlansAndOptionsProxyService;
import com.ericsson.cac.sprint.adapters.QueryPrepaidSubscriberProxyService;
import com.ericsson.cac.sprint.adapters.QuerySubscriberInfoProxyService;
import com.ericsson.cac.sprint.adapters.QueryUsageProxyService;
import com.ericsson.cac.sprint.selfcare.workflow.commons.HeaderData;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.AccountBalanceResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.DeviceResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberAccount;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberAccountResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberPlanResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.cac.sprint.selfcare.workflow.myaccount.model.MyAccountResponse;
import com.sprint.integration.common.header.wsmessageheader.v2.TrackingMessageHeaderType;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.querydeviceinfoservice.v1.querydeviceinfoservice.Faultmessage2;
import com.sprint.integration.eai.services.queryplansandoptionsservice.v1.queryplansandoptionsservice.Faultmessage;
import com.sprint.integration.interfaces.queryaccountbasicinfo.v1.queryaccountbasicinfo.QueryAccountBasicInfo;
import com.sprint.integration.interfaces.queryaccountbasicinfo.v1.queryaccountbasicinfo.QueryAccountBasicInfoResponse;
import com.sprint.integration.interfaces.queryaccountinfoservice.v1.queryaccountinfoservice.SoapFaultv2;
import com.sprint.integration.interfaces.queryaccountsharinggroupdetails.v1.queryaccountsharinggroupdetails.PlanTypeCodeType;
import com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.BanInfoType;
import com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.CallingApplicationInfoType;
import com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.DeviceInfoType;
import com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.OrderInfoType;
import com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.OrderValidationModuleOrderTypeCodeType;
import com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.QueryAvailableOptionsResponseType;
import com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.QueryAvailableOptionsType;
import com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.ServiceAreaInfoType;
import com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.ServiceAreaZipType;
import com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.QueryAvailablePlansResponseType;
import com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.QueryAvailablePlansType;
import com.sprint.integration.interfaces.querybalanceandcharges.v2.querybalanceandchargesv2.QueryBalanceAndChargesV2RequestType;
import com.sprint.integration.interfaces.querybalanceandcharges.v2.querybalanceandchargesv2.QueryBalanceAndChargesV2ResponseType;
import com.sprint.integration.interfaces.querybalanceandcharges.v2.querybalanceandchargesv2.SubscriberInformationType;
import com.sprint.integration.interfaces.querydeviceinfo.v1.querydeviceinfo.InfoType;
import com.sprint.integration.interfaces.querydeviceinfo.v1.querydeviceinfo.QueryDeviceInfoResponseType;
import com.sprint.integration.interfaces.querydeviceinfo.v1.querydeviceinfo.QueryDeviceInfoType;
import com.sprint.integration.interfaces.queryprepaidallowance.v1.queryprepaidallowance.ApplicationCallingInfoType;
import com.sprint.integration.interfaces.queryprepaidallowance.v1.queryprepaidallowance.QueryPrepaidAllowanceRequestType;
import com.sprint.integration.interfaces.queryprepaidallowance.v1.queryprepaidallowance.QueryPrepaidAllowanceResponseType;
import com.sprint.integration.interfaces.queryprepaidbalanceandthresholdinfo.v1.queryprepaidbalanceandthresholdinfo.QueryPrepaidBalanceAndThresholdInfoRequestType;
import com.sprint.integration.interfaces.queryprepaidbalanceandthresholdinfo.v1.queryprepaidbalanceandthresholdinfo.QueryPrepaidBalanceAndThresholdInfoResponseType;
import com.sprint.integration.interfaces.queryprepaidsubscriberservice.v1.queryprepaidsubscriberservice.SoapFaultV2;
import com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.QuerySubscriberPrepaidInfoResponseType;
import com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.QuerySubscriberPrepaidInfoType;
import com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.SearchCriteriaType;
import com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.SubscriberStatusCodeType;
import com.sprint.integration.interfaces.querysubscriberservices.v1.querysubscriberservices.QuerySubscriberServicesRequest;
import com.sprint.integration.interfaces.querysubscriberservices.v1.querysubscriberservices.QuerySubscriberServicesRequest.Info;
import com.sprint.integration.interfaces.querysubscriberservices.v1.querysubscriberservices.QuerySubscriberServicesResponse;
import com.sprint.integration.interfaces.queryusageservice.v1.queryusageservice.SoapFault;

/**
 * @author emeeras
 * 
 */
@Service
public class MyAccountImpl implements MyAccount {

	@Autowired
	private QueryPrepaidSubscriberProxyService queryPrepaidSubscriberProxyService;

	@Autowired
	private QueryAccountInfoServiceProxyService queryAccountInfoServiceProxyService;

	@Autowired
	private QuerySubscriberInfoProxyService querySubscriberInfoProxyService;

	@Autowired
	private QueryDeviceInfoProxyService queryDeviceInfoProxyService;

	@Autowired
	private QueryUsageProxyService queryUsageProxyService;

	@Autowired
	private QueryPlansAndOptionsProxyService queryPlansAndOptionsProxyService;	
	
	@Value("${QueryPrepaidSubscriberService.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${QueryPrepaidSubscriberService.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${QueryPrepaidSubscriberService.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${QueryPrepaidSubscriberService.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${QueryPrepaidSubscriberService.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${QueryPrepaidSubscriberService.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${QueryPrepaidSubscriberService.trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${QueryPrepaidSubscriberService.trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${QueryPrepaidSubscriberService.trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${QueryPrepaidSubscriberService.trackingHeadFailTimeToLive}")
	private String trackingHeadFailTimeToLive;

	
	@Value("${MyAccount.planType}")
	private String planType;
	
	@Value("${MyAccount.mdn}")
	private String mdn;

	@Value("${MyAccount.nextAnniversaryDateInd}")
	private String nextAnniversaryDateInd;
	
	@Value("${MyAccount.banId}")
	private String banId;
	
	@Value("${MyAccount.equipmentId}")
	private String equipmentId;
	
	@Value("${MyAccount.accessId}")
	private String accessId;
	
	@Value("${MyAccount.pin}")
	private String pin;
	
	@Value("${MyAccount.vendorCode}")
	private String vendorCode;
	
	@Value("${MyAccount.accountType}")
	private String accountType;
	
	@Value("${MyAccount.accountSubType}")
	private String accountSubType;
	
	@Value("${MyAccount.zipCode}")
	private String zipCode;
	
	@Value("${MyAccount.numberSubscribers}")
	private String numberSubscribers;
	
	@Value("${MyAccount.itemId}")
	private String itemId;
	
	private WsMessageHeaderType successHead = new WsMessageHeaderType();
	private Holder<WsMessageHeaderType> successHeader = null;

	private MyAccountResponse accountResponse = null;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyAccountImpl.class);

	public void formatSuccessHeader() throws DatatypeConfigurationException {

		LOGGER.info("Setting headers......");
		
		TrackingMessageHeaderType trackingHead = new TrackingMessageHeaderType();
		trackingHead.setApplicationId(trackingHeadAppId);
		trackingHead.setApplicationUserId(trackingHeadAppUsrId);
		trackingHead.setConsumerId(trackingHeadConsumerId);
		trackingHead.setMessageId(trackingHeadMessageId);
		trackingHead.setConversationId(trackingHeadConversationId);
		trackingHead.setTimeToLive(new BigInteger(trackingHeadTimeToLive));
		GregorianCalendar c = new GregorianCalendar();
		trackingHead.setMessageDateTimeStamp(DatatypeFactory.newInstance()
				.newXMLGregorianCalendar(c));

		successHead.setTrackingMessageHeader(trackingHead);
		successHeader = new Holder<WsMessageHeaderType>(successHead);

		accountResponse = new MyAccountResponse();
		
		LOGGER.info("Headers has been set successfully....");
	}
	
	
	
	@Override
	public SubscriberAccountResponse getAccount(UserContextRequest request) {
		LOGGER.info("Start of MyAccount::getAccount");
		
		String mdn = request.getUserContext().getMsisdn();
		LOGGER.debug("Subscriber MDN: " +mdn);
		
		LOGGER.debug("Calling querySubscriberPrepaidInfo.....");
		
		QuerySubscriberPrepaidInfoResponseType querySubscriberPrepaidInfoResponse = null;
		SubscriberAccountResponse subscriberResponse = new SubscriberAccountResponse();
		

		//Create Request to Backend
		QuerySubscriberPrepaidInfoType querySubscriberPrepaidInfoType = new QuerySubscriberPrepaidInfoType();
		SearchCriteriaType searchCriteriaType = new SearchCriteriaType();
		searchCriteriaType.setMdn(mdn);
		querySubscriberPrepaidInfoType.setInfo(searchCriteriaType);		
		

		try {
			formatSuccessHeader();
			querySubscriberPrepaidInfoResponse = queryPrepaidSubscriberProxyService
					.querySubscriberPrepaidInfo(querySubscriberPrepaidInfoType,
							successHeader);
			
			
			if(querySubscriberPrepaidInfoResponse != null){
				LOGGER.debug("querySubscriberPrepaidInfo has been called successfully ");
				
				subscriberResponse = populateSubscriberAccountResponse(querySubscriberPrepaidInfoResponse);
				
				
			} else {
				LOGGER.debug("querySubscriberPrepaidInfo unsuccessful ");
			}
			
			

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			raiseSnmpAlarm();
		}		
		
		LOGGER.info("End of MyAccount::getAccount");
		return subscriberResponse;
	}
	
	private SubscriberAccountResponse populateSubscriberAccountResponse(QuerySubscriberPrepaidInfoResponseType querySubscriberPrepaidInfoResponse){
		LOGGER.info("Start of populateSubscriberAccountResponse");
		SubscriberAccountResponse subscriberResponse = new SubscriberAccountResponse();
		SubscriberAccount subAccount = new SubscriberAccount();
		
		subAccount.setAccountStatus(querySubscriberPrepaidInfoResponse.getBasicInfo().getAccountStatus().name().toString());
		subAccount.setAccountType(querySubscriberPrepaidInfoResponse.getBasicInfo().getAccountType().toString());
		
		//CONTINUE MAPPING RESPONSE TO DATA MODEL ACCORDING TO FD
		
		LOGGER.info("End of populateSubscriberAccountResponse");
		return subscriberResponse;
		
	}

	@Override
	public AccountBalanceResponse getAccountBalance(UserContextRequest request) {
		LOGGER.info("Start of MyAccount::getAccountBalance");
		
		LOGGER.info("End of MyAccount::getAccountBalance");
		return null;
	}

	@Override
	public SubscriberPlanResponse getPlan(UserContextRequest request) {
		LOGGER.info("Start of MyAccount::getPlan");
		
		LOGGER.info("End of MyAccount::getPlan");
		return null;
	}

	@Override
	public DeviceResponse getDevice(UserContextRequest request) {
		LOGGER.info("Start of MyAccount::getDevice");
		
		LOGGER.info("End of MyAccount::getDevice");
		return null;
	}

	@Override
	public SubscriberPlanResponse getPlanUsage(UserContextRequest request) {
		LOGGER.info("Start of MyAccount::getPlanUsage");
		
		LOGGER.info("End of MyAccount::getPlanUsage");
		return null;
	}
	
	
	

	@Override
	public MyAccountResponse querySubscriberPrepaidInfo(HeaderData data,
			String mdn) {

		LOGGER.info("Calling querySubscriberPrepaidInfo.....");
		
		QuerySubscriberPrepaidInfoResponseType querySubscriberPrepaidInfoResponse = null;

		QuerySubscriberPrepaidInfoType querySubscriberPrepaidInfoType = new QuerySubscriberPrepaidInfoType();
		SearchCriteriaType searchCriteriaType = new SearchCriteriaType();
		searchCriteriaType.setMdn(mdn);

		querySubscriberPrepaidInfoType.setInfo(searchCriteriaType);
		
		LOGGER.debug("MDN for searchcriteria is...."+mdn);

		try {
			formatSuccessHeader();
			querySubscriberPrepaidInfoResponse = queryPrepaidSubscriberProxyService
					.querySubscriberPrepaidInfo(querySubscriberPrepaidInfoType,
							successHeader);
			
			
			if(querySubscriberPrepaidInfoResponse != null){
				if(querySubscriberPrepaidInfoResponse.getPlanInfo().isIsDemoPlanInd() == true){
					transactionLog();
					//LOGGER.info("CALL REDIRECTION ON error.jsp");
				}else{
					LOGGER.info("isIsDemoPlanInd   "+querySubscriberPrepaidInfoResponse.getPlanInfo().isIsDemoPlanInd());
				}
				
				if(querySubscriberPrepaidInfoResponse.getEquipmentInfo().isIsBroadband2GoInd() == true){
					transactionLog();
					//LOGGER.info("ADDPARAMTOUSERPROFILE.............");
				}else{
					LOGGER.info("isIsBroadband2GoInd   "+querySubscriberPrepaidInfoResponse.getEquipmentInfo().isIsBroadband2GoInd());
				}
				
				if(querySubscriberPrepaidInfoResponse.getBasicInfo().getSubscriberStatus() == SubscriberStatusCodeType.A ||
						querySubscriberPrepaidInfoResponse.getBasicInfo().isIsInterruptedStatusInd() == true && "Monthly".equalsIgnoreCase(planType)){
					transactionLog();
				}else{
					LOGGER.info("SubscriberStatus   "+querySubscriberPrepaidInfoResponse.getBasicInfo().getSubscriberStatus()
							+ "   IsInterruptedStatus  "+querySubscriberPrepaidInfoResponse.getBasicInfo().isIsInterruptedStatusInd());
					//LOGGER.info("ADDPARAMTOUSERPROFILE.............");
				}
				
				if(querySubscriberPrepaidInfoResponse.getBasicInfo().getSubscriberStatus() == SubscriberStatusCodeType.S ||
						querySubscriberPrepaidInfoResponse.getBasicInfo().isIsInterruptedStatusInd() == true ){
					transactionLog();
				}else{
					LOGGER.info("SubscriberStatus   "+querySubscriberPrepaidInfoResponse.getBasicInfo().getSubscriberStatus()
							+ "   IsInterruptedStatus  "+querySubscriberPrepaidInfoResponse.getBasicInfo().isIsInterruptedStatusInd());
					//LOGGER.info("ADDPARAMTOUSERPROFILE.............");
				}
			}
			accountResponse
					.setPrepaidInfoResponseType(querySubscriberPrepaidInfoResponse);
			
			LOGGER.info("querySubscriberPrepaidInfo has been called successfully ");

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			raiseSnmpAlarm();
		}

		return accountResponse;
	}

	@Override
	public MyAccountResponse queryAccountBasicInfo(HeaderData data, String mdn) {
		
		LOGGER.info("Calling queryAccountBasicInfo.....");

		QueryAccountBasicInfoResponse accountBasicInfoResponse = null;
		QueryAccountBasicInfo queryAccountBasicInfo = new QueryAccountBasicInfo();

		com.sprint.integration.interfaces.queryaccountbasicinfo.v1.queryaccountbasicinfo.SearchCriteria searchCriteriaType = new com.sprint.integration.interfaces.queryaccountbasicinfo.v1.queryaccountbasicinfo.SearchCriteria();

		searchCriteriaType.setMdn(mdn);
		
		LOGGER.debug("MDN for searchcriteria is ....."+mdn);

		queryAccountBasicInfo.setInfo(searchCriteriaType);

		try {
			formatSuccessHeader();
			accountBasicInfoResponse = queryAccountInfoServiceProxyService
					.queryAccountBasicInfo(queryAccountBasicInfo, successHeader);

			accountResponse
					.setQueryAccountBasicInfoResponse(accountBasicInfoResponse);
			LOGGER.info("queryAccountBasicInfo has been called successfully ");

		} catch (DatatypeConfigurationException e) {
			LOGGER.error(e.getMessage());
			raiseSnmpAlarm();
		} catch (SoapFaultv2 e) {
			LOGGER.error(e.getMessage());
			raiseSnmpAlarm();
		}

		return accountResponse;
	}

	@Override
	public MyAccountResponse queryBalanceAndChargesV2(HeaderData data,
			String mdn) {

		LOGGER.info("Calling queryBalanceAndChargesV2.....");
		
		QueryBalanceAndChargesV2ResponseType queryBalanceAndChargesV2ResponseType = null;

		QueryBalanceAndChargesV2RequestType queryBalanceAndChargesV2Request = new QueryBalanceAndChargesV2RequestType();

		SubscriberInformationType subscriberInformationType = new SubscriberInformationType();

		try {

			subscriberInformationType.setMobileDirectoryNumber(mdn);

			queryBalanceAndChargesV2Request
					.setSubscriberInformation(subscriberInformationType);
			queryBalanceAndChargesV2Request
					.setNextAnniversaryDateInd(new Boolean(nextAnniversaryDateInd));

			LOGGER.debug("MDN for searchcriteria is ....."+mdn);
			LOGGER.debug("nextAnniversaryDateInd for searchcriteria is ....."+nextAnniversaryDateInd);
			
			formatSuccessHeader();
			queryBalanceAndChargesV2ResponseType = queryPrepaidSubscriberProxyService
					.queryBalanceAndChargesV2(queryBalanceAndChargesV2Request,
							successHeader);

			accountResponse
					.setQueryBalanceAndChargesV2ResponseType(queryBalanceAndChargesV2ResponseType);
			
			LOGGER.info("queryBalanceAndChargesV2 has been called successfully ");
			
		} catch (SoapFaultV2 e) {
			LOGGER.error(e.getMessage());
			raiseSnmpAlarm();
		} catch (DatatypeConfigurationException e) {
			LOGGER.error(e.getMessage());
			raiseSnmpAlarm();
		}

		return accountResponse;
	}

	@Override
	public MyAccountResponse queryPrepaidAllowance(HeaderData data, String mdn) {
		
		LOGGER.info("Calling queryPrepaidAllowance.....");

		QueryPrepaidAllowanceResponseType queryPrepaidAllowanceResponseType = null;

		QueryPrepaidAllowanceRequestType queryPrepaidAllowanceRequestType = new QueryPrepaidAllowanceRequestType();

		com.sprint.integration.interfaces.queryprepaidallowance.v1.queryprepaidallowance.SearchCriteriaType searchCriteriaType = new com.sprint.integration.interfaces.queryprepaidallowance.v1.queryprepaidallowance.SearchCriteriaType();

		ApplicationCallingInfoType applicationCallingInfoType = new ApplicationCallingInfoType();

		try {

			searchCriteriaType.setMdn(mdn);
			queryPrepaidAllowanceRequestType
					.setSubscriberInfo(searchCriteriaType);

			applicationCallingInfoType.setAccessId(accessId);
			
			LOGGER.debug("MDN for searchcriteria is ....."+mdn);
			LOGGER.debug("AccessId for searchcriteria is ....."+accessId);

			queryPrepaidAllowanceRequestType
					.setApplicationCallingInfo(applicationCallingInfoType);

			formatSuccessHeader();
			queryPrepaidAllowanceResponseType = queryPrepaidSubscriberProxyService
					.queryPrepaidAllowance(queryPrepaidAllowanceRequestType,
							successHeader);

			accountResponse
					.setQueryPrepaidAllowanceResponseType(queryPrepaidAllowanceResponseType);
			
			LOGGER.info("queryPrepaidAllowance has been called successfully ");
			
		} catch (DatatypeConfigurationException e) {
			LOGGER.error(e.getMessage());
			raiseSnmpAlarm();
		} catch (SoapFaultV2 e) {
			LOGGER.error(e.getMessage());
			raiseSnmpAlarm();
		}

		return accountResponse;
	}

	@Override
	public MyAccountResponse querySubscriberServices(HeaderData data, String mdn) {
		
		LOGGER.info("Calling querySubscriberServices.....");

		QuerySubscriberServicesResponse querySubscriberServicesResponse = null;

		QuerySubscriberServicesRequest querySubscriberServicesRequest = new QuerySubscriberServicesRequest();

		QuerySubscriberServicesRequest.Info info = new Info();

		info.setBanId(banId);

		querySubscriberServicesRequest.setInfo(info);
		
		LOGGER.debug("BanId for searchcriteria is ....."+banId);

		try {
			formatSuccessHeader();
			querySubscriberServicesResponse = querySubscriberInfoProxyService
					.querySubscriberServices(querySubscriberServicesRequest,
							successHeader);

			accountResponse
					.setQuerySubscriberServicesResponse(querySubscriberServicesResponse);
			
			LOGGER.info("querySubscriberServices has been called successfully ");

		} catch (DatatypeConfigurationException e) {
			LOGGER.error(e.getMessage());
			raiseSnmpAlarm();
		} catch (com.sprint.integration.interfaces.querysubscriberinfoservice.v1.querysubscriberinfoservice.SoapFaultV2 e) {
			LOGGER.error(e.getMessage());
			raiseSnmpAlarm();
		}

		return accountResponse;
	}

	@Override
	public MyAccountResponse queryDeviceInfo(HeaderData data, String mdn) {

		LOGGER.info("Calling queryDeviceInfo.....");
		
		QueryDeviceInfoResponseType deviceInfoResponseType = null;
		QueryDeviceInfoType deviceInfoType = new QueryDeviceInfoType();

		InfoType infoType = new InfoType();

		infoType.setEquipmentId(equipmentId);

		deviceInfoType.setInfo(infoType);

		LOGGER.debug("equipmentId for searchcriteria is ....."+equipmentId);
		try {
			formatSuccessHeader();

			deviceInfoResponseType = queryDeviceInfoProxyService.queryDeviceInfo(
					deviceInfoType, successHeader);

			accountResponse
					.setQueryDeviceInfoResponseType(deviceInfoResponseType);
			
			LOGGER.info("queryDeviceInfo has been called successfully ");

		} catch (DatatypeConfigurationException e) {
			LOGGER.error(e.getMessage());
			raiseSnmpAlarm();
		} catch (Faultmessage2 e) {
			LOGGER.error(e.getMessage());
			raiseSnmpAlarm();
		}
		return accountResponse;
	}

	@Override
	public MyAccountResponse queryPrepaidBalanceAndThresholdInfo(
			HeaderData data, String mdn) {
		
		LOGGER.info("Calling queryPrepaidBalanceAndThresholdInfo.....");
		
		QueryPrepaidBalanceAndThresholdInfoResponseType queryPrepaidBalanceAndThresholdInfoResponseType = null;
		
		QueryPrepaidBalanceAndThresholdInfoRequestType requestType = new QueryPrepaidBalanceAndThresholdInfoRequestType();
		
		com.sprint.integration.interfaces.queryprepaidbalanceandthresholdinfo.v1.queryprepaidbalanceandthresholdinfo.SearchCriteriaType searchCriteriaType = new com.sprint.integration.interfaces.queryprepaidbalanceandthresholdinfo.v1.queryprepaidbalanceandthresholdinfo.SearchCriteriaType();
		
		searchCriteriaType.setMdn(mdn);
		
		requestType.setSubscriberInfo(searchCriteriaType);
		
		com.sprint.integration.interfaces.queryprepaidbalanceandthresholdinfo.v1.queryprepaidbalanceandthresholdinfo.ApplicationCallingInfoType applicationCallingInfoType = new com.sprint.integration.interfaces.queryprepaidbalanceandthresholdinfo.v1.queryprepaidbalanceandthresholdinfo.ApplicationCallingInfoType();
		applicationCallingInfoType.setAccessId(accessId);
		
		requestType.setApplicationCallingInfo(applicationCallingInfoType);
		
		LOGGER.debug("MDN for searchcriteria is ....."+mdn);
		LOGGER.debug("accessId for searchcriteria is ....."+accessId);
		
		try {
			formatSuccessHeader();
			
			queryPrepaidBalanceAndThresholdInfoResponseType = queryUsageProxyService.queryPrepaidBalanceAndThresholdInfo(requestType, successHeader);
			
			accountResponse.setQueryPrepaidBalanceAndThresholdInfoResponseType(queryPrepaidBalanceAndThresholdInfoResponseType);
			
			LOGGER.info("queryPrepaidBalanceAndThresholdInfo has been called successfully ");
			
		} catch (DatatypeConfigurationException e) {
			LOGGER.error(e.getMessage());
			raiseSnmpAlarm();
		} catch (SoapFault e) {
			LOGGER.error(e.getMessage());
			raiseSnmpAlarm();
		}
		
		
		return accountResponse;
	}

	@Override
	public MyAccountResponse queryAvailableOptions(HeaderData data, String mdn) {
		
		LOGGER.info("Calling queryAvailableOptions.....");
		
		QueryAvailableOptionsResponseType queryAvailableOptionsResponseType = null;
		QueryAvailableOptionsType queryAvailableOptionsType = new QueryAvailableOptionsType();
		
		CallingApplicationInfoType callingApplicationInfoType = new CallingApplicationInfoType();
		
		callingApplicationInfoType.setPin(pin);
		callingApplicationInfoType.setPin(vendorCode);
		
		queryAvailableOptionsType.setCallingApplicationInfo(callingApplicationInfoType);
		
		com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.SearchCriteriaType searchCriteriaType = new com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.SearchCriteriaType();
		
		BanInfoType banInfoType = new BanInfoType();
		banInfoType.setBan(banId);
		banInfoType.setAccountType(accountType);
		banInfoType.setAccountSubType(accountSubType);
		
		
		ServiceAreaInfoType serviceAreaInfoType = new ServiceAreaInfoType();		
		ServiceAreaZipType areaZipType = new ServiceAreaZipType();		
		areaZipType.setZipCode(zipCode);		
		serviceAreaInfoType.setServiceAreaZip(areaZipType);
		
		OrderInfoType orderInfoType = new OrderInfoType();		
		orderInfoType.setOrderType(OrderValidationModuleOrderTypeCodeType.NEW);		
		orderInfoType.setNumberSubscribers(new BigInteger(numberSubscribers));

		DeviceInfoType deviceInfoType = new DeviceInfoType();
		deviceInfoType.setItemId(itemId);
		
		searchCriteriaType.setBanInfo(banInfoType);
		searchCriteriaType.setServiceAreaInfo(serviceAreaInfoType);
		searchCriteriaType.setOrderInfo(orderInfoType);
		searchCriteriaType.setDeviceInfo(deviceInfoType);
		
		// Setting searchCriteria....
		queryAvailableOptionsType.setSearchCriteria(searchCriteriaType);
		
		LOGGER.debug("pin for searchcriteria is ....."+pin);
		LOGGER.debug("vendorCode for searchcriteria is ....."+vendorCode);
		LOGGER.debug("banId for searchcriteria is ....."+banId);
		LOGGER.debug("accountType for searchcriteria is ....."+accountType);
		LOGGER.debug("accountSubType for searchcriteria is ....."+accountSubType);
		LOGGER.debug("numberSubscribers for searchcriteria is ....."+numberSubscribers);
		LOGGER.debug("itemId for searchcriteria is ....."+itemId);
		
		
		try {
			formatSuccessHeader();
			  
			queryAvailableOptionsResponseType = queryPlansAndOptionsProxyService.queryAvailableOptions(queryAvailableOptionsType, successHeader);
			
			accountResponse.setQueryAvailableOptionsResponseType(queryAvailableOptionsResponseType);
			
			LOGGER.info("queryAvailableOptions has been called successfully ");
			
		} catch (DatatypeConfigurationException e) {
			LOGGER.error(e.getMessage());
			raiseSnmpAlarm();
		} catch (Faultmessage e) {
			LOGGER.error(e.getMessage());
			raiseSnmpAlarm();
		}
		
		return accountResponse;
	}

	@Override
	public MyAccountResponse queryAvailablePlans(HeaderData data, String mdn) {
		
		LOGGER.info("Calling queryAvailablePlans.....");
		
		QueryAvailablePlansResponseType queryAvailablePlansResponseType = null;
		
		QueryAvailablePlansType queryAvailablePlansType = new QueryAvailablePlansType();
		
		com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.CallingApplicationInfoType callingApplicationInfoType = new com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.CallingApplicationInfoType();
		
		callingApplicationInfoType.setPin(pin);
		callingApplicationInfoType.setVendorCode(vendorCode);
		
		com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.SearchCriteriaType searchCriteriaType = new com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.SearchCriteriaType();
		com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.ServiceAreaInfoType serviceAreaInfoType = new com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.ServiceAreaInfoType();
		
		com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.ServiceAreaZipType areaZipType = new com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.ServiceAreaZipType();
		areaZipType.setZipCode(zipCode);		
		serviceAreaInfoType.setServiceAreaZip(areaZipType);
		
		searchCriteriaType.setServiceAreaInfo(serviceAreaInfoType);
		
		com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.DeviceInfoType  deviceInfoType = new com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.DeviceInfoType();
		deviceInfoType.setItemId(itemId);
		
		searchCriteriaType.setDeviceInfo(deviceInfoType);
		
		com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.OrderInfoType orderInfoType = new com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.OrderInfoType();
		orderInfoType.setOrderType(com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.OrderValidationModuleOrderTypeCodeType.NEW);
		
		searchCriteriaType.setOrderInfo(orderInfoType);
		
		com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.BanInfoType banInfoType = new com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.BanInfoType();
		banInfoType.setBan(banId);
		
		searchCriteriaType.setBanInfo(banInfoType);
		
		queryAvailablePlansType.setCallingApplicationInfo(callingApplicationInfoType);
		queryAvailablePlansType.setSearchCriteria(searchCriteriaType);
		
		LOGGER.debug("pin for searchcriteria is ....."+pin);
		LOGGER.debug("vendorCode for searchcriteria is ....."+vendorCode);
		LOGGER.debug("banId for searchcriteria is ....."+banId);
		/*LOGGER.debug("accountType for searchcriteria is ....."+accountType);
		LOGGER.debug("accountSubType for searchcriteria is ....."+accountSubType);
		LOGGER.debug("numberSubscribers for searchcriteria is ....."+numberSubscribers);
		LOGGER.debug("itemId for searchcriteria is ....."+itemId);*/
		
		try {
			formatSuccessHeader();
			queryAvailablePlansResponseType = queryPlansAndOptionsProxyService.queryAvailablePlans(queryAvailablePlansType, successHeader);
			
			accountResponse.setQueryAvailablePlansResponseType(queryAvailablePlansResponseType);
			
			LOGGER.info("queryAvailablePlans has been called successfully ");
			
		} catch (DatatypeConfigurationException e) {
			LOGGER.error(e.getMessage());
			raiseSnmpAlarm();
		} catch (Faultmessage e) {
			LOGGER.error(e.getMessage());
			raiseSnmpAlarm();
		}
		
		return accountResponse;
	}
	
	private void raiseSnmpAlarm() {
		LOGGER.debug("Raise Alarm to be implemented");

	}

	@Override
	public MyAccountResponse createPrepaidPaymentSession(HeaderData data,
			String mdn) {
		LOGGER.info("createPrepaidPaymentSession : PLACEHOLDER");
		return null;
	}
	
	public void transactionLog() {
		LOGGER.debug("Transaction Log to be implemented");
	}

	


}
