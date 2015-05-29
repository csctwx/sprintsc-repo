package com.sprint.msdp.commandservice.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericsson.cac.sprint.adapters.LoggingManagementProxyService;
import com.ericsson.cac.sprint.adapters.LoggingManagementServiceLogging;
import com.ericsson.cac.sprint.adapters.QueryAccountInfoServiceProxyService;
import com.ericsson.cac.sprint.adapters.QueryPrepaidSubscriberProxyService;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.interfaces.queryaccountsecurityinfo.v1.queryaccountsecurityinfo.QueryAccountSecurityInfo;
import com.sprint.integration.interfaces.queryaccountsecurityinfo.v1.queryaccountsecurityinfo.QueryAccountSecurityInfoResponse;
import com.sprint.integration.interfaces.queryaccountsecurityinfo.v1.queryaccountsecurityinfo.SearchCriteria;
import com.sprint.integration.interfaces.queryloginattemptstatus.v1.queryloginattemptstatus.QueryLoginAttemptStatusRequestType;
import com.sprint.integration.interfaces.queryloginattemptstatus.v1.queryloginattemptstatus.QueryLoginAttemptStatusResponseType;
import com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.QuerySubscriberPrepaidInfoResponseType;
import com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.QuerySubscriberPrepaidInfoType;
import com.sprint.integration.interfaces.submitloginattempt.v1.submitloginattempt.AccountStatusCodeType;
import com.sprint.integration.interfaces.submitloginattempt.v1.submitloginattempt.SubmitLoginAttemptRequestType;
import com.sprint.integration.interfaces.submitloginattempt.v1.submitloginattempt.SubmitLoginAttemptRequestType.LockInfo;
import com.sprint.integration.interfaces.submitloginattempt.v1.submitloginattempt.SubmitLoginAttemptResponseType;
import com.sprint.msdp.commandservice.CommandMetaWs;
import com.sprint.msdp.commandservice.CommandRequest;
import com.sprint.msdp.commandservice.CommandResponse;
import com.sprint.msdp.commandservice.CommandResult;
import com.sprint.msdp.commandservice.WSExceptionException;

@Service
public class SprintCommandServiceImpl implements SprintCommandService {
	private static Logger logger = LoggerFactory.getLogger(LoggingManagementServiceLogging.class);

	@Autowired
	private QueryPrepaidSubscriberProxyService service;
	
	@Autowired
	private LoggingManagementProxyService loggingService;
	
	@Autowired
	private QueryAccountInfoServiceProxyService securityService;

	@Autowired
	private Holder<WsMessageHeaderType> successHeader;

	@Override
	public CommandResponse commandRequest(CommandRequest parameters) throws WSExceptionException {
		List<CommandMetaWs> commandMeta = parameters.getCommandMeta();
		CommandResult result = commandRequest("", commandMeta );
		CommandResponse response = new CommandResponse();
		response.setReturn(result);
		return response;
	}

