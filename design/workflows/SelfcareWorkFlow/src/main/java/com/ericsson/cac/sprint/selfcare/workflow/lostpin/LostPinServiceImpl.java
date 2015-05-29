package com.ericsson.cac.sprint.selfcare.workflow.lostpin;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ericsson.cac.sprint.adapters.LoggingManagementProxyService;
import com.ericsson.cac.sprint.adapters.QueryAccountInfoServiceProxyService;
import com.ericsson.cac.sprint.adapters.QueryPrepaidSubscriberProxyService;
import com.ericsson.cac.sprint.adapters.QueryReferenceInfoProxyService;
import com.ericsson.cac.sprint.adapters.SecurityManagementProxyService;
import com.ericsson.cac.sprint.selfcare.workflow.commons.HeaderData;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.AccountSecurityRequest;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.AccountSecurityResponse;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.BrandCodeRequest;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.BrandCodeResponse;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.LoginAttemptRequest;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.LoginAttemptResponse;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.QuestionInfo;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.ResendSecurityRequest;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.ResendSecurityResponse;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.SecurityRequest;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.SecurityResponse;
import com.sprint.integration.common.header.wsmessageheader.v2.TrackingMessageHeaderType;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.loggingmanagementservice.v1.loggingmanagementservice.SoapFault;
import com.sprint.integration.interfaces.queryaccountinfoservice.v1.queryaccountinfoservice.SoapFaultv2;
import com.sprint.integration.interfaces.queryaccountsecurityinfo.v1.queryaccountsecurityinfo.QueryAccountSecurityInfo;
import com.sprint.integration.interfaces.queryaccountsecurityinfo.v1.queryaccountsecurityinfo.QueryAccountSecurityInfoResponse;
import com.sprint.integration.interfaces.queryloginattemptstatus.v1.queryloginattemptstatus.QueryLoginAttemptStatusRequestType;
import com.sprint.integration.interfaces.queryloginattemptstatus.v1.queryloginattemptstatus.QueryLoginAttemptStatusResponseType;
import com.sprint.integration.interfaces.queryprepaidsubscriberservice.v1.queryprepaidsubscriberservice.SoapFaultV2;
import com.sprint.integration.interfaces.queryreferencesecurityinfo.v1.queryreferencesecurityinfo.QueryReferenceSecurityInfoRequest;
import com.sprint.integration.interfaces.queryreferencesecurityinfo.v1.queryreferencesecurityinfo.QueryReferenceSecurityInfoResponse;
import com.sprint.integration.interfaces.queryreferencesecurityinfo.v1.queryreferencesecurityinfo.RequestedSecurityInfo;
import com.sprint.integration.interfaces.queryreferencesecurityinfo.v1.queryreferencesecurityinfo.SecurityQuestionInfo;
import com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.QuerySubscriberPrepaidInfoResponseType;
import com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.QuerySubscriberPrepaidInfoType;
import com.sprint.integration.interfaces.resendsecurityinfo.v1.resendsecurityinfo.ResendSecurityInfoResponseType;
import com.sprint.integration.interfaces.resendsecurityinfo.v1.resendsecurityinfo.ResendSecurityInfoType;
import com.sprint.integration.interfaces.resendsecurityinfo.v1.resendsecurityinfo.SearchCriteriaType;
import com.sprint.integration.interfaces.submitloginattempt.v1.submitloginattempt.SubmitLoginAttemptRequestType;
import com.sprint.integration.interfaces.submitloginattempt.v1.submitloginattempt.SubmitLoginAttemptResponseType;

/**
 * @author elmpqqy
 * 
 */
@Service
public class LostPinServiceImpl implements LostPinService {

	@Autowired
	private LoggingManagementProxyService loggingManagementProxyService;

	@Autowired
	private QueryReferenceInfoProxyService queryReferenceInfoProxyService;

	@Autowired
	private QueryPrepaidSubscriberProxyService queryPrepaidSubscriberProxyService;

	@Autowired
	private SecurityManagementProxyService securityManagementProxyService;

	@Autowired
	private QueryAccountInfoServiceProxyService accountInfoServiceProxyService;

	private static final String FAILURE = "FAILURE";

	private static final String SUCCESS = "SUCCESS";
	
