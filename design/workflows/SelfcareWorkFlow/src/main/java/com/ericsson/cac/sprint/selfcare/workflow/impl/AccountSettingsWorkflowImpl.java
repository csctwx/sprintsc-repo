package com.ericsson.cac.sprint.selfcare.workflow.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.Holder;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ericsson.cac.sprint.adapters.AccountManagementProxyService;
import com.ericsson.cac.sprint.adapters.AddressManagementProxyService;
import com.ericsson.cac.sprint.adapters.QueryReferenceInfoProxyService;
import com.ericsson.cac.sprint.adapters.QuerySubscriberInfoProxyService;
import com.ericsson.cac.sprint.adapters.SecurityManagementProxyService;
import com.ericsson.cac.sprint.adapters.SmsPreferenceManagementProxyService;
import com.ericsson.cac.sprint.selfcare.workflow.AccountSettingsWorkflow;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.BlockedMessagesRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.BlockedMessagesResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.Recipient;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SecretQuestions;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SecretQuestionsResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessage;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessageResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberAccountRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberSetting;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberSettingsRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberSettingsResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.TsCsResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UpdatePinRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.cac.sprint.selfcare.workflow.util.AlarmUtil;
import com.ericsson.cac.sprint.selfcare.workflow.util.CommonUtil;
import com.ericsson.cac.sprint.selfcare.workflow.util.HeaderHandler;
import com.ericsson.cac.sprint.selfcare.workflow.util.Service;
import com.google.gson.Gson;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.customerdataprivacymanagementservice.v1.customerdataprivacymanagementservice.CustomerDataPrivacyManagementServicePortType;
import com.sprint.integration.eai.services.customerdataprivacymanagementservice.v1.customerdataprivacymanagementservice.SoapFault;
import com.sprint.integration.interfaces.queryreferencesecurityinfo.v1.queryreferencesecurityinfo.QueryReferenceSecurityInfoRequest;
import com.sprint.integration.interfaces.queryreferencesecurityinfo.v1.queryreferencesecurityinfo.QueryReferenceSecurityInfoResponse;
import com.sprint.integration.interfaces.queryreferencesecurityinfo.v1.queryreferencesecurityinfo.RequestedSecurityInfo;
import com.sprint.integration.interfaces.queryreferencesecurityinfo.v1.queryreferencesecurityinfo.SecurityQuestionInfo;
import com.sprint.integration.interfaces.querysmspreferenceinfo.v1.querysmspreferenceinfo.InfoType;
import com.sprint.integration.interfaces.querysmspreferenceinfo.v1.querysmspreferenceinfo.QuerySmsPreferenceInfoRequestType;
import com.sprint.integration.interfaces.querysmspreferenceinfo.v1.querysmspreferenceinfo.QuerySmsPreferenceInfoResponseType;
import com.sprint.integration.interfaces.querysmspreferenceinfo.v1.querysmspreferenceinfo.ResourceInfoType;
import com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.QuerySubscriberPrepaidInfoResponseType;
import com.sprint.integration.interfaces.querysubscriberservices.v1.querysubscriberservices.FeatureInfoType;
import com.sprint.integration.interfaces.querysubscriberservices.v1.querysubscriberservices.FetchFeatureListType;
import com.sprint.integration.interfaces.querysubscriberservices.v1.querysubscriberservices.FetchSocListType;
import com.sprint.integration.interfaces.querysubscriberservices.v1.querysubscriberservices.QuerySubscriberServicesRequest;
import com.sprint.integration.interfaces.querysubscriberservices.v1.querysubscriberservices.QuerySubscriberServicesResponse;
import com.sprint.integration.interfaces.queryuserpreferencesinfo.v2.queryuserpreferencesinfov2.QueryUserPreferencesInfoV2ResponseType;
import com.sprint.integration.interfaces.queryuserpreferencesinfo.v2.queryuserpreferencesinfov2.QueryUserPreferencesInfoV2Type;
import com.sprint.integration.interfaces.queryuserpreferencesinfo.v2.queryuserpreferencesinfov2.UserIdentityInfoType;
import com.sprint.integration.interfaces.queryuserpreferencesinfo.v2.queryuserpreferencesinfov2.UserPreferenceInfoType;
import com.sprint.integration.interfaces.queryuserpreferencesinfo.v2.queryuserpreferencesinfov2.UserPreferenceListType;
import com.sprint.integration.interfaces.updateaccountdetails.v1.updateaccountdetails.AccountDetailsInfoType;
import com.sprint.integration.interfaces.updateaccountdetails.v1.updateaccountdetails.AccountIndividualInfoType;
import com.sprint.integration.interfaces.updateaccountdetails.v1.updateaccountdetails.AddressVerificationSystemOverrideCodeEnsType;
import com.sprint.integration.interfaces.updateaccountdetails.v1.updateaccountdetails.BanValueObjInfoType;
import com.sprint.integration.interfaces.updateaccountdetails.v1.updateaccountdetails.BillingAddressInfoType;
import com.sprint.integration.interfaces.updateaccountdetails.v1.updateaccountdetails.BillingNameInfoType;
import com.sprint.integration.interfaces.updateaccountdetails.v1.updateaccountdetails.ContactNumberInfo;
import com.sprint.integration.interfaces.updateaccountdetails.v1.updateaccountdetails.OtherCommunicationMethodsType;
import com.sprint.integration.interfaces.updateaccountdetails.v1.updateaccountdetails.SecurityInfoType;
import com.sprint.integration.interfaces.updateaccountdetails.v1.updateaccountdetails.UpdateAccountDetailsRequestType;
import com.sprint.integration.interfaces.updateaccountdetails.v1.updateaccountdetails.UpdateAccountDetailsResponseType;
import com.sprint.integration.interfaces.updatesmspreferenceinfo.v1.updatesmspreferenceinfo.ResourceListType;
import com.sprint.integration.interfaces.updatesmspreferenceinfo.v1.updatesmspreferenceinfo.UpdateSmsPreferenceInfoRequestType;
import com.sprint.integration.interfaces.updatesmspreferenceinfo.v1.updatesmspreferenceinfo.UpdateSmsPreferenceInfoResponseType;
import com.sprint.integration.interfaces.updateuserpreferencesinfo.v2.updateuserpreferencesinfov2.PreferenceValueListType;
import com.sprint.integration.interfaces.updateuserpreferencesinfo.v2.updateuserpreferencesinfov2.UpdateUserPreferencesInfoV2ResponseType;
import com.sprint.integration.interfaces.updateuserpreferencesinfo.v2.updateuserpreferencesinfov2.UpdateUserPreferencesInfoV2Type;
import com.sprint.integration.interfaces.updateuserpreferencesinfo.v2.updateuserpreferencesinfov2.UserIdentifierTypeCodeType;
import com.sprint.integration.interfaces.updatevoicemailpasscode.v1.updatevoicemailpasscode.SearchCriteriaType;
import com.sprint.integration.interfaces.updatevoicemailpasscode.v1.updatevoicemailpasscode.UpdateVoiceMailPasscodeResponseType;
import com.sprint.integration.interfaces.updatevoicemailpasscode.v1.updatevoicemailpasscode.UpdateVoiceMailPasscodeType;
import com.sprint.integration.interfaces.validateaddress.v1.validateaddress.RequestAddressType;
import com.sprint.integration.interfaces.validateaddress.v1.validateaddress.ValidateAddressResponseType;
import com.sprint.integration.interfaces.validateaddress.v1.validateaddress.ValidateAddressType;

@Component
public class AccountSettingsWorkflowImpl implements AccountSettingsWorkflow {

