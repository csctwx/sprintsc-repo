package com.ericsson.cac.sprint.adapters;

import java.math.BigInteger;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.Holder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sprint.integration.common.header.wsmessageheader.v2.TrackingMessageHeaderType;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.interfaces.querysubscriberinfoservice.v1.querysubscriberinfoservice.SoapFaultV2;
import com.sprint.integration.interfaces.querysubscriberservices.v1.querysubscriberservices.QuerySubscriberServicesRequest;
import com.sprint.integration.interfaces.querysubscriberservices.v1.querysubscriberservices.QuerySubscriberServicesResponse;

/**
 * Created by esvwxal on 21/01/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class QuerySubscriberInfoProxyServiceTest {
    private Logger logger = LoggerFactory.getLogger(QuerySubscriberInfoProxyServiceTest.class);
    

    
    @Autowired
    private QuerySubscriberInfoProxyService service;

    //~ @Autowired
    //~ private QuerySubscriberInfoPortType mockPort;


    WsMessageHeaderType successHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> successHeader = new Holder<WsMessageHeaderType>(
			successHead);
	
    WsMessageHeaderType failHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> failureHeader = new Holder<WsMessageHeaderType>(
			failHead);
	
	
	@Value("${QuerySubscriberInfoService.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${QuerySubscriberInfoService.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${QuerySubscriberInfoService.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${QuerySubscriberInfoService.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${QuerySubscriberInfoService.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${QuerySubscriberInfoService.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${QuerySubscriberInfoService.trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${QuerySubscriberInfoService.trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${QuerySubscriberInfoService.trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${QuerySubscriberInfoService.trackingHeadFailTimeToLive}")
	private String trackingHeadFailTimeToLive;
	
	

    @Before
    public void instructMock() throws SoapFaultV2, DatatypeConfigurationException{

    	TrackingMessageHeaderType trackingHead = new TrackingMessageHeaderType();
		trackingHead.setApplicationId(trackingHeadAppId);
		trackingHead.setApplicationUserId(trackingHeadAppUsrId);
		trackingHead.setConsumerId("trackingHeadConsumerId");
		trackingHead.setTimeToLive(new BigInteger(trackingHeadTimeToLive));
		trackingHead.setMessageId(trackingHeadMessageId);
		trackingHead.setConversationId(trackingHeadConversationId);
		GregorianCalendar c = new GregorianCalendar();
		trackingHead.setMessageDateTimeStamp(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
		successHead = new WsMessageHeaderType();
		successHead.setTrackingMessageHeader(trackingHead);
		successHeader = new Holder<WsMessageHeaderType>(successHead);
		
		TrackingMessageHeaderType trackingHeadFail = new TrackingMessageHeaderType();
		trackingHeadFail.setApplicationId(trackingHeadFailAppId);
		trackingHeadFail.setApplicationUserId(trackingHeadFailAppUsrId);
		trackingHeadFail.setConsumerId(trackingHeadFailConsumerId);
		trackingHeadFail.setTimeToLive(new BigInteger(trackingHeadFailTimeToLive));
		trackingHeadFail.setMessageDateTimeStamp(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));

		failHead = new WsMessageHeaderType();
		failHead.setTrackingMessageHeader(trackingHead);
		failureHeader = new Holder<WsMessageHeaderType>(failHead);
	
   }


    @Test
    public void testQuerySubscriberServices() throws SoapFaultV2, DatatypeConfigurationException {
            QuerySubscriberServicesRequest querySubscriberServicesRequest = new QuerySubscriberServicesRequest();
        	QuerySubscriberServicesRequest.Info info = new QuerySubscriberServicesRequest.Info();
        	info.setImsi("000009139760829");
        	querySubscriberServicesRequest.setInfo(info);       	
        	
        	
        	QuerySubscriberServicesResponse querySubscriberServicesResponse = service.querySubscriberServices(querySubscriberServicesRequest, successHeader);
        	
        	Assert.assertEquals("SPP552LTE",querySubscriberServicesResponse.getPricePlanInfo().getSocCode());
        	
    }
    
}
