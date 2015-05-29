/**
 * Sprint Template
 */
package com.ericsson.cac.sprint.selfcare.workflow.lostphone;

import java.math.BigInteger;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.Holder;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ericsson.cac.sprint.adapters.SubscriberManagementProxyService;
import com.ericsson.cac.sprint.selfcare.workflow.commons.HeaderData;
import com.ericsson.cac.sprint.selfcare.workflow.lostphone.model.RestoreRequest;
import com.ericsson.cac.sprint.selfcare.workflow.lostphone.model.RestoreResponse;
import com.ericsson.cac.sprint.selfcare.workflow.lostphone.model.SuspendRequest;
import com.ericsson.cac.sprint.selfcare.workflow.lostphone.model.SuspendResponse;
import com.sprint.integration.common.header.wsmessageheader.v2.TrackingMessageHeaderType;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber.RestoreSubscriberRequestType;
import com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber.RestoreSubscriberResponseType;
import com.sprint.integration.interfaces.suspendsubscriber.v1.suspendsubscriber.ActivityInfoType;
import com.sprint.integration.interfaces.suspendsubscriber.v1.suspendsubscriber.ChangeSubStatusInfoType;
import com.sprint.integration.interfaces.suspendsubscriber.v1.suspendsubscriber.MemoInfoType;
import com.sprint.integration.interfaces.suspendsubscriber.v1.suspendsubscriber.SubscriberInfoType;
import com.sprint.integration.interfaces.suspendsubscriber.v1.suspendsubscriber.SuspendSubscriberRequestType;
import com.sprint.integration.interfaces.suspendsubscriber.v1.suspendsubscriber.SuspendSubscriberResponseType;


/**
 * LostPhoneImpl is used to call the external adapters for sending the request and 
 * fetching information
 * 
 * @author Ericsson
 * @version $Revision: 1.0 $
 */
@Service
public class LostPhoneServiceImpl implements LostPhoneService{

	/** Logger class info  */
	private static final Logger LOGGER = LoggerFactory.getLogger(LostPhoneServiceImpl.class);
	
	private static final String FAILURE="FAILURE";
	
	private static final String INVALIDMDN="Please Enter a valid MDN";
	/**   */
	@Autowired
	private SubscriberManagementProxyService subscriberManagementProxyService;

	/**   */
	private WsMessageHeaderType successHead = new WsMessageHeaderType();

	/**   */
	private Holder<WsMessageHeaderType> successHeader = null;

	
	@Value("${SubscriberManagementService.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${SubscriberManagementService.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${SubscriberManagementService.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${SubscriberManagementService.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${SubscriberManagementService.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${SubscriberManagementService.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${SubscriberManagementService.trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${SubscriberManagementService.trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${SubscriberManagementService.trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${SubscriberManagementService.trackingHeadFailTimeToLive}")
	private String trackingHeadFailTimeToLive;
	
	@Value("${LostPhone.activityReason}")
	private String activityReason;
	@Value("${LostPhone.memoSource}")
	private String memoSource;
	@Value("${LostPhone.userText}")
	private String userText;
	
	
	/**
	 * Method formatSuccessHeader.
	 * @throws DatatypeConfigurationException 
	 */
	public void formatSuccessHeader() throws DatatypeConfigurationException{
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
		successHeader = new Holder<WsMessageHeaderType>(
				successHead);
	}


	@Override
	public SuspendResponse suspendSubscriber(SuspendRequest request) {
		HeaderData data = request.getHeaderData();
		String mdn = request.getMdn();
		
		if(StringUtils.isBlank(mdn)){
			return new SuspendResponse(INVALIDMDN);
		}
		else{
				
		SuspendSubscriberResponseType suspendSubscriberResponse = null;
		MemoInfoType memoInfo;
		ActivityInfoType activityInfo;
		ChangeSubStatusInfoType changeSubStatusInfo;
		SuspendSubscriberRequestType suspendSubscriberRequest;
		SubscriberInfoType subscriberInfo;
		
		Boolean isSessionValid = isSessionValid();
		
		
		if(isSessionValid){
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
			suspendSubscriberRequest.setChangeSubStatusInfo(changeSubStatusInfo);

			try {
				formatSuccessHeader();
				suspendSubscriberResponse = subscriberManagementProxyService.suspendSubscriber(suspendSubscriberRequest, successHeader);
				return new SuspendResponse(suspendSubscriberResponse.getMessage());

			} catch (Exception e) {
				raiseSnmpAlarm();
				return new SuspendResponse(FAILURE);
			}
		}
		else{
			return new SuspendResponse(FAILURE);
		}
	}
	}	

	@Override
	public RestoreResponse restoreSubscriber(RestoreRequest request) {
		HeaderData data = request.getHeaderData();
		String mdn = request.getMdn();
		
		if(StringUtils.isBlank(mdn)){
			return new RestoreResponse(INVALIDMDN);
		}
		else{
		RestoreSubscriberResponseType restoreSubscriberResponse = null; 
		RestoreSubscriberRequestType restoreSubscriberRequest = new RestoreSubscriberRequestType();

		com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber.SubscriberInfoType subscriberInfo = new com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber.SubscriberInfoType();
		subscriberInfo.setMdn(mdn);
		restoreSubscriberRequest.setSubscriberInfo(subscriberInfo);

		com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber.ChangeSubStatusInfoType changeSubStatusInfo = new com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber.ChangeSubStatusInfoType();
		com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber.ActivityInfoType activityInfoType = new com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber.ActivityInfoType();
		activityInfoType.setActivityReason(activityReason);
		changeSubStatusInfo.setActivityInfo(activityInfoType);

		com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber.MemoInfoType MemoInfo = new com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber.MemoInfoType();
		MemoInfo.setMemoSource(memoSource);
		MemoInfo.setUserText(userText);
		changeSubStatusInfo.setMemoInfo(MemoInfo);

		restoreSubscriberRequest.setChangeSubStatusInfo(changeSubStatusInfo);

		try {
			formatSuccessHeader();
			restoreSubscriberResponse = subscriberManagementProxyService.restoreSubscriber(restoreSubscriberRequest, successHeader);
			return new RestoreResponse(restoreSubscriberResponse.getMessage());
		} catch (Exception e) {
			raiseSnmpAlarm();
			return new RestoreResponse(FAILURE);
		}
	}
	}
	/**
	 * 
	 */
	public void transactionLog(){
		LOGGER.debug("Transaction Log to be implemented");
	}

	/**
	 * 
	 */
	private void raiseSnmpAlarm() {
		LOGGER.debug("Raise Alarm to be implemented");

	}

	/**
	 * Method isSessionValid.
	 * @return Boolean
	 */
	public Boolean isSessionValid(){
		return true;
	}


	/**
	 * Method getquerySubscriberPrepaidInfo.
	 * @return String 
	 */
	public String getquerySubscriberPrepaidInfo(){
		return "subcriberPrepaidInfoObject" ;
	}


}
