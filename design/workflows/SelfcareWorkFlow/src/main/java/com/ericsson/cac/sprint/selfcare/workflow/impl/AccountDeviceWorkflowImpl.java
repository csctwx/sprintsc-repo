package com.ericsson.cac.sprint.selfcare.workflow.impl;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.Holder;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ericsson.cac.sprint.adapters.AppleDeviceManagementProxyService;
import com.ericsson.cac.sprint.adapters.QueryCsaProxyService;
import com.ericsson.cac.sprint.adapters.QueryDeviceInfoProxyService;
import com.ericsson.cac.sprint.adapters.QueryPlansAndOptionsProxyService;
import com.ericsson.cac.sprint.adapters.QueryPrepaidSubscriberProxyService;
import com.ericsson.cac.sprint.adapters.QueryProvisioningInfoProxyService;
import com.ericsson.cac.sprint.adapters.QueryResourceInfoProxyService;
import com.ericsson.cac.sprint.adapters.QuerySubscriberInfoProxyService;
import com.ericsson.cac.sprint.adapters.SubscriberManagementProxyService;
import com.ericsson.cac.sprint.selfcare.workflow.AccountDeviceWorkflow;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.Address;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.ChangeNumberResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.CoverageAreaRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.CoverageAreaResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.Device;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.DeviceInsurance;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.DeviceInsuranceResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.DeviceResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.ESNSwapEligibleRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.ESNSwapEligibleResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.ESNSwapPrepareRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.ESNSwapPrepareResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessage;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessageResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SwapEsnResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.cac.sprint.selfcare.workflow.util.AlarmUtil;
import com.ericsson.cac.sprint.selfcare.workflow.util.CommonUtil;
import com.ericsson.cac.sprint.selfcare.workflow.util.HeaderHandler;
import com.ericsson.cac.sprint.selfcare.workflow.util.Service;
import com.sprint.integration.common.errordetailsv2.ProviderErrorType;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.queryplansandoptionsservice.v1.queryplansandoptionsservice.Faultmessage;
import com.sprint.integration.eai.services.subscribermanagementservice.v1.subscribermanagementservice.FaultMessage;
import com.sprint.integration.interfaces.managesubscriberservices.v1.managesubscriberservices.AllocationMethodType;
import com.sprint.integration.interfaces.managesubscriberservices.v1.managesubscriberservices.ManageSubscriberServicesResponseType;
import com.sprint.integration.interfaces.managesubscriberservices.v1.managesubscriberservices.ManageSubscriberServicesType;
import com.sprint.integration.interfaces.managesubscriberservices.v1.managesubscriberservices.PortedNumberIndicatorType;
import com.sprint.integration.interfaces.managesubscriberservices.v1.managesubscriberservices.ResourceInfoType;
import com.sprint.integration.interfaces.managesubscriberservices.v1.managesubscriberservices.ResourceMdnInfoType;
import com.sprint.integration.interfaces.managesubscriberservices.v1.managesubscriberservices.ResourceNpaNxxInfoType;
import com.sprint.integration.interfaces.queryappledevicestatus.v1.queryappledevicestatus.QueryAppleDeviceStatusRequestType;
import com.sprint.integration.interfaces.queryappledevicestatus.v1.queryappledevicestatus.QueryAppleDeviceStatusResponseType;
import com.sprint.integration.interfaces.querydeviceinfo.v1.querydeviceinfo.InfoType;
import com.sprint.integration.interfaces.querydeviceinfo.v1.querydeviceinfo.QueryDeviceInfoResponseType;
import com.sprint.integration.interfaces.querydeviceinfo.v1.querydeviceinfo.QueryDeviceInfoType;
import com.sprint.integration.interfaces.queryprogramminginstructions.v1.queryprogramminginstructions.QueryProgrammingInstructionsRequestType;
import com.sprint.integration.interfaces.queryprogramminginstructions.v1.queryprogramminginstructions.QueryProgrammingInstructionsResponseType;
import com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.QuerySubscriberPrepaidInfoResponseType;
import com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.QuerySubscriberPrepaidInfoType;
import com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.SearchCriteriaType;
import com.sprint.integration.interfaces.swapcsa.v1.swapcsa.MarketInfoType;
import com.sprint.integration.interfaces.swapcsa.v1.swapcsa.MemoInfoType;
import com.sprint.integration.interfaces.swapcsa.v1.swapcsa.NetworkResonInfoType;
import com.sprint.integration.interfaces.swapcsa.v1.swapcsa.NetworkTypeCodeType;
import com.sprint.integration.interfaces.swapcsa.v1.swapcsa.NpaNxxInfoType;
import com.sprint.integration.interfaces.swapcsa.v1.swapcsa.SwapCsaRequestType;
import com.sprint.integration.interfaces.swapcsa.v1.swapcsa.SwapCsaResponseType;
import com.sprint.integration.interfaces.swapsubscriberequipment.v1.swapsubscriberequipment.EquipmentActivityCodeType;
import com.sprint.integration.interfaces.swapsubscriberequipment.v1.swapsubscriberequipment.EquipmentListType;
import com.sprint.integration.interfaces.swapsubscriberequipment.v1.swapsubscriberequipment.SwapSubscriberEquipmentResponseType;
import com.sprint.integration.interfaces.swapsubscriberequipment.v1.swapsubscriberequipment.SwapSubscriberEquipmentType;
import com.sprint.integration.interfaces.validateplansandoptions.v1.validateplansandoptions.ActionType;
import com.sprint.integration.interfaces.validateplansandoptions.v1.validateplansandoptions.CallingApplicationInfoType;
import com.sprint.integration.interfaces.validateplansandoptions.v1.validateplansandoptions.OrderInfoType;
import com.sprint.integration.interfaces.validateplansandoptions.v1.validateplansandoptions.ServiceAreaInfoType;
import com.sprint.integration.interfaces.validateplansandoptions.v1.validateplansandoptions.TransactionTypeCodeType;
import com.sprint.integration.interfaces.validateplansandoptions.v1.validateplansandoptions.ValidatePlansAndOptionsResponseType;
import com.sprint.integration.interfaces.validateplansandoptions.v1.validateplansandoptions.ValidatePlansAndOptionsType;


