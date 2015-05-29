package com.ericsson.cac.sprint.selfcare.workflow.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.Holder;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ericsson.cac.sprint.adapters.QueryOrderFulfillmentProxyService;
import com.ericsson.cac.sprint.adapters.QueryPrepaidSubscriberProxyService;
import com.ericsson.cac.sprint.adapters.QuerySubscriberInfoProxyService;
import com.ericsson.cac.sprint.adapters.QueryUsageProxyService;
import com.ericsson.cac.sprint.selfcare.workflow.AccountServiceWorkflow;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.AddOnsRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.AddOnsResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.CartPaymentResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.CartRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.EligibleServicesResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.OrderHistory;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.PaymentHistory;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.PaymentHistoryPagination;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.PlanUsageResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SearchCriteriaRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.ServiceType;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessageResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberPlan;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberPlanResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.TalkHistory;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.TalkHistoryResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.TextHistory;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.TextHistoryResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.TransactionHistory;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.TransactionHistoryResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.cac.sprint.selfcare.workflow.util.AlarmUtil;
import com.ericsson.cac.sprint.selfcare.workflow.util.CommonUtil;
import com.ericsson.cac.sprint.selfcare.workflow.util.HeaderHandler;
import com.ericsson.cac.sprint.selfcare.workflow.util.Service;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.interfaces.queryaccountorders.v1.queryaccountorders.OrderInfoType;
import com.sprint.integration.interfaces.queryaccountorders.v1.queryaccountorders.QueryAccountOrdersResponseType;
import com.sprint.integration.interfaces.queryaccountorders.v1.queryaccountorders.QueryAccountOrdersType;
import com.sprint.integration.interfaces.queryorderinfo.v1.queryorderinfo.InfoType;
import com.sprint.integration.interfaces.queryorderinfo.v1.queryorderinfo.OrderKeyInfoType;
import com.sprint.integration.interfaces.queryorderinfo.v1.queryorderinfo.OrderLineType;
import com.sprint.integration.interfaces.queryorderinfo.v1.queryorderinfo.QueryOrderInfoResponseType;
import com.sprint.integration.interfaces.queryorderinfo.v1.queryorderinfo.QueryOrderInfoType;
import com.sprint.integration.interfaces.queryprepaidallowance.v1.queryprepaidallowance.AllowanceResponseInfoType;
import com.sprint.integration.interfaces.queryprepaidallowance.v1.queryprepaidallowance.QueryPrepaidAllowanceRequestType;
import com.sprint.integration.interfaces.queryprepaidallowance.v1.queryprepaidallowance.QueryPrepaidAllowanceResponseType;
import com.sprint.integration.interfaces.queryprepaidbalanceandthresholdinfo.v1.queryprepaidbalanceandthresholdinfo.DataBalanceBucketInfoType;
import com.sprint.integration.interfaces.queryprepaidbalanceandthresholdinfo.v1.queryprepaidbalanceandthresholdinfo.MessagingBalanceBucketInfoType;
import com.sprint.integration.interfaces.queryprepaidbalanceandthresholdinfo.v1.queryprepaidbalanceandthresholdinfo.QueryPrepaidBalanceAndThresholdInfoRequestType;
import com.sprint.integration.interfaces.queryprepaidbalanceandthresholdinfo.v1.queryprepaidbalanceandthresholdinfo.QueryPrepaidBalanceAndThresholdInfoResponseType;
import com.sprint.integration.interfaces.queryprepaidusagedetails.v1.queryprepaidusagedetails.ApplicationCallingInfoType;
import com.sprint.integration.interfaces.queryprepaidusagedetails.v1.queryprepaidusagedetails.EventCategoryInfoType;
import com.sprint.integration.interfaces.queryprepaidusagedetails.v1.queryprepaidusagedetails.PaginationInfoType;
import com.sprint.integration.interfaces.queryprepaidusagedetails.v1.queryprepaidusagedetails.PrepaidUsageDetailsHistoryInfoType;
import com.sprint.integration.interfaces.queryprepaidusagedetails.v1.queryprepaidusagedetails.PrepaidUsageDetailsRequestInfoType;
import com.sprint.integration.interfaces.queryprepaidusagedetails.v1.queryprepaidusagedetails.QueryPrepaidUsageDetailsRequestType;
import com.sprint.integration.interfaces.queryprepaidusagedetails.v1.queryprepaidusagedetails.QueryPrepaidUsageDetailsResponseType;
import com.sprint.integration.interfaces.queryprepaidusagedetails.v1.queryprepaidusagedetails.SearchCriteriaType;
import com.sprint.integration.interfaces.querysubscriberinfoservice.v1.querysubscriberinfoservice.SoapFaultV2;
import com.sprint.integration.interfaces.querysubscriberservices.v1.querysubscriberservices.FetchSocListType;
import com.sprint.integration.interfaces.querysubscriberservices.v1.querysubscriberservices.PricePlanInfoType;
import com.sprint.integration.interfaces.querysubscriberservices.v1.querysubscriberservices.QuerySubscriberServicesRequest;
import com.sprint.integration.interfaces.querysubscriberservices.v1.querysubscriberservices.QuerySubscriberServicesResponse;
import com.sprint.integration.interfaces.querysubscriberservices.v1.querysubscriberservices.SocListType;

@Component
public class AccountServiceWorkflowImpl implements AccountServiceWorkflow {