	private static final String INVALID_MDN = "Please enter a valid MDN";
	private static final String INVALID_ANSWER = "Please enter a Answer";
	private static final String INVALID_QUESTION = "Please select a Question";
	private static final String INVALID_BRANDCODE = "Please enter a Brandcode";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LostPinServiceImpl.class);

	/**   */
	private WsMessageHeaderType successHead = new WsMessageHeaderType();

	/**   */
	private Holder<WsMessageHeaderType> successHeader = null;

	@Value("${LoggingManagementService.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${LoggingManagementService.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${LoggingManagementService.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${LoggingManagementService.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${LoggingManagementService.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${LoggingManagementService.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${LoggingManagementService.trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${LoggingManagementService.trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${LoggingManagementService.trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${LoggingManagementService.trackingHeadFailTimeToLive}")
	private String trackingHeadFailTimeToLive;

	@Value("${LostPin.numberOfFailedAttempts}")
	private String numberOfFailedAttempts;
	@Value("${LostPin.numberOfSuccessAttempts}")
	private String numberOfSuccessAttempts;
	@Value("${LostPin.brandCode}")
	private String brandCode;
	@Value("${LostPin.accountType}")
	private String accountType;
	@Value("${LostPin.accountSubType}")
	private String accountSubType;

	/**
	 * Method formatSuccessHeader.
	 * 
	 * @throws DatatypeConfigurationException
	 */
	public void formatSuccessHeader() throws DatatypeConfigurationException {
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
	}

	// Step1

	/*
	 * Fetches the security question for a particular brand
	 * 
	 * @see com.ericsson.sprintselfcare.poc.service.LostPinService#
	 * fetchSecurityQuestionsForLostPin()
	 */
	@Override
	public SecurityResponse fetchSecurityQuestionsForLostPin(
			SecurityRequest securityRequest) {

		// IdentifyBrandCode is not clear

		QueryReferenceSecurityInfoResponse queryReferenceSecurityInfoResponse = null;
		String METHOD_NAME = "fetchSecurityQuestionsForLostPin";
		LOGGER.debug(METHOD_NAME + ":" + "start");
		LOGGER.debug("MDN: " +securityRequest.getMdn());
		LOGGER.debug("Session Info: " +securityRequest.getHeaderData().getSessionInfo());

		QueryReferenceSecurityInfoRequest queryReferenceSecurityInfoRequest = new QueryReferenceSecurityInfoRequest();
		RequestedSecurityInfo requestedSecurityInfo = new RequestedSecurityInfo();
		requestedSecurityInfo.setBrandCode(brandCode);
		requestedSecurityInfo.setAccountType(accountType);
		requestedSecurityInfo.setAccountSubType(accountSubType);
		queryReferenceSecurityInfoRequest
				.setSecurityInfo(requestedSecurityInfo);

		try {
			formatSuccessHeader();
			queryReferenceSecurityInfoResponse = queryReferenceInfoProxyService
					.queryReferenceSecurityInfo(
							queryReferenceSecurityInfoRequest, successHeader);
		} catch (DatatypeConfigurationException e) {
			transactionLog();
			raiseSnmpAlarm();
			return new SecurityResponse(FAILURE);
		} catch (com.sprint.integration.eai.services.queryreferenceinfoservice.v1.queryreferenceinfoservice.Faultmessage e) {
			transactionLog();
			raiseSnmpAlarm();
			return new SecurityResponse(FAILURE);
		}

		LOGGER.debug(METHOD_NAME + ":" + "end");

		return returnSecurityQuestionList(queryReferenceSecurityInfoResponse);

	}

	/*
	 * Resends the security Info to the user mobile via SMS
	 * 
	 * @see com.ericsson.sprintselfcare.poc.service.LostPinService#
	 * resendSecurityInfoToUser()
	 */
	@Override
	public ResendSecurityResponse resendSecurityInfoToUser(
			ResendSecurityRequest resendSecurityRequest) {

		String METHOD_NAME = "resendSecurityInfoToUser";

		LOGGER.debug(METHOD_NAME + ":" + "start");

		HeaderData data = resendSecurityRequest.getHeaderData();
		String mdn = resendSecurityRequest.getMdn();
		if(StringUtils.isBlank(mdn))
		{
			return new ResendSecurityResponse(INVALID_MDN);
		}

		ResendSecurityInfoResponseType resendSecurityInfoResponseType = null;

		ResendSecurityInfoType resendSecurityInfoRequest = new ResendSecurityInfoType();
		SearchCriteriaType searchCriteriaType = new SearchCriteriaType();
		searchCriteriaType.setMdn(mdn);
		resendSecurityInfoRequest.setAccountInfo(searchCriteriaType);

		// Update the required request to the log in module
		try {
			formatSuccessHeader();
			resendSecurityInfoResponseType = securityManagementProxyService
					.resendSecurityInfo(resendSecurityInfoRequest,
							successHeader);
			return new ResendSecurityResponse(SUCCESS);
		} catch (DatatypeConfigurationException e) {
			transactionLog();
			raiseSnmpAlarm();
			return new ResendSecurityResponse(FAILURE);
		} catch (com.sprint.integration.eai.services.securitymanagementservice.v1.securitymanagementservice.SoapFault e) {
			transactionLog();
			raiseSnmpAlarm();
			return new ResendSecurityResponse(FAILURE);
		}

		// return returnSecurityInfo(resendSecurityInfoResponseType);

	}

	// Step 3
	/*
	 * Validates the security answer entered by user
	 * 
	 * @see com.ericsson.sprintselfcare.poc.service.LostPinService#
	 * validateSecurityAnswers
	 * (com.sprint.integration.interfaces.queryreferencesecurityinfo
	 * .v1.queryreferencesecurityinfo.SecurityQuestionList)
	 */
	@Override
	public AccountSecurityResponse validateSecurityAnswers(
			AccountSecurityRequest accountSecurityRequest,
			LoginAttemptResponse loginAttemptResponse) {

		String METHOD_NAME = "validateSecurityAnswers";
		LOGGER.debug(METHOD_NAME + ":" + "start");
		HeaderData data = accountSecurityRequest.getHeaderData();
		String mdn = accountSecurityRequest.getMdn();

		String answer = accountSecurityRequest.getAnswer();
		String question = accountSecurityRequest.getQuestion();
		
		if(StringUtils.isBlank(mdn))
		{
			return new AccountSecurityResponse(INVALID_MDN);
		}
		else if(StringUtils.isBlank(answer))
		{
			return new AccountSecurityResponse(INVALID_ANSWER);
		}
		else if(StringUtils.isBlank(question))
		{
			return new AccountSecurityResponse(INVALID_QUESTION);
		}

		QueryAccountSecurityInfoResponse accountSecurityInfoResponse = null;

		QueryAccountSecurityInfo queryAccountSecurityInfoRequest = new QueryAccountSecurityInfo();
		com.sprint.integration.interfaces.queryaccountsecurityinfo.v1.queryaccountsecurityinfo.SearchCriteria searchCriteria = new com.sprint.integration.interfaces.queryaccountsecurityinfo.v1.queryaccountsecurityinfo.SearchCriteria();
		searchCriteria.setMdn(mdn);
		queryAccountSecurityInfoRequest.setInfo(searchCriteria);

		// Update the required request to the log in module

		try {
			formatSuccessHeader();
			accountSecurityInfoResponse = accountInfoServiceProxyService
					.queryAccountSecurityInfo(queryAccountSecurityInfoRequest,
							successHeader);
		} catch (DatatypeConfigurationException e1) {
			transactionLog();
			raiseSnmpAlarm();
			return new AccountSecurityResponse(FAILURE);
		} catch (SoapFaultv2 e) {
			transactionLog();
			raiseSnmpAlarm();
			return new AccountSecurityResponse(FAILURE);
		}

		// If the account is not locked
		if (loginAttemptResponse.getLockedUntil() != null) {
			if (question.equals(accountSecurityInfoResponse.getSecurityInfo()
					.getQuestion()) && answer.equals(accountSecurityInfoResponse.getSecurityInfo()
					.getAnswer())) {

				// Update the login status
				LoginAttemptRequest loginAttemptRequest = new LoginAttemptRequest(
						data, mdn);
				loginAttemptRequest
						.setNumberOfFailedAttempts(numberOfSuccessAttempts);
				// TODO update time
				//if we get the mdn means successfully able to submit loginAttempt
				if (SubmitLoginAttemptStatus(loginAttemptRequest).getMessage()
						.equals(mdn)) {
					// Via SMS send PIN
					ResendSecurityResponse resendSecurityResponse = resendSecurityInfoToUser(new ResendSecurityRequest(
							new HeaderData(), mdn));

					return new AccountSecurityResponse(
							resendSecurityResponse.getMessage());
				} else {
					transactionLog();
					raiseSnmpAlarm();
					return new AccountSecurityResponse(FAILURE);
				}
			}
			// If answer is not matched
			else {

				// Update the login failure status
				LoginAttemptRequest loginAttemptRequest = new LoginAttemptRequest(
						data, mdn);
				loginAttemptRequest
						.setNumberOfFailedAttempts(numberOfFailedAttempts);
				return new AccountSecurityResponse(SubmitLoginAttemptStatus(
						loginAttemptRequest).getMessage());
			}
		}
		// If the account is locked once
		else {
			GregorianCalendar c = new GregorianCalendar();
			try {
				XMLGregorianCalendar date1 = DatatypeFactory.newInstance()
						.newXMLGregorianCalendar(c);
			} catch (DatatypeConfigurationException e) {
			}
			Date lockedUntilDate = loginAttemptResponse.getLockedUntil()
					.toGregorianCalendar().getTime();
			Date currentDate = new Date();

			if (currentDate.compareTo(lockedUntilDate) > 0) {
				// Account is automatically unlocked
				if (question.equals(accountSecurityInfoResponse.getSecurityInfo()
						.getQuestion()) && answer.equals(accountSecurityInfoResponse.getSecurityInfo()
						.getAnswer())) {

					// Update the login status
					LoginAttemptRequest loginAttemptRequest = new LoginAttemptRequest(
							data, mdn);
					loginAttemptRequest
							.setNumberOfFailedAttempts(numberOfSuccessAttempts);
					if (SubmitLoginAttemptStatus(loginAttemptRequest)
							.getMessage().equals(mdn)) {

						// Via SMS send PIN
						ResendSecurityResponse resendSecurityResponse = resendSecurityInfoToUser(new ResendSecurityRequest(
								new HeaderData(), mdn));

						return new AccountSecurityResponse(
								resendSecurityResponse.getMessage());
					} else {
						transactionLog();
						raiseSnmpAlarm();
						return new AccountSecurityResponse(FAILURE);
					}
				}
				// If answer is not matched
				else {

					// Update the login status
					LoginAttemptRequest loginAttemptRequest = new LoginAttemptRequest(
							data, mdn);
					loginAttemptRequest
							.setNumberOfFailedAttempts(numberOfFailedAttempts);
					return new AccountSecurityResponse(
							SubmitLoginAttemptStatus(loginAttemptRequest)
									.getMessage());
				}

			}
			// Account is still locked
			else {

				// Update the login failure status
				LoginAttemptRequest loginAttemptRequest = new LoginAttemptRequest(
						data, mdn);
				loginAttemptRequest
						.setNumberOfFailedAttempts(numberOfSuccessAttempts);
				transactionLog();
				raiseSnmpAlarm();
				return new AccountSecurityResponse(SubmitLoginAttemptStatus(
						loginAttemptRequest).getMessage());
			}

		}

		// return returnSecurityAnswer(accountSecurityInfoResponse);
	}

	// Step 2

	/*
	 * Fetches the brandcode for that mdn from EAI and cross checks with the URI
	 * brandcode
	 * 
	 * @see
	 * com.ericsson.sprintselfcare.poc.service.LostPinService#fetchBrandCode()
	 */
	@Override
	public BrandCodeResponse validateBrandCode(BrandCodeRequest brandCodeRequest) {

		String METHOD_NAME = "validateBrandCode";
		LOGGER.debug(METHOD_NAME + ":" + "start");
		QuerySubscriberPrepaidInfoResponseType querySubscriberPrepaidInfoResponseType = null;
		if (validateRequest(brandCodeRequest)) {
			HeaderData data = brandCodeRequest.getHeaderData();
			String mdn = brandCodeRequest.getMdn();
			
			if(StringUtils.isBlank(mdn))
			{
				return new BrandCodeResponse(INVALID_MDN);
			}
			// TODO How to get the brand
			String brandCode = brandCodeRequest.getBrandCode();
			if(StringUtils.isBlank(brandCode))
			{
				return new BrandCodeResponse(INVALID_BRANDCODE);
			}

			QuerySubscriberPrepaidInfoType querySubscriberPrepaidInfoRequest = new QuerySubscriberPrepaidInfoType();
			com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.SearchCriteriaType searchCriteria = new com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.SearchCriteriaType();
			searchCriteria.setMdn(mdn);
			querySubscriberPrepaidInfoRequest.setInfo(searchCriteria);

			try {
				formatSuccessHeader();
				querySubscriberPrepaidInfoResponseType = queryPrepaidSubscriberProxyService
						.querySubscriberPrepaidInfo(
								querySubscriberPrepaidInfoRequest,
								successHeader);
				
				if (brandCode.equals(querySubscriberPrepaidInfoResponseType
						.getPrepaidInfo().getBrandCode())) {
					return new BrandCodeResponse(SUCCESS);
				}
				// If the brandcode is not matched
				else {
					transactionLog();
					return new BrandCodeResponse(FAILURE);
				}

			} catch (SoapFaultV2 e) {
				transactionLog();
				raiseSnmpAlarm();
				return new BrandCodeResponse(FAILURE);
			} catch (DatatypeConfigurationException e) {
				transactionLog();
				raiseSnmpAlarm();
				return new BrandCodeResponse(FAILURE);
			}
		}
		// If the request is not valid
		else {
			transactionLog();
			raiseSnmpAlarm();
			return new BrandCodeResponse(FAILURE);
		}
	}

	// Step 3
	/*
	 * Returns the login status of an account
	 * 
	 * @see com.ericsson.cac.sprint.selfcare.workflow.lostpin.LostPinService#
	 * loginAttemptStatus
	 * (com.ericsson.cac.sprint.selfcare.workflow.lostpin.model
	 * .LoginAttemptRequest)
	 */
	public LoginAttemptResponse loginAttemptStatus(
			LoginAttemptRequest loginAttemptRequest) {

		String METHOD_NAME = "loginAttemptStatus";
		LOGGER.debug(METHOD_NAME + ":" + "start");

		HeaderData data = loginAttemptRequest.getHeaderData();
		String mdn = loginAttemptRequest.getMdn();
		
		if(StringUtils.isBlank(mdn))
		{
			return new LoginAttemptResponse(INVALID_MDN);
		}

		QueryLoginAttemptStatusResponseType queryLoginAttemptStatusResponseType = null;

		QueryLoginAttemptStatusRequestType queryLoginAttemptStatusRequest = new QueryLoginAttemptStatusRequestType();
		queryLoginAttemptStatusRequest.setMdn(mdn);

		// Update the required request to the log in module
		try {
			formatSuccessHeader();
			queryLoginAttemptStatusResponseType = loggingManagementProxyService
					.queryLoginAttemptStatus(queryLoginAttemptStatusRequest,
							successHeader);
			return new LoginAttemptResponse(
					queryLoginAttemptStatusResponseType.getLockedUntil());
		} catch (DatatypeConfigurationException e) {
			transactionLog();
			raiseSnmpAlarm();
			return new LoginAttemptResponse(FAILURE);
		} catch (SoapFault e) {
			transactionLog();
			raiseSnmpAlarm();
			return new LoginAttemptResponse(FAILURE);
		}

		// return returnLoginAttemptStatus(queryLoginAttemptStatusResponseType);

	}

	public LoginAttemptResponse SubmitLoginAttemptStatus(
			LoginAttemptRequest loginAttemptRequest) {

		String METHOD_NAME = "SubmitLoginAttemptStatus";
		LOGGER.debug(METHOD_NAME + ":" + "start");

		HeaderData data = loginAttemptRequest.getHeaderData();
		String mdn = loginAttemptRequest.getMdn();
		
		if(StringUtils.isBlank(mdn))
		{
			return new LoginAttemptResponse(INVALID_MDN);
		}

		SubmitLoginAttemptResponseType submitLoginAttemptResponseType = null;
		try {
			formatSuccessHeader();
			SubmitLoginAttemptRequestType submitLoginAttemptRequest = new SubmitLoginAttemptRequestType();
			submitLoginAttemptRequest.setMdn(mdn);
			GregorianCalendar c = new GregorianCalendar();
			submitLoginAttemptRequest.setLoginDateTime(DatatypeFactory
					.newInstance().newXMLGregorianCalendar(c));
			submitLoginAttemptRequest.setNumberOfFailedAttempts(new BigInteger(
					loginAttemptRequest.getNumberOfFailedAttempts()));

			submitLoginAttemptResponseType = loggingManagementProxyService
					.submitLoginAttempt(submitLoginAttemptRequest,
							successHeader);
			return new LoginAttemptResponse(
					submitLoginAttemptResponseType.getMdn());
		} catch (DatatypeConfigurationException e) {
			transactionLog();
			raiseSnmpAlarm();
			return new LoginAttemptResponse(FAILURE);
		} catch (SoapFault e) {
			e.printStackTrace();
			transactionLog();
			raiseSnmpAlarm();
			return new LoginAttemptResponse(FAILURE);
		}

		// return returnLoginAttemptStatus(queryLoginAttemptStatusResponseType);

	}

	private boolean validateRequest(BrandCodeRequest brandCodeRequest) {
		return brandCodeRequest != null ? true : false;
	}

	private SecurityResponse returnSecurityQuestionList(
			QueryReferenceSecurityInfoResponse queryReferenceSecurityInfoResponse) {
		
		LOGGER.debug("Parsing List of questions");
		
		List<SecurityQuestionInfo> questionList = queryReferenceSecurityInfoResponse.getSecurityInfo().getSecurityQuestionList().getSecurityQuestionInfo();
		List<QuestionInfo> questionListDataModel = new ArrayList<QuestionInfo>();
		
		for(SecurityQuestionInfo qInfo : questionList){
			
			String qCode = qInfo.getQuestionCode();
			String qBrandCode = qInfo.getBrandCode();
			String qDescription = qInfo.getQuestion();
			
			LOGGER.debug("Question Code: " +qCode);
			LOGGER.debug("Brand Code: " +qBrandCode);
			LOGGER.debug("Question: " +qDescription);
			
			QuestionInfo questionInfo = new QuestionInfo();
			questionInfo.setQuestionCode(qCode);
			questionInfo.setBrandCode(qBrandCode);
			questionInfo.setQuestion(qDescription);			
			
			questionListDataModel.add(questionInfo);
			
		}
		return new SecurityResponse(questionListDataModel);
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

	// private LoginAttemptResponse returnRestoreMdn(
	// SubmitLoginAttemptResponseType submitLoginAttemptResponseType) {
	// return new LoginAttemptResponse(submitLoginAttemptResponseType.getMdn());
	// }
	//
	// private ResendSecurityResponse returnSecurityInfo(
	// ResendSecurityInfoResponseType resendSecurityInfoResponseType) {
	// // TODO Not sure what method needs to be called to get the response
	// // value
	// return new ResendSecurityResponse(resendSecurityInfoResponseType
	// .getPrimaryMethodInfo().getNotificationMethod());
	// }
	//
	// private AccountSecurityResponse returnSecurityAnswer(
	// QueryAccountSecurityInfoResponse accountSecurityInfoResponse) {
	// return new AccountSecurityResponse(accountSecurityInfoResponse
	// .getSecurityInfo().getAnswer());
	// }
	//
	// private LoginAttemptResponse returnLoginAttemptStatus(
	// QueryLoginAttemptStatusResponseType queryLoginAttemptStatusResponseType)
	// {
	// return new LoginAttemptResponse(
	// queryLoginAttemptStatusResponseType.getLockedUntil());
	// }

	// private BrandCodeResponse returnRestoreBrand(
	// QuerySubscriberPrepaidInfoResponseType
	// querySubscriberPrepaidInfoResponseType) {
	// return new BrandCodeResponse(querySubscriberPrepaidInfoResponseType
	// .getPrepaidInfo().getBrandCode());
	// }

}