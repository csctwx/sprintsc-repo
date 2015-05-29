package com.ericsson.cac.sprint.adapters;

import java.math.BigDecimal;
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
import com.sprint.integration.eai.services.querycsa.v1.querycsa.Faultmessage;
import com.sprint.integration.eai.services.querycsa.v1.querycsa.Faultmessage2;
import com.sprint.integration.interfaces.querycsa.v2.querycsav2.GeocodingTypeCodeType;
import com.sprint.integration.interfaces.querycsa.v2.querycsav2.QueryCsaV2ResponseType;
import com.sprint.integration.interfaces.querycsa.v2.querycsav2.QueryCsaV2Type;
import com.sprint.integration.interfaces.querylocationinfo.v1.querylocationinfo.QueryLocationInfoRequestType;
import com.sprint.integration.interfaces.querylocationinfo.v1.querylocationinfo.QueryLocationInfoResponseType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class QueryCsaProxyServiceTest {
	
	private Logger logger = LoggerFactory.getLogger(QueryCsaProxyServiceTest.class);
	@Autowired
	private QueryCsaProxyService service;
	private QueryCsaV2ResponseType queryCsaV2Response;
	private QueryLocationInfoResponseType queryLocationInfoResponse;
	
	WsMessageHeaderType successHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> successHeader = new Holder<WsMessageHeaderType>(
			successHead);
	
    WsMessageHeaderType failHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> failureHeader = new Holder<WsMessageHeaderType>(
			failHead);
	
	@Value("${EnterprisePaymentService.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${EnterprisePaymentService.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${EnterprisePaymentService.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${EnterprisePaymentService.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${EnterprisePaymentService.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${EnterprisePaymentService.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${EnterprisePaymentService.trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${EnterprisePaymentService.trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${EnterprisePaymentService.trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${EnterprisePaymentService.trackingHeadFailTimeToLive}")
	private String trackingHeadFailTimeToLive;
	
	
	@Before
    public void instructMock() throws DatatypeConfigurationException{

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
		
		service = new QueryCsaProxyService();
		
	}
	
	@Test
	public void testQueryCsaV2() throws Faultmessage2{
		
		QueryCsaV2Type request= new QueryCsaV2Type();
		GeocodingTypeCodeType geoCode=GeocodingTypeCodeType.ZIP;
		request.setGeoCode(geoCode);
		queryCsaV2Response=service.queryCsaV2(request, successHeader);
		Assert.assertEquals("7331",queryCsaV2Response.getZip());
	}
	
	@Test
	public void testQueryLocationInfo() throws Faultmessage{
		
		QueryLocationInfoRequestType request=new QueryLocationInfoRequestType();
		BigDecimal lat = new BigDecimal("10.0001");
		request.setLatitude(lat);
		BigDecimal lon = new BigDecimal("10.0001");
		request.setLongitude(lon);
		queryLocationInfoResponse=service.queryLocationInfo(request, successHeader);
		Assert.assertEquals("Champaign",queryLocationInfoResponse.getCity());
	}
	
}
