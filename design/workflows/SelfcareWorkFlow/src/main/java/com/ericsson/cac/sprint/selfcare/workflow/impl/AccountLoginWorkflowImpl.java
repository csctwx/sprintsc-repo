package com.ericsson.cac.sprint.selfcare.workflow.impl;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.ws.Holder;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ericsson.cac.sprint.adapters.SecurityManagementProxyService;
import com.ericsson.cac.sprint.adapters.SubscriberManagementProxyService;
import com.ericsson.cac.sprint.selfcare.workflow.AccountLoginWorkflow;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.ForgotPinRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.LoginRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessage;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessageResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.cac.sprint.selfcare.workflow.util.AlarmUtil;
import com.ericsson.cac.sprint.selfcare.workflow.util.CommonUtil;
import com.ericsson.cac.sprint.selfcare.workflow.util.HeaderHandler;
import com.ericsson.cac.sprint.selfcare.workflow.util.Service;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.QuerySubscriberPrepaidInfoResponseType;
import com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.SubscriberStatusCodeType;
import com.sprint.integration.interfaces.resendsecurityinfo.v1.resendsecurityinfo.ResendSecurityInfoResponseType;
import com.sprint.integration.interfaces.resendsecurityinfo.v1.resendsecurityinfo.ResendSecurityInfoType;
import com.sprint.integration.interfaces.resendsecurityinfo.v1.resendsecurityinfo.SearchCriteriaType;
import com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber.RestoreSubscriberRequestType;
import com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber.RestoreSubscriberResponseType;
import com.sprint.integration.interfaces.suspendsubscriber.v1.suspendsubscriber.ActivityInfoType;
import com.sprint.integration.interfaces.suspendsubscriber.v1.suspendsubscriber.ChangeSubStatusInfoType;
import com.sprint.integration.interfaces.suspendsubscriber.v1.suspendsubscriber.MemoInfoType;
import com.sprint.integration.interfaces.suspendsubscriber.v1.suspendsubscriber.SubscriberInfoType;
import com.sprint.integration.interfaces.suspendsubscriber.v1.suspendsubscriber.SuspendSubscriberRequestType;
import com.sprint.integration.interfaces.suspendsubscriber.v1.suspendsubscriber.SuspendSubscriberResponseType;

@Component
public class AccountLoginWorkflowImpl implements AccountLoginWorkflow {