	/** Logger class info */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AccountSettingsWorkflowImpl.class);

	@Autowired
	private HeaderHandler headerHandler;

	@Autowired
	private SecurityManagementProxyService securityManagementProxyService;
	@Autowired
	private QuerySubscriberInfoProxyService querySubscriberInfoProxyService;
	@Autowired
	private CustomerDataPrivacyManagementServicePortType customerDataPrivacyManagementService;

	@Autowired
	private SmsPreferenceManagementProxyService smsPreferenceManagementProxyService;
	
	@Autowired
	private AccountManagementProxyService accountManagementProxyService;
	
	@Autowired
	private AddressManagementProxyService addressManagementProxyService;
	
	@Autowired
	private CommonUtil commonUtil;

	private QuerySmsPreferenceInfoResponseType querySmsPreferenceInfoResponse;
	private QuerySubscriberServicesResponse querySubscriberServicesResponse;
	private QueryUserPreferencesInfoV2ResponseType queryUserPreferencesInfoV2Response;
	private ValidateAddressResponseType validateAddressResponse;
	
	private static final String FAILURE = "FAILURE";

	@Value("${blockedAllMessages.activateWhiteListInd}")
	private String trueValue;

	// Added for Security questions

	@Autowired
	private QueryReferenceInfoProxyService queryReferenceInfoProxyService;

	@Value("${LostPin.brandCode}")
	private String brandCode;

	@Value("${LostPin.accountType}")
	private String accountType;

	@Value("${LostPin.accountSubType}")
	private String accountSubType;
	@Value("${selfcareworkflow.date.format}")
	private String dateFormat;
	
	
	@Value("${getUserSettings.featureCode.international.calling}")
	private String getUserSettings_FeatureCode_InternationalCalling;
	@Value("${getUserSettings.featureCode.no.international.calling}")
	private String getUserSettings_FeatureCode_NoInternationalCalling;
	@Value("${getUserSettings.featureCode.callerid.restricted}")
	private String getUserSettings_FeatureCode_CallerIdRestricted;
	@Value("${getUserSettings.ownerType}")
	private String getUserSettings_OwnerType;
	@Value("${getUserSettings.contextName}")
	private String getUserSettings_ContextName;
	@Value("${getUserSettings.preferenceName}")
	private String getUserSettings_PreferenceName;
	@Value("${getUserSettings.preferenceValue.notset}")
	private String getUserSettings_PrefValue_NotSet;
	@Value("${getUserSettings.preferenceValue.off}")
	private String getUserSettings_PrefValue_Off;
	@Value("${getUserSettings.preferenceValue.on}")
	private String getUserSettings_PrefValue_On;
	@Value("${getUserSettings.subscriber.cf.id}")
	private String getUserSettings_Subscriber_Cf_Id;
	@Value("${getUserSettings.subscriber.title.contentFilter}")
	private String getUserSettings_Subscriber_Title_ContentFilter;
	@Value("${getUserSettings.subscriber.description.off}")
	private String getUserSettings_Subscriber_Desc_Off;
	@Value("${getUserSettings.subscriber.description.on}")
	private String getUserSettings_Subscriber_Desc_On;
			
	
	private SecretQuestionsResponse returnSecurityQuestionList(
			QueryReferenceSecurityInfoResponse queryReferenceSecurityInfoResponse) {

		LOGGER.debug("Parsing List of questions");

		List<SecurityQuestionInfo> questionList = queryReferenceSecurityInfoResponse
				.getSecurityInfo().getSecurityQuestionList()
				.getSecurityQuestionInfo();

		SecretQuestions secretQuestions = new SecretQuestions();

		String[] questions = new String[questionList.size()];

		String[] questionCodes = new String[questionList.size()];

		// List<QuestionInfo> questionListDataModel = new
		// ArrayList<QuestionInfo>();

		for (int i = 0; i < questionList.size(); i++) {

			String qCode = questionList.get(i).getQuestionCode();
			String qBrandCode = questionList.get(i).getBrandCode();
			String qDescription = questionList.get(i).getQuestion();

			LOGGER.debug("Question Code: " + qCode);
			LOGGER.debug("Brand Code: " + qBrandCode);
			LOGGER.debug("Question: " + qDescription);

			questions[i] = qDescription;

			questionCodes[i] = qCode;

			// QuestionInfo questionInfo = new QuestionInfo();
			// questionInfo.setQuestionCode(qCode);
			// questionInfo.setBrandCode(qBrandCode);
			// questionInfo.setQuestion(qDescription);
			// questionListDataModel.add(questionInfo);

		}

		secretQuestions.setQuestions(questions);

		secretQuestions.setQuestionCodes(questionCodes);

		SecretQuestionsResponse secretQuestionsResponse = new SecretQuestionsResponse();

		secretQuestionsResponse.setSecretQuestions(secretQuestions);
		
		secretQuestionsResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(false,""));

		return secretQuestionsResponse;
	}

	/*
	 * When user has lost his account’s pin and he is not able to access his
	 * account then user can request for new pin. This new pin is sent via SMS
	 * to the user’s device if user entered security question and answer matches
	 * for his provided phone number then only new pin is sent. This particular
	 * method pulls list of questions that will be posed to the subscriber.
	 * Subscriber has to pick the correct question that he had set for his
	 * account and then answer it correctly for this workflow to be successful.
	 * This method will also be used for changing a subscribers security
	 * settings in the settings area of MyAccount
	 */

	@Override
	public SecretQuestionsResponse getSecretQuestions(UserContextRequest request) {

		LOGGER.info("Entering getSecretQuestions workflow method");

		// IdentifyBrandCode is not clear

		QueryReferenceSecurityInfoResponse queryReferenceSecurityInfoResponse = null;
		
		SecretQuestionsResponse secretQuestionsResponse = new SecretQuestionsResponse();
		
		// String METHOD_NAME = "fetchSecurityQuestionsForLostPin";

		LOGGER.debug("AccountSettingsWorkflowImpl.fetchSecurityQuestionsForLostPin() start");

		// LOGGER.debug("MDN: " + request.getUserContext().getMsisdn());

		LOGGER.debug("MSISDN: " + request.getUserContext().getMsisdn());

		LOGGER.debug("SubscriberId: "
				+ request.getUserContext().getSubscriberId());

		LOGGER.debug("Language: " + request.getUserContext().getLanguage());

		// LOGGER.debug("Session Info: "
		// + securityRequest.getHeaderData().getSessionInfo());

		QueryReferenceSecurityInfoRequest queryReferenceSecurityInfoRequest = new QueryReferenceSecurityInfoRequest();

		RequestedSecurityInfo requestedSecurityInfo = new RequestedSecurityInfo();

		requestedSecurityInfo.setBrandCode(brandCode);

		requestedSecurityInfo.setAccountType(accountType);

		requestedSecurityInfo.setAccountSubType(accountSubType);

		queryReferenceSecurityInfoRequest
				.setSecurityInfo(requestedSecurityInfo);

		try {

			Holder<WsMessageHeaderType> header = headerHandler
					.getHeader(Service.QUERY_REFERENCE_INFO_SERVICE);

			queryReferenceSecurityInfoResponse = queryReferenceInfoProxyService
					.queryReferenceSecurityInfo(
							queryReferenceSecurityInfoRequest, header);

		} catch (DatatypeConfigurationException e) {

			LOGGER.error("DatatypeConfigurationException while querying QueryReferenceInfoProxyService.queryReferenceInfoProxyService() "
					+ e.getMessage(), e);
			// transactionLog();

			AlarmUtil.raiseSnmpAlarm();
			
			secretQuestionsResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(true, e.getMessage()));
			
			return secretQuestionsResponse;
					
		} catch (com.sprint.integration.eai.services.queryreferenceinfoservice.v1.queryreferenceinfoservice.Faultmessage e) {
			// transactionLog();
			LOGGER.error("Faultmessage while querying QueryReferenceInfoProxyService.queryReferenceInfoProxyService() "
					+ e.getMessage(), e);

			AlarmUtil.raiseSnmpAlarm();
			
			secretQuestionsResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(true, e.getMessage()));
			
			return secretQuestionsResponse;
		
		}
		
		// LOGGER.debug(METHOD_NAME + ":" + "end");

		LOGGER.debug("AccountSettingsWorkflowImpl.fetchSecurityQuestionsForLostPin() Ended");

		LOGGER.info("Leaving getSecretQuestions workflow method");

		secretQuestionsResponse = returnSecurityQuestionList(queryReferenceSecurityInfoResponse);
		return secretQuestionsResponse;

	}

	@Override
	public StatusMessageResponse updateAccount(SubscriberAccountRequest request,UserContextRequest context) {
		
		LOGGER.info("Entering updateAccount workflow method");
		
		StatusMessageResponse statusMessageResponse = new StatusMessageResponse();
		StatusMessage statusMessage = new StatusMessage();
		
		context=new UserContextRequest();
		int age;
		Date birthDate;
		birthDate=request.getSubscriberAccount().getBirthdate();
		age=calculateAge(birthDate);
		try{
			if(age<13){
				
				statusMessage.setStatus(101);
				statusMessage.setReason("User Age < 13");
				statusMessage.setDescription("Sorry, the Children's Online Privacy Protection Act says that we can't keep your personal information "
						+ "so we will not be able to update your account information");
				statusMessageResponse.setStatusMessage(statusMessage);
			}
		
			else{
				
				ValidateAddressType vaidateAddressReq=new ValidateAddressType();
				RequestAddressType addressInfo=new RequestAddressType();
				Holder<WsMessageHeaderType> header = headerHandler.getHeader(Service.ADDRESS_MANAGEMENT_PROXY_SERVICE);
				addressInfo.setAddressLine1(request.getSubscriberAccount().getAddress().getAddress1());
				addressInfo.setAddressLine2(request.getSubscriberAccount().getAddress().getAddress2());
				addressInfo.setCity(request.getSubscriberAccount().getAddress().getCity());
				addressInfo.setState(request.getSubscriberAccount().getAddress().getState());
				addressInfo.setZipCode(request.getSubscriberAccount().getAddress().getZipCode());
				vaidateAddressReq.setAddressInfo(addressInfo);
				
				validateAddressResponse=addressManagementProxyService.validateAddress(vaidateAddressReq, header);
				String confidence="";
				for(int i=0;i<validateAddressResponse.getValidatedAddressList().getValidatedAddressInfo().size();i++){
					confidence=validateAddressResponse.getValidatedAddressList().getValidatedAddressInfo().get(i).getAdditionalAddressInfo().getConfidence();
						if(validateAddressResponse!=null && confidence.equalsIgnoreCase("100")){
							statusMessageResponse=updateAccountDetails(request,validateAddressResponse);
						}
						else{
							statusMessage.setStatus(102);
							statusMessageResponse.setStatusMessage(statusMessage);
						}
				}
				
			}
		
			}catch (Exception e) {
				LOGGER.error("Exception occured --> " + e);
				statusMessage.setStatus(102);
				statusMessageResponse.setStatusMessage(statusMessage);
				AlarmUtil.raiseSnmpAlarm();
			}
			
		LOGGER.info("Leaving updateAccount workflow method");

		return statusMessageResponse;
	}
	
	private int calculateAge(Date birthDate) {
		int age;
		Date currentDate=new Date();
		age= (currentDate.getYear())-(birthDate.getYear());
		if(age!=0)
			return age;
		else 
			return 0;
	}
	
	private StatusMessageResponse updateAccountDetails(SubscriberAccountRequest request,ValidateAddressResponseType validateAddressResponse){

		StatusMessageResponse statusMessageResponse = null; 

		UpdateAccountDetailsRequestType updateAccountDetailsRequest = null;
		UpdateAccountDetailsResponseType updateAccountDetailsResponse = null;
		SimpleDateFormat dtFormat = new SimpleDateFormat(dateFormat);

		try{
			Holder<WsMessageHeaderType> header = headerHandler
					.getHeader(Service.ACCOUNT_MANAGEMENT_SERVICE);

			updateAccountDetailsRequest = new UpdateAccountDetailsRequestType();
			com.sprint.integration.interfaces.updateaccountdetails.v1.updateaccountdetails.SearchCriteriaType searchCriteria = new com.sprint.integration.interfaces.updateaccountdetails.v1.updateaccountdetails.SearchCriteriaType();
			searchCriteria.setMdn(request.getSubscriberAccount().getMsisdn());
			updateAccountDetailsRequest.setInfo(searchCriteria);

			BanValueObjInfoType accountValueObjectInfo = new BanValueObjInfoType();

			BillingNameInfoType billingNameInfo = new BillingNameInfoType();
			billingNameInfo.setFirstName(request.getSubscriberAccount().getFirstName());
			billingNameInfo.setMiddleName(request.getSubscriberAccount().getMiddleName());
			billingNameInfo.setLastName(request.getSubscriberAccount().getLastName());
			accountValueObjectInfo.setBillingNameInfo(billingNameInfo);

			AccountDetailsInfoType accountDetailsInfo = new AccountDetailsInfoType();
			OtherCommunicationMethodsType otherCommunicationMethods = new OtherCommunicationMethodsType();
			otherCommunicationMethods.setCommunicationEmail(request.getSubscriberAccount().getEmail());
			accountDetailsInfo.setOtherCommunicationMethods(otherCommunicationMethods);

			ContactNumberInfo otherNumberInfo = new ContactNumberInfo();
			otherNumberInfo.setPhoneNumber(request.getSubscriberAccount().getAlternatePhone());
			accountDetailsInfo.setOtherNumberInfo(otherNumberInfo);
			accountDetailsInfo.setOtherPhoneType(request.getSubscriberAccount().getAlternatePhone());

			accountValueObjectInfo.setAccountDetailsInfo(accountDetailsInfo);
			if(!StringUtils.isBlank(request.getSubscriberAccount().getEmail()))
			{
				accountDetailsInfo.setCommunicationMethod("E");
			}
			else
			{
				accountDetailsInfo.setCommunicationMethodValue("L");
			}
			BillingAddressInfoType billingAddressInfo = new BillingAddressInfoType();
			billingAddressInfo.setAddressUpType("A");
			billingAddressInfo.setApplyAddress(AddressVerificationSystemOverrideCodeEnsType.I);

			for(int i=0;i<validateAddressResponse.getValidatedAddressList().getValidatedAddressInfo().size();i++){
				if(validateAddressResponse.getValidatedAddressList().getValidatedAddressInfo().get(i).getAddressType().equalsIgnoreCase("") ||
						validateAddressResponse.getValidatedAddressList().getValidatedAddressInfo().get(i).getAddressType()!=null){
					billingAddressInfo.setAddressType("S");
				}
				else{
					billingAddressInfo.setAddressType(validateAddressResponse.getValidatedAddressList().getValidatedAddressInfo().get(i).getAddressType());
				}
				billingAddressInfo.setCity(validateAddressResponse.getValidatedAddressList().getValidatedAddressInfo().get(i).getCity());
				billingAddressInfo.setState(validateAddressResponse.getValidatedAddressList().getValidatedAddressInfo().get(i).getState());
				billingAddressInfo.setZipCode(validateAddressResponse.getValidatedAddressList().getValidatedAddressInfo().get(i).getZipCode());
				billingAddressInfo.setStreetNumber(validateAddressResponse.getValidatedAddressList().getValidatedAddressInfo().get(i).getHouseNumber());

				if(request.getSubscriberAccount().getAccountType().equalsIgnoreCase("M") || 
						(request.getSubscriberAccount().getAccountType().equalsIgnoreCase("F"))){
					billingAddressInfo.setLine1(validateAddressResponse.getValidatedAddressList().getValidatedAddressInfo().get(i).getAddressLine1());
				}

				billingAddressInfo.setRuralNumber(validateAddressResponse.getValidatedAddressList().getValidatedAddressInfo().get(i).getRuralNumber());
				billingAddressInfo.setPostofficeBoxNumber(validateAddressResponse.getValidatedAddressList().getValidatedAddressInfo().get(i).getPobNumber());
				billingAddressInfo.setStreetName(validateAddressResponse.getValidatedAddressList().getValidatedAddressInfo().get(i).getStreetName());
				//Needed clarification on street type prefix/suffix
				billingAddressInfo.setStreetType(validateAddressResponse.getValidatedAddressList().getValidatedAddressInfo().get(i).getStreetPrefix());
				//billingAddressInfo.setStreetType(validateAddressResponse.getValidatedAddressList().getValidatedAddressInfo().get(i).getStreetSuffix());
				billingAddressInfo.setApartment(validateAddressResponse.getValidatedAddressList().getValidatedAddressInfo().get(i).getApartmentNumber());
				billingAddressInfo.setApartmentType(validateAddressResponse.getValidatedAddressList().getValidatedAddressInfo().get(i).getApartmentType());

			}

			AccountIndividualInfoType accountIndividualInfo = new AccountIndividualInfoType();
			accountIndividualInfo.setDateOfBirth(DatatypeFactory
					.newInstance().newXMLGregorianCalendar(
							dtFormat.format(request.getSubscriberAccount().getBirthdate())));

			accountValueObjectInfo.setAccountIndividualInfo(accountIndividualInfo);
			accountValueObjectInfo.setBillingAddressInfo(billingAddressInfo);
			updateAccountDetailsRequest.setAccountValueObjectInfo(accountValueObjectInfo);

			updateAccountDetailsResponse = accountManagementProxyService.updateAccountDetails(updateAccountDetailsRequest,
					header);

			if(StringUtils.isBlank(updateAccountDetailsResponse.getMessage()))
			{
				statusMessageResponse = commonUtil.populateStatusRespMesg(true, "Null response obtained from accountManagementProxyService");
			}
			else
			{
				statusMessageResponse = commonUtil.populateStatusRespMesg(false, "");
			}

		}catch (Exception e) {
			LOGGER.error("Exception occured --> " + e.getMessage(), e);
			AlarmUtil.raiseSnmpAlarm();
			statusMessageResponse = commonUtil.populateStatusRespMesg(true, e.getMessage());
		}

		return statusMessageResponse;

	}
	

	@Override
	public TsCsResponse getTsCs(UserContextRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BlockedMessagesResponse getBlockedMessages(UserContextRequest request) {

		LOGGER.info("Entering getBlockedMessages workflow method");
		
		QuerySmsPreferenceInfoRequestType querySmsPreferenceInfo = new QuerySmsPreferenceInfoRequestType();
		InfoType info = new InfoType();
		info.setMdn(request.getUserContext().getMsisdn());
		querySmsPreferenceInfo.setInfo(info);
		BlockedMessagesResponse blockedMessagesResponse = new BlockedMessagesResponse();
		List<Recipient> recipientsList = new ArrayList<Recipient>();

		try {
			AlarmUtil.clearSnmpAlarm();

			Holder<WsMessageHeaderType> header = headerHandler
					.getHeader(Service.SMS_PREFERENCE_MANAGEMENT_PROXY_SERVICE);
			QuerySmsPreferenceInfoResponseType querySmsPreferenceInfoResponseType = smsPreferenceManagementProxyService
					.querySmsPreferenceInfo(querySmsPreferenceInfo, header);

			if (!querySmsPreferenceInfoResponseType.isWhiteListActiveInd()) {

				List<ResourceInfoType> resourceListType = (List<ResourceInfoType>) querySmsPreferenceInfoResponseType
						.getBlockList().getResourceInfo();

				for (ResourceInfoType resourceInfoType : resourceListType) {
					Recipient recipient = new Recipient();
					recipient.setSender(resourceInfoType.getResourceId());
					recipientsList.add(recipient);
				}

				blockedMessagesResponse
						.setRecipients((Recipient[]) recipientsList
								.toArray(new Recipient[recipientsList.size()]));

			} else {
				blockedMessagesResponse
						.setRecipients((Recipient[]) recipientsList
								.toArray(new Recipient[recipientsList.size()]));
			}
			
			blockedMessagesResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(false, ""));

		} catch (Exception e) {
			LOGGER.error("Exception occured --> " + e.getMessage(), e);
			AlarmUtil.raiseSnmpAlarm();
			blockedMessagesResponse.setStatusMessageResponse(commonUtil.populateStatusRespMesg(true, e.getMessage()));
		}
		
		LOGGER.info("Leaving getBlockedMessages workflow method");

		return blockedMessagesResponse;
	}

	@Override
	public StatusMessageResponse blockMessages(
			UserContextRequest userContextRequest,
			BlockedMessagesRequest blockedMessagesRequest) {
		// TODO

		LOGGER.info("Entering blockMessages workflow method");

		Recipient[] recipientsArray = null;
		String msisdnFromRequest = "";
		StatusMessageResponse statusMessageResponse = new StatusMessageResponse();
		/*StatusMessage statusMessage = new StatusMessage();*/

		try {
			Holder<WsMessageHeaderType> header = headerHandler
					.getHeader(Service.SMS_PREFERENCE_MANAGEMENT_PROXY_SERVICE);

			msisdnFromRequest = userContextRequest.getUserContext().getMsisdn();
			recipientsArray = blockedMessagesRequest.getRecipients();

			if (msisdnFromRequest != null && msisdnFromRequest.length() > 0) {
				if (recipientsArray.length <= 50) {
					for (Recipient recipientvalue : recipientsArray) { // recipientslength
						if (recipientvalue.getSender() != null
								&& recipientvalue.getSender().length() <= 255) {
							LOGGER.info("Valid recipent value -> "
									+ recipientvalue.getSender());
						} else {
							throw new Exception(
									"Invalid length obtained for recipeint="
											+ recipientvalue.getSender());
						}
					}
				} else {
					throw new Exception(
							"Invalid length of recipients obtained ="
									+ recipientsArray.length);
				}

				UpdateSmsPreferenceInfoRequestType updateSmsPreferenceInfo = new UpdateSmsPreferenceInfoRequestType();
				com.sprint.integration.interfaces.updatesmspreferenceinfo.v1.updatesmspreferenceinfo.InfoType info = new com.sprint.integration.interfaces.updatesmspreferenceinfo.v1.updatesmspreferenceinfo.InfoType();
				info.setMdn(msisdnFromRequest);
				updateSmsPreferenceInfo.setInfo(info);

				updateSmsPreferenceInfo.setActivateWhiteListInd(false);

				ResourceListType resourceListType = new ResourceListType();
				com.sprint.integration.interfaces.updatesmspreferenceinfo.v1.updatesmspreferenceinfo.ResourceInfoType resourceInfoType = new com.sprint.integration.interfaces.updatesmspreferenceinfo.v1.updatesmspreferenceinfo.ResourceInfoType();
				for (Recipient recipientvalue : recipientsArray) {
					LOGGER.info("Adding recipient="
							+ recipientvalue.getSender() + " to the block list");
					resourceInfoType.setResourceId(recipientvalue.getSender());
					resourceListType.getResourceInfo().add(resourceInfoType);
				}

				LOGGER.debug("Updating recipient block list");
				updateSmsPreferenceInfo.setBlockList(resourceListType);
				//Wensheng: The white list is not an option, we need assign the current white data as well.
//				updateSmsPreferenceInfo.setWhiteList(new ResourceListType());

				UpdateSmsPreferenceInfoResponseType updateSmsPrefInfoResponse = smsPreferenceManagementProxyService
						.updateSmsPreferenceInfo(updateSmsPreferenceInfo,
								header);

				String msgFromSMSPrefManagementService = updateSmsPrefInfoResponse
						.getConfirmationMessage();
				LOGGER.info("Message obtained from SmsPreferenceManagementService -> "
						+ msgFromSMSPrefManagementService);

				statusMessageResponse = commonUtil.populateStatusRespMesg(false, msgFromSMSPrefManagementService);
				
				
			} else {
				LOGGER.error("MSISDN not obtained from request");
				throw new Exception("MSISDN not obtained from request");
			}

		} catch (Exception e) {
			LOGGER.error("Exception occured --> " + e.getMessage(), e);
			AlarmUtil.raiseSnmpAlarm();
			statusMessageResponse = commonUtil.populateStatusRespMesg(true, e.getMessage());
		}

		LOGGER.info("Leaving blockMessages workflow method");

		return statusMessageResponse;

	}
