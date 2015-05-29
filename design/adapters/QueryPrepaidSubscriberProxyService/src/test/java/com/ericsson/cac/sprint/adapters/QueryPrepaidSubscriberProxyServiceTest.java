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
import com.sprint.integration.interfaces.querybalanceandcharges.v2.querybalanceandchargesv2.QueryBalanceAndChargesV2RequestType;
import com.sprint.integration.interfaces.querybalanceandcharges.v2.querybalanceandchargesv2.QueryBalanceAndChargesV2ResponseType;
import com.sprint.integration.interfaces.querybalanceandcharges.v2.querybalanceandchargesv2.SubscriberInformationType;
import com.sprint.integration.interfaces.queryprepaidallowance.v1.queryprepaidallowance.ApplicationCallingInfoType;
import com.sprint.integration.interfaces.queryprepaidallowance.v1.queryprepaidallowance.QueryPrepaidAllowanceRequestType;
import com.sprint.integration.interfaces.queryprepaidallowance.v1.queryprepaidallowance.QueryPrepaidAllowanceResponseType;
import com.sprint.integration.interfaces.queryprepaidallowance.v1.queryprepaidallowance.SearchCriteriaType;
import com.sprint.integration.interfaces.queryprepaidsubscriberservice.v1.queryprepaidsubscriberservice.QueryPrepaidSubscriberPortType;
import com.sprint.integration.interfaces.queryprepaidsubscriberservice.v1.queryprepaidsubscriberservice.SoapFaultV2;
import com.sprint.integration.interfaces.queryprepaidusagedetails.v1.queryprepaidusagedetails.PaginationInfoType;
import com.sprint.integration.interfaces.queryprepaidusagedetails.v1.queryprepaidusagedetails.PrepaidUsageDetailsRequestInfoType;
import com.sprint.integration.interfaces.queryprepaidusagedetails.v1.queryprepaidusagedetails.QueryPrepaidUsageDetailsRequestType;
import com.sprint.integration.interfaces.queryprepaidusagedetails.v1.queryprepaidusagedetails.QueryPrepaidUsageDetailsResponseType;
import com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.QuerySubscriberPrepaidInfoResponseType;
import com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.QuerySubscriberPrepaidInfoType;