	/** Logger class info */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AccountServiceWorkflowImpl.class);

	@Value("${getPlanService.trueValue}")
	private String trueValue;
	@Value("${getPlanService.falseValue}")
	private String falseValue;
	@Value("${getPlanService.unit}")
	private String unit;
	@Value("${getPlanService.type}")
	private String type;
	@Value("${getPlanService.currency}")
	private String currency;
	@Value("${getPlanService.recurrence}")
	private String recurrence;
	@Value("${getPlanService.pSocTypeValue}")
	private String pSocTypeValue;
	@Value("${getPlanService.socList.value}")
	private String isSocListValue;
	@Value("${getPlanService.socList.includeExpired}")
	private String isSocListIncludeExpired;
	@Value("${getPlanService.socList.includeFuturedated}")
	private String isSocListIncludeFuturedated;
	@Value("${getTalkHistoryService.accessId}")
	private String getTalkHistoryAccessId;
	@Value("${getTalkHistoryService.bufferSize}")
	private String bufferSize;

	@Value("${getTalkHistoryService.serviceType.voice}")
	private String getTalkHistoryServiceTypeVoice;
	@Value("${getTalkHistoryService.callDirection.outgoing}")
	private String getTalkHistoryCallDirectionOutgoing;

	@Value("${getTextHistoryService.accessId}")
	private String getTextHistoryAccessId;
	@Value("${getTextHistoryService.serviceType.textMessaging}")
	private String getTextHistoryServiceTypeTextMessaging;
	@Value("${getTextHistoryService.callDirection.outgoing}")
	private String getTextHistoryCallDirectionOutgoing;
	@Value("${getTextHistoryBufferSize}")
	private String getTextHistoryBufferSize;

	@Value("${getTransactionHistory.bufferSize}")
	private String getTransactionHistoryBufferSize;

	@Value("${getTransactionHistory.accessId}")
	private String getTransactionHistoryAccessId;

	@Value("${getTransactionHistory.eventCategoryType.ChargesAndCredit}")
	private String getTransactionHistoryEventCategoryChargesAndCredit;

	@Value("${getTransactionHistory.currency.USD}")
	private String getTransactionHistoryCurrencyUSD;

	@Value("${getPlanUsage.setAccessId}")
	private String getPlanUsageAccessId;

	@Value("${getPlanUsage.startingNow}")
	private String startingNow;

	@Value("${getPlanUsage.addOn}")
	private String addOn;

	@Value("${getPlanUsage.dataUnits}")
	private String dataUnits;

	@Autowired
	private QuerySubscriberInfoProxyService querySubcriberInfoProxyService;

	@Autowired
	private QueryPrepaidSubscriberProxyService queryPrepaidSubscriberProxyService;

	@Autowired
	private QueryUsageProxyService queryUsageProxyService;

	@Autowired
	private QueryOrderFulfillmentProxyService queryOrderFulfillmentProxyService;

	@Autowired
	private HeaderHandler headerHandler;

	@Autowired
	private CommonUtil commonUtil;

	@Value("${selfcareworkflow.date.format}")
	private String dateFormat;

	@Override
	public SubscriberPlanResponse getPlan(UserContextRequest request) {

		LOGGER.info("Entering getPlan workflow method");

		SubscriberPlanResponse subscriberPlanResponse = new SubscriberPlanResponse();
		QuerySubscriberServicesRequest querySubscriberServicesRequest = null;
		QuerySubscriberServicesResponse querySubscriberServiceResponse = null;

		try {
			// Step 1: Call QuerySubscriberInfoProxyService
			querySubscriberServicesRequest = new QuerySubscriberServicesRequest();

			LOGGER.info("Subscriber Id obtained from request is "
					+ request.getUserContext().getSubscriberId());

			System.out.println("Subscriber Id obtained from request is "
					+ request.getUserContext().getSubscriberId());

			if (null != request.getUserContext()) {

				Holder<WsMessageHeaderType> header = headerHandler
						.getHeader(Service.QUERY_SUBSCRIBER_INFO_SERVICE);

				QuerySubscriberServicesRequest.Info info = new QuerySubscriberServicesRequest.Info();
				info.setSubsId(request.getUserContext().getSubscriberId());

				FetchSocListType socList = new FetchSocListType();
				socList.setValue(Boolean.valueOf(isSocListValue));
				socList.setIncludeExpired(Boolean
						.valueOf(isSocListIncludeExpired));
				socList.setIncludeFutureDated(Boolean
						.valueOf(isSocListIncludeFuturedated));

				querySubscriberServicesRequest.setSocList(socList);
				querySubscriberServicesRequest.setInfo(info);
				LOGGER.debug("Invoking QuerySubscriberService -> querySubscriberServices method");
				querySubscriberServiceResponse = querySubcriberInfoProxyService
						.querySubscriberServices(
								querySubscriberServicesRequest, header);

				if (null != querySubscriberServiceResponse
						&& null != querySubscriberServiceResponse.getSocList()
						&& null != querySubscriberServiceResponse.getSocList()
								.getSocInfo()
						&& null != querySubscriberServiceResponse
								.getPricePlanInfo()) {

					// Step 2: Create SubscriberPlan using output from Step 1
					SubscriberPlan subscriberPlan = new SubscriberPlan();

					// Formulating Baseplan response
					LOGGER.debug("Building basePlan response");
					subscriberPlan
							.setBaseplan(getBasePlanServiceList(querySubscriberServiceResponse
									.getPricePlanInfo()));

					// Formulating Addon response
					LOGGER.debug("Building addOn response");
					subscriberPlan
							.setAddOns(getAddOnServiceList(querySubscriberServiceResponse
									.getSocList().getSocInfo()));

					// Step 3: Create SubscriberPlanResponse from SubscriberPlan
					// and
					// return.
					subscriberPlanResponse.setSubscriberPlan(subscriberPlan);

					subscriberPlanResponse.setStatusMessageResponse(commonUtil
							.populateStatusRespMesg(false, ""));
				} else {

					throw new Exception(
							"Null response obtained from querySubcriberInfoProxyService");
				}
			} else {
				LOGGER.error("Subscriber Id obtained in request is null or empty");
				throw new Exception(
						"Subscriber Id obtained in request is null or empty");
			}

		} catch (SoapFaultV2 ex) {

			LOGGER.error(
					"SoapFaultV2 exception occurred --> " + ex.getMessage(), ex);
			raiseSnmpAlarm();
			subscriberPlanResponse.setStatusMessageResponse(commonUtil
					.populateStatusRespMesg(true, ex.getMessage()));

		} catch (Exception e) {
			LOGGER.error("Exception occurred --> " + e.getMessage(), e);
			subscriberPlanResponse.setStatusMessageResponse(commonUtil
					.populateStatusRespMesg(true,
							"Exception occured while fetching plans"));
		}

		LOGGER.info("Leaving getPlan workflow method");
		return subscriberPlanResponse;
	}

	@Override
	public PlanUsageResponse getPlanUsage(UserContextRequest request) {

		LOGGER.info("Entering getPlanUsage workflow method");

		QueryPrepaidAllowanceRequestType queryPrepaidAllowanceRequest = new QueryPrepaidAllowanceRequestType();
		QueryPrepaidAllowanceResponseType queryPrepaidAllowanceResponse = null;
		PlanUsageResponse planUsageResponse = new PlanUsageResponse();
		QueryPrepaidBalanceAndThresholdInfoRequestType queryPrepaidBalanceAndThresholdInfoRequest = new QueryPrepaidBalanceAndThresholdInfoRequestType();
		QueryPrepaidBalanceAndThresholdInfoResponseType queryPrepaidBalanceAndThresholdInfoResponse = null;
		List<com.ericsson.cac.sprint.selfcare.workflow.datamodel.Service> serviceList = null;
		

		com.sprint.integration.interfaces.queryprepaidallowance.v1.queryprepaidallowance.SearchCriteriaType searchCriteria = new com.sprint.integration.interfaces.queryprepaidallowance.v1.queryprepaidallowance.SearchCriteriaType();
		if (StringUtils.isBlank(request.getUserContext().getMsisdn())) {
			// error mapping is not given so returning null response
			planUsageResponse.setStatusMessageResponse(commonUtil
					.populateStatusRespMesg(true,
							"MSISDN is not present in request"));
			return planUsageResponse;
		} else {
			searchCriteria.setMdn(request.getUserContext().getMsisdn());
		}
		com.sprint.integration.interfaces.queryprepaidallowance.v1.queryprepaidallowance.ApplicationCallingInfoType applicationCallingInfo = new com.sprint.integration.interfaces.queryprepaidallowance.v1.queryprepaidallowance.ApplicationCallingInfoType();
		applicationCallingInfo.setAccessId(getPlanUsageAccessId);

		queryPrepaidAllowanceRequest.setSubscriberInfo(searchCriteria);
		queryPrepaidAllowanceRequest
				.setApplicationCallingInfo(applicationCallingInfo);

		try {
			AlarmUtil.clearSnmpAlarm();
			Holder<WsMessageHeaderType> header = headerHandler
					.getHeader(Service.QUERY_PREPAID_SUBSCRIBER_SERVICE);
			LOGGER.debug("Calling queryPrepaidAllowance");
			queryPrepaidAllowanceResponse = queryPrepaidSubscriberProxyService
					.queryPrepaidAllowance(queryPrepaidAllowanceRequest, header);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			AlarmUtil.raiseSnmpAlarm();
			planUsageResponse.setStatusMessageResponse(commonUtil
					.populateStatusRespMesg(true, e.getMessage()));
		}

		com.sprint.integration.interfaces.queryprepaidbalanceandthresholdinfo.v1.queryprepaidbalanceandthresholdinfo.SearchCriteriaType searchCriteriaType = new com.sprint.integration.interfaces.queryprepaidbalanceandthresholdinfo.v1.queryprepaidbalanceandthresholdinfo.SearchCriteriaType();
		searchCriteriaType.setMdn(request.getUserContext().getMsisdn());
		com.sprint.integration.interfaces.queryprepaidbalanceandthresholdinfo.v1.queryprepaidbalanceandthresholdinfo.ApplicationCallingInfoType applicationCallingInfoType = new com.sprint.integration.interfaces.queryprepaidbalanceandthresholdinfo.v1.queryprepaidbalanceandthresholdinfo.ApplicationCallingInfoType();
		applicationCallingInfoType.setAccessId(getPlanUsageAccessId);
		queryPrepaidBalanceAndThresholdInfoRequest
				.setSubscriberInfo(searchCriteriaType);
		queryPrepaidBalanceAndThresholdInfoRequest
				.setApplicationCallingInfo(applicationCallingInfoType);

		LOGGER.debug("Setting Call Details");

		/*
		 * serviceList
		 * .addAll(getBasePlanServiceForCall(queryPrepaidAllowanceResponse));
		 */
		serviceList = getBasePlanServiceForCall(
				queryPrepaidAllowanceResponse);
		if(serviceList != null && serviceList.size() > 0)
		{
			planUsageResponse.setTalkUsage(serviceList.get(0));
		}

		try {
			AlarmUtil.clearSnmpAlarm();
			Holder<WsMessageHeaderType> header = headerHandler
					.getHeader(Service.QUERY_USAGE_SERVICE);
			LOGGER.debug("queryPrepaidBalanceAndThresholdInfo");
			queryPrepaidBalanceAndThresholdInfoResponse = queryUsageProxyService
					.queryPrepaidBalanceAndThresholdInfo(
							queryPrepaidBalanceAndThresholdInfoRequest, header);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			AlarmUtil.raiseSnmpAlarm();
			planUsageResponse.setStatusMessageResponse(commonUtil
					.populateStatusRespMesg(true, e.getMessage()));
		}

		LOGGER.debug("Setting Data Details");

		/*
		 * serviceList .addAll(getBasePlanServiceForData(
		 * queryPrepaidBalanceAndThresholdInfoResponse));
		 */
		
		serviceList = getBasePlanServiceForData(queryPrepaidBalanceAndThresholdInfoResponse);
		if(serviceList != null && serviceList.size() > 0)
		{
			planUsageResponse.setDataUsage(serviceList.get(0));
		}
		

		LOGGER.debug("Setting Message Details");

		/*
		 * serviceList .addAll(getBasePlanServiceForMessage(
		 * queryPrepaidBalanceAndThresholdInfoResponse));
		 */
		serviceList = getBasePlanServiceForMessage(queryPrepaidBalanceAndThresholdInfoResponse);
		if(serviceList != null && serviceList.size() > 0)
		{
			planUsageResponse.setTextUsage(serviceList.get(0));
		}


		/*
		 * subscriberPlan = new SubscriberPlan();
		 * 
		 * subscriberPlan
		 * .setBaseplan((com.ericsson.cac.sprint.selfcare.workflow
		 * .datamodel.Service[]) serviceList .toArray(new
		 * com.ericsson.cac.sprint
		 * .selfcare.workflow.datamodel.Service[serviceList .size()]));
		 * 
		 * subscriberPlanResponse = new SubscriberPlanResponse();
		 * 
		 * subscriberPlanResponse.setSubscriberPlan(subscriberPlan);
		 * 
		 * subscriberPlanResponse.setStatusMessageResponse(commonUtil.
		 * populateStatusRespMesg(false, ""));
		 */

		LOGGER.info("Leaving getPlanUsage workflow method");
		return planUsageResponse;
	}

	private List<com.ericsson.cac.sprint.selfcare.workflow.datamodel.Service> getBasePlanServiceForCall(
			QueryPrepaidAllowanceResponseType queryPrepaidAllowanceResponse) {
		List<com.ericsson.cac.sprint.selfcare.workflow.datamodel.Service> serviceList = new ArrayList<com.ericsson.cac.sprint.selfcare.workflow.datamodel.Service>();
		com.ericsson.cac.sprint.selfcare.workflow.datamodel.Service service = null;
		try {
			for (AllowanceResponseInfoType allowanceResponseInfo : queryPrepaidAllowanceResponse
					.getAllowanceResponseInfoList().getAllowanceResponseInfo()) {
				LOGGER.debug("Calling units details: SocCode:"
						+ allowanceResponseInfo.getSocCode() + " SocDesc: "
						+ allowanceResponseInfo.getSocDesc()
						+ " IncludedUnits: "
						+ allowanceResponseInfo.getIncludedUnits()
						+ " unitOfMeasurement: "
						+ allowanceResponseInfo.getUnitOfMeasurement()
						+ " UsedUnits: "
						+ String.valueOf(allowanceResponseInfo.getUsedUnits())
						+ " ServiceType: " + ServiceType.Talk
						// + allowanceResponseInfo.getServiceType()
						+ " addOn: " + Boolean.valueOf(addOn)
						+ " startingNow: " + Boolean.valueOf(startingNow));

				service = new com.ericsson.cac.sprint.selfcare.workflow.datamodel.Service();
				if (null != allowanceResponseInfo.getSocCode())
					service.setId(allowanceResponseInfo.getSocCode());
				if (null != allowanceResponseInfo.getSocDesc())
					service.setName(allowanceResponseInfo.getSocDesc());
				if (null != allowanceResponseInfo.getIncludedUnits())
					service.setCapacity(allowanceResponseInfo
							.getIncludedUnits());
				if (null != String
						.valueOf(allowanceResponseInfo.getUsedUnits()))
					service.setUsage(String.valueOf(allowanceResponseInfo
							.getUsedUnits()));
				/*
				 * if (null != allowanceResponseInfo.getServiceType())
				 * service.setType(allowanceResponseInfo.getServiceType());
				 */
				service.setType(ServiceType.Talk.getValue());
				service.setAddOn(Boolean.valueOf(addOn));
				service.setStartingNow(Boolean.valueOf(startingNow));
				if (null != allowanceResponseInfo.getUnitOfMeasurement())
					service.setUnit(allowanceResponseInfo
							.getUnitOfMeasurement());

				LOGGER.debug("service object:" + service.getId() + " "
						+ service.getName() + " " + service.getCapacity() + " "
						+ service.getUsage() + " " + service.getType() + " "
						+ service.getAddOn() + " " + service.getStartingNow()
						+ " " + service.getUnit() + "\n");

				serviceList.add(service);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return serviceList;
	}

	private List<com.ericsson.cac.sprint.selfcare.workflow.datamodel.Service> getBasePlanServiceForData(
			QueryPrepaidBalanceAndThresholdInfoResponseType queryPrepaidBalanceAndThresholdInfoResponse) {
		
		LOGGER.debug("**starting getBasePlanServiceForData**");

		List<com.ericsson.cac.sprint.selfcare.workflow.datamodel.Service> serviceList = new ArrayList<com.ericsson.cac.sprint.selfcare.workflow.datamodel.Service>();
		com.ericsson.cac.sprint.selfcare.workflow.datamodel.Service service = null;
		String capacity = null;
		String usage = null;

		try {
			capacity = queryPrepaidBalanceAndThresholdInfoResponse
					.getAccountBalanceResponse().getDataUsageInfo()
					.getTotal3GBalance();
			usage = queryPrepaidBalanceAndThresholdInfoResponse
					.getAccountBalanceResponse().getDataUsageInfo()
					.getTotal3G4GHotspotUsage();

			for (DataBalanceBucketInfoType dataBalanceBucketInfo : queryPrepaidBalanceAndThresholdInfoResponse
					.getAccountBalanceResponse().getDataUsageInfo()
					.getDataBalanceBucketList().getDataBalanceBucketInfo()) {
				LOGGER.debug("Datausage details: BucketType: "
						+ String.valueOf(dataBalanceBucketInfo.getBucketType())
						+ " BucketName: "
						+ (String) dataBalanceBucketInfo.getBalanceBucketName()
								.getValue()
						+ " ServiceType: "
						// + dataBalanceBucketInfo.getServiceType()
						+ ServiceType.Data
						+ " ExpiryDate: "
						+ dataBalanceBucketInfo.getExpiryDate()
								.toGregorianCalendar().getTime()
						+ " startingNow: " + Boolean.valueOf(startingNow)
						+ " dataUnits: " + dataUnits);

				service = new com.ericsson.cac.sprint.selfcare.workflow.datamodel.Service();

				if (null != String.valueOf(dataBalanceBucketInfo
						.getBucketType()))
					service.setId(String.valueOf(dataBalanceBucketInfo
							.getBucketType()));
				if (null != (String) dataBalanceBucketInfo
						.getBalanceBucketName().getValue())
					service.setName((String) dataBalanceBucketInfo
							.getBalanceBucketName().getValue());
				/*
				 * if (null != dataBalanceBucketInfo.getServiceType())
				 * service.setType(dataBalanceBucketInfo.getServiceType());
				 */
				service.setType(ServiceType.Data.getValue());
				if (null != dataBalanceBucketInfo.getExpiryDate()
						.toGregorianCalendar().getTime())
					service.setExpiry(dataBalanceBucketInfo.getExpiryDate()
							.toGregorianCalendar().getTime());
				service.setUnit(dataUnits);
				service.setCapacity(capacity);
				service.setUsage(usage);

				service.setStartingNow(Boolean.valueOf(startingNow));
				LOGGER.debug("service object:" + service.getId() + " "
						+ service.getName() + " " + service.getType() + " "
						+ service.getExpiry() + " " + service.getCapacity()
						+ " " + service.getUsage() + " " + service.getUnit()
						+ "\n");
				serviceList.add(service);
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		LOGGER.debug("**ending getBasePlanServiceForData**");
		
		return serviceList;
		

	}

	private List<com.ericsson.cac.sprint.selfcare.workflow.datamodel.Service> getBasePlanServiceForMessage(
			QueryPrepaidBalanceAndThresholdInfoResponseType queryPrepaidBalanceAndThresholdInfoResponse) {
		
		LOGGER.debug("**starting getBasePlanServiceForMessage**");

		String capacity = null;
		String usage = null;

		List<com.ericsson.cac.sprint.selfcare.workflow.datamodel.Service> serviceList = new ArrayList<com.ericsson.cac.sprint.selfcare.workflow.datamodel.Service>();
		com.ericsson.cac.sprint.selfcare.workflow.datamodel.Service service = null;
		try {
			capacity = queryPrepaidBalanceAndThresholdInfoResponse
					.getAccountBalanceResponse().getMessagingUsageInfo()
					.getTotalMessagingBalance();
			usage = queryPrepaidBalanceAndThresholdInfoResponse
					.getAccountBalanceResponse().getMessagingUsageInfo()
					.getTotalMessagingUsage();
			for (MessagingBalanceBucketInfoType messagingBalanceBucketInfo : queryPrepaidBalanceAndThresholdInfoResponse
					.getAccountBalanceResponse().getMessagingUsageInfo()
					.getMessagingBalanceBucketList()
					.getMessagingBalanceBucketInfo()) {
				LOGGER.debug("Messageusage details: BucketType: "
						+ String.valueOf(messagingBalanceBucketInfo
								.getBucketType())
						+ " BucketName: "
						+ (String) messagingBalanceBucketInfo
								.getBalanceBucketName().getValue()
						+ " ServiceType: "
						// + messagingBalanceBucketInfo.getServiceType()
						+ ServiceType.Text
						+ " ExpiryDate: "
						+ messagingBalanceBucketInfo.getExpiryDate()
								.toGregorianCalendar().getTime()
						+ " startingNow: " + Boolean.valueOf(startingNow));

				service = new com.ericsson.cac.sprint.selfcare.workflow.datamodel.Service();

				if (null != String.valueOf(messagingBalanceBucketInfo
						.getBucketType()))
					service.setId(String.valueOf(messagingBalanceBucketInfo
							.getBucketType()));
				if (null != (String) messagingBalanceBucketInfo
						.getBalanceBucketName().getValue())
					service.setName((String) messagingBalanceBucketInfo
							.getBalanceBucketName().getValue());
				/*
				 * if (null != messagingBalanceBucketInfo.getServiceType())
				 * service.setType(messagingBalanceBucketInfo.getServiceType());
				 */
				service.setType(ServiceType.Text.getValue());
				if (null != messagingBalanceBucketInfo.getExpiryDate()
						.toGregorianCalendar().getTime())
					service.setExpiry(messagingBalanceBucketInfo
							.getExpiryDate().toGregorianCalendar().getTime());
				service.setStartingNow(Boolean.valueOf(startingNow));
				service.setCapacity(capacity);
				service.setUsage(usage);

				LOGGER.debug("service object:" + service.getId() + " "
						+ service.getName() + " " + service.getType() + " "
						+ service.getExpiry() + " " + service.getStartingNow()
						+ " " + service.getCapacity() + " "
						+ service.getUsage() + "\n");
				serviceList.add(service);
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		LOGGER.debug("**ending getBasePlanServiceForMessage**");
		
		return serviceList;

	}

	@Override
	public EligibleServicesResponse getEligibleServices(
			UserContextRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TalkHistoryResponse getTalkHistory(UserContextRequest request,
			SearchCriteriaRequest criteria) {

		LOGGER.info("Entering getTalkHistory workflow method");

		QueryPrepaidUsageDetailsRequestType queryPrepaidUsageDetailsRequest = null;
		QueryPrepaidUsageDetailsResponseType queryPrepaidUsageDetails = null;
		TalkHistoryResponse talkHistoryResponse = new TalkHistoryResponse();

		try {
			Holder<WsMessageHeaderType> header = headerHandler
					.getHeader(Service.QUERY_PREPAID_SUBSCRIBER_SERVICE);

			queryPrepaidUsageDetailsRequest = new QueryPrepaidUsageDetailsRequestType();

			SimpleDateFormat dtFormat = new SimpleDateFormat(dateFormat);

			if (commonUtil.isNullOrBlank(request.getUserContext()
					.getSubscriberId())) {

				LOGGER.error("Subscriber Id obtained in request is null or empty");
				throw new Exception(
						"Subscriber Id obtained in request is null or empty");
			}

			SearchCriteriaType subscriberInfo = new SearchCriteriaType();
			subscriberInfo.setSubscriberId(request.getUserContext()
					.getSubscriberId());

			ApplicationCallingInfoType applicationCallingInfo = new ApplicationCallingInfoType();
			applicationCallingInfo.setAccessId(getTalkHistoryAccessId);

			PrepaidUsageDetailsRequestInfoType prepaidUsageDetailsRequestInfo = new PrepaidUsageDetailsRequestInfoType();
			prepaidUsageDetailsRequestInfo.setFromDate(DatatypeFactory
					.newInstance().newXMLGregorianCalendar(
							dtFormat.format(criteria.getFromDate())));

			PaginationInfoType paginationInfo = new PaginationInfoType();
			paginationInfo.setPageNumber((short) criteria.getPageNumber());
			paginationInfo.setPageBufferSize(Short.parseShort(bufferSize));
			prepaidUsageDetailsRequestInfo.setPaginationInfo(paginationInfo);

			queryPrepaidUsageDetailsRequest.setSubscriberInfo(subscriberInfo);
			queryPrepaidUsageDetailsRequest
					.setApplicationCallingInfo(applicationCallingInfo);
			queryPrepaidUsageDetailsRequest
					.setPrepaidUsageDetailsRequestInfo(prepaidUsageDetailsRequestInfo);

			LOGGER.info("Invoking queryPrepaidSubscriberProxyService -> queryPrepaidUsageDetails");
			queryPrepaidUsageDetails = queryPrepaidSubscriberProxyService
					.queryPrepaidUsageDetails(queryPrepaidUsageDetailsRequest,
							header);

			if (null == queryPrepaidUsageDetails
					|| null == queryPrepaidUsageDetails
							.getPrepaidUsageDetailsHistoryInfoList()) {
				throw new Exception(
						"Null response obtained from queryPrepaidSubscriberProxyService -> queryPrepaidUsageDetails");
			}

			List<PrepaidUsageDetailsHistoryInfoType> prepaidUsageDetailsHistoryInfoTypesList = queryPrepaidUsageDetails
					.getPrepaidUsageDetailsHistoryInfoList()
					.getPrepaidUsageDetailsHistoryInfo();

			if (null != prepaidUsageDetailsHistoryInfoTypesList) {

				LOGGER.debug("queryPrepaidUsageDetails has prepaidUsageDetailsHistoryInfoTypesList");

				talkHistoryResponse
						.setTalkHistory(getTalkHistoryResponse(prepaidUsageDetailsHistoryInfoTypesList));

				talkHistoryResponse.setStatusMessageResponse(commonUtil
						.populateStatusRespMesg(false, ""));
			}

		} catch (Exception e) {
			LOGGER.error("Exception occured --> " + e.getMessage(), e);
			raiseSnmpAlarm();
			talkHistoryResponse.setStatusMessageResponse(commonUtil
					.populateStatusRespMesg(true, e.getMessage()));
		}

		LOGGER.info("Leaving getTalkHistory workflow method");
		return talkHistoryResponse;
	}

	public TextHistoryResponse getTextHistory(UserContextRequest request,
			SearchCriteriaRequest criteria) {

		LOGGER.info("Entering getTextHistory workflow method");

		QueryPrepaidUsageDetailsRequestType queryPrepaidUsageDetailsRequest = null;
		QueryPrepaidUsageDetailsResponseType queryPrepaidUsageDetailsResponse = null;

		TextHistoryResponse textHistoryResponse = new TextHistoryResponse();

		try {

			/*
			 * Step 1: call Query Prepaid Subsriber Proxy Adapter
			 */

			SimpleDateFormat dtFormat = new SimpleDateFormat(dateFormat);

			queryPrepaidUsageDetailsRequest = new QueryPrepaidUsageDetailsRequestType();
			queryPrepaidUsageDetailsResponse = new QueryPrepaidUsageDetailsResponseType();

			if (commonUtil.isNullOrBlank(request.getUserContext()
					.getSubscriberId())) {

				LOGGER.error("Subscriber Id obtained in request is null or empty");
				throw new Exception(
						"Subscriber Id obtained in request is null or empty");
			}

			LOGGER.info("Subscriber Id obtained from request is "
					+ request.getUserContext().getSubscriberId());

			Holder<WsMessageHeaderType> header = headerHandler
					.getHeader(Service.QUERY_PREPAID_SUBSCRIBER_PROXY_SERVICE);

			SearchCriteriaType subscriberInfo = new SearchCriteriaType();
			subscriberInfo.setSubscriberId(request.getUserContext()
					.getSubscriberId());

			ApplicationCallingInfoType applicationCallingInfoType = new ApplicationCallingInfoType();
			applicationCallingInfoType.setAccessId(getTextHistoryAccessId);

			PrepaidUsageDetailsRequestInfoType prepaidUsageDetailsRequestInfoType = new PrepaidUsageDetailsRequestInfoType();

			prepaidUsageDetailsRequestInfoType.setFromDate(DatatypeFactory
					.newInstance().newXMLGregorianCalendar(
							dtFormat.format(criteria.getFromDate())));

			PaginationInfoType paginationInfo = new PaginationInfoType();

			paginationInfo.setPageNumber((short) criteria.getPageNumber());
			paginationInfo.setPageBufferSize(Short
					.parseShort(getTextHistoryBufferSize));

			prepaidUsageDetailsRequestInfoType
					.setPaginationInfo(paginationInfo);

			queryPrepaidUsageDetailsRequest
					.setPrepaidUsageDetailsRequestInfo(prepaidUsageDetailsRequestInfoType);
			queryPrepaidUsageDetailsRequest
					.setApplicationCallingInfo(applicationCallingInfoType);
			queryPrepaidUsageDetailsRequest.setSubscriberInfo(subscriberInfo);

			LOGGER.info("Invoking queryPrepaidSubscriberProxyService -> queryPrepaidUsageDetails");

			queryPrepaidUsageDetailsResponse = queryPrepaidSubscriberProxyService
					.queryPrepaidUsageDetails(queryPrepaidUsageDetailsRequest,
							header);

			if (null == queryPrepaidUsageDetailsResponse
					|| null == queryPrepaidUsageDetailsResponse
							.getPrepaidUsageDetailsHistoryInfoList()) {

				throw new Exception(
						"Null response obtained from queryPrepaidSubscriberProxyService -> queryPrepaidUsageDetails");
			}

			List<PrepaidUsageDetailsHistoryInfoType> prepaidUsageDetailsHistoryInfoTypesList = queryPrepaidUsageDetailsResponse
					.getPrepaidUsageDetailsHistoryInfoList()
					.getPrepaidUsageDetailsHistoryInfo();

			if (null != prepaidUsageDetailsHistoryInfoTypesList) {

				textHistoryResponse
						.setTextHistory(getTextHistoryResponse(prepaidUsageDetailsHistoryInfoTypesList));

				textHistoryResponse.setStatusMessageResponse(commonUtil
						.populateStatusRespMesg(false, ""));
			}
		}

		catch (Exception e) {
			LOGGER.error("Exception occurred --> " + e.getMessage(), e);
			raiseSnmpAlarm();
			textHistoryResponse.setStatusMessageResponse(commonUtil
					.populateStatusRespMesg(true, e.getMessage()));
		}

		LOGGER.info("Leaving getTextHistory workflow method");
		return textHistoryResponse;
	}

	@Override
	public StatusMessageResponse updatePlan(CartRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AddOnsResponse getEligibleAddOns(AddOnsRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CartPaymentResponse restartPlan(UserContextRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	private TextHistory[] getTextHistoryResponse(
			List<PrepaidUsageDetailsHistoryInfoType> prepaidUsageDetailsHistoryInfoTypesList) {

		List<TextHistory> textHistoryList = new ArrayList<TextHistory>();

		int len = prepaidUsageDetailsHistoryInfoTypesList.size();

		TextHistory textHistory = null;

		for (int i = 0; i < len; i++) {

			textHistory = new TextHistory();

			if (null != prepaidUsageDetailsHistoryInfoTypesList.get(i)) {

				if (null != prepaidUsageDetailsHistoryInfoTypesList.get(i)
						.getPrepaidUsageDetailsInfo()
						&& null != prepaidUsageDetailsHistoryInfoTypesList
								.get(i).getPrepaidUsageDetailsInfo()
								.getUsageInfo()
						&& !StringUtils
								.isBlank(prepaidUsageDetailsHistoryInfoTypesList
										.get(i).getPrepaidUsageDetailsInfo()
										.getUsageInfo().getServiceType())
						&& getTextHistoryServiceTypeTextMessaging
								.equalsIgnoreCase(prepaidUsageDetailsHistoryInfoTypesList
										.get(i).getPrepaidUsageDetailsInfo()
										.getUsageInfo().getServiceType())) {

					LOGGER.debug("Service type of Text Messaging is present in usage details history");

					textHistory
							.setDate(commonUtil
									.getDateFromXmlGregorianCal(prepaidUsageDetailsHistoryInfoTypesList
											.get(i).getEventStartTime()));
				} else {
					LOGGER.debug("Text history event start time is empty or null");
				}

				if (!StringUtils
						.isBlank(prepaidUsageDetailsHistoryInfoTypesList.get(i)
								.getCallDirection())) {
					// Check for CallDirection - Outgoing
					if (getTextHistoryCallDirectionOutgoing
							.equalsIgnoreCase(prepaidUsageDetailsHistoryInfoTypesList
									.get(i).getCallDirection())) {

						LOGGER.debug("Call direction of OUTGOING is present in usage details history");

						textHistory
								.setTo(prepaidUsageDetailsHistoryInfoTypesList
										.get(i).getCalledNumber());

					} else {

						if (!StringUtils
								.isBlank(prepaidUsageDetailsHistoryInfoTypesList
										.get(i).getCalledNumber())) {
							textHistory
									.setFrom(prepaidUsageDetailsHistoryInfoTypesList
											.get(i).getCalledNumber());
						} else {

							LOGGER.debug("Text history called number is empty or null");

							textHistory.setFrom("");
							textHistory.setTo("");

						}
					}
				} else {
					LOGGER.debug("Text history call direction is empty or null");
					textHistory.setTo("");
					textHistory.setFrom("");
				}

				textHistoryList.add(textHistory);
			}
		}

		return (TextHistory[]) textHistoryList
				.toArray(new TextHistory[textHistoryList.size()]);

	}

	private TalkHistory[] getTalkHistoryResponse(
			List<PrepaidUsageDetailsHistoryInfoType> prepaidUsageDetailsHistoryInfoTypesList) {

		List<TalkHistory> talkHistoryList = new ArrayList<TalkHistory>();

		int len = prepaidUsageDetailsHistoryInfoTypesList.size();

		TalkHistory talkHistory = null;

		for (int i = 0; i < len; i++) {

			talkHistory = new TalkHistory();

			if (null != prepaidUsageDetailsHistoryInfoTypesList.get(i)
					.getPrepaidUsageDetailsInfo()
					&& null != prepaidUsageDetailsHistoryInfoTypesList.get(i)
							.getPrepaidUsageDetailsInfo().getUsageInfo()
					&& !StringUtils
							.isBlank(prepaidUsageDetailsHistoryInfoTypesList
									.get(i).getPrepaidUsageDetailsInfo()
									.getUsageInfo().getServiceType())
					&& getTalkHistoryServiceTypeVoice
							.equalsIgnoreCase(prepaidUsageDetailsHistoryInfoTypesList
									.get(i).getPrepaidUsageDetailsInfo()
									.getUsageInfo().getServiceType())) {

				LOGGER.debug("Service type of VOICE is present in usage details history");

				if (null != prepaidUsageDetailsHistoryInfoTypesList.get(i)
						.getEventStartTime()) {
					talkHistory
							.setDate(commonUtil
									.getDateFromXmlGregorianCal(prepaidUsageDetailsHistoryInfoTypesList
											.get(i).getEventStartTime()));
				} else {
					LOGGER.debug("Talk history event start time received is null or empty");
				}

				if (!StringUtils
						.isBlank(prepaidUsageDetailsHistoryInfoTypesList.get(i)
								.getCallDirection())) {

					if (getTalkHistoryCallDirectionOutgoing
							.equalsIgnoreCase(prepaidUsageDetailsHistoryInfoTypesList
									.get(i).getCallDirection())) {

						LOGGER.debug("Call direction of OUTGOING is present in usage details history");

						talkHistory
								.setTo(prepaidUsageDetailsHistoryInfoTypesList
										.get(i).getCalledNumber());
					} else {

						if (!StringUtils
								.isBlank(prepaidUsageDetailsHistoryInfoTypesList
										.get(i).getCalledNumber())) {

							talkHistory
									.setFrom(prepaidUsageDetailsHistoryInfoTypesList
											.get(i).getCalledNumber());

						} else {

							LOGGER.debug("Talk history called number is empty or null and hence setting to and from is set to empty");

							talkHistory.setFrom("");
							talkHistory.setTo("");
						}
					}
				} else {
					LOGGER.debug("Talk history call direction is empty or null and hence setting to and from is set to empty");
					talkHistory.setFrom("");
					talkHistory.setTo("");
				}

				talkHistoryList.add(talkHistory);
			}
		}

		return (TalkHistory[]) talkHistoryList
				.toArray(new TalkHistory[talkHistoryList.size()]);
	}

	private com.ericsson.cac.sprint.selfcare.workflow.datamodel.Service[] getAddOnServiceList(
			List<SocListType.SocInfo> socInfoList) throws Exception {

		List<com.ericsson.cac.sprint.selfcare.workflow.datamodel.Service> addOnServiceList = new ArrayList<com.ericsson.cac.sprint.selfcare.workflow.datamodel.Service>();

		com.ericsson.cac.sprint.selfcare.workflow.datamodel.Service addOnService = null;

		// for (SocListType.SocInfo socInfo : socInfoList) {

		SocListType.SocInfo socInfo = null;

		for (int i = 0; i < socInfoList.size(); i++) {

			socInfo = socInfoList.get(i);

			if (null != socInfo) {
				if (!socInfo.getSocType().equalsIgnoreCase(pSocTypeValue)) {

					addOnService = new com.ericsson.cac.sprint.selfcare.workflow.datamodel.Service();

					if (!StringUtils.isBlank(socInfo.getSocCode())) {
						LOGGER.debug("AddOn: SocCode:" + socInfo.getSocCode());
						addOnService.setId(socInfo.getSocCode());
					} else {
						addOnService.setId("");
					}

					if (!StringUtils.isBlank(socInfo.getName())) {
						LOGGER.debug("AddOn: Name:" + socInfo.getName());
						addOnService.setName(socInfo.getName());
					} else {
						addOnService.setName("");
					}

					if (!StringUtils.isBlank(socInfo.getPlanTypeDesc())) {
						if (socInfo.getPlanTypeDesc().contains("&")) {
							LOGGER.debug("AddOn: PlanTypeDesc:"
									+ socInfo.getPlanTypeDesc().replace("&",
											"/"));
							addOnService.setDescription(socInfo
									.getPlanTypeDesc().replace("&", "/"));
						} else {
							LOGGER.debug("AddOn: PlanTypeDesc:"
									+ socInfo.getPlanTypeDesc());
							addOnService.setDescription(socInfo
									.getPlanTypeDesc());
						}
					} else {
						addOnService.setDescription("");
					}

					if (null != socInfo.getPrepaidSocAttributeInfo()
							&& null != socInfo.getPrepaidSocAttributeInfo()
									.getAllowanceDataList()) {
						addOnService.setCapacity(socInfo
								.getPrepaidSocAttributeInfo()
								.getAllowanceDataList().getAllowanceDataInfo()
								.get(0).getAllowance().toString());
					} else {
						addOnService.setCapacity("");
					}

					addOnService.setUnit(unit);

					if (!StringUtils.isBlank(socInfo.getSocType())) {
						LOGGER.debug("AddOn: Type:" + socInfo.getSocType());
						addOnService.setType(socInfo.getSocType());
					} else {
						addOnService.setType("");
					}

					if (null != socInfo.getRecurringCharge()) {
						LOGGER.debug("AddOn: Rate:"
								+ socInfo.getRecurringCharge().floatValue());
						addOnService.setRate(socInfo.getRecurringCharge()
								.floatValue());
					} else {
						LOGGER.debug("AddOn rate is empty or null");
						// addOnService.setRate(0.00f);
					}

					LOGGER.debug("AddOn: Currency:" + currency.toString());
					addOnService.setCurrency(currency);
					addOnService.setAddOn(Boolean.valueOf(trueValue));

					if (null != socInfo.getExpiryDate()) {

						LOGGER.debug("AddOn: ExpiryDate:"
								+ socInfo.getExpiryDate().toGregorianCalendar()
										.getTime());
						addOnService.setExpiry(commonUtil
								.getDateFromXmlGregorianCal(socInfo
										.getExpiryDate()));
					} else {

						LOGGER.debug("AddOn expiry date is empty or null");
					}

					LOGGER.debug("AddOn: Recurrence:" + recurrence.toString());
					addOnService.setRecurrence(recurrence);

					addOnServiceList.add(addOnService);
					LOGGER.debug("Added addOn service to array");
				}
			}
		}

		// Convert list to array
		com.ericsson.cac.sprint.selfcare.workflow.datamodel.Service[] serviceArr = addOnServiceList
				.toArray(new com.ericsson.cac.sprint.selfcare.workflow.datamodel.Service[addOnServiceList
						.size()]);
		return serviceArr;
	}

	private com.ericsson.cac.sprint.selfcare.workflow.datamodel.Service[] getBasePlanServiceList(
			PricePlanInfoType pricePlanInfoType) throws Exception

	{
		com.ericsson.cac.sprint.selfcare.workflow.datamodel.Service basePlanService = new com.ericsson.cac.sprint.selfcare.workflow.datamodel.Service();

		if (!StringUtils.isBlank(pricePlanInfoType.getSocCode())) {
			LOGGER.debug("BasePlan: SocCode:" + pricePlanInfoType.getSocCode());
			basePlanService.setId(pricePlanInfoType.getSocCode());
		} else {
			basePlanService.setId("");
		}

		if (!StringUtils.isBlank(pricePlanInfoType.getName())) {
			LOGGER.debug("BasePlan: Name:" + pricePlanInfoType.getName());
			basePlanService.setName(pricePlanInfoType.getName());
		} else {
			basePlanService.setName("");
		}

		if (!StringUtils.isBlank(pricePlanInfoType.getPlanTypeDesc())) {
			if (pricePlanInfoType.getPlanTypeDesc().contains("&")) {
				LOGGER.debug("BasePlan: PlanTypeDesc:"
						+ pricePlanInfoType.getPlanTypeDesc().replace("&", "/"));
				basePlanService.setDescription(pricePlanInfoType
						.getPlanTypeDesc().replace("&", "/"));
			} else {
				LOGGER.debug("BasePlan: PlanTypeDesc:"
						+ pricePlanInfoType.getPlanTypeDesc());
				basePlanService.setDescription(pricePlanInfoType
						.getPlanTypeDesc());
			}
		} else {
			basePlanService.setDescription("");
		}

		if (null != pricePlanInfoType.getPrepaidPlanAttributeInfo()
				&& null != pricePlanInfoType.getPrepaidPlanAttributeInfo()
						.getAllowanceDataList()) {

			LOGGER.debug("BasePlan: Capacity:"
					+ pricePlanInfoType.getPrepaidPlanAttributeInfo()
							.getAllowanceDataList().getAllowanceDataInfo()
							.get(0).getAllowance().toString());

			basePlanService.setCapacity(pricePlanInfoType
					.getPrepaidPlanAttributeInfo().getAllowanceDataList()
					.getAllowanceDataInfo().get(0).getAllowance().toString());
		} else {
			basePlanService.setCapacity("");
		}

		basePlanService.setUnit(unit);
		basePlanService.setType(type);

		if (null != pricePlanInfoType.getRecurringCharge()) {

			LOGGER.debug("BasePlan: Rate:"
					+ pricePlanInfoType.getRecurringCharge().floatValue());

			basePlanService.setRate(pricePlanInfoType.getRecurringCharge()
					.floatValue());
		} else {
			// basePlanService.setRate(0.00f);
			LOGGER.debug("Base plan service rate is empty or null");
		}

		basePlanService.setCapacity(currency);
		basePlanService.setAddOn(Boolean.valueOf(falseValue));

		if (null != pricePlanInfoType.getExpiryDate()) {

			LOGGER.debug("BasePlan: Expiry:"
					+ pricePlanInfoType.getExpiryDate().toGregorianCalendar()
							.getTime());

			basePlanService.setExpiry(commonUtil
					.getDateFromXmlGregorianCal(pricePlanInfoType
							.getExpiryDate()));
		} else {
			LOGGER.debug("Base plan service expiry date is empty or null");
		}

		basePlanService.setRecurrence(recurrence);

		com.ericsson.cac.sprint.selfcare.workflow.datamodel.Service[] basePlan = { basePlanService };

		return basePlan;
	}

	/**
	 * Method isSessionValid.
	 * 
	 * @return Boolean
	 */
	public Boolean isSessionValid() {
		return true;
	}

	/**
	 * 
	 */
	public void transactionLog() {
		LOGGER.debug("Transaction Log to be implemented");
	}

	/**
	 * 
	 */
	private void raiseSnmpAlarm() {
		LOGGER.debug("Raise Alarm to be implemented");

	}

	@Override
	public TransactionHistoryResponse getTransactionHistory(
			UserContextRequest request, SearchCriteriaRequest criteria) {

		LOGGER.info("Entering getTransactionHistory workflow method");

		TransactionHistoryResponse transactionHistoryResponse = new TransactionHistoryResponse();

		TransactionHistory transactionHistory = new TransactionHistory();

		try {

			transactionHistory.setPaymentHistoryPagination((getPaymentHistory(
					request, criteria)));

			transactionHistory.setOrders(getOrderHistory(request, criteria));

			transactionHistoryResponse
					.setTransactionHistory(transactionHistory);

			transactionHistoryResponse.setStatusMessageResponse(commonUtil
					.populateStatusRespMesg(false, ""));

		} catch (Exception e) {
			LOGGER.error("Exception occured --> " + e.getMessage(), e);
			raiseSnmpAlarm();
			transactionHistoryResponse.setStatusMessageResponse(commonUtil
					.populateStatusRespMesg(true, e.getMessage()));
		}

		LOGGER.info("Leaving getTransactionHistory workflow method");
		return transactionHistoryResponse;
	}

	private OrderHistory[] getOrderHistory(UserContextRequest request,
			SearchCriteriaRequest criteria) throws Exception {

		QueryAccountOrdersType queryAccountOrdersTypeReq = null;
		List<OrderHistory> orderHistoryList = new ArrayList<OrderHistory>();
		OrderHistory orderHistory = null;

		Holder<WsMessageHeaderType> header = headerHandler
				.getHeader(Service.QUERY_ORDER_FULFILLMENT_SERVICE);

		queryAccountOrdersTypeReq = new QueryAccountOrdersType();

		if (commonUtil
				.isNullOrBlank(request.getUserContext().getSubscriberId())) {

			LOGGER.error("Subscriber Id obtained in request is null or empty");
			throw new Exception(
					"Subscriber Id obtained in request is null or empty");
		}

		com.sprint.integration.interfaces.queryaccountorders.v1.queryaccountorders.SearchCriteriaType subscriberInfo = new com.sprint.integration.interfaces.queryaccountorders.v1.queryaccountorders.SearchCriteriaType();
		subscriberInfo.setSubscriberId(request.getUserContext()
				.getSubscriberId());

		queryAccountOrdersTypeReq.setInfo(subscriberInfo);

		QueryAccountOrdersResponseType queryAccountOrdersResponseType = queryOrderFulfillmentProxyService
				.queryAccountOrdersResponseType(queryAccountOrdersTypeReq,
						header);

		if (queryAccountOrdersResponseType.getOrderList() != null) {
			for (OrderInfoType orderInfoType : queryAccountOrdersResponseType
					.getOrderList().getOrderInfo()) {
				orderHistory = getOrderHistory(orderInfoType.getOrderKey());
				if (orderHistory != null) {
					orderHistoryList.add(orderHistory);
				}
			}
		}

		return (OrderHistory[]) orderHistoryList
				.toArray(new OrderHistory[orderHistoryList.size()]);
	}

	private OrderHistory getOrderHistory(String orderKey) {
		QueryOrderInfoType queryOrderInfoType = null;
		OrderHistory orderHistory = null;
		try {
			Holder<WsMessageHeaderType> header = headerHandler
					.getHeader(Service.QUERY_ORDER_FULFILLMENT_SERVICE);

			queryOrderInfoType = new QueryOrderInfoType();
			InfoType infoType = new InfoType();
			OrderKeyInfoType orderInfo = new OrderKeyInfoType();
			orderInfo.setOrderKey(orderKey);
			infoType.setOrderKeyInfo(orderInfo);
			queryOrderInfoType.setInfo(infoType);

			QueryOrderInfoResponseType queryOrderInfoResponseType = queryOrderFulfillmentProxyService
					.queryOrderInfoResponseType(queryOrderInfoType, header);

			orderHistory = new OrderHistory();
			if (queryOrderInfoResponseType.getOrderInfo().getOrderDate() != null) {
				orderHistory.setDate(queryOrderInfoResponseType.getOrderInfo()
						.getOrderDate().toGregorianCalendar().getTime());
			}
			orderHistory.setStatus(queryOrderInfoResponseType.getOrderInfo()
					.getOrderStatus());
			orderHistory
					.setDescription(getOrderHistoryDescription(queryOrderInfoResponseType));
			if (queryOrderInfoResponseType.getOrderInfo().getDeliveredDate() != null) {
				orderHistory.setDeliveryDate(queryOrderInfoResponseType
						.getOrderInfo().getDeliveredDate()
						.toGregorianCalendar().getTime());
			}

		} catch (Exception e) {
			LOGGER.error("Exception occured --> " + e.getMessage(), e);
			raiseSnmpAlarm();
		}
		return orderHistory;
	}

	private String getOrderHistoryDescription(
			QueryOrderInfoResponseType queryOrderInfoResponseType)
			throws Exception {

		String orderHistoryDescription = null;

		for (OrderLineType orderLineType : queryOrderInfoResponseType
				.getOrderLinesList().getOrderLineInfo()) {
			orderHistoryDescription = orderLineType.getItemDesc();
		}

		return orderHistoryDescription;
	}

	private PaymentHistoryPagination getPaymentHistory(
			UserContextRequest request, SearchCriteriaRequest criteria)
			throws Exception {

		QueryPrepaidUsageDetailsRequestType queryPrepaidUsageDetailsRequest = null;
		QueryPrepaidUsageDetailsResponseType queryPrepaidUsageDetails = null;
		PaymentHistory[] paymentHistory = null;
		PaymentHistoryPagination paymentPagination = new PaymentHistoryPagination();

		Holder<WsMessageHeaderType> header = headerHandler
				.getHeader(Service.QUERY_PREPAID_SUBSCRIBER_SERVICE);

		queryPrepaidUsageDetailsRequest = new QueryPrepaidUsageDetailsRequestType();

		SimpleDateFormat dtFormat = new SimpleDateFormat(dateFormat);

		if (commonUtil
				.isNullOrBlank(request.getUserContext().getSubscriberId())) {

			LOGGER.error("Subscriber Id obtained in request is null or empty");
			throw new Exception(
					"Subscriber Id obtained in request is null or empty");
		}

		SearchCriteriaType subscriberInfo = new SearchCriteriaType();
		subscriberInfo.setSubscriberId(request.getUserContext()
				.getSubscriberId());

		ApplicationCallingInfoType applicationCallingInfo = new ApplicationCallingInfoType();
		applicationCallingInfo.setAccessId(getTransactionHistoryAccessId);

		PrepaidUsageDetailsRequestInfoType prepaidUsageDetailsRequestInfo = new PrepaidUsageDetailsRequestInfoType();
		if (criteria.getFromDate() != null) {
			prepaidUsageDetailsRequestInfo.setFromDate(DatatypeFactory
					.newInstance().newXMLGregorianCalendar(
							dtFormat.format(criteria.getFromDate())));
		}

		PaginationInfoType paginationInfo = new PaginationInfoType();
		paginationInfo.setPageNumber((short) criteria.getPageNumber());
		paginationInfo.setPageBufferSize((short) criteria.getNumberOfItems());
		prepaidUsageDetailsRequestInfo.setPaginationInfo(paginationInfo);

		queryPrepaidUsageDetailsRequest.setSubscriberInfo(subscriberInfo);
		queryPrepaidUsageDetailsRequest
				.setApplicationCallingInfo(applicationCallingInfo);
		queryPrepaidUsageDetailsRequest
				.setPrepaidUsageDetailsRequestInfo(prepaidUsageDetailsRequestInfo);

		LOGGER.info("Invoking queryPrepaidSubscriberProxyService -> queryPrepaidUsageDetails");
		queryPrepaidUsageDetails = queryPrepaidSubscriberProxyService
				.queryPrepaidUsageDetails(queryPrepaidUsageDetailsRequest,
						header);

		if (null == queryPrepaidUsageDetails
				|| null == queryPrepaidUsageDetails
						.getPrepaidUsageDetailsHistoryInfoList()) {
			throw new Exception(
					"Null response obtained from queryPrepaidSubscriberProxyService -> queryPrepaidUsageDetails");
		}

		List<PrepaidUsageDetailsHistoryInfoType> prepaidUsageDetailsHistoryInfoTypesList = queryPrepaidUsageDetails
				.getPrepaidUsageDetailsHistoryInfoList()
				.getPrepaidUsageDetailsHistoryInfo();

		if (null != prepaidUsageDetailsHistoryInfoTypesList) {

			LOGGER.debug("queryPrepaidUsageDetails has prepaidUsageDetailsHistoryInfoTypesList");

			paymentHistory = getPaymentHistory(prepaidUsageDetailsHistoryInfoTypesList);
		}

		// queryPrepaidUsageDetails.getPaginationResultInfo().getTotalCount();

		paymentPagination.setPayments(paymentHistory);
		paymentPagination.setTotalPaymentHistoryCount(queryPrepaidUsageDetails
				.getPaginationResultInfo().getTotalCount().intValue());
		return paymentPagination;
	}

	private PaymentHistory[] getPaymentHistory(
			List<PrepaidUsageDetailsHistoryInfoType> prepaidUsageDetailsHistoryInfoTypesList) {

		List<PaymentHistory> paymentHistoryList = new ArrayList<PaymentHistory>();

		PaymentHistory paymentHistory = null;

		for (PrepaidUsageDetailsHistoryInfoType prepaidUsageDetailsHistoryInfoType : prepaidUsageDetailsHistoryInfoTypesList) {
			if (checkEventCategoryType(prepaidUsageDetailsHistoryInfoType
					.getEventCategoryList().getEventCategoryInfo(),
					getTransactionHistoryEventCategoryChargesAndCredit)) {
				paymentHistory = new PaymentHistory();

				paymentHistory.setDate(prepaidUsageDetailsHistoryInfoType
						.getEventStartTime().toGregorianCalendar().getTime());
				paymentHistory.setCurrency(getTransactionHistoryCurrencyUSD);
				paymentHistory.setAmount(prepaidUsageDetailsHistoryInfoType
						.getChargeAmount().floatValue());
				paymentHistory
						.setDescription(prepaidUsageDetailsHistoryInfoType
								.getTransactionDescription());
				paymentHistory.setDebit(true);

				paymentHistoryList.add(paymentHistory);
			}

		}

		return (PaymentHistory[]) paymentHistoryList
				.toArray(new PaymentHistory[paymentHistoryList.size()]);

	}

	private boolean checkEventCategoryType(
			List<EventCategoryInfoType> eventCategoryList,
			String eventCategoryType) {
		boolean flag = false;

		try {
			for (EventCategoryInfoType eventCategoryInfoType : eventCategoryList) {
				if (eventCategoryType.equalsIgnoreCase(eventCategoryInfoType
						.getEventCategory())) {
					flag = true;
					break;
				}
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return flag;
	}
}
