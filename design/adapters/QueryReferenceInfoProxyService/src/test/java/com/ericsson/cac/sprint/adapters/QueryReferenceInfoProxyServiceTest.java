
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
import com.sprint.integration.eai.services.queryreferenceinfoservice.v1.queryreferenceinfoservice.Faultmessage;
import com.sprint.integration.interfaces.queryreferencesecurityinfo.v1.queryreferencesecurityinfo.QueryReferenceSecurityInfoRequest;
import com.sprint.integration.interfaces.queryreferencesecurityinfo.v1.queryreferencesecurityinfo.QueryReferenceSecurityInfoResponse;
import com.sprint.integration.interfaces.queryreferencesecurityinfo.v1.queryreferencesecurityinfo.RequestedSecurityInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class QueryReferenceInfoProxyServiceTest{
    private Logger logger = LoggerFactory.getLogger(QueryReferenceInfoProxyServiceTest.class);
    
    @Autowired
    private QueryReferenceInfoProxyService service;

   /* @Autowired
    private QueryReferenceInfoProxyService mockPort;*/
    
    private QueryReferenceSecurityInfoResponse queryReferenceSecurityInfoResponse = null;


    WsMessageHeaderType successHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> successHeader = new Holder<WsMessageHeaderType>(
			successHead);
	
    WsMessageHeaderType failHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> failureHeader = new Holder<WsMessageHeaderType>(
			failHead);
	
	@Value("${QueryReferenceInfoService.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${QueryReferenceInfoService.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${QueryReferenceInfoService.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${QueryReferenceInfoService.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${QueryReferenceInfoService.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${QueryReferenceInfoService.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${QueryReferenceInfoService.trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${QueryReferenceInfoService.trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${QueryReferenceInfoService.trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${QueryReferenceInfoService.trackingHeadFailTimeToLive}")
	private String trackingHeadFailTimeToLive;

    @Before
    public void instructMock() throws Faultmessage, DatatypeConfigurationException{


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
		
	
/*	
	when(mockPort.queryReferenceSecurityInfo(any(QueryReferenceSecurityInfoRequest.class), eq(successHeader))).thenReturn(queryReferenceSecurityInfoResponse);
	
	ErrorDetailsType details = new ErrorDetailsType();
	
	when(
			mockPort.queryReferenceSecurityInfo(
					any(QueryReferenceSecurityInfoRequest.class),
					eq(failureHeader))).thenThrow(new Faultmessage("test fail", details));*/

	

    }


    @Test
    public void testqueryReferenceSecurityInfo() throws Faultmessage {
    	
    	QueryReferenceSecurityInfoRequest queryReferenceSecurityInfoRequest = new QueryReferenceSecurityInfoRequest();
    	RequestedSecurityInfo requestedSecurityInfo = new RequestedSecurityInfo();
    	requestedSecurityInfo.setBrandCode("SPP");
    	requestedSecurityInfo.setAccountType("I");
    	requestedSecurityInfo.setAccountSubType("M");
    	queryReferenceSecurityInfoRequest.setSecurityInfo(requestedSecurityInfo);   	
    	
    	QueryReferenceSecurityInfoResponse queryReferenceSecurityInfoResponse2 = service.queryReferenceSecurityInfo(queryReferenceSecurityInfoRequest, successHeader);
    	//assert queryReferenceSecurityInfoResponse2.getSecurityInfo().getSecurityQuestionList().getSecurityQuestionInfo().get(0).getQuestionCode().equals(queryReferenceSecurityInfoResponse.getSecurityInfo().getSecurityQuestionList().getSecurityQuestionInfo().get(0).getQuestionCode()) : "Responses's Question Codes are not same";
    	Assert.assertEquals("Responses's Question Codes are not same", "b01", queryReferenceSecurityInfoResponse2.getSecurityInfo().getSecurityQuestionList().getSecurityQuestionInfo().get(0).getQuestionCode());
    				
            
        }
    
    
}