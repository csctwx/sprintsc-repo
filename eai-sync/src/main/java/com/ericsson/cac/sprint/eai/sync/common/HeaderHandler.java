package com.ericsson.cac.sprint.eai.sync.common;

import java.math.BigInteger;
import java.util.GregorianCalendar;
import java.util.UUID;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sprint.integration.common.header.wsmessageheader.v2.TrackingMessageHeaderType;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;

@Component
public class HeaderHandler {
	
	@Value("${services.common.trackingHeadAppId}")
	private String commonTrackingHeadAppId;
	
	@Value("${services.common.trackingHeadAppUsrId}")
	private String commonTrackingHeadAppUserId;
	
	@Value("${services.common.trackingHeadConsumerId}")
	private String commonTracking_headConsumerId;
	
	@Value("${services.common.trackingHeadTimeToLive}")
	private String commonTrackingHeadTimeToLive;
	
	@Value("${services.common.trackingHeadMessageId}")
	private String commonTrackingHeadMessageId;
	
	@Value("${services.common.trackingHeadConversationId}")
	private String commonTrackingHeadConversationId;
	
	public Holder<WsMessageHeaderType> getHeader() throws DatatypeConfigurationException {
		return getCommonHeader();
	}
	
	public Holder<WsMessageHeaderType> getCommonHeader() throws DatatypeConfigurationException {
		String s = UUID.randomUUID().toString();
		s = s.substring(0, s.indexOf("-"));
		TrackingMessageHeaderType trackingHead = new TrackingMessageHeaderType();
		
		trackingHead.setApplicationId(commonTrackingHeadAppId);
		trackingHead.setApplicationUserId(commonTrackingHeadAppUserId);
		trackingHead.setConsumerId(commonTracking_headConsumerId);
		trackingHead.setMessageId(commonTrackingHeadMessageId+s);
		trackingHead.setConversationId(commonTrackingHeadConversationId+s);
		trackingHead.setTimeToLive(new BigInteger(commonTrackingHeadTimeToLive));
		GregorianCalendar c = new GregorianCalendar();
		trackingHead.setMessageDateTimeStamp(DatatypeFactory.newInstance()
				.newXMLGregorianCalendar(c));
		WsMessageHeaderType successHead = new WsMessageHeaderType();
		successHead.setTrackingMessageHeader(trackingHead);
		Holder<WsMessageHeaderType> successHeader = new Holder<WsMessageHeaderType>(successHead);
		return successHeader;
	}
}