	/** Logger class info */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AccountDeviceWorkflowImpl.class);

	@Value("${LostPhone.activityReason}")
	private String activityReason;

	@Value("${LostPhone.memoSource}")
	private String memoSource;

	@Value("${LostPhone.userText}")
	private String userText;

	@Value("${querySubscriberPrepaidInfoResponse.basicInfo.statusChangeReasonCode.rma.suspended}")
	private String statusChangeReasonCodeRMASuspend;

	@Autowired
	private SecurityManagementProxyService securityManagementProxyService;

	@Autowired
	private CommonUtil commonUtil;

	/**
	 * 
	 * @param status
	 * @param reason
	 * @return StatusMessageResponse
	 */

	/*private StatusMessageResponse getStatusMessageResponse(int status,
			String reason) {
		StatusMessage statusMessage = new StatusMessage();
		statusMessage.setStatus(status);
		statusMessage.setReason(reason);

		StatusMessageResponse statusMessageResponse = new StatusMessageResponse();
		statusMessageResponse.setStatusMessage(statusMessage);

		return statusMessageResponse;
	}*/

	/**
	 * When user has lost his account’s pin and he is not able to access his
	 * account then user can request for new pin. This new pin is sent via SMS
	 * to the user’s device if user entered security question and answer matches
	 * for his provided phone number then only new pin is sent. This particular
	 * method sends user entered question, and answer to the back end which then
	 * verifies this data with the security info on the user profile if they
	 * match then new PIN will be send via SMS to users phone
	 * 
	 * @param request
	 * 
	 * @return StatusMessageResponse
	 */

	@Override
	public StatusMessageResponse sendPin(ForgotPinRequest request) {
		
		LOGGER.info("Entering sendPin workflow method");

		String mdn = request.getMsisdn();

		// Get error message from property

		if (StringUtils.isBlank(mdn)) {

			return commonUtil.populateStatusRespMesg(true, "Msisdn not present !!");

		}

		ResendSecurityInfoResponseType resendSecurityInfoResponseType = null;

		ResendSecurityInfoType resendSecurityInfoRequest = new ResendSecurityInfoType();
		SearchCriteriaType searchCriteriaType = new SearchCriteriaType();
		searchCriteriaType.setMdn(mdn);
		resendSecurityInfoRequest.setAccountInfo(searchCriteriaType);

		// Update the required request to the log in module
		try {

			Holder<WsMessageHeaderType> header = headerHandler
					.getHeader(Service.SUBSCRIBER_MANAGEMENT_SERVICE);

			resendSecurityInfoResponseType = securityManagementProxyService
					.resendSecurityInfo(resendSecurityInfoRequest, header);

			LOGGER.info("Leaving sendPin workflow method");
			
			return commonUtil.populateStatusRespMesg(false, "");

		} catch (DatatypeConfigurationException e) {

			LOGGER.error("DatatypeConfigurationException while securityManagementProxyService.resendSecurityInfo() "
					+ e);

			AlarmUtil.raiseSnmpAlarm();

			LOGGER.info("Leaving sendPin workflow method");

			return commonUtil.populateStatusRespMesg(true, 
					"DatatypeConfigurationException while securityManagementProxyService.resendSecurityInfo() !!");

		} catch (com.sprint.integration.eai.services.securitymanagementservice.v1.securitymanagementservice.SoapFault e) {
			LOGGER.error("SoapFault while securityManagementProxyService.resendSecurityInfo() "
					+ e);
			AlarmUtil.raiseSnmpAlarm();

			LOGGER.info("Leaving sendPin workflow method");

			return commonUtil.populateStatusRespMesg(true, 
					"SoapFault Error while securityManagementProxyService.resendSecurityInfo() ");
		}

		// return returnSecurityInfo(resendSecurityInfoResponseType);

		// TODO Auto-generated method stub
		// return null;
	}

	@Override
	public StatusMessageResponse login(LoginRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Subscriber has lost his phone and in order to avoid any activities this
	 * workflow allows them to temporarily suspend their account such that phone
	 * cannot be used to make phone calls or send messages or surf the internet
	 * using mobile network.
	 * 
	 * @param request
	 * 
	 * @return statusMessageResponse
	 */

	@Autowired
	private HeaderHandler headerHandler;

	@Autowired
	private SubscriberManagementProxyService subscriberManagementProxyService;

	@Value("${FoundPhone.activityReason}")
	private String foundPhoneActivityReason;
	@Value("${FoundPhone.accountStatus}")
	private String accountStatus;
	@Value("${FoundPhone.response}")
	private String response;

	@Override
	public StatusMessageResponse foundPhone(UserContextRequest request) {
		
		LOGGER.info("Entering foundPhone workflow method");

		StatusMessageResponse statusMessageResponse = new StatusMessageResponse();
		RestoreSubscriberResponseType restoreSubscriberResponse = null;
		RestoreSubscriberRequestType restoreSubscriberRequest = new RestoreSubscriberRequestType();

		com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber.SubscriberInfoType subscriberInfo = new com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber.SubscriberInfoType();
		subscriberInfo.setSubscriberId(request.getUserContext()
				.getSubscriberId());
		subscriberInfo.setMdn(request.getUserContext().getMsisdn());
		restoreSubscriberRequest.setSubscriberInfo(subscriberInfo);

		com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber.ChangeSubStatusInfoType changeSubStatusInfo = new com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber.ChangeSubStatusInfoType();
		com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber.ActivityInfoType activityInfoType = new com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber.ActivityInfoType();
		activityInfoType.setActivityReason(foundPhoneActivityReason);
		changeSubStatusInfo.setActivityInfo(activityInfoType);

		/*
		 * com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber
		 * .MemoInfoType MemoInfo = new
		 * com.sprint.integration.interfaces.restoresubscriber
		 * .v1.restoresubscriber.MemoInfoType();
		 * MemoInfo.setMemoSource(memoSource); MemoInfo.setUserText(userText);
		 * changeSubStatusInfo.setMemoInfo(MemoInfo);
		 */

		restoreSubscriberRequest.setChangeSubStatusInfo(changeSubStatusInfo);

		try {

			QuerySubscriberPrepaidInfoResponseType querySubscriberPrepaidInfoResponseType = commonUtil
					.getSubscriberPrepaidInfo(request.getUserContext()
							.getSubscriberId());
			if (accountStatus
					.equalsIgnoreCase(querySubscriberPrepaidInfoResponseType
							.getBasicInfo().getAccountStatus().value()))

			{
				Holder<WsMessageHeaderType> header = headerHandler
						.getHeader(Service.SUBSCRIBER_MANAGEMENT_SERVICE);
				restoreSubscriberResponse = subscriberManagementProxyService
						.restoreSubscriber(restoreSubscriberRequest, header);

				if (response.equalsIgnoreCase(restoreSubscriberResponse
						.getMessage())) {
					
					statusMessageResponse = commonUtil.populateStatusRespMesg(true, "Successfully restored account");
					
				} else {
					statusMessageResponse = commonUtil.populateStatusRespMesg(true, "Failed to restore account");
				}
			} else {
				statusMessageResponse = commonUtil.populateStatusRespMesg(true, "Failed to restore as account is not suspended");
			}
			return statusMessageResponse;

		} catch (Exception e) {
			LOGGER.error("Exception occured --> " + e.getMessage() , e);
			AlarmUtil.raiseSnmpAlarm();
			statusMessageResponse = commonUtil.populateStatusRespMesg(true, e.getMessage());
		}

		LOGGER.info("Leaving foundPhone workflow method");

		return statusMessageResponse;
	}

	/**
	 * Need to implement the logic
	 * 
	 * Method isSessionValid.
	 * 
	 * @return Boolean
	 */
	private Boolean isSessionValid() {
		return true;
	}

	/**
	 * Subscriber has lost his phone and in order to avoid any activities this
	 * workflow allows them to temporarily suspend their account such that phone
	 * cannot be used to make phone calls or send messages or surf the internet
	 * using mobile network.
	 * 
	 * @param request
	 * 
	 * @return statusMessageResponse
	 */

	@Override
	public StatusMessageResponse lostPhone(UserContextRequest request) {
		
		LOGGER.info("Entering lostPhone workflow method");

		if (null == request.getUserContext()) {

			return commonUtil.populateStatusRespMesg(true,
					"Request is invalid ,UserContext is Empty!!");

		}

		String mdn = request.getUserContext().getMsisdn();

		String subscriberId = request.getUserContext().getSubscriberId();

		if (StringUtils.isBlank(mdn) && StringUtils.isBlank(subscriberId)) {

			return commonUtil.populateStatusRespMesg(true,
					"Msisdn or Subscriber Id should present !!");

		} else {

			SuspendSubscriberResponseType suspendSubscriberResponse = null;

			MemoInfoType memoInfo;

			ActivityInfoType activityInfo;

			ChangeSubStatusInfoType changeSubStatusInfo;

			SuspendSubscriberRequestType suspendSubscriberRequest;

			SubscriberInfoType subscriberInfo;

			Boolean isSessionValid = isSessionValid();

			if (isSessionValid) {

				try {
					// 1.Account Status as SUSPENDED_LOST would mean
					// querySubscriberPrepaidInfoResponse.basicInfo.subscriberStatus
					// is 'S' and
					// querySubscriberPrepaidInfoResponse.basicInfo.isInterruptedStatusInd
					// is 'false'
					// querySubscriberPrepaidInfoResponse.basicInfo.isFraudInd
					// is
					// 'false' and the account is not RMA suspended
					// 2. Account Status as PAST_CURRENT_LOST would mean
					// querySubscriberPrepaidInfoResponse.basicInfo.subscriberStatus
					// is 'S'
					// and
					// querySubscriberPrepaidInfoResponse.basicInfo.isInterruptedStatusInd
					// is true
					// If any of the above conditions are true
					//
					if (checkSuspendLostOrPastCurrentLost(subscriberId, mdn)) {

						return commonUtil.populateStatusRespMesg(false, "");

					}

					LOGGER.debug("We need to get this from session once Login Flow is complete.");

					suspendSubscriberRequest = new SuspendSubscriberRequestType();

					subscriberInfo = new SubscriberInfoType();

					subscriberInfo.setMdn(mdn);

					suspendSubscriberRequest.setSubscriberInfo(subscriberInfo);

					changeSubStatusInfo = new ChangeSubStatusInfoType();

					activityInfo = new ActivityInfoType();

					activityInfo.setActivityReason(activityReason);

					changeSubStatusInfo.setActivityInfo(activityInfo);

					memoInfo = new MemoInfoType();

					memoInfo.setMemoSource(memoSource);

					memoInfo.setUserText(userText);

					changeSubStatusInfo.setMemoInfo(memoInfo);

					suspendSubscriberRequest
							.setChangeSubStatusInfo(changeSubStatusInfo);

					Holder<WsMessageHeaderType> header = headerHandler
							.getHeader(Service.SUBSCRIBER_MANAGEMENT_SERVICE);

					suspendSubscriberResponse = subscriberManagementProxyService
							.suspendSubscriber(suspendSubscriberRequest, header);

					if ("SUCCESS".equalsIgnoreCase(suspendSubscriberResponse
							.getMessage())) {

						LOGGER.info("Leaving lostPhone workflow method");

						return commonUtil.populateStatusRespMesg(false,
								suspendSubscriberResponse.getMessage());

					}
					LOGGER.info("Leaving lostPhone workflow method");

					return commonUtil.populateStatusRespMesg(true,
							suspendSubscriberResponse.getMessage());

				} catch (Exception e) {
					
					LOGGER.error("Exception occured --> " + e.getMessage(), e);

					AlarmUtil.raiseSnmpAlarm();

					LOGGER.info("Leaving lostPhone workflow method");

					return commonUtil.populateStatusRespMesg(true,
							"Exception while calling subscriberManagementProxyService.suspendSubscriber()");
				}
			} else {
				LOGGER.info("Leaving lostPhone workflow method");

				return commonUtil.populateStatusRespMesg(true, "Session Invalid !!");
			}
		}

		// return null;
	}

	/**
	 * Need to implement the logic
	 * 
	 * Method isSessionValid.
	 * 
	 * @return Boolean
	 */
	private boolean checkSuspendLostOrPastCurrentLost(String subscriber,
			String msisdn) throws Exception {

		QuerySubscriberPrepaidInfoResponseType querySubscriberPrepaidInfoResponseType = null;

		if (StringUtils.isNotBlank(subscriber)) {

			querySubscriberPrepaidInfoResponseType = commonUtil
					.getSubscriberPrepaidInfo(subscriber);
		}

		else {
			querySubscriberPrepaidInfoResponseType = commonUtil
					.getSubscriberPrepaidInfoWithMsisdn(msisdn);
		}

		// QuerySubscriberPrepaidInfoResponseType
		// querySubscriberPrepaidInfoResponseType = CommonUtil
		// .getSubscriberPrepaidInfo(subscriber);

		if (null == querySubscriberPrepaidInfoResponseType
				|| null == querySubscriberPrepaidInfoResponseType
						.getBasicInfo()
				|| null == querySubscriberPrepaidInfoResponseType
						.getBasicInfo().getSubscriberStatus()) {

			return false;

		}

		SubscriberStatusCodeType subscriberStatusCodeType = querySubscriberPrepaidInfoResponseType
				.getBasicInfo().getSubscriberStatus();

		boolean isFraudInd = querySubscriberPrepaidInfoResponseType
				.getBasicInfo().isIsFraudInd();

		String statusChangeReasonCode = querySubscriberPrepaidInfoResponseType
				.getBasicInfo().getStatusChangeReasonCode();

		boolean isInterruptedStatusInd = querySubscriberPrepaidInfoResponseType
				.getBasicInfo().isIsInterruptedStatusInd();

		// 1.Account Status as SUSPENDED_LOST would mean
		// querySubscriberPrepaidInfoResponse.basicInfo.subscriberStatus
		// is 'S' and
		// querySubscriberPrepaidInfoResponse.basicInfo.isInterruptedStatusInd
		// is 'false'
		// querySubscriberPrepaidInfoResponse.basicInfo.isFraudInd is
		// 'false' and the account is not RMA suspended
		if (SubscriberStatusCodeType.S.value().equals(
				subscriberStatusCodeType.value())
				&& isFraudInd == false
				&& statusChangeReasonCodeRMASuspend
						.equals(statusChangeReasonCode)
				&& isInterruptedStatusInd == false)

		{
			return true;
		}
		// 2. Account Status as PAST_CURRENT_LOST would mean
		// querySubscriberPrepaidInfoResponse.basicInfo.subscriberStatus is 'S'
		// and
		// querySubscriberPrepaidInfoResponse.basicInfo.isInterruptedStatusInd
		// is true
		// If any of the above conditions are true
		//

		if (SubscriberStatusCodeType.S.value().equals(
				subscriberStatusCodeType.value())
				&& isInterruptedStatusInd == true) {

			return true;

		}

		return false;
	}

}