@Component
public class AccountDeviceWorkflowImpl implements AccountDeviceWorkflow {

	/** Logger class info */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AccountDeviceWorkflowImpl.class);

	/**   */
	@Autowired
	private QueryPrepaidSubscriberProxyService queryPrepaidSubscriberProxyService;

	/**   */
	@Autowired
	private QueryDeviceInfoProxyService queryDeviceInfoProxyService;

	@Autowired
	private AppleDeviceManagementProxyService appleDeviceManagementProxyService;

	@Autowired
	private HeaderHandler headerHandler;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private QueryCsaProxyService queryCsaProxyService;

	@Autowired
	private QueryResourceInfoProxyService queryResourceInfoProxyService;

	@Autowired
	private SubscriberManagementProxyService subscriberManagementProxyService;

	@Autowired
	private QueryProvisioningInfoProxyService queryProvisioningInfoProxyService;

	@Autowired
	private QueryPlansAndOptionsProxyService queryPlansAndOptionsProxyService;
	
	@Autowired
	
	private QuerySubscriberInfoProxyService querySubscriberInfoProxyService;

	@Autowired
	AccountWorkflowImpl accountWorkflowImpl;
	
	@Value("${changeNumber.swapCsa.memo.memoSource}") 
	 private String memoSource; 
	  
	 @Value("${changeNumber.swapCsa.npaNxx.numberLocation}") 
	 private String numberLocation; 
	  
	 @Value("${changeNumber.swapCsa.memo.userText}") 
	 private String userText; 
	  
	 @Value("${changeNumber.swapCsa.networkReson.reasonCode}") 
	 private String reasonCode; 
	  
	 @Value("${changeNumber.manageSubscribe.resource.action}") 
	 private String action; 
	  
	 @Value("${changeNumber.manageSubscribe.resource.resourceType}") 
	 private String resourceType; 
	  
	 @Value("${changeNumber.manageSubscribe.resource.reasonCode}") 
	 private String resourceReasonCode; 
	  
	 @Value("${changeNumber.manageSubscribe.numberLocation}") 
	 private String resourceNumberLocation; 
	  
	 @Value("${changeNumber.pin}") 
	 private String pin; 
	  
	 @Value("${changeNumber.vendorCode}") 
	 private String vendorCode; 
	  
	 @Value("${changeNumber.manageSubscribe.additionalSocInd}") 
	 private String additionalSocInd;
	

	@Override
	public DeviceResponse getDevice(UserContextRequest request) {

		LOGGER.info("Entering getDevice workflow method");
		QuerySubscriberPrepaidInfoResponseType querySubscriberPrepaidInfoResponse = new QuerySubscriberPrepaidInfoResponseType();
		QuerySubscriberPrepaidInfoType querySubscriberPrepaidInfoRequest = new QuerySubscriberPrepaidInfoType();
		QueryDeviceInfoResponseType queryDeviceInfoResponse = new QueryDeviceInfoResponseType();
		QueryDeviceInfoType queryDeviceInfoRequest = new QueryDeviceInfoType();
		Device device = new Device();
		DeviceInsurance deviceInsurance = new DeviceInsurance();
		DeviceResponse deviceResponse = new DeviceResponse();
		
		com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.SearchCriteriaType info = new com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.SearchCriteriaType();

		InfoType infoType = new InfoType();

		try {
			AlarmUtil.clearSnmpAlarm();
			LOGGER.debug("request.getUserContext().getSubscriberId()");
			Holder<WsMessageHeaderType> header = headerHandler
					.getHeader(Service.QUERY_PREPAID_SUBSCRIBER_SERVICE);
			info.setSubscriberId(request.getUserContext().getSubscriberId());
			querySubscriberPrepaidInfoRequest.setInfo(info);
			querySubscriberPrepaidInfoResponse = queryPrepaidSubscriberProxyService
					.querySubscriberPrepaidInfo(
							querySubscriberPrepaidInfoRequest, header);

			infoType.setBrandCode(querySubscriberPrepaidInfoResponse
					.getPrepaidInfo().getBrandCode());
			infoType.setEquipmentId(querySubscriberPrepaidInfoResponse
					.getEquipmentInfo().getEsn());

			queryDeviceInfoRequest.setInfo(infoType);
			header = headerHandler.getHeader(Service.QUERY_DEVICE_INFO_SERVICE);
			queryDeviceInfoResponse = queryDeviceInfoProxyService
					.queryDeviceInfo(queryDeviceInfoRequest, header);
			
			device.setMsisdn(querySubscriberPrepaidInfoResponse
					.getResourceInfo().getMdn());
			device.setMeid(queryDeviceInfoResponse.getEquipmentInfo().getImei());
			device.setName(queryDeviceInfoResponse.getEquipmentInfo()
					.getMarketingName());
			device.setBrand(queryDeviceInfoResponse.getEquipmentInfo()
					.getModelNumber());
			device.setModel(queryDeviceInfoResponse.getEquipmentInfo()
					.getPhoneModel());
			
			deviceResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(false, ""));
			
			LOGGER.debug("Msisdn" + device.getMsisdn());
			LOGGER.debug("setMeid" + device.getMeid());
			LOGGER.debug("Name" + device.getName());
			LOGGER.debug("Brand" + device.getBrand());
			LOGGER.debug("Model" + device.getModel());

			device.setInsurance(deviceInsurance);
			if (queryDeviceInfoResponse.getEquipmentInfo()
					.getIsDeviceInsuranceEligibleInd().equals("Y")) {
				device.getInsurance().setInsured(true);
			} else {
				device.getInsurance().setInsured(false);
			}

			// device.getInsurance().setInsured(Boolean.valueOf(queryDeviceInfoResponse.getEquipmentInfo().getIsDeviceInsuranceEligibleInd()));

			deviceResponse.setDevice(device);
			LOGGER.debug("Device Object" + device.toString());
			return deviceResponse;

		} catch (Exception e) {
			deviceResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(true, ""));
			AlarmUtil.raiseSnmpAlarm();
			LOGGER.error(e.getMessage(), e);

		}

		LOGGER.info("Leaving getDevice workflow method");
		return deviceResponse;
	}

	/*
	 * This method is to check whether the device is eligible for insurance or
	 * not.
	 * 
	 * @see com.ericsson.cac.sprint.selfcare.workflow.AccountDeviceWorkflow#
	 * getDeviceInsurance
	 * (com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest)
	 */
	@Override
	public DeviceInsuranceResponse getDeviceInsurance(UserContextRequest request) {

		LOGGER.info("Entering getDeviceInsurance workflow method");
		DeviceInsuranceResponse deviceInsuranceResponse = new DeviceInsuranceResponse();
		QuerySubscriberPrepaidInfoResponseType querySubscriberPrepaidInfoResponse = null;
		// String subscriberId = request.getUserContext().getSubscriberId();
		try {
			AlarmUtil.clearSnmpAlarm();

			querySubscriberPrepaidInfoResponse = commonUtil
					.getSubscriberPrepaidInfo(request.getUserContext()
							.getSubscriberId());
			DeviceInsurance deviceInsurance = new DeviceInsurance();
			deviceInsurance.setInsured(querySubscriberPrepaidInfoResponse
					.getEquipmentInfo().isHasDeviceInsuranceInd());
			LOGGER.debug("setInsured" + deviceInsurance.getInsured());
			
			deviceInsuranceResponse.setDeviceInsurance(deviceInsurance);
			
			
			deviceInsuranceResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(false, ""));
			
		} catch (Exception e) {
			deviceInsuranceResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(true, ""));
			AlarmUtil.raiseSnmpAlarm();
			LOGGER.error(e.getMessage());

		}

		

		LOGGER.info("Leaving getDeviceInsurance workflow method");
		return deviceInsuranceResponse;
	}

	@Override
	public StatusMessageResponse suspendAccount(UserContextRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChangeNumberResponse changeNumber(Address request1 ,UserContextRequest request) {
		String zipCodeRequest = request1.getZipCode();
		LOGGER.debug("Zipcode form request : "+zipCodeRequest);
		LOGGER.debug("UserContext form request : "+request.getUserContext().getMsisdn());
		// Created checkcoverage area request // Step 0
		CoverageAreaRequest coverageAreaRequest = new CoverageAreaRequest();
		CoverageAreaResponse coverageAreaResponse = new CoverageAreaResponse();
		coverageAreaRequest.setZipCode(zipCodeRequest);
		try {
			coverageAreaResponse = accountWorkflowImpl.checkCoverageArea(coverageAreaRequest);
		} catch (Exception e) {
			AlarmUtil.raiseSnmpAlarm();
			LOGGER.error("coverageAreaResponse Failed", e);
		}

		ChangeNumberResponse changeNumberResponse = new ChangeNumberResponse();
		// Getting from the prepaidInfoResponseType Step 1
		QuerySubscriberPrepaidInfoResponseType prepaidInfoResponseType = new QuerySubscriberPrepaidInfoResponseType();
		QuerySubscriberPrepaidInfoType querySubscriberPrepaidInfoType = new QuerySubscriberPrepaidInfoType();
		SearchCriteriaType searchCriteriaType = new SearchCriteriaType();
		searchCriteriaType.setMdn(request.getUserContext().getMsisdn());
		querySubscriberPrepaidInfoType.setInfo(searchCriteriaType);	
		
		try {		
			LOGGER.debug("prepaidInfoResponseType : Started");
			Holder<WsMessageHeaderType> header = headerHandler.getHeader(Service.QUERY_PREPAID_SUBSCRIBER_SERVICE);
			prepaidInfoResponseType = queryPrepaidSubscriberProxyService.querySubscriberPrepaidInfo(querySubscriberPrepaidInfoType, header);
			prepaidInfoResponseType.getDetailInfo().getCsa();

			prepaidInfoResponseType.getAddressInfo().getZipCode();
			changeNumberResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(false, ""));
			changeNumberResponse.setStatusMessage(changeNumberResponse.getStatusMessageResponse().getStatusMessage());

			LOGGER.debug("prepaidInfoResponseType : Subscriber type"+prepaidInfoResponseType.getBasicInfo().getSubscriberId());
		} catch (Exception e2) {
            LOGGER.error(e2.getMessage(), e2);
			changeNumberResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(true, ""));
			changeNumberResponse.setStatusMessage(changeNumberResponse.getStatusMessageResponse().getStatusMessage());
			AlarmUtil.raiseSnmpAlarm();
		}
		LOGGER.debug("prepaidInfoResponseType csa : "+prepaidInfoResponseType.getDetailInfo().getCsa());

		if (!coverageAreaResponse.getCsa().equals(prepaidInfoResponseType.getDetailInfo().getCsa())) {
			LOGGER.debug("swapcsa : started");
			try {
				Holder<WsMessageHeaderType> header = headerHandler.getHeader(Service.SUBSCRIBER_MANAGEMENT_SERVICE);
				SwapCsaRequestType swapCsaRequestType = new SwapCsaRequestType();
				MarketInfoType marketInfoType = new MarketInfoType();
				MemoInfoType memoInfoType = new MemoInfoType();
				NpaNxxInfoType npaNxxInfoType = new NpaNxxInfoType();
				NetworkResonInfoType networkResonInfoType = new NetworkResonInfoType();

				// swapCSA.subscriberInfo.mdn = request.msisdn
				com.sprint.integration.interfaces.swapcsa.v1.swapcsa.SubscriberInfoType subscriberInfoType = new com.sprint.integration.interfaces.swapcsa.v1.swapcsa.SubscriberInfoType();
				subscriberInfoType.setMdn(request.getUserContext().getMsisdn());
				swapCsaRequestType.setSubscriberInfo(subscriberInfoType);

				// swapCSA.networkIndicator
				networkResonInfoType.setNetworkIndicator(NetworkTypeCodeType.C);
				marketInfoType.setNetworkReasonInfo(networkResonInfoType);
				swapCsaRequestType.setMarketInfo(marketInfoType);

				// swapCSA.MemoSource
				memoInfoType.setMemoSource(memoSource.trim());
				swapCsaRequestType.setMemoInfo(memoInfoType);

				// swapCSA.numberLocation
				npaNxxInfoType.setNumberLocation(numberLocation.trim());
				marketInfoType.setNapNxxNlInfo(npaNxxInfoType);
				swapCsaRequestType.setMarketInfo(marketInfoType);

				// swapCSA.userText
				memoInfoType.setUserText(userText.trim());
				swapCsaRequestType.setMemoInfo(memoInfoType);

				// swapCSA.ReasonCode
				networkResonInfoType.setReasonCode(reasonCode.trim());
				marketInfoType.setNetworkReasonInfo(networkResonInfoType);
				swapCsaRequestType.setMarketInfo(marketInfoType);

				// swapCSA.Npa and swap.ngp
				npaNxxInfoType.setNpa(coverageAreaResponse.getNpa());
				marketInfoType.setNapNxxNlInfo(npaNxxInfoType);
				swapCsaRequestType.setMarketInfo(marketInfoType);

				npaNxxInfoType.setNgp(coverageAreaResponse.getCsa());
				marketInfoType.setNapNxxNlInfo(npaNxxInfoType);
				swapCsaRequestType.setMarketInfo(marketInfoType);
						
				SwapCsaResponseType swapCsaResponseType = subscriberManagementProxyService.swapCsa(swapCsaRequestType, header);
				LOGGER.debug("changeNumberResponse : "+changeNumberResponse);
				changeNumberResponse.setMsisdn(swapCsaResponseType.getSubscriberResponseInfo().getMdn());
				changeNumberResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(false, ""));
				changeNumberResponse.setStatusMessage(changeNumberResponse.getStatusMessageResponse().getStatusMessage());
				
			} catch (FaultMessage e) {
				LOGGER.error("swapCsaResponseType FaultMessage NPA/CSA: " , e);
				AlarmUtil.raiseSnmpAlarm();
				changeNumberResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(true, ""));
				changeNumberResponse.setStatusMessage(changeNumberResponse.getStatusMessageResponse().getStatusMessage());
			} catch (DatatypeConfigurationException e1) {
				LOGGER.error("swapCsaResponseType DatatypeConfigurationException : ", e1);
				AlarmUtil.raiseSnmpAlarm();
				changeNumberResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(true, ""));
				changeNumberResponse.setStatusMessage(changeNumberResponse.getStatusMessageResponse().getStatusMessage());
			}

		} else {
			LOGGER.debug("ManageSubscriberServicesType : started");
			try {
				Holder<WsMessageHeaderType> header = headerHandler.getHeader(Service.SUBSCRIBER_MANAGEMENT_SERVICE);
				ManageSubscriberServicesType manageSubscriberServicesType = new ManageSubscriberServicesType();
				
				ResourceInfoType resourceInfoType = new ResourceInfoType();
				ResourceMdnInfoType resourceMdnInfoType = new ResourceMdnInfoType();
				ResourceNpaNxxInfoType resourceNpaNxxInfoType = new ResourceNpaNxxInfoType();	

				// managed services MDN
				com.sprint.integration.interfaces.managesubscriberservices.v1.managesubscriberservices.SubscriberInfoType subscriberInfoType = new com.sprint.integration.interfaces.managesubscriberservices.v1.managesubscriberservices.SubscriberInfoType();
				subscriberInfoType.setMdn(request.getUserContext().getMsisdn());
				manageSubscriberServicesType.setSubscriberInfo(subscriberInfoType);
				
				// Resource type
				resourceInfoType.setAction(action.trim());
				resourceInfoType.setResourceType(resourceType.trim());
				resourceInfoType.setReasonCode(resourceReasonCode.trim());
				resourceNpaNxxInfoType.setNpa(coverageAreaResponse.getNpa());
				resourceNpaNxxInfoType.setNxx(coverageAreaResponse.getNxx());
				resourceInfoType.setNpaNxxInfo(resourceNpaNxxInfoType);
				resourceMdnInfoType.setMdnType(PortedNumberIndicatorType.RGL);
				resourceMdnInfoType.setNumberLocation(resourceNumberLocation.trim());
				resourceMdnInfoType.setMethod(AllocationMethodType.R);
				resourceInfoType.setMdnInfo(resourceMdnInfoType);
				manageSubscriberServicesType.setResourceInfo(resourceInfoType);
				
				manageSubscriberServicesType.setAdditionalSocInd(Boolean.valueOf(additionalSocInd.trim()));
				com.sprint.integration.interfaces.managesubscriberservices.v1.managesubscriberservices.CallingApplicationInfoType callingApplicationInfoType = new com.sprint.integration.interfaces.managesubscriberservices.v1.managesubscriberservices.CallingApplicationInfoType();
				callingApplicationInfoType.setPin(pin.trim());
				callingApplicationInfoType.setVendorCode(vendorCode.trim());
				manageSubscriberServicesType.setCallingApplicationInfo(callingApplicationInfoType);
			
				ManageSubscriberServicesResponseType manageSubscriberServicesResponseType = subscriberManagementProxyService.manageSubscriberServices(manageSubscriberServicesType,	header);
				changeNumberResponse.setMsisdn(manageSubscriberServicesResponseType.getSubscriberDetailsList().getSubscriberDetails().get(0).getMdn());
				changeNumberResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(false, ""));
				changeNumberResponse.setStatusMessage(changeNumberResponse.getStatusMessageResponse().getStatusMessage());
			} catch (FaultMessage e) {
				LOGGER.error("ManageSubscriberServicesResponseType FaultMessage NPA/CSA: ", e);
				AlarmUtil.raiseSnmpAlarm();
				changeNumberResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(true, ""));
				changeNumberResponse.setStatusMessage(changeNumberResponse.getStatusMessageResponse().getStatusMessage());
			} catch (DatatypeConfigurationException e1) {
				LOGGER.error("ManageSubscriberServicesResponseType DatatypeConfigurationException : "
						, e1);
				changeNumberResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(true, ""));
				changeNumberResponse.setStatusMessage(changeNumberResponse.getStatusMessageResponse().getStatusMessage());
			}
		}

		LOGGER.info("Leaving changeNumber workflow method");
		return changeNumberResponse;
	}

	@Override
	public ESNSwapEligibleResponse checkEsnSwap(
			ESNSwapEligibleRequest esnSwaprequest, UserContextRequest request) {

		LOGGER.info("Entering checkEsnSwap workflow method");

		QuerySubscriberPrepaidInfoResponseType querySubscriberPrepaidInfoResponse = null;
		QueryAppleDeviceStatusResponseType queryAppleDeviceStatusResponse = null;
		QueryAppleDeviceStatusRequestType queryAppleDeviceStatusRequest = new QueryAppleDeviceStatusRequestType();
		String esn = null;
		ESNSwapEligibleResponse esnSwapEligbleResponse = new ESNSwapEligibleResponse();
		StatusMessage statusMessage = new StatusMessage();
		esnSwapEligbleResponse.setStatusMessage(statusMessage);
		LOGGER.debug("checkEsnSwap method");

		// String subscriberId = request.getUserContext().getSubscriberId();
		try {
			AlarmUtil.clearSnmpAlarm();
			LOGGER.debug("**MDN**" + request.getUserContext().getMsisdn());
			querySubscriberPrepaidInfoResponse = commonUtil
					.getSubscriberPrepaidInfoWithMsisdn(request
							.getUserContext().getMsisdn());
			esnSwapEligbleResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(false, ""));
			esnSwapEligbleResponse.setStatusMessage(esnSwapEligbleResponse.getStatusMessageResponse().getStatusMessage());
		} catch (Exception e) {
			esnSwapEligbleResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(true, ""));
			esnSwapEligbleResponse.setStatusMessage(esnSwapEligbleResponse.getStatusMessageResponse().getStatusMessage());			
			AlarmUtil.raiseSnmpAlarm();
			LOGGER.error(e.getMessage());
			return esnSwapEligbleResponse;

		}
		esn = querySubscriberPrepaidInfoResponse.getEquipmentInfo().getEsn();
		com.sprint.integration.interfaces.queryappledevicestatus.v1.queryappledevicestatus.SearchCriteriaType info = new com.sprint.integration.interfaces.queryappledevicestatus.v1.queryappledevicestatus.SearchCriteriaType();
		info.setDeviceSerialNumber(esn);
		queryAppleDeviceStatusRequest.setInfo(info);
		try {
			AlarmUtil.clearSnmpAlarm();
			Holder<WsMessageHeaderType> header = headerHandler
					.getHeader(Service.APPLE_MANAGEMENT_SERVICE);
			queryAppleDeviceStatusResponse = appleDeviceManagementProxyService
					.queryAppleDeviceStatus(queryAppleDeviceStatusRequest,
							header);
			esnSwapEligbleResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(false, ""));
			esnSwapEligbleResponse.setStatusMessage(esnSwapEligbleResponse.getStatusMessageResponse().getStatusMessage());
		} catch (Exception e) {
			AlarmUtil.raiseSnmpAlarm();
			LOGGER.error(e.getMessage(), e);
			esnSwapEligbleResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(true, ""));
			esnSwapEligbleResponse.setStatusMessage(esnSwapEligbleResponse.getStatusMessageResponse().getStatusMessage());
			return esnSwapEligbleResponse;
		}

		LOGGER.debug("DeviceSerialNumber***"
				+ queryAppleDeviceStatusResponse.getDeviceList()
						.getDeviceInfo().get(0).getDeviceSerialNumber());
		if (!StringUtils.isBlank(queryAppleDeviceStatusResponse.getDeviceList()
				.getDeviceInfo().get(0).getDeviceSerialNumber())) {
			LOGGER.debug("Setting DeviceSerialNumber***"
					+ queryAppleDeviceStatusResponse.getDeviceList()
							.getDeviceInfo().get(0).getDeviceSerialNumber());
			esnSwapEligbleResponse
					.setSerialNumber(queryAppleDeviceStatusResponse
							.getDeviceList().getDeviceInfo().get(0)
							.getDeviceSerialNumber());
		}
		LOGGER.debug("LockedInd***"
				+ queryAppleDeviceStatusResponse.getDeviceList()
						.getDeviceInfo().get(0).isLockedInd());
		if (!StringUtils.isBlank(String.valueOf(queryAppleDeviceStatusResponse
				.getDeviceList().getDeviceInfo().get(0).isLockedInd()))) {
			LOGGER.debug("Setting LockedInd***"
					+ queryAppleDeviceStatusResponse.getDeviceList()
							.getDeviceInfo().get(0).isLockedInd());
			esnSwapEligbleResponse.setLockedInd(String
					.valueOf(queryAppleDeviceStatusResponse.getDeviceList()
							.getDeviceInfo().get(0).isLockedInd()));
		}

		LOGGER.info("Leaving checkEsnSwap workflow method");
		return esnSwapEligbleResponse;
	}

	@Override
	public ESNSwapPrepareResponse prepareEsnSwap(ESNSwapPrepareRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SwapEsnResponse swapESN(ESNSwapPrepareRequest request) {
		// TODO Auto-generated method stub

		LOGGER.info("Entering swapESN workflow method");

		String esn = "";
		String esnNew = request.getSerialNumber();
		String validated = "Y";
		Holder<WsMessageHeaderType> header = null;
		SwapEsnResponse swapEsnResponse = new SwapEsnResponse();
		StatusMessage status = new StatusMessage();

		// have to check if new device is compatible with existing plan else
		// inform UI needs to know what action needs to be performed

		// queryDeviceInfo
		QueryDeviceInfoType queryDeviceInfoRequest = new QueryDeviceInfoType();
		com.sprint.integration.interfaces.querydeviceinfo.v1.querydeviceinfo.InfoType info = new com.sprint.integration.interfaces.querydeviceinfo.v1.querydeviceinfo.InfoType();
		QuerySubscriberPrepaidInfoResponseType querySubscriberPrepaidInfoResponse = null; // from
																							// cache
		LOGGER.debug("Got esn***" + esnNew);
		info.setEquipmentId(esnNew);
		queryDeviceInfoRequest.setInfo(info);

		QueryDeviceInfoResponseType queryDeviceInfoResponse = null;

		String brandCode = "SPP";// querySubscriberPrepaidInfoResponse.getPrepaidInfo().getBrandCode();
									// // from cache

		if (brandCode.equals("SPP")) { // only for SPP
			try {
				AlarmUtil.clearSnmpAlarm();
				header = headerHandler
						.getHeader(Service.QUERY_DEVICE_INFO_SERVICE);
				queryDeviceInfoResponse = queryDeviceInfoProxyService
						.queryDeviceInfo(queryDeviceInfoRequest, header);
//				status.setStatus(0);
//				swapEsnResponse.setStatus(status);
				swapEsnResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(false, ""));
				swapEsnResponse.setStatus(swapEsnResponse.getStatusMessageResponse().getStatusMessage());
				// cache response
			} catch (Exception e) {
				AlarmUtil.raiseSnmpAlarm();
				LOGGER.error(e.getMessage(), e);
				swapEsnResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(true, ""));
				swapEsnResponse.setStatus(swapEsnResponse.getStatusMessageResponse().getStatusMessage());
				validated = "N";
			}
		}

		String manufacturer = queryDeviceInfoResponse.getEquipmentInfo()
				.getManufacturer();
		String networkInd = queryDeviceInfoResponse.getEquipmentInfo()
				.getNetworkInd().value();
		String equipmentStatus = queryDeviceInfoResponse.getEquipmentInfo()
				.getEquipmentStatus();
		boolean lteInd = queryDeviceInfoResponse.getEquipmentInfo().isLteInd();
		String isDeviceInsuranceEligibleInd = queryDeviceInfoResponse
				.getEquipmentInfo().getIsDeviceInsuranceEligibleInd();
		String isAppleInsuranceApplicableInd = "true";// queryDeviceInfoResponse.getEquipmentInfo().getIsAppleInsuranceApplicableInd();

		LOGGER.debug("Got equipmentStatus***" + equipmentStatus);

		// check: place
		// manufacturer,networkInd,equipmentStatus,lteInd,isDeviceInsuranceEligibleInd,isAppleInsuranceApplicableInd
		// on composeResponse object

		if (equipmentStatus != null && equipmentStatus.equals("D")) {
			validated = "N";
			LOGGER.debug("Device is not available for swapping");
			validated = "N";
			System.exit(0); // confirm if this is OK (?)
		}

		if (validated != null
				&& validated.equals("Y")
				&& (isAppleInsuranceApplicableInd != null && isAppleInsuranceApplicableInd
						.equals("true"))) {
			validated = "Y";
		} else {
			validated = "N";
		}

		// To determine the apple lock status of the new device
		QueryAppleDeviceStatusRequestType queryAppleDeviceStatusRequest = new QueryAppleDeviceStatusRequestType();
		QueryAppleDeviceStatusResponseType queryAppleDeviceStatusResponse = null;

		if (validated != null && validated.equals("Y")) {
			com.sprint.integration.interfaces.queryappledevicestatus.v1.queryappledevicestatus.SearchCriteriaType info1 = new com.sprint.integration.interfaces.queryappledevicestatus.v1.queryappledevicestatus.SearchCriteriaType();

			info1.setDeviceSerialNumber(esnNew); // new device serial number;
													// confirm if this is
													// deviceSerialNumber or
													// meidDec
			queryAppleDeviceStatusRequest.setInfo(info1);

			try {
				AlarmUtil.clearSnmpAlarm();
				header = headerHandler
						.getHeader(Service.APPLE_MANAGEMENT_SERVICE);
				queryAppleDeviceStatusResponse = appleDeviceManagementProxyService
						.queryAppleDeviceStatus(queryAppleDeviceStatusRequest,
								header);
				status.setStatus(0);
				swapEsnResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(false, ""));
				swapEsnResponse.setStatus(swapEsnResponse.getStatusMessageResponse().getStatusMessage());
				// cache response
			} catch (Exception e) {
				AlarmUtil.raiseSnmpAlarm();
				LOGGER.error(e.getMessage(), e);
				swapEsnResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(true, ""));
				swapEsnResponse.setStatus(swapEsnResponse.getStatusMessageResponse().getStatusMessage());
				validated = "N";
			}
		}

		if (validated != null && validated.equals("Y")) {
			if (esn.equals(esnNew)) {
				validated = "N";
				swapEsnResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(true, ""));
				swapEsnResponse.setStatus(swapEsnResponse.getStatusMessageResponse().getStatusMessage());
													// (?)
				LOGGER.error("ESN and ESN-NEW are same!");
			}
		}

		QueryProgrammingInstructionsResponseType queryProgrammingInstructionsResponse = null;
		String itemId = "";
		if (validated != null && validated.equals("Y")) {
			itemId = queryDeviceInfoResponse.getEquipmentInfo().getItemId(); // itemId
																				// of
																				// new
																				// device

			try {
				AlarmUtil.clearSnmpAlarm();
				header = headerHandler
						.getHeader(Service.QUERY_PROVISIONING_INFO_SERVICE);
				QueryProgrammingInstructionsRequestType queryProgrammingInstructionsRequest = new QueryProgrammingInstructionsRequestType();
				queryProgrammingInstructionsRequest.setItemId(itemId);
				queryProgrammingInstructionsRequest.setBrandCode("SPP");
				queryProgrammingInstructionsResponse = queryProvisioningInfoProxyService
						.queryProgrammingInstructions(
								queryProgrammingInstructionsRequest, header);
				swapEsnResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(false, ""));
				swapEsnResponse.setStatus(swapEsnResponse.getStatusMessageResponse().getStatusMessage());
				// cache response
			} catch (Exception e) {
				AlarmUtil.raiseSnmpAlarm();
				LOGGER.error(e.getMessage(), e);
				swapEsnResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(true, ""));
				swapEsnResponse.setStatus(swapEsnResponse.getStatusMessageResponse().getStatusMessage());
				swapEsnResponse.setStatus(status);
				validated = "N";
			}
		}

		com.sprint.integration.interfaces.validateplansandoptions.v1.validateplansandoptions.ServiceAgreementInfoType serviceAgreementInfo = null;
		com.sprint.integration.interfaces.validateplansandoptions.v1.validateplansandoptions.ActionType actionType = null;
		com.sprint.integration.interfaces.validateplansandoptions.v1.validateplansandoptions.BanInfoType banInfo = null;
		com.sprint.integration.interfaces.validateplansandoptions.v1.validateplansandoptions.DeviceInfoType deviceInfo = null;
		ValidatePlansAndOptionsResponseType validatePlansAndOptionsV2Response = null;
		if (validated != null && validated.equals("Y")) {
			serviceAgreementInfo = new com.sprint.integration.interfaces.validateplansandoptions.v1.validateplansandoptions.ServiceAgreementInfoType();
			actionType = new ActionType();
			TransactionTypeCodeType transactionTypeCode = TransactionTypeCodeType.CHECK;
			actionType.setActionType(transactionTypeCode);
			actionType.setSubscriberId("27864372121");// querySubscriberPrepaidInfoResponse.getBasicInfo().getSubscriberId()
			serviceAgreementInfo.setAction(actionType);
			banInfo = new com.sprint.integration.interfaces.validateplansandoptions.v1.validateplansandoptions.BanInfoType();
			banInfo.setBan("111387846");// querySubscriberPrepaidInfoResponse.getBasicInfo().getBan()
			serviceAgreementInfo.setBanInfo(banInfo);
			deviceInfo = new com.sprint.integration.interfaces.validateplansandoptions.v1.validateplansandoptions.DeviceInfoType();
			deviceInfo.setItemId(itemId);
			serviceAgreementInfo.setDeviceInfo(deviceInfo);
			OrderInfoType orderInfo = new OrderInfoType();
			try {
				/*
				 * SimpleDateFormat dtFormat = new SimpleDateFormat(dateFormat);
				 * Date date = new Date();//2009-06-08T16:15:20-05:00
				 * orderInfo.setOrderDate(DatatypeFactory
				 * .newInstance().newXMLGregorianCalendar(
				 * dtFormat.format(date)));
				 */

				// private XMLGregorianCalendar orderDate;
				GregorianCalendar c = new GregorianCalendar();
				orderInfo.setOrderDate(DatatypeFactory.newInstance()
						.newXMLGregorianCalendar(c));
			} catch (Exception ex) {
				LOGGER.error("date format error");
			}
			serviceAgreementInfo.setOrderInfo(orderInfo);
			ValidatePlansAndOptionsType validatePlansAndOptionsV2Request = new ValidatePlansAndOptionsType();
			CallingApplicationInfoType callingApplicationInfo = new CallingApplicationInfoType();
			callingApplicationInfo.setPin("1331");
			callingApplicationInfo.setVendorCode("WU");
			ServiceAreaInfoType serviceAreaInfo = new ServiceAreaInfoType();
			serviceAreaInfo.setCsa("NEVOCN760");
			// DeviceInfoType deviceInfo = new DeviceInfoType();
			deviceInfo.setItemId("REC4S16WHSB");
			serviceAgreementInfo.setServiceAreaInfo(serviceAreaInfo);
			serviceAgreementInfo.setDeviceInfo(deviceInfo);

			String errCode = "";
			try {
				AlarmUtil.clearSnmpAlarm();
				header = headerHandler
						.getHeader(Service.QUERY_PLANS_AND_OPTIONS_SERVICE);
				validatePlansAndOptionsV2Request
						.setServiceAgreementInfo(serviceAgreementInfo);
				validatePlansAndOptionsV2Request
						.setCallingApplicationInfo(callingApplicationInfo);
				validatePlansAndOptionsV2Response = queryPlansAndOptionsProxyService
						.validatePlansAndOptions(
								validatePlansAndOptionsV2Request, header);
				swapEsnResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(false, ""));
				swapEsnResponse.setStatus(swapEsnResponse.getStatusMessageResponse().getStatusMessage());
			} catch (Faultmessage e) {
				AlarmUtil.raiseSnmpAlarm();
				LOGGER.error(e.getMessage(), e);
				validated = "N";
				for (ProviderErrorType err : e.getFaultInfo()
						.getProviderError()) {
					errCode = err.getProviderErrorCode();
					if (errCode.equals("104") || errCode.equals("105")
							|| errCode.equals("190") || errCode.equals("197")
							|| errCode.equals("199") || errCode.equals("217")
							|| errCode.equals("243") || errCode.equals("251")
							|| errCode.equals("252") || errCode.equals("273")
							|| errCode.equals("281") || errCode.equals("283")
							|| errCode.equals("293") || errCode.equals("303")
							|| errCode.equals("314")) {
						// record action "priceplanchange" in the response (how
						// to set)
						swapEsnResponse.setAction("priceplanchange");
					}
				}
				swapEsnResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(true, ""));
				swapEsnResponse.setStatus(swapEsnResponse.getStatusMessageResponse().getStatusMessage());
			} catch (DatatypeConfigurationException e1) {
				swapEsnResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(true, ""));
				swapEsnResponse.setStatus(swapEsnResponse.getStatusMessageResponse().getStatusMessage());
				AlarmUtil.raiseSnmpAlarm();
				LOGGER.error(e1.getMessage(), e1);
				validated = "N";
			}

		}

		SwapSubscriberEquipmentType swapSubscriberEquipment = new SwapSubscriberEquipmentType();
		EquipmentListType equipmentList = new EquipmentListType();
		SwapSubscriberEquipmentResponseType swapSubscriberEquipmentResponse = null;
		com.sprint.integration.interfaces.swapsubscriberequipment.v1.swapsubscriberequipment.SearchCriteriaType subscriberInfo = null;
		if (validated != null && validated.equals("Y")
				&& validatePlansAndOptionsV2Response.isValidationSucceededInd()) {

			// call swapSubscriberEquipment adapter operation

			subscriberInfo = new com.sprint.integration.interfaces.swapsubscriberequipment.v1.swapsubscriberequipment.SearchCriteriaType();
			subscriberInfo.setDeviceSerialNumber(esn);

			com.sprint.integration.interfaces.swapsubscriberequipment.v1.swapsubscriberequipment.EquipmentInfoType equipmentInfo1 = new com.sprint.integration.interfaces.swapsubscriberequipment.v1.swapsubscriberequipment.EquipmentInfoType();
			com.sprint.integration.interfaces.swapsubscriberequipment.v1.swapsubscriberequipment.EquipmentInfoType equipmentInfo2 = new com.sprint.integration.interfaces.swapsubscriberequipment.v1.swapsubscriberequipment.EquipmentInfoType();

			// esnCurrent
			equipmentInfo1.setActivity(EquipmentActivityCodeType.D);
			equipmentInfo1.setDeviceSerialNumber(esn);
			// esnNew
			equipmentInfo2.setActivity(EquipmentActivityCodeType.A); //
			equipmentInfo2.setDeviceSerialNumber(esnNew);

			equipmentList.getEquipmentInfo().add(equipmentInfo1);
			equipmentList.getEquipmentInfo().add(equipmentInfo2);
			swapSubscriberEquipment.setEquipmentList(equipmentList);
			// swapSubscriberEquipment.getEquipmentList().getEquipmentInfo().add(equipmentInfo1);
			// swapSubscriberEquipment.getEquipmentList().getEquipmentInfo().add(equipmentInfo2);

			try {
				AlarmUtil.clearSnmpAlarm();
				header = headerHandler
						.getHeader(Service.SUBSCRIBER_MANAGEMENT_SERVICE);
				swapSubscriberEquipmentResponse = subscriberManagementProxyService
						.swapSubscriberEquipment(swapSubscriberEquipment,
								header);
				swapEsnResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(false, ""));
				swapEsnResponse.setStatus(swapEsnResponse.getStatusMessageResponse().getStatusMessage());
			} catch (FaultMessage e) {
				AlarmUtil.raiseSnmpAlarm();
				LOGGER.error(e.getMessage(), e);
				swapEsnResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(true, ""));
				swapEsnResponse.setStatus(swapEsnResponse.getStatusMessageResponse().getStatusMessage());
				validated = "N";
			} catch (DatatypeConfigurationException e2) {
				swapEsnResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(true, ""));
				swapEsnResponse.setStatus(swapEsnResponse.getStatusMessageResponse().getStatusMessage());
				AlarmUtil.raiseSnmpAlarm();
				LOGGER.error(e2.getMessage(), e2);
				validated = "N";
			}

		}

		if (validated != null && validated.equals("Y")) {
			// set msisdn on SwapEsnResponse
			swapEsnResponse.setMsisdn(swapSubscriberEquipmentResponse
					.getCdmaProgrammingInfo().getMdn());
			// swapEsnResponse.setMsisdn(swapSubscriberEquipmentResponse.getResourceInfo().getMdn());

			// record actionz"programphone" in the response (how to set)
			swapEsnResponse.setAction("programphone");
		}

		LOGGER.info("Leaving swapESN workflow method");
		return swapEsnResponse;
	}

}
