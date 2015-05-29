
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
import com.sprint.integration.interfaces.queryprepaidbalanceandthresholdinfo.v1.queryprepaidbalanceandthresholdinfo.ApplicationCallingInfoType;
import com.sprint.integration.interfaces.queryprepaidbalanceandthresholdinfo.v1.queryprepaidbalanceandthresholdinfo.QueryPrepaidBalanceAndThresholdInfoRequestType;
import com.sprint.integration.interfaces.queryprepaidbalanceandthresholdinfo.v1.queryprepaidbalanceandthresholdinfo.QueryPrepaidBalanceAndThresholdInfoResponseType;
import com.sprint.integration.interfaces.queryprepaidbalanceandthresholdinfo.v1.queryprepaidbalanceandthresholdinfo.SearchCriteriaType;
import com.sprint.integration.interfaces.queryusageservice.v1.queryusageservice.QueryUsageService;
import com.sprint.integration.interfaces.queryusageservice.v1.queryusageservice.QueryUsageServicePortType;
import com.sprint.integration.interfaces.queryusageservice.v1.queryusageservice.SoapFault;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)

public class QueryUsageProxyServiceTest{
    private Logger logger = LoggerFactory.getLogger(QueryUsageProxyServiceTest.class);
    
    /*@Autowired
	private PropertiesConfiguration configuration;*/
    
    @Autowired
    private QueryUsageServicePortType service;

    @Autowired
    private QueryUsageService mockPort;
    
    
    QueryPrepaidBalanceAndThresholdInfoResponseType queryPrepaidBalanceAndThresholdInfoResponse = null;


    WsMessageHeaderType successHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> successHeader = new Holder<WsMessageHeaderType>(
			successHead);
	
    WsMessageHeaderType failHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> failureHeader = new Holder<WsMessageHeaderType>(
			failHead);

	@Value("${QueryUsageService.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${QueryUsageService.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${QueryUsageService.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${QueryUsageService.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${QueryUsageService.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${QueryUsageService.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${QueryUsageService.trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${QueryUsageService.trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${QueryUsageService.trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${QueryUsageService.trackingHeadFailTimeToLive}")
	private String trackingHeadFailTimeToLive;
	
	@Before
    public void instructMock() throws SoapFault, DatatypeConfigurationException{

		TrackingMessageHeaderType trackingHead = new TrackingMessageHeaderType();
		trackingHead.setApplicationId(trackingHeadAppId);
		trackingHead.setApplicationUserId(trackingHeadAppUsrId);
		trackingHead.setConsumerId(trackingHeadConsumerId);
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


       
	/*queryPrepaidBalanceAndThresholdInfoResponse.setVolumeThresholdApiInd("abcd");
	queryPrepaidBalanceAndThresholdInfoResponse.setAccountBalanceApiInd("xyz");
	queryPrepaidBalanceAndThresholdInfoResponse.setAccessId("abcd");*/
	/*when(mockPort.queryPrepaidBalanceAndThresholdInfo(any(QueryPrepaidBalanceAndThresholdInfoRequestType.class), eq(successHeader))).thenReturn(queryPrepaidBalanceAndThresholdInfoResponse);
	
	ErrorDetailsType details = new ErrorDetailsType();
	
	when(
			mockPort.queryPrepaidBalanceAndThresholdInfo(
					any(QueryPrepaidBalanceAndThresholdInfoRequestType.class),
					eq(failureHeader))).thenThrow(new SoapFault("test fail", details));*/

	

    }


    @Test
    public void testqueryPrepaidBalanceAndThresholdInfo() throws SoapFault {
    	
    	QueryPrepaidBalanceAndThresholdInfoRequestType queryPrepaidBalanceAndThresholdInfoRequest = new QueryPrepaidBalanceAndThresholdInfoRequestType();
    	SearchCriteriaType searchCriteriaType = new SearchCriteriaType();
    	searchCriteriaType.setSubscriberId("80068822121");
    	//searchCriteriaType.setMdn("3179566118");
    	ApplicationCallingInfoType applicationCallingInfoType = new ApplicationCallingInfoType();
    	applicationCallingInfoType.setAccessId("KHK");
    	queryPrepaidBalanceAndThresholdInfoRequest.setSubscriberInfo(searchCriteriaType); 
    	queryPrepaidBalanceAndThresholdInfoRequest.setApplicationCallingInfo(applicationCallingInfoType);
    	
    	QueryPrepaidBalanceAndThresholdInfoResponseType queryPrepaidBalanceAndThresholdInfoResponse2 = service.queryPrepaidBalanceAndThresholdInfo(queryPrepaidBalanceAndThresholdInfoRequest, successHeader);
    		Assert.assertEquals("Message not same", queryPrepaidBalanceAndThresholdInfoResponse2.getAccessId().equals( queryPrepaidBalanceAndThresholdInfoResponse.getAccessId()));
            
        }
    
    
}