/**
 * Created by echasis on 20/01/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)

public class QueryPrepaidSubscriberProxyServiceTest {
	private Logger logger = LoggerFactory
			.getLogger(QueryPrepaidSubscriberProxyServiceTest.class);
	@Autowired
	private QueryPrepaidSubscriberProxyService service;

	@Autowired
	private QueryPrepaidSubscriberPortType mockPort;
	
	
	WsMessageHeaderType successHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> successHeader = new Holder<WsMessageHeaderType>(
			successHead);
	
	WsMessageHeaderType failHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> failureHeader = new Holder<WsMessageHeaderType>(
			failHead);
	
	private QueryBalanceAndChargesV2ResponseType queryBalanceAndChargesV2Response = null;
	private QueryPrepaidAllowanceResponseType queryPrepaidAllowanceResponse = null;
	private QueryPrepaidUsageDetailsResponseType queryPrepaidUsageDetailsResponse = null;
	private QuerySubscriberPrepaidInfoResponseType querySubscriberPrepaidInfoResponse = null;
	
	@Value("${QueryPrepaidSubscriberService.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${QueryPrepaidSubscriberService.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${QueryPrepaidSubscriberService.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${QueryPrepaidSubscriberService.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${QueryPrepaidSubscriberService.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${QueryPrepaidSubscriberService.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${QueryPrepaidSubscriberService.trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${QueryPrepaidSubscriberService.trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${QueryPrepaidSubscriberService.trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${QueryPrepaidSubscriberService.trackingHeadFailTimeToLive}")
	private String trackingHeadFailTimeToLive;

	@Before
	public void instructMock() throws SoapFaultV2,
			DatatypeConfigurationException {

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
	}

	@Test
	public void testQueryBalanceAndChargesV2() throws SoapFaultV2 {
		QueryBalanceAndChargesV2RequestType queryBalanceAndChargesV2Request = new QueryBalanceAndChargesV2RequestType();
		SubscriberInformationType subscriberInformation= new SubscriberInformationType();
		subscriberInformation.setMobileDirectoryNumber("9133878656");
		queryBalanceAndChargesV2Request.setSubscriberInformation(subscriberInformation);
		QueryBalanceAndChargesV2ResponseType queryBalanceAndChargesV2Response2 = service.queryBalanceAndChargesV2(queryBalanceAndChargesV2Request,successHeader);
		Assert.assertEquals(new java.math.BigDecimal("0.0"), queryBalanceAndChargesV2Response2.getCurrentBalance());	}
	
	@Test
	public void testQueryPrepaidAllowance() throws SoapFaultV2 {

	 QueryPrepaidAllowanceRequestType queryPrepaidAllowanceRequest = new QueryPrepaidAllowanceRequestType();
	 SearchCriteriaType searchCriteria = new SearchCriteriaType();
	 searchCriteria.setSubscriberId("80068822121");
	 ApplicationCallingInfoType applicationCallingInfo = new ApplicationCallingInfoType();
	 applicationCallingInfo.setAccessId("KHK");
	 
	 queryPrepaidAllowanceRequest.setSubscriberInfo(searchCriteria);
	 queryPrepaidAllowanceRequest.setApplicationCallingInfo(applicationCallingInfo);
	 	 
	QueryPrepaidAllowanceResponseType queryPrepaidAllowanceResponse2 = service.queryPrepaidAllowance(queryPrepaidAllowanceRequest, successHeader);

	 Assert.assertEquals("unlimited", queryPrepaidAllowanceResponse2.getAllowanceResponseInfoList().getAllowanceResponseInfo().get(0).getIncludedUnits());	 
	 }

	
	
	
	
	 @Test
	public void testQueryPrepaidUsageDetails() throws SoapFaultV2 {
	 
	 QueryPrepaidUsageDetailsRequestType queryPrepaidUsageDetailsRequest = new QueryPrepaidUsageDetailsRequestType();
	 com.sprint.integration.interfaces.queryprepaidusagedetails.v1.queryprepaidusagedetails.SearchCriteriaType searchCriteria = new com.sprint.integration.interfaces.queryprepaidusagedetails.v1.queryprepaidusagedetails.SearchCriteriaType();
	 searchCriteria.setSubscriberId("80068822121");
	 
	 com.sprint.integration.interfaces.queryprepaidusagedetails.v1.queryprepaidusagedetails.ApplicationCallingInfoType applicationCallingInfo = new com.sprint.integration.interfaces.queryprepaidusagedetails.v1.queryprepaidusagedetails.ApplicationCallingInfoType();
	 applicationCallingInfo.setAccessId("KHK");
	 
	 PrepaidUsageDetailsRequestInfoType prepaidUsageDetailsRequestInfo = new PrepaidUsageDetailsRequestInfoType();
	 PaginationInfoType paginationInfo = new PaginationInfoType();
	 paginationInfo.setPageNumber((short)1);
	 paginationInfo.setPageBufferSize((short)1);
	 prepaidUsageDetailsRequestInfo.setPaginationInfo(paginationInfo);
	 
	 queryPrepaidUsageDetailsRequest.setSubscriberInfo(searchCriteria);
	 queryPrepaidUsageDetailsRequest.setApplicationCallingInfo(applicationCallingInfo);
	 queryPrepaidUsageDetailsRequest.setPrepaidUsageDetailsRequestInfo(prepaidUsageDetailsRequestInfo);
	 
	 
	 QueryPrepaidUsageDetailsResponseType queryPrepaidUsageDetailsResponse2 = service.queryPrepaidUsageDetails(queryPrepaidUsageDetailsRequest, successHeader);
	 Assert.assertEquals(new java.math.BigInteger("0"), queryPrepaidUsageDetailsResponse2.getPaginationResultInfo().getMoreRows());
	
	}
	
	 @Test
	 public void testQuerySubscriberPrepaidInfo() throws SoapFaultV2 {
		 QuerySubscriberPrepaidInfoType querySubscriberPrepaidInfoRequest = new QuerySubscriberPrepaidInfoType();
		 com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.SearchCriteriaType searchCriteria = new com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.SearchCriteriaType();
		 searchCriteria.setMdn("9133878656");
		 querySubscriberPrepaidInfoRequest.setInfo(searchCriteria);
		 
		 QuerySubscriberPrepaidInfoResponseType querySubscriberPrepaidInfoResponse2 = service.querySubscriberPrepaidInfo(querySubscriberPrepaidInfoRequest, successHeader);
		 System.out.println(querySubscriberPrepaidInfoResponse2.getPlanInfo().getPricePlanCode());
		 Assert.assertEquals("100565543", querySubscriberPrepaidInfoResponse2.getBasicInfo().getBan());
	 }

}
