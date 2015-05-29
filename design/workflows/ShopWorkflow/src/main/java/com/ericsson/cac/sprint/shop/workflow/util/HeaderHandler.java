/*package com.ericsson.cac.sprint.shopcare.workflow.util;

import java.math.BigInteger;
import java.util.GregorianCalendar;
import java.util.UUID;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.Holder;

import org.apache.commons.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sprint.integration.common.header.wsmessageheader.v2.TrackingMessageHeaderType;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;

@Component
public class HeaderHandler {
	
	private static final String TRACKING_HEAD_APP_ID=".trackingHeadAppId";
	private static final String TRACKING_HEAD_APP_USER_ID=".trackingHeadAppUsrId";
	private static final String TRACKING_HEAD_CONSUMER_ID=".trackingHeadConsumerId";
	private static final String TRACKING_HEAD_TIME_TO_LIVE=".trackingHeadTimeToLive";
	private static final String TRACKING_HEAD_MESSAGE_ID=".trackingHeadMessageId";
	private static final String TRACKING_HEAD_CONVERSATION_ID=".trackingHeadConversationId";
	
	private static final String COMMON_TRACKING_HEAD_APP_ID="services.common.trackingHeadAppId";
	private static final String COMMON_TRACKING_HEAD_APP_USER_ID="services.common.trackingHeadAppUsrId";
	private static final String COMMON_TRACKING_HEAD_CONSUMER_ID="services.common.trackingHeadConsumerId";
	private static final String COMMON_TRACKING_HEAD_TIME_TO_LIVE="services.common.trackingHeadTimeToLive";
	private static final String COMMON_TRACKING_HEAD_MESSAGE_ID="services.common.trackingHeadMessageId";
	private static final String COMMON_TRACKING_HEAD_CONVERSATION_ID="services.common.trackingHeadConversationId";
	
	@Autowired
    private Configuration config;
	
	public Holder<WsMessageHeaderType> getHeader(Service service) throws DatatypeConfigurationException {
		String s = UUID.randomUUID().toString();
		s = s.substring(0, s.indexOf("-"));
		TrackingMessageHeaderType trackingHead = new TrackingMessageHeaderType();
		
		String trackingAppId = config.getString(service.getValue()+TRACKING_HEAD_APP_ID);
		
		if(trackingAppId == null){
			return getCommonHeader();
		}
		
		trackingHead.setApplicationId(config.getString(service.getValue()+TRACKING_HEAD_APP_ID));
		trackingHead.setApplicationUserId(config.getString(service.getValue()+TRACKING_HEAD_APP_USER_ID));
		trackingHead.setConsumerId(config.getString(service.getValue()+TRACKING_HEAD_CONSUMER_ID));
		trackingHead.setMessageId(config.getString(service.getValue()+TRACKING_HEAD_MESSAGE_ID)+s);
		trackingHead.setConversationId(config.getString(service.getValue()+TRACKING_HEAD_CONVERSATION_ID)+s);
		trackingHead.setTimeToLive(new BigInteger(config.getString(service.getValue()+TRACKING_HEAD_TIME_TO_LIVE)));
		GregorianCalendar c = new GregorianCalendar();
		trackingHead.setMessageDateTimeStamp(DatatypeFactory.newInstance()
				.newXMLGregorianCalendar(c));
		WsMessageHeaderType successHead = new WsMessageHeaderType();
		successHead.setTrackingMessageHeader(trackingHead);
		Holder<WsMessageHeaderType> successHeader = new Holder<WsMessageHeaderType>(successHead);
		return successHeader;
	}
	
	public Holder<WsMessageHeaderType> getCommonHeader() throws DatatypeConfigurationException {
		String s = UUID.randomUUID().toString();
		s = s.substring(0, s.indexOf("-"));
		TrackingMessageHeaderType trackingHead = new TrackingMessageHeaderType();
		
		trackingHead.setApplicationId(config.getString(COMMON_TRACKING_HEAD_APP_ID));
		trackingHead.setApplicationUserId(config.getString(COMMON_TRACKING_HEAD_APP_USER_ID));
		trackingHead.setConsumerId(config.getString(COMMON_TRACKING_HEAD_CONSUMER_ID));
		trackingHead.setMessageId(config.getString(COMMON_TRACKING_HEAD_MESSAGE_ID)+s);
		trackingHead.setConversationId(config.getString(COMMON_TRACKING_HEAD_CONVERSATION_ID)+s);
		trackingHead.setTimeToLive(new BigInteger(config.getString(COMMON_TRACKING_HEAD_TIME_TO_LIVE)));
		GregorianCalendar c = new GregorianCalendar();
		trackingHead.setMessageDateTimeStamp(DatatypeFactory.newInstance()
				.newXMLGregorianCalendar(c));
		WsMessageHeaderType successHead = new WsMessageHeaderType();
		successHead.setTrackingMessageHeader(trackingHead);
		Holder<WsMessageHeaderType> successHeader = new Holder<WsMessageHeaderType>(successHead);
		return successHeader;
	}
}
*/