	private CommandResult commandRequest(String command, List<CommandMetaWs> commandMeta) throws WSExceptionException {
		// 1. Extract MDN and Pin from meta input
		String mdn = getMetaString(commandMeta, "msisdn");
		String enteredPin = getMetaString(commandMeta, "pin");
		logger.info("Subscriber MDN: " + mdn);
		logger.info("Subscriber Pin: " + enteredPin);
		
		// 2. Send QuerySusbcriberPrepaidInfo request to EAI
		logger.info("Preparing QuerySusbcriberPrepaidInfo request");
		
		QuerySubscriberPrepaidInfoType querySubscriberPrepaidInfoRequest = new QuerySubscriberPrepaidInfoType();
		com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.SearchCriteriaType searchCriteria = new com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.SearchCriteriaType();
		searchCriteria.setMdn(mdn);
		querySubscriberPrepaidInfoRequest.setInfo(searchCriteria);

		logger.info("Sending querySubscriberPrepaidInfoRequest");

		String brandCode = "";
		String subscriberStatus = "";
		String subscriberIdentity = "";
		String errorDescription = "";
		GregorianCalendar gregorianCalendarObj1 = new GregorianCalendar();
		XMLGregorianCalendar currentTime;
		XMLGregorianCalendar lastAttempt;
		XMLGregorianCalendar lastAttempt1;
		GregorianCalendar gregorianCalendarObj2 = new GregorianCalendar();
		Date dateObj = new Date();
		XMLGregorianCalendar softlockTime,hardlockTime;
		int noOfFailedAttempts;
		
		//GET FROM CONFIG
		//no of attempts
		int softLock = 3;
		int hardLock = 5;
		//In Hrs
		int softLockUntil = 4;				
		int hardLockUntil = 10000;
		
		Boolean submitLoginAttemptResult = false;
		
		try {
			QuerySubscriberPrepaidInfoResponseType querySubscriberPrepaidInfoResponse = service.querySubscriberPrepaidInfo(
					querySubscriberPrepaidInfoRequest, successHeader);

			if (querySubscriberPrepaidInfoResponse != null && querySubscriberPrepaidInfoResponse.getBasicInfo() != null) {
				logger.info("Response Info:");
				brandCode = querySubscriberPrepaidInfoResponse.getPrepaidInfo().getBrandCode();
				logger.info("BrandCode " + brandCode);

				subscriberStatus = querySubscriberPrepaidInfoResponse.getBasicInfo().getSubscriberStatus().value();
				logger.info("Subscriber Status " + subscriberStatus);

				subscriberIdentity = querySubscriberPrepaidInfoResponse.getBasicInfo().getSubscriberId();
				logger.info("Subscriber Identity " + subscriberIdentity);

				if (subscriberStatus != null && subscriberStatus.equals("R")) { // Preactive
					errorDescription = "Subscriber Status is in PreActive State";
				}

				if (subscriberStatus != null && subscriberStatus.equals("D")) { // Expired
					errorDescription = "Subscriber Status is in Expired State";
				}

				if (subscriberStatus != null && subscriberStatus.equals("S")) { // Suspended
					String statusChangeReasonCode = querySubscriberPrepaidInfoResponse.getBasicInfo().getStatusChangeReasonCode();
					boolean fraudInd = querySubscriberPrepaidInfoResponse.getBasicInfo().isIsFraudInd();
					boolean demoPlanInd = querySubscriberPrepaidInfoResponse.getPlanInfo().isIsDemoPlanInd();

					if ((statusChangeReasonCode != null && (statusChangeReasonCode.equals("VSUS")) || fraudInd || demoPlanInd)) {
						errorDescription = "Subscriber Status is in Suspended State and is either Fraudulant or a Demo account";
					} else {
						errorDescription = "Subscriber Status is in Suspended State";
					}
				}
				logger.info("Error Description: " + errorDescription);
			} else {
				errorDescription = "No Subscriber found";				
			}
			
			//3. If no error occured in first call to backend, move to checking Login Attempts and Pin
			if(errorDescription.isEmpty()){
				logger.info("Preparing QuerySusbcriberPrepaidInfo request");
				QueryLoginAttemptStatusRequestType queryLoginAttemptStatusRequest = new QueryLoginAttemptStatusRequestType();
				queryLoginAttemptStatusRequest.setMdn(mdn); 
				QueryLoginAttemptStatusResponseType queryLoginAttemptStatusResponse = loggingService.queryLoginAttemptStatus(queryLoginAttemptStatusRequest, successHeader);
				
				
				if(queryLoginAttemptStatusResponse!=null && queryLoginAttemptStatusResponse.getMdn()!=null && 
						queryLoginAttemptStatusResponse.getNumberOfFailedAttempts()!=null && 
						queryLoginAttemptStatusResponse.getStatus().value()!=null) {
					
					logger.info("Received Response from Login Management Proxy");
					currentTime=DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendarObj1);
					
					noOfFailedAttempts=	queryLoginAttemptStatusResponse.getNumberOfFailedAttempts().intValue();
					logger.info("Number of Failed Login Attempts retrieved: " +noOfFailedAttempts);
					
					if(querySubscriberPrepaidInfoResponse.getBasicInfo().getSubscriberStatus().value().equalsIgnoreCase("L")){ //LOCKED						
						
						if((currentTime.getHour() < queryLoginAttemptStatusResponse.getLockedUntil().getHour()) &&
								(queryLoginAttemptStatusResponse.getNumberOfFailedAttempts().intValue() >= softLock)){
							
							logger.info("Updating the number of failed attempts for SoftLock");
							noOfFailedAttempts = noOfFailedAttempts + 1;											
							
							submitLoginAttemptResult = submitLoginAttempt(mdn, noOfFailedAttempts);
							
							//UNCLEAR  WHAT THIS THIS. Seems like 17.27 waitTime Calculation
							int waitTime = 0;
							if(submitLoginAttemptResult) {
								currentTime=DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendarObj1);
								if(softLockUntil>=currentTime.getHour()) {
									//~ 17.27 waitTime
									waitTime = softLockUntil - currentTime.getHour();
								}
								logger.info("Wait Time calculated is: " +waitTime);
								//d.setHours(waitTime);//soft lock cal time
								//c1.setGregorianChange(d);
								//softlockTime=DatatypeFactory.newInstance().newXMLGregorianCalendar(c1);
								//softlock the account
								//LockInfo softLock=new LockInfo();
								//softLock.setLockedUntil(softlockTime);
								//submitLoginAttemptRequest.setLockInfo(softLock);
								errorDescription = "You must wait " +waitTime+ " hours before your next login attempt";
							} else {
								errorDescription = "Error Submitting Login Attempts";
							}
						} else if((currentTime.getHour() < queryLoginAttemptStatusResponse.getLockedUntil().getHour()) &&
								(queryLoginAttemptStatusResponse.getNumberOfFailedAttempts().intValue() >= hardLock)){
							
							logger.info("Updating the number of failed attempts for HardLock");
							noOfFailedAttempts = noOfFailedAttempts + 1;
							
							submitLoginAttemptResult = submitLoginAttempt(mdn, noOfFailedAttempts);
							
							if(submitLoginAttemptResult) {
								errorDescription = "User is in a hardlock state. Please call customer care";
							} else {
								errorDescription = "Error Submitting Login Attempts";
							}
							
						}
					} else if(
							querySubscriberPrepaidInfoResponse.getBasicInfo().getSubscriberStatus().value().equalsIgnoreCase("F") ||
							querySubscriberPrepaidInfoResponse.getBasicInfo().getSubscriberStatus().value().equalsIgnoreCase("A")
							
							) { // Subscriber Status is set to Free
						
						if(noOfFailedAttempts<softLock) {
							
							logger.info("Number of Failed Attempts is less than soft lock limit");
							QueryAccountSecurityInfo queryAccountSecurityInfoRequest = new QueryAccountSecurityInfo();
							QueryAccountSecurityInfoResponse queryAccountSecurityInfoResponse = new QueryAccountSecurityInfoResponse();
							SearchCriteria value = new SearchCriteria();
							value.setMdn(mdn);
							queryAccountSecurityInfoRequest.setInfo(value );
							queryAccountSecurityInfoRequest.setFetchPrivacyInfo(true);
				
							queryAccountSecurityInfoResponse = securityService.queryAccountSecurityInfo(queryAccountSecurityInfoRequest, successHeader);
							String backendPin = queryAccountSecurityInfoResponse.getSecurityInfo().getPin();							
							
							//Verify Pin
							if(enteredPin.equals(backendPin)){
								logger.info("Pin is valid");
								//Everything went good. Submit a successful login attempt						
								submitLoginAttemptResult = submitLoginAttempt(mdn, 0);
								
								if(!submitLoginAttemptResult) {
									errorDescription = "Error Submitting Login Attempts";
								}
							} else {
								logger.info("Pin is invalid");
								
								noOfFailedAttempts=noOfFailedAttempts+1;
								
								submitLoginAttemptResult = submitLoginAttempt(mdn, noOfFailedAttempts);
								
								if(submitLoginAttemptResult) {
									errorDescription = "Pin invalid";
								} else {
									errorDescription = "Error Submitting Login Attempts";
								}							
							}
						} else if(noOfFailedAttempts == softLock) {
							logger.info("Number of Failed Attempts is equal to soft lock limit");
							QueryAccountSecurityInfo queryAccountSecurityInfoRequest = new QueryAccountSecurityInfo();
							QueryAccountSecurityInfoResponse queryAccountSecurityInfoResponse = new QueryAccountSecurityInfoResponse();
							
							SearchCriteria info = new SearchCriteria();
							info.setMdn(mdn);
							queryAccountSecurityInfoRequest.setInfo(info);
							queryAccountSecurityInfoRequest.setFetchPrivacyInfo(true);
				
							queryAccountSecurityInfoResponse = securityService.queryAccountSecurityInfo(queryAccountSecurityInfoRequest, successHeader);
							String backendPin = queryAccountSecurityInfoResponse.getSecurityInfo().getPin();							
							//Verify Pin
							if(enteredPin.equals(backendPin)){
								logger.info("Pin is valid");
								//Everything went good. Submit a successful login attempt						
								submitLoginAttemptResult = submitLoginAttempt(mdn, 0);
								
								if(!submitLoginAttemptResult) {
									errorDescription = "Error Submitting Login Attempts";
								}
							} else {
								logger.info("Pin is invalid");
								noOfFailedAttempts=noOfFailedAttempts+1;
								submitLoginAttemptResult = submitLoginAttempt(mdn, noOfFailedAttempts);
								if(submitLoginAttemptResult) {
									errorDescription = "Pin invalid. Your account has been softlocked. You will need to wait " +String.valueOf(softLock)+ " hours until the next login attempt";
								} else {
									errorDescription = "Error Submitting Login Attempts";
								}
																								
							}
				
						}
					} else {
						logger.info("Soo.... What should I do?");								
					}
				} else {
					errorDescription = "Unable to retrieve Login Status";
				}
			}
		} catch (Exception e) {
			errorDescription = e.getMessage();
			logger.error("Error Description: ", e);
		}
		
		
		return setCommandResult(subscriberIdentity, subscriberStatus, brandCode, errorDescription);

	}

	private boolean submitLoginAttempt(String mdn, int numberOfFailedAttempts){
		logger.info("submitLoginAttempt start");
		GregorianCalendar gregorianCalendarObj1 = new GregorianCalendar();
		boolean result = false;
		
		try{
			SubmitLoginAttemptRequestType submitLoginAttemptRequest = new SubmitLoginAttemptRequestType();
			
			submitLoginAttemptRequest.setMdn(mdn); 
			submitLoginAttemptRequest.setNumberOfFailedAttempts(BigInteger.valueOf(numberOfFailedAttempts));
			
			XMLGregorianCalendar currentTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendarObj1);
			submitLoginAttemptRequest.setLastAttempt(currentTime);// Login date time
			
			if(numberOfFailedAttempts == 0){
				LockInfo lockInfo=new LockInfo();
				lockInfo.setClearOnLock(currentTime);
				submitLoginAttemptRequest.setLockInfo(lockInfo);
				//submitLoginAttemptRequest.setStatus(AccountStatusCodeType.F);
			}
								
			SubmitLoginAttemptResponseType submitLoginAttemptResponse = loggingService.submitLoginAttempt(submitLoginAttemptRequest, successHeader);
			
			if(submitLoginAttemptResponse!=null && submitLoginAttemptResponse.getMdn()!=null && submitLoginAttemptResponse.getStatus().value()!=null) {
				logger.info("SubmitLoginAttempt request successful");
				logger.info("submitLoginAttempt end");
				result = true;
			} else {
				logger.info("SubmitLoginAttempt request not successful");
				logger.info("submitLoginAttempt end");
				
			}
		} catch (Exception e) {			
			logger.error("Error Description: ", e);			
		}
		
		return result;
	}
	
	
	private CommandResult setCommandResult(String subId, String subStatus, String brandCode, String errorDescription) {
		logger.info("setCommandResult Start");

		CommandResult commandResult = new CommandResult();

		commandResult.setUri(subId);

		CommandMetaWs metaSubId = new CommandMetaWs();
		metaSubId.setKey("subscriberIdentity");
		metaSubId.setValue(subId);
		commandResult.getExtraAttributes().add(metaSubId);

		CommandMetaWs metaSubStatus = new CommandMetaWs();
		metaSubStatus.setKey("subscriberStatus");
		metaSubStatus.setValue(subStatus);
		commandResult.getExtraAttributes().add(metaSubStatus);

		CommandMetaWs metaBrandCode = new CommandMetaWs();
		metaBrandCode.setKey("brandCode");
		metaBrandCode.setValue(brandCode);
		commandResult.getExtraAttributes().add(metaBrandCode);

		CommandMetaWs metaError = new CommandMetaWs();
		metaError.setKey("Error");
		metaError.setValue(errorDescription);
		commandResult.getExtraAttributes().add(metaError);		
		
		if(errorDescription.isEmpty()){
			commandResult.setResult(true);
		} else {
			commandResult.setResult(false);
		}
		

		logger.info("setCommandResult End");

		return commandResult;
	}

	public String getMetaString(List<CommandMetaWs> metaListArg, String key) {
		String result = "";
		for (CommandMetaWs s : metaListArg) {
			if (s.getKey().equals(key)) {
				result = s.getValue();
				logger.info("getMetaString - Found Meta with Key=" + key + ". Value=" + result);
				break;
			}
		}
		return result;
	}

}
