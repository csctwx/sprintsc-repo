package com.ericsson.cac.sprint.selfcare.workflow.impl;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.ws.Holder;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ch.qos.logback.core.status.Status;

import com.ericsson.cac.sprint.adapters.QueryAccountInfoServiceProxyService;
import com.ericsson.cac.sprint.adapters.QueryPrepaidSubscriberProxyService;
import com.ericsson.cac.sprint.selfcare.workflow.AccountHomeWorkflow;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.AccountBalance;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.AccountBalanceResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.Address;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessage;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessageResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberAccount;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberAccountResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.cac.sprint.selfcare.workflow.util.AlarmUtil;
import com.ericsson.cac.sprint.selfcare.workflow.util.CommonUtil;
import com.ericsson.cac.sprint.selfcare.workflow.util.HeaderHandler;
import com.ericsson.cac.sprint.selfcare.workflow.util.Service;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.interfaces.queryaccountbasicinfo.v1.queryaccountbasicinfo.FetchBasicInfoType;
import com.sprint.integration.interfaces.queryaccountbasicinfo.v1.queryaccountbasicinfo.QueryAccountBasicInfo;
import com.sprint.integration.interfaces.queryaccountbasicinfo.v1.queryaccountbasicinfo.QueryAccountBasicInfoResponse;
import com.sprint.integration.interfaces.queryaccountbasicinfo.v1.queryaccountbasicinfo.SearchCriteria;
import com.sprint.integration.interfaces.querybalanceandcharges.v2.querybalanceandchargesv2.QueryBalanceAndChargesV2RequestType;
import com.sprint.integration.interfaces.querybalanceandcharges.v2.querybalanceandchargesv2.QueryBalanceAndChargesV2ResponseType;
import com.sprint.integration.interfaces.querybalanceandcharges.v2.querybalanceandchargesv2.SubscriberInformationType;
import com.sprint.integration.interfaces.queryprepaidsubscriberservice.v1.queryprepaidsubscriberservice.SoapFaultV2;
import com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.QuerySubscriberPrepaidInfoResponseType;

@Component
public class AccountHomeWorkflowImpl implements AccountHomeWorkflow {