;
	@Override
	public StatusMessageResponse unblockMessages(
			UserContextRequest userContextRequest,
			BlockedMessagesRequest blockedMessagesRequest) {
		
		LOGGER.info("Entering unblockMessages workflow method");

		String msisdnFromRequest = userContextRequest.getUserContext()
				.getMsisdn();
		userContextRequest = new UserContextRequest();
		QuerySmsPreferenceInfoRequestType querySmsPreferenceInfoReq = new QuerySmsPreferenceInfoRequestType();
		InfoType info = new InfoType();
		info.setMdn(msisdnFromRequest);
		querySmsPreferenceInfoReq.setInfo(info);

		List<Recipient> recipientsList = new ArrayList<Recipient>();
		BlockedMessagesResponse blockedMessagesResponse = new BlockedMessagesResponse();
		
		StatusMessageResponse statusMessageResponse = new StatusMessageResponse();
		//StatusMessage statusMessage = new StatusMessage();
		
		Recipient[] recipientsArrayReq = blockedMessagesRequest.getRecipients();

		try {
			Holder<WsMessageHeaderType> header = headerHandler
					.getHeader(Service.SMS_PREFERENCE_MANAGEMENT_PROXY_SERVICE);
			// 1. calling smsPreferenceManagementProxyService
			querySmsPreferenceInfoResponse = smsPreferenceManagementProxyService
					.querySmsPreferenceInfo(querySmsPreferenceInfoReq, header);
			// 2. Creating List<ResourceInfoType>
			List<ResourceInfoType> resourceInfoTypeWhiteList = (List<ResourceInfoType>) querySmsPreferenceInfoResponse
					.getWhiteList().getResourceInfo();
			if (querySmsPreferenceInfoResponse != null
					&& querySmsPreferenceInfoResponse.getInfo().getMdn() != null) {
				// Check if whitelisted TRUE
				if (querySmsPreferenceInfoResponse.isWhiteListActiveInd()) {
					if (recipientsArrayReq != null
							&& !(recipientsArrayReq.length == 0)) {

						for (int i = 0; i < recipientsArrayReq.length; i++) {
							List<Recipient> recipientsListReq = Arrays
									.asList(recipientsArrayReq);
							// Comparing whitelist recipients from response with
							// recipients in blockedRequest
							if ((resourceInfoTypeWhiteList != null)
									&& !(resourceInfoTypeWhiteList.get(i)
											.equals(recipientsListReq.get(i)))) {
								for (ResourceInfoType resourceInfoType : resourceInfoTypeWhiteList) {
									Recipient recipient = new Recipient();
									recipient.setSender(resourceInfoType
											.getResourceId());
									recipientsList.add(recipient);
								}
							}
							// set whitelist to response
							blockedMessagesResponse
									.setRecipients((Recipient[]) recipientsList
											.toArray(new Recipient[recipientsList
													.size()]));
						}
					} else {
						LOGGER.debug("No blocked recipients..");
					}
				} else {
					// remove all sender in request from blockList
					List<ResourceInfoType> resourceInfoTypeBlockList = (List<ResourceInfoType>) querySmsPreferenceInfoResponse
							.getBlockList().getResourceInfo();
					if (recipientsArrayReq != null
							&& !(recipientsArrayReq.length == 0)) {
						for (int i = 0; i < recipientsArrayReq.length; i++) {
							List<Recipient> recipientsListReq = Arrays
									.asList(recipientsArrayReq);
							// Comparing blacklist recipients from response with
							// recipients in blockedRequest
							if ((resourceInfoTypeBlockList != null)
									&& resourceInfoTypeBlockList.get(i).equals(
											recipientsListReq.get(i))) {
								if (resourceInfoTypeBlockList != null) {
									resourceInfoTypeBlockList.remove(i);
								}
							}
						}
					}
				}

				ResourceListType resourceListType = new ResourceListType();
				com.sprint.integration.interfaces.updatesmspreferenceinfo.v1.updatesmspreferenceinfo.ResourceInfoType resourceInfoType = new com.sprint.integration.interfaces.updatesmspreferenceinfo.v1.updatesmspreferenceinfo.ResourceInfoType();
				// 3. Create ResourceListType from List<ResourceInfoType>
				for (Recipient recipientvalue : recipientsArrayReq) {
					LOGGER.info("Adding recipient="
							+ recipientvalue.getSender() + " to the block list");
					resourceInfoType.setResourceId(recipientvalue.getSender());
					resourceListType.getResourceInfo().add(resourceInfoType);
				}
				UpdateSmsPreferenceInfoRequestType updateSmsPreferenceInfo = new UpdateSmsPreferenceInfoRequestType();
				com.sprint.integration.interfaces.updatesmspreferenceinfo.v1.updatesmspreferenceinfo.InfoType updateInfo = new com.sprint.integration.interfaces.updatesmspreferenceinfo.v1.updatesmspreferenceinfo.InfoType();
				updateInfo.setMdn(msisdnFromRequest);
				updateSmsPreferenceInfo.setInfo(updateInfo);
				if (querySmsPreferenceInfoResponse.isWhiteListActiveInd()) {

					LOGGER.debug("Updating recipient white list");
					updateSmsPreferenceInfo.setWhiteList(resourceListType);

				} else {

					LOGGER.debug("Updating recipient black list");
					updateSmsPreferenceInfo.setBlockList(resourceListType);
				}
				UpdateSmsPreferenceInfoResponseType updateSmsPrefInfoResponse = smsPreferenceManagementProxyService
						.updateSmsPreferenceInfo(updateSmsPreferenceInfo,
								header);
				String msgFromSMSPrefManagementService = updateSmsPrefInfoResponse
						.getConfirmationMessage();
				LOGGER.info("Message obtained from SmsPreferenceManagementService -> "
						+ msgFromSMSPrefManagementService);

				statusMessageResponse = commonUtil.populateStatusRespMesg(false, msgFromSMSPrefManagementService);
			} else {
				LOGGER.debug("Response null..!Error unbocking the senders..!");
			}
		} catch (Exception e) {

			LOGGER.error("Exception occured --> " + e);
			AlarmUtil.raiseSnmpAlarm();
			statusMessageResponse = commonUtil.populateStatusRespMesg(true, e.getMessage());
		}
		
		LOGGER.info("Leaving unblockMessages workflow method");

		return statusMessageResponse;
	}

	@Override
	public StatusMessageResponse blockAllMessages(UserContextRequest request) {

		// TODO
		
		LOGGER.info("Entering blockAllMessages workflow method");

		String msisdn = "";
		StatusMessageResponse statusMessageResponse = new StatusMessageResponse();
		//StatusMessage statusMessage = new StatusMessage();

		try {

			AlarmUtil.clearSnmpAlarm();

			Holder<WsMessageHeaderType> header = headerHandler
					.getHeader(Service.QUERY_PREPAID_SUBSCRIBER_SERVICE);

			// Step 1: Validate that an msisdn was passed in the
			// UserContextRequest input
			msisdn = request.getUserContext().getMsisdn();
			LOGGER.info("MSISDN obtianed from request is " + msisdn);

			if (msisdn == null || msisdn.length() <= 0) {

				LOGGER.error("MSISDN obtained in request is null or empty");

				// Checking if Subscriber Id is obtained from request or
				// not, if not obtained throw an error
				if (request.getUserContext().getSubscriberId() == null
						|| request.getUserContext().getSubscriberId().length() <= 0) {

					LOGGER.error("Subscriber Id obtained in request is null or empty");
					throw new Exception(
							"Subscriber Id obtained in request is null or empty");

				} else {

					// msisdn =
					// CommonUtil.getSubscriberPrepaidInfo(request.getUserContext().getSubscriberId());
					QuerySubscriberPrepaidInfoResponseType querySubscriberPrepaidInfo = commonUtil
							.getSubscriberPrepaidInfo(request.getUserContext()
									.getSubscriberId());

					if (null != querySubscriberPrepaidInfo) {

						msisdn = querySubscriberPrepaidInfo.getResourceInfo()
								.getMdn();

						LOGGER.debug("MSISDN obtained from QuerySubscriberPrepaidInfo="
								+ msisdn);

					} else {

						throw new Exception(
								"Null response obtained from queryPrepaidSubscriberProxyService");
					}
				}
			}

			// Step 2: Call the "SmsPreferenceManagement" adapter
			String message = updateSmsPreferenceInfo(msisdn, header);
			
			// Step 3: Update StatusMessageResponse with appropriate response
			statusMessageResponse = commonUtil.populateStatusRespMesg(false, message);

		} catch (Exception e) {
			LOGGER.error("Exception occurred --> " + e);
			AlarmUtil.raiseSnmpAlarm();
			// Step 3: Update StatusMessageResponse with appropriate response
			statusMessageResponse = commonUtil.populateStatusRespMesg(true, e.getMessage());
		}

		LOGGER.info("Leaving blockAllMessages workflow method");

		return statusMessageResponse;
	}

	@Override
	public StatusMessageResponse resetVM(UserContextRequest request) {
		
		LOGGER.info("Entering resetVM workflow method");

		String msisdn = "";
		String subscriberId = "";
		StatusMessageResponse statusMessageResponse = new StatusMessageResponse();
		//StatusMessage statusMessage = new StatusMessage();

		/*
		 * step1 :Validate that the mdn is passed in the UserContextRequest. If
		 * not then return an error in StatusMessageResponse
		 */
		try {

			AlarmUtil.clearSnmpAlarm();

			if (request.getUserContext().getMsisdn() == null
					|| request.getUserContext().getMsisdn().length() <= 0) {

				LOGGER.error("MSISDN obtained in request is null or empty");

				// Checking if Subscriber Id is obtained from request or
				// not, if not obtained throw an error
				if (request.getUserContext().getSubscriberId() == null
						|| request.getUserContext().getSubscriberId().length() <= 0) {

					LOGGER.error("Subscriber Id obtained in request is null or empty");

					throw new Exception(
							"Subscriber Id obtained in request is null or empty");

				} else {
					subscriberId = request.getUserContext().getSubscriberId();

					QuerySubscriberPrepaidInfoResponseType querySubscriberPrepaidInfoResponseType = commonUtil
							.getSubscriberPrepaidInfo(request.getUserContext()
									.getSubscriberId());

					if (null == querySubscriberPrepaidInfoResponseType) {
						throw new Exception(
								"Null response obtained from queryPrepaidSubscriberProxyService");
					}

					msisdn = querySubscriberPrepaidInfoResponseType
							.getResourceInfo().getMdn();

					LOGGER.info("MSISDN obtained from QueryPrepaidSubscriberInfo is "
							+ msisdn);
				}
			} else if (request.getUserContext().getSubscriberId() == null
					|| request.getUserContext().getSubscriberId().length() <= 0) {

				msisdn = request.getUserContext().getMsisdn();

				LOGGER.error("Subscriber obtained in request is null or empty");

				// Checking if Subscriber Id is obtained from request or
				// not, if not obtained throw an error
				if (request.getUserContext().getMsisdn() == null
						|| request.getUserContext().getMsisdn().length() <= 0) {

					LOGGER.error("MSISDN obtained in request is null or empty");

					throw new Exception(
							"MSISDN obtained in request is null or empty");

				} else {

					QuerySubscriberPrepaidInfoResponseType querySubscriberPrepaidInfoResponseType = commonUtil
							.getSubscriberPrepaidInfoWithMsisdn(request
									.getUserContext().getMsisdn());

					if (null == querySubscriberPrepaidInfoResponseType) {
						throw new Exception(
								"Null response obtained from queryPrepaidSubscriberProxyService");
					}

					subscriberId = querySubscriberPrepaidInfoResponseType
							.getBasicInfo().getSubscriberId();

					LOGGER.info("SubscriberId obtained from QueryPrepaidSubscriberInfo is "
							+ subscriberId);
				}
			}

			Holder<WsMessageHeaderType> header = headerHandler
					.getHeader(Service.SECURITY_MANAGEMENT_SERVICE);

			/* Step 2: Constructing a passcode by taking last 7 digits of mdn */

			String passCode = (msisdn.length() > 6) ? msisdn.substring(msisdn
					.length() - 7) : msisdn;

			LOGGER.debug("Code=" + passCode);

			/* Step 3 : calling Service Management Service adapter */

			UpdateVoiceMailPasscodeType updateVoiceMailRequest = new UpdateVoiceMailPasscodeType();
			SearchCriteriaType searchCriteriaType = new SearchCriteriaType();

			if (request.getUserContext().getSubscriberId() == null
					|| request.getUserContext().getSubscriberId().length() <= 0) {
				searchCriteriaType.setSubscriberId(subscriberId);
			} else {
				searchCriteriaType.setSubscriberId(request.getUserContext()
						.getSubscriberId());
			}

			updateVoiceMailRequest.setSubscriberInfo(searchCriteriaType);
			updateVoiceMailRequest.setMdnPasscode(passCode);

			UpdateVoiceMailPasscodeResponseType updateVoiceMailPasscodeResponse = securityManagementProxyService
					.updateVoiceMailPasscode(updateVoiceMailRequest, header);

			if (null == updateVoiceMailPasscodeResponse) {
				throw new Exception(
						"Null response obtained from securityManagementProxyService");
			}

			// updateVoiceMailPasscodeResponse.getStatusInfo().getStatus();

			/* step 4: Updating StatusMessageResponse with appropriate response */

			statusMessageResponse = commonUtil.populateStatusRespMesg(false, updateVoiceMailPasscodeResponse
					.getStatusInfo().getStatus());
			
			/*statusMessage.setDescription(updateVoiceMailPasscodeResponse
					.getStatusInfo().getStatus());
			statusMessage.setReason(updateVoiceMailPasscodeResponse
					.getStatusInfo().getStatus());*/

		} catch (Exception e) {
			LOGGER.error("Exception occured --> " + e);
			AlarmUtil.raiseSnmpAlarm();
			statusMessageResponse = commonUtil.populateStatusRespMesg(true, e.getMessage());
		}
		
		LOGGER.info("Leaving resetVM workflow method");

		return statusMessageResponse;
	}

	@Override
	public StatusMessageResponse updatePin(UserContextRequest request,
			UpdatePinRequest updateRequest) {
		
		LOGGER.info("Entering updatePin workflow method");

		StatusMessageResponse statusMessageResponse = new StatusMessageResponse();
		//StatusMessage statusMessage = new StatusMessage();

		try {

			AlarmUtil.clearSnmpAlarm();

			Holder<WsMessageHeaderType> header = headerHandler
					.getHeader(Service.ACCOUNT_MANAGEMENT_PROXY_SERVICE);

			// Building input request parameters for
			// AccountManagementProxyService
			UpdateAccountDetailsRequestType updateAccountDetailsRequest = new UpdateAccountDetailsRequestType();

			com.sprint.integration.interfaces.updateaccountdetails.v1.updateaccountdetails.SearchCriteriaType searchCriteriaType = new com.sprint.integration.interfaces.updateaccountdetails.v1.updateaccountdetails.SearchCriteriaType();
			searchCriteriaType.setSubscriberId(request.getUserContext()
					.getSubscriberId());
			updateAccountDetailsRequest.setInfo(searchCriteriaType);

			BanValueObjInfoType accountValueObjectInfo = new BanValueObjInfoType();

			SecurityInfoType securityInfoType = new SecurityInfoType();
			securityInfoType.setPin(updateRequest.getPin());
			securityInfoType.setSecurityQuestion(updateRequest
					.getSecretQuestion());
			securityInfoType.setSecurityAnswer(updateRequest.getSecretAnswer());

			accountValueObjectInfo.setSecurityInfo(securityInfoType);
			updateAccountDetailsRequest
					.setAccountValueObjectInfo(accountValueObjectInfo);

			// Step 1: Call the "AccountManagement" adapter
			UpdateAccountDetailsResponseType updateAccountDetailsResponseType = accountManagementProxyService
					.updateAccountDetails(updateAccountDetailsRequest, header);

			if (null == updateAccountDetailsResponseType) {
				throw new Exception(
						"Null response obtained from accountManagementProxyService");
			}

			/*statusMessage.setDescription(updateAccountDetailsResponseType
					.getMessage());
			statusMessage.setReason(updateAccountDetailsResponseType
					.getMessage());*/

			// Step 2: Update StatusMessageResponse with appropriate response
			// according to result from Step 1
			statusMessageResponse = commonUtil.populateStatusRespMesg(false, updateAccountDetailsResponseType
					.getMessage());

		} catch (Exception e) {
			LOGGER.error("Exception occurred --> " + e.getMessage(), e);
			AlarmUtil.raiseSnmpAlarm();			

			// Step 2: Update StatusMessageResponse with appropriate response
			// according to result from Step 1
			statusMessageResponse = commonUtil.populateStatusRespMesg(true, e.getMessage());
		}

		LOGGER.info("Leaving updatePin workflow method");

		return statusMessageResponse;
	}

	@Override
	public SubscriberSettingsResponse getUserSettings(
			UserContextRequest userContextRequest) {
		
		LOGGER.info("Entering getUserSettings workflow method");

		SubscriberSettingsResponse subscriberSettingsResponse = new SubscriberSettingsResponse();
		Holder<WsMessageHeaderType> header;
		
		try {
			
			String subscriberId = userContextRequest.getUserContext().getSubscriberId();
			QuerySubscriberServicesRequest.Info info=new QuerySubscriberServicesRequest.Info();
			info.setSubsId(subscriberId);
			
			QuerySubscriberServicesRequest querySubscriberServicesRequest = new QuerySubscriberServicesRequest();
			
			FetchFeatureListType featureList = new FetchFeatureListType();
			featureList.setValue(true);
			featureList.setIncludeFutureDated(true);

			FetchSocListType socList = new FetchSocListType();
			socList.setIncludeExpired(true);
			
			querySubscriberServicesRequest.setFeatureList(featureList);
			querySubscriberServicesRequest.setSocList(socList);
			querySubscriberServicesRequest.setInfo(info);
			List<FeatureInfoType> featureInfoList;
			//List<SocListType.SocInfo> socInfo;
			
			header = headerHandler
					.getHeader(Service.QUERY_SUBSCRIBER_INFO_SERVICE);
			
			// 1. Call the "QuerySubscriberInfo" adapter
			querySubscriberServicesResponse = querySubscriberInfoProxyService.querySubscriberServices(querySubscriberServicesRequest,header);

			if (null != querySubscriberServicesResponse
					&& null != querySubscriberServicesRequest.getFeatureList()
					&& null != querySubscriberServicesResponse.getFeatureList()
							.getFeatureInfo()) {

				String featureCode = "";
				String name = "";
				
				// socInfo =
				// querySubscriberServicesResponse.getSocList().getSocInfo();

				// step 2 & 3
				// SubscriberSetting[] subscriberSettings = new
				// SubscriberSetting[featureInfoList.size()];
				List<SubscriberSetting> subscriberSettingsList = new ArrayList<SubscriberSetting>();

				for (FeatureInfoType featureInfo : querySubscriberServicesResponse
						.getFeatureList().getFeatureInfo()) {

					LOGGER.debug("FeatureCode: "
							+ featureInfo.getFeatureCode().toString());
					LOGGER.debug("SocCode: "
							+ featureInfo.getSocCode().toString());
					LOGGER.debug("Feature Name: " + featureInfo.getName());

					if (!StringUtils.isBlank(featureInfo.getSocCode())) {

						if (featureInfo
								.getFeatureCode()
								.equalsIgnoreCase(
										getUserSettings_FeatureCode_InternationalCalling)) {
							// International Calling is available
							SubscriberSetting subscriberSetting = new SubscriberSetting();
							featureCode = featureInfo.getFeatureCode();
							name = featureInfo.getName();
							subscriberSetting.setId(featureCode);
							subscriberSetting.setTitle(name);
							subscriberSetting.setSettingEnabled(true);

							subscriberSettingsList.add(subscriberSetting);

						} else if (featureInfo
								.getSocCode()
								.equalsIgnoreCase(
										getUserSettings_FeatureCode_NoInternationalCalling)) {
							// International Calling is not available
							SubscriberSetting subscriberSetting = new SubscriberSetting();
							featureCode = featureInfo.getFeatureCode();
							name = featureInfo.getName();
							subscriberSetting.setId(featureCode);
							subscriberSetting.setTitle(name);
							subscriberSetting.setSettingEnabled(false);

							subscriberSettingsList.add(subscriberSetting);
						} else if (featureInfo.getSocCode().equalsIgnoreCase(
								getUserSettings_FeatureCode_CallerIdRestricted)) {
							// user does not have CallerID functionality
							// available, else it is available.
							SubscriberSetting subscriberSetting = new SubscriberSetting();
							featureCode = featureInfo.getFeatureCode();
							name = featureInfo.getName();
							subscriberSetting.setId(featureCode);
							subscriberSetting.setTitle(name);
							subscriberSetting.setSettingEnabled(false);

							subscriberSettingsList.add(subscriberSetting);
						}
					} else {
						LOGGER.debug("SocCode is empty or null in querySubscriberServicesResponse");
						throw new Exception("SocCode is empty or null in querySubscriberServicesResponse");
					}
				}

				// step 4
				// subscriberSettingsResponse.setSubscriberSettings((SubscriberSetting[])

				// step 5 - Call to "CustomerDataPrivacyManagement" adapter
				Holder<WsMessageHeaderType> header1 = headerHandler
						.getHeader(Service.CUSTOMER_DATA_PRIVACY_MANAGEMENT_SERVICE);

				QueryUserPreferencesInfoV2Type queryUserPreferencesInfoV2Request = new QueryUserPreferencesInfoV2Type();
				UserIdentityInfoType userIdentityInfo = new UserIdentityInfoType();
				userIdentityInfo.setOwnerId(userContextRequest.getUserContext()
						.getMsisdn());
				userIdentityInfo.setOwnerType(getUserSettings_OwnerType);

				UserPreferenceListType userPreferenceListType = new UserPreferenceListType();
				UserPreferenceInfoType userPreferenceInfoType = new UserPreferenceInfoType();
				userPreferenceInfoType
						.setContextName(getUserSettings_ContextName);
				userPreferenceInfoType
						.setPreferenceName(getUserSettings_PreferenceName);
				userPreferenceListType.getPreferenceInfo().add(
						userPreferenceInfoType);

				queryUserPreferencesInfoV2Request
						.setUserIdentityInfo(userIdentityInfo);
				queryUserPreferencesInfoV2Request
						.setUserPreferenceList(userPreferenceListType);

				queryUserPreferencesInfoV2Response = customerDataPrivacyManagementService
						.queryUserPreferencesInfoV2(
								queryUserPreferencesInfoV2Request, header1);

				if (null != queryUserPreferencesInfoV2Response
						&& null != queryUserPreferencesInfoV2Response
								.getUserPreferenceList()
						&& null != queryUserPreferencesInfoV2Response
								.getUserPreferenceList().getPreferenceInfo()) {

					for (int i = 0; i < userPreferenceListType
							.getPreferenceInfo().size(); i++) {

						LOGGER.debug("queryUserPreferencesInfoV2Response--->contextName:::"
								+ queryUserPreferencesInfoV2Response
										.getUserPreferenceList()
										.getPreferenceInfo().get(0)
										.getContextName());

						if (null != queryUserPreferencesInfoV2Response
								.getUserPreferenceList().getPreferenceInfo()
								.get(i).getPreferenceValueList()
								&& null != queryUserPreferencesInfoV2Response
										.getUserPreferenceList()
										.getPreferenceInfo().get(i)
										.getPreferenceValueList()
										.getPreferenceValueInfo()
								&& !StringUtils
										.isBlank(queryUserPreferencesInfoV2Response
												.getUserPreferenceList()
												.getPreferenceInfo().get(i)
												.getPreferenceValueList()
												.getPreferenceValueInfo()
												.get(0).getValue())) {

							// step 6
							String value = queryUserPreferencesInfoV2Response
									.getUserPreferenceList()
									.getPreferenceInfo().get(i)
									.getPreferenceValueList()
									.getPreferenceValueInfo().get(0).getValue();

							if (value
									.equalsIgnoreCase(getUserSettings_PrefValue_NotSet)
									|| (value
											.equalsIgnoreCase(getUserSettings_PrefValue_Off))) {
								// the Content Filter is OFF
								SubscriberSetting subscriberSetting = new SubscriberSetting();
								subscriberSetting
										.setId(getUserSettings_Subscriber_Cf_Id);
								subscriberSetting
										.setTitle(getUserSettings_Subscriber_Title_ContentFilter);
								subscriberSetting
										.setDescription(getUserSettings_Subscriber_Desc_Off);
								subscriberSetting.setSettingEnabled(false);

								subscriberSettingsList.add(subscriberSetting);

							} else if (value
									.equalsIgnoreCase(getUserSettings_PrefValue_On)) {
								// the Content Filter is ON
								SubscriberSetting subscriberSetting = new SubscriberSetting();
								subscriberSetting
										.setId(getUserSettings_Subscriber_Cf_Id);
								subscriberSetting
										.setTitle(getUserSettings_Subscriber_Title_ContentFilter);
								subscriberSetting
										.setDescription("getUserSettings_Subscriber_Desc_On");
								subscriberSetting.setSettingEnabled(true);

								subscriberSettingsList.add(subscriberSetting);

							}
						} else {
							// the Content Filter is OFF
							SubscriberSetting subscriberSetting = new SubscriberSetting();
							subscriberSetting
									.setId(getUserSettings_Subscriber_Cf_Id);
							subscriberSetting
									.setTitle(getUserSettings_Subscriber_Title_ContentFilter);
							subscriberSetting
									.setDescription(getUserSettings_Subscriber_Desc_Off);
							subscriberSetting.setSettingEnabled(false);

							subscriberSettingsList.add(subscriberSetting);
						}
					}
				}

				subscriberSettingsResponse
						.setSubscriberSettings((SubscriberSetting[]) subscriberSettingsList
								.toArray(new SubscriberSetting[subscriberSettingsList
										.size()]));

				subscriberSettingsResponse.setStatusMessage(commonUtil
						.populateStatusRespMesg(false, "").getStatusMessage());
			} else {

				LOGGER.debug("querySubscriberServicesResponse NULL..!");

				throw new Exception(
						"Null response obtained from querySubscriberServicesResponse");
			}

		} catch (Exception e) {

			LOGGER.error(e.getMessage(), e);
			AlarmUtil.raiseSnmpAlarm();

			subscriberSettingsResponse
					.setSubscriberSettings(populateSettingsToDisabled());

			subscriberSettingsResponse.setStatusMessage(commonUtil
					.populateStatusRespMesg(true, e.getMessage())
					.getStatusMessage());
		}

		LOGGER.info("Leaving getUserSettings workflow method");

		return subscriberSettingsResponse;
	}
	
	private SubscriberSetting[] populateSettingsToDisabled() {
		
		SubscriberSetting subSettingNoIntCalling = new SubscriberSetting();
		subSettingNoIntCalling.setId(getUserSettings_FeatureCode_NoInternationalCalling);
		subSettingNoIntCalling.setTitle("International Calling");
		subSettingNoIntCalling.setDescription("getUserSettings_Subscriber_Desc_Off");
		subSettingNoIntCalling.setSettingEnabled(false);
		
		SubscriberSetting subSettingCallerIdRest = new SubscriberSetting();
		subSettingCallerIdRest.setId(getUserSettings_FeatureCode_CallerIdRestricted);
		subSettingCallerIdRest.setTitle("Caller Id");
		subSettingCallerIdRest.setDescription("getUserSettings_Subscriber_Desc_Off");
		subSettingCallerIdRest.setSettingEnabled(false);
		
		SubscriberSetting subSettingNoContentFilter = new SubscriberSetting();
		subSettingNoContentFilter.setId(getUserSettings_Subscriber_Cf_Id);
		subSettingNoContentFilter.setTitle(getUserSettings_Subscriber_Title_ContentFilter);
		subSettingNoContentFilter.setDescription(getUserSettings_Subscriber_Desc_Off);
		subSettingNoContentFilter.setSettingEnabled(false);
		
		SubscriberSetting[] subscriberSettingArr = 
			{subSettingNoIntCalling, subSettingCallerIdRest, subSettingNoContentFilter};
		
		return subscriberSettingArr;		
	}

	@Override
	// The purpose of this method is to update the subscriber settings,
	// specifically Content settings (i.e Content Filter)
	public StatusMessageResponse updateUserSettings(
			UserContextRequest userContext,
			SubscriberSettingsRequest subscriberSettingsRequest) {

		LOGGER.info("Entering updateUserSettings workflow method");

		StatusMessageResponse statusMessageResponse = new StatusMessageResponse();
		// Send request, get response.
		// StatusMessage statusMessage = new StatusMessage();

		UpdateUserPreferencesInfoV2Type updateUserPreferencesInfoV2Type = new UpdateUserPreferencesInfoV2Type();
		try {

			com.sprint.integration.interfaces.updateuserpreferencesinfo.v2.updateuserpreferencesinfov2.UserIdentityInfoType userIdentityInfoType = new com.sprint.integration.interfaces.updateuserpreferencesinfo.v2.updateuserpreferencesinfov2.UserIdentityInfoType();
			com.sprint.integration.interfaces.updateuserpreferencesinfo.v2.updateuserpreferencesinfov2.UserPreferenceListType userPreferenceListType = new com.sprint.integration.interfaces.updateuserpreferencesinfo.v2.updateuserpreferencesinfov2.UserPreferenceListType();
			com.sprint.integration.interfaces.updateuserpreferencesinfo.v2.updateuserpreferencesinfov2.UserPreferenceInfoType contextNameUserPref = new com.sprint.integration.interfaces.updateuserpreferencesinfo.v2.updateuserpreferencesinfov2.UserPreferenceInfoType();
			com.sprint.integration.interfaces.updateuserpreferencesinfo.v2.updateuserpreferencesinfov2.UserPreferenceInfoType preferenceNameUserPref = new com.sprint.integration.interfaces.updateuserpreferencesinfo.v2.updateuserpreferencesinfov2.UserPreferenceInfoType();
			com.sprint.integration.interfaces.updateuserpreferencesinfo.v2.updateuserpreferencesinfov2.UserPreferenceInfoType preferenceValueInfoUserPref = new com.sprint.integration.interfaces.updateuserpreferencesinfo.v2.updateuserpreferencesinfov2.UserPreferenceInfoType();
			String contentFilterActiveString = "NO";

			// Set MDN + ownertype
			userIdentityInfoType.setOwnerId(userContext.getUserContext()
					.getMsisdn());
			userIdentityInfoType.setOwnerType(UserIdentifierTypeCodeType.MDN);
			updateUserPreferencesInfoV2Type
					.setUserIdentityInfo(userIdentityInfoType);

			// Set contextName + preferenceName
			contextNameUserPref.setContextName("nwpolicy");
			userPreferenceListType.getPreferenceInfo().add(contextNameUserPref);
			preferenceNameUserPref.setPreferenceName("CIPA");
			userPreferenceListType.getPreferenceInfo().add(
					preferenceNameUserPref);

			// SubscriberSetting.id = CF will indicate a ContentFilter
			SubscriberSetting[] list = subscriberSettingsRequest
					.getSubscriberSettings();
			for (SubscriberSetting s : list) {
				if (s!=null && s.getId()!=null && s.getId().equalsIgnoreCase("CF") && s.isSettingEnabled()) {
					contentFilterActiveString = "YES";
				}
			}

			PreferenceValueListType preferenceValueListType = new PreferenceValueListType();
			com.sprint.integration.interfaces.updateuserpreferencesinfo.v2.updateuserpreferencesinfov2.PreferenceValueInfoType preferenceValueInfoType = new com.sprint.integration.interfaces.updateuserpreferencesinfo.v2.updateuserpreferencesinfov2.PreferenceValueInfoType();
			preferenceValueInfoType.setValue(contentFilterActiveString);
			preferenceValueListType.getPreferenceValueInfo().add(
					preferenceValueInfoType);
			preferenceValueInfoUserPref
					.setPreferenceValueList(preferenceValueListType);
			userPreferenceListType.getPreferenceInfo().add(
					preferenceValueInfoUserPref);
			updateUserPreferencesInfoV2Type
					.setUserPreferencesList(userPreferenceListType);

			// Set notificationInd
			com.sprint.integration.interfaces.updateuserpreferencesinfo.v2.updateuserpreferencesinfov2.NotificationInfoType notificationInfoType = new com.sprint.integration.interfaces.updateuserpreferencesinfo.v2.updateuserpreferencesinfov2.NotificationInfoType();
			notificationInfoType.setNotificationInd(true);
			updateUserPreferencesInfoV2Type
					.setNotificationInfo(notificationInfoType);

			Gson gson=new Gson();
	        String className = this.getClass().getName();
	        String methodName = "updateUserSettings";
	        String arguments = gson.toJson(updateUserPreferencesInfoV2Type);
	        LOGGER.info("going to call method {} of class {} with arguments {}", methodName, className, arguments);
			
			Holder<WsMessageHeaderType> header = headerHandler
					.getHeader(Service.CUSTOMER_DATA_PRIVACY_MANAGEMENT_SERVICE);
			UpdateUserPreferencesInfoV2ResponseType updateUserPreferencesInfoV2ResponseType = customerDataPrivacyManagementService
					.updateUserPreferencesInfoV2(
							updateUserPreferencesInfoV2Type, header);

			/*
			 * statusMessage
			 * .setDescription(updateUserPreferencesInfoV2ResponseType
			 * .getConfirmationMessage());
			 * 
			 * statusMessageResponse.setStatusMessage(statusMessage);
			 */

			statusMessageResponse = commonUtil.populateStatusRespMesg(false,
					updateUserPreferencesInfoV2ResponseType
							.getConfirmationMessage());

		} catch (DatatypeConfigurationException e) {
			LOGGER.error(
					"DatatypeConfigurationException " + e.getLocalizedMessage(),
					e);

			AlarmUtil.raiseSnmpAlarm();

			statusMessageResponse = commonUtil.populateStatusRespMesg(true,
					e.getLocalizedMessage());

		} catch (SoapFault e) {
			LOGGER.error("SoapFault " + e.getLocalizedMessage(), e);

			AlarmUtil.raiseSnmpAlarm();

			statusMessageResponse = commonUtil.populateStatusRespMesg(true,
					e.getLocalizedMessage());
		}

		LOGGER.info("Leaving updateUserSettings workflow method");

		return statusMessageResponse;
	}

	// Invoking SmsPreferenceManagementService for "updateSmsPreferenceInfo"
	private String updateSmsPreferenceInfo(String msisdn,
			Holder<WsMessageHeaderType> header) throws Exception {

		String message = "";
		UpdateSmsPreferenceInfoRequestType updateSmsPreferenceInfo = new UpdateSmsPreferenceInfoRequestType();
		com.sprint.integration.interfaces.updatesmspreferenceinfo.v1.updatesmspreferenceinfo.InfoType info = new com.sprint.integration.interfaces.updatesmspreferenceinfo.v1.updatesmspreferenceinfo.InfoType();
		info.setMdn(msisdn);
		updateSmsPreferenceInfo.setInfo(info);
		updateSmsPreferenceInfo.setActivateWhiteListInd(Boolean
				.valueOf(trueValue));

		// Assiging an empty object for the whiteList
		ResourceListType resourceListType = new ResourceListType();
		/*
		 * List<ResourceInfoType> resourceInfoTypeList = resourceListType
		 * .getResourceInfo(); resourceInfoTypeList = Collections
		 * .<ResourceInfoType> emptyList();
		 */

		updateSmsPreferenceInfo.setWhiteList(resourceListType);
		//Wensheng: We need assiging the current block data as well.
//		updateSmsPreferenceInfo.setBlockList(resourceListType);

		LOGGER.debug("Invoking SMSPreferenceManagement -> updateSmsPreferenceInfo method");
		UpdateSmsPreferenceInfoResponseType updateSmsPreferenceInfoResponseType = smsPreferenceManagementProxyService
				.updateSmsPreferenceInfo(updateSmsPreferenceInfo, header);

		if (null == updateSmsPreferenceInfoResponseType) {
			throw new Exception(
					"Null response obtained from SMSPreferenceManagement");
		}

		message = updateSmsPreferenceInfoResponseType.getConfirmationMessage();
		LOGGER.info("Message obtianed from SMSPreferenceManagement -> "
				+ message);

		return message;
	}
}