	/** Logger class info */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AccountHomeWorkflowImpl.class);

	@Value("${getAccount.fetchBasicInfo}")
	private String isFetchBasicInfo;
	@Value("${getAccount.fetchExpandedBasicInfo}")
	private String isFetchExpandedBasicInfo;
	@Value("${getAccount.fetchBillingAddressInfo}")
	private String isFetchBillingAddressInfo;
	@Value("${getAccount.fetchBillingNameInfo}")
	private String isFetchBillingNameInfo;
	@Value("${getAccount.fetchOtherInfo}")
	private String isFetchOtherInfo;
	@Value("${getAccount.fetchOtherNameInfo}")
	private String isFetchOtherNameInfo;

	@Value("${getAccount.prepaidAccountType}")
	private String prepaidAccountType;

	/**   */
	@Autowired
	private QueryPrepaidSubscriberProxyService queryPrepaidSubscriberProxyService;

	@Autowired
	private QueryAccountInfoServiceProxyService queryAccountInfoServiceProxyService;

	@Autowired
	private HeaderHandler headerHandler;

	@Value("${GetAccountBalance.nextAnniversaryDateInd}")
	private String nextAnniversaryDateInd;

	@Autowired
	private CommonUtil commonUtil;

	@Override
	public StatusMessageResponse getStatusMessage(UserContextRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SubscriberAccountResponse getAccount(UserContextRequest request) {

		LOGGER.info("Entering getAccount workflow method");

		SubscriberAccountResponse subscriberAccountResponse = new SubscriberAccountResponse();
			
		try {

			AlarmUtil.clearSnmpAlarm();

			Holder<WsMessageHeaderType> header = headerHandler
					.getHeader(Service.QUERY_ACCOUNT_INFO_SERVICE);

			// Step 1: Call the QueryAccountInfo adapter
			QueryAccountBasicInfo queryAccountBasicInfoParams = new QueryAccountBasicInfo();
			SearchCriteria searchCriteria = new SearchCriteria();
			searchCriteria.setSubscriberId(request.getUserContext()
					.getSubscriberId());

			queryAccountBasicInfoParams.setInfo(searchCriteria);

			FetchBasicInfoType fetchBasicInfoType = new FetchBasicInfoType();
			fetchBasicInfoType.setFetchBasicInfo(Boolean
					.valueOf(isFetchBasicInfo));
			fetchBasicInfoType.setFetchExpandedBasicInfo(Boolean
					.valueOf(isFetchExpandedBasicInfo));

			queryAccountBasicInfoParams.setBasicInfo(fetchBasicInfoType);

			queryAccountBasicInfoParams.setFetchBillingAddressInfo(Boolean
					.valueOf(isFetchBillingAddressInfo));

			queryAccountBasicInfoParams.setFetchBillingNameInfo(Boolean
					.valueOf(isFetchBillingNameInfo));

			queryAccountBasicInfoParams.setFetchOtherInfo(Boolean
					.valueOf(isFetchOtherInfo));

			queryAccountBasicInfoParams.setFetchOtherNameInfo(Boolean
					.valueOf(isFetchOtherNameInfo));

			QueryAccountBasicInfoResponse queryAccountBasicInfoResponse = queryAccountInfoServiceProxyService
					.queryAccountBasicInfo(queryAccountBasicInfoParams, header);

			// Step 4: Create SubscriberAccountResponse with SubscriberAccount
			// and return
			subscriberAccountResponse
					.setSubscriberAccount(getAccountSubscriberResponse(request,
							queryAccountBasicInfoResponse));
			
			subscriberAccountResponse.setStatusMessageResponse(commonUtil
					.populateStatusRespMesg(false, ""));

		} catch (Exception e) {
			LOGGER.error("Exception occurred --> " + e.getMessage(), e);
			AlarmUtil.raiseSnmpAlarm();
			subscriberAccountResponse.setStatusMessageResponse(commonUtil
					.populateStatusRespMesg(true, e.getMessage()));
		}

		LOGGER.info("Leaving getAccount workflow method");

		return subscriberAccountResponse;
	}

	@Override
	public AccountBalanceResponse getAccountBalance(UserContextRequest request) {

		LOGGER.info("Entering getAccountBalance workflow method");

		QueryBalanceAndChargesV2ResponseType queryBalanceAndChargesV2Response = new QueryBalanceAndChargesV2ResponseType();
		QueryBalanceAndChargesV2RequestType queryBalanceAndChargesV2Request = new QueryBalanceAndChargesV2RequestType();
		AccountBalanceResponse accountBalanceResponse = new AccountBalanceResponse();
		AccountBalance accountBalance = new AccountBalance();

		try {

			AlarmUtil.clearSnmpAlarm();

			SubscriberInformationType subscriberInformation = new SubscriberInformationType();
			if (!StringUtils
					.isBlank(request.getUserContext().getSubscriberId())) {
				subscriberInformation.setSubscriptionId(request
						.getUserContext().getSubscriberId());
			} else if (!StringUtils.isBlank(request.getUserContext()
					.getMsisdn())) {
				subscriberInformation.setMobileDirectoryNumber(request
						.getUserContext().getMsisdn());
			} else {
				throw new Exception("Both subscriberId and mdn are null");
			}
			queryBalanceAndChargesV2Request
					.setSubscriberInformation(subscriberInformation);
			queryBalanceAndChargesV2Request.setNextAnniversaryDateInd(Boolean
					.valueOf(nextAnniversaryDateInd));

			Holder<WsMessageHeaderType> header = headerHandler
					.getHeader(Service.QUERY_PREPAID_SUBSCRIBER_SERVICE);

			queryBalanceAndChargesV2Response = queryPrepaidSubscriberProxyService
					.queryBalanceAndChargesV2(queryBalanceAndChargesV2Request,
							header);

			if (null == queryBalanceAndChargesV2Response) {
				throw new Exception(
						"Null response obtained from queryPrepaidSubscriberProxyService");
			}

			accountBalance.setAccountStatus(queryBalanceAndChargesV2Response
					.getSubscriberStatus().toString());
			accountBalance.setNextMonthCharges(queryBalanceAndChargesV2Response
					.getTotalRecurringAmount().floatValue());
			accountBalance.setCashBalance(queryBalanceAndChargesV2Response
					.getCurrentBalance().floatValue());
			accountBalance.setTotalCharges(queryBalanceAndChargesV2Response
					.getTotalAmount().floatValue());

			// accountBalance.setDueAmount(queryBalanceAndChargesV2Response.get);
			if (queryBalanceAndChargesV2Response.getResponseDateChoice()
					.getNextAnniversaryPaymentDate() != null) {
				accountBalance.setDueBy(queryBalanceAndChargesV2Response
						.getResponseDateChoice()
						.getNextAnniversaryPaymentDate().toGregorianCalendar()
						.getTime());
			} else {
				accountBalance
						.setDueBy(commonUtil
								.getDateFromXmlGregorianCal(queryBalanceAndChargesV2Response
										.getResponseDateChoice()
										.getBalanceExpirationDate()));
			}
			// Mapping is not present in FD
			/*
			 * accountBalance.setBillingCycleDay(queryBalanceAndChargesV2Response
			 * .get ); accountBalance.setCurrency();
			 * accountBalance.setBalanceInfo();
			 */

			accountBalanceResponse.setAccountBalance(accountBalance);
			
			accountBalanceResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(false, ""));

		} catch (SoapFaultV2 e) {
			
			LOGGER.error("SoapFaultV2 occured --> " + e.getMessage(), e);			
			AlarmUtil.raiseSnmpAlarm();			
			accountBalanceResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(true, e.getMessage()));
			
		} catch (DatatypeConfigurationException e) {
			
			LOGGER.error("DatatypeConfigurationException occured --> " + e.getMessage(), e);			
			accountBalanceResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(true, e.getMessage()));
			
		} catch (Exception ex) {
			LOGGER.error("Exception occured --> " + ex.getMessage(), ex);
			accountBalanceResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(true, ex.getMessage()));
		}

		LOGGER.info("Leaving getAccountBalance workflow method");

		return accountBalanceResponse;
	}

	private SubscriberAccount getAccountSubscriberResponse(
			UserContextRequest request,
			QueryAccountBasicInfoResponse queryAccountBasicInfoResponse)
			throws Exception {

		// Step 2: Create the SubscriberAccount Object with the
		// queryAccountBasicInfoResponse received from Step 1

		SubscriberAccount subscriberAccount = new SubscriberAccount();

		Address address = new Address();

		if (null == queryAccountBasicInfoResponse) {
			throw new Exception(
					"Null response obtained from queyAccountInfoServiceProxyService");
		}

		// Fetch MSISDN from QueryPrepaidSubscriberInfo with subscriberId as
		// input
		QuerySubscriberPrepaidInfoResponseType querySubscriberPrepaidInfoResponseType = commonUtil
				.getSubscriberPrepaidInfo(request.getUserContext()
						.getSubscriberId());

		if (null == querySubscriberPrepaidInfoResponseType) {
			throw new Exception(
					"Null response obtained from queryPrepaidSubscriberProxyService");
		}

		subscriberAccount.setMsisdn(querySubscriberPrepaidInfoResponseType
				.getResourceInfo().getMdn());

		// Populating other subscriberAccount response

		if (null != queryAccountBasicInfoResponse.getBillingNameInfo()
				&& null != queryAccountBasicInfoResponse.getBillingNameInfo()
						.getIndividualNameInfo()) {

			if (!commonUtil.isNullOrBlank(queryAccountBasicInfoResponse
					.getBillingNameInfo().getIndividualNameInfo()
					.getFirstName())) {

				subscriberAccount.setFirstName(queryAccountBasicInfoResponse
						.getBillingNameInfo().getIndividualNameInfo()
						.getFirstName());
			}

			if (!commonUtil
					.isNullOrBlank(queryAccountBasicInfoResponse
							.getBillingNameInfo().getIndividualNameInfo()
							.getLastName())) {
				subscriberAccount.setLastName(queryAccountBasicInfoResponse
						.getBillingNameInfo().getIndividualNameInfo()
						.getLastName());

			}

			if (!commonUtil.isNullOrBlank(queryAccountBasicInfoResponse
					.getBillingNameInfo().getIndividualNameInfo()
					.getMiddleName())) {
				subscriberAccount.setMiddleName(queryAccountBasicInfoResponse
						.getBillingNameInfo().getIndividualNameInfo()
						.getMiddleName());
			}

		}

		subscriberAccount.setAccountType(prepaidAccountType);

		if (null != queryAccountBasicInfoResponse.getBasicInfo()) {

			if (!commonUtil.isNullOrBlank(queryAccountBasicInfoResponse
					.getBasicInfo().getPrepaidBrandCode())) {
				subscriberAccount.setBrand(queryAccountBasicInfoResponse
						.getBasicInfo().getPrepaidBrandCode());
			}

			if (null != queryAccountBasicInfoResponse.getBasicInfo()
					.getAccountStatus()) {
				subscriberAccount
						.setAccountStatus(queryAccountBasicInfoResponse
								.getBasicInfo().getAccountStatus().value());
			}

			if (!commonUtil.isNullOrBlank(queryAccountBasicInfoResponse
					.getBasicInfo().getLanguageCode())) {
				subscriberAccount
						.setPreferredLanguage(queryAccountBasicInfoResponse
								.getBasicInfo().getLanguageCode());
			}
		}

		if (null != queryAccountBasicInfoResponse.getOtherInfo()) {

			subscriberAccount.setAlternatePhone(queryAccountBasicInfoResponse
					.getOtherInfo().getHomeNumber());

			subscriberAccount.setBirthdate(queryAccountBasicInfoResponse
					.getOtherInfo().getBirthDate().toGregorianCalendar()
					.getTime());

			subscriberAccount.setEmail(queryAccountBasicInfoResponse
					.getOtherInfo().getCommEmail());
		}

		if (null != queryAccountBasicInfoResponse.getBillingAddressInfo()) {

			// Populating address parameters for subscriberAccount response

			address.setAddress1(queryAccountBasicInfoResponse
					.getBillingAddressInfo().getLine1());
			address.setAddress2(queryAccountBasicInfoResponse
					.getBillingAddressInfo().getLine2());
			address.setCity(queryAccountBasicInfoResponse
					.getBillingAddressInfo().getCity());
			address.setState(queryAccountBasicInfoResponse
					.getBillingAddressInfo().getState());
			address.setZipCode(queryAccountBasicInfoResponse
					.getBillingAddressInfo().getZipCode());
			subscriberAccount.setAddress(address);
		}

		// Step 3: Call the getAccountBalance workflow method and use the
		// AccountBalance object in the response to populate the AccountBalance
		// object in SubscriberAccount.
		AccountBalanceResponse accountBalanceResponse = getAccountBalance(request);
		subscriberAccount
				.setBalance(accountBalanceResponse.getAccountBalance());

		return subscriberAccount;
	}

}
