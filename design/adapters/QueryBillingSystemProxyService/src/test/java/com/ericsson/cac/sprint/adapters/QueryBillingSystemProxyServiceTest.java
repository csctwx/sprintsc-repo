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
import com.sprint.integration.interfaces.querybillingsystemcode.v5.querybillingsystemcodev5.InFlightTypeCodeType;
import com.sprint.integration.interfaces.querybillingsystemcode.v5.querybillingsystemcodev5.QueryBillingSystemCodeV5RequestType;
import com.sprint.integration.interfaces.querybillingsystemcode.v5.querybillingsystemcodev5.QueryBillingSystemCodeV5ResponseType;
import com.sprint.integration.interfaces.querybillingsystemservice.v1.querybillingsystemservice.SoapFault;

/**
 * Created by esvwxal on 21/01/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class QueryBillingSystemProxyServiceTest {
	private Logger logger = LoggerFactory.getLogger(QueryBillingSystemProxyServiceTest.class);

	/*
	 * @Autowired private PropertiesConfiguration configuration;
	 */

	@Autowired
	private QueryBillingSystemProxyService service;

	/*
	 * @Autowired private QueryBillingSystemPortType mockPort;
	 */

	private QueryBillingSystemCodeV5ResponseType queryBillingSystemCodeV5Response = null;

	WsMessageHeaderType successHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> successHeader = new Holder<WsMessageHeaderType>(successHead);

	WsMessageHeaderType failHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> failureHeader = new Holder<WsMessageHeaderType>(failHead);

	@Value("${QueryBillingSystemService.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${QueryBillingSystemService.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${QueryBillingSystemService.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${QueryBillingSystemService.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${QueryBillingSystemService.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${QueryBillingSystemService.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${QueryBillingSystemService.trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${QueryBillingSystemService.trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${QueryBillingSystemService.trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${QueryBillingSystemService.trackingHeadFailTimeToLive}")
	private String trackingHeadFailTimeToLive;

	@Before
	public void instructMock() throws SoapFault, DatatypeConfigurationException {

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

		queryBillingSystemCodeV5Response = new QueryBillingSystemCodeV5ResponseType();
		queryBillingSystemCodeV5Response.setBillingSystemCode("ENS");
		queryBillingSystemCodeV5Response.setInFlight(InFlightTypeCodeType.N);

		/*
		 * when(mockPort.queryBillingSystemCodeV5(any(
		 * QueryBillingSystemCodeV5RequestType.class),
		 * eq(successHeader))).thenReturn(queryBillingSystemCodeV5Response);
		 * 
		 * ErrorDetailsType details = new ErrorDetailsType();
		 * 
		 * swhen(mockPort.queryBillingSystemCodeV5(any(
		 * QueryBillingSystemCodeV5RequestType.class),
		 * eq(failureHeader))).thenThrow(new SoapFault("test fail", details));
		 */

	}

	@Test
	public void testValidateAddress() throws SoapFault {

		QueryBillingSystemCodeV5RequestType queryBillingSystemCodeV5Request = new QueryBillingSystemCodeV5RequestType();
		queryBillingSystemCodeV5Request.setMdn("3179566118");

		QueryBillingSystemCodeV5ResponseType queryBillingSystemCodeV5Response2 = service.queryBillingSystemCodeV5(
				queryBillingSystemCodeV5Request, successHeader);
		// assert
		// queryBillingSystemCodeV5Response2.getBillingSystemCode().equals(queryBillingSystemCodeV5Response.getBillingSystemCode())
		// : "BillingSystemCode is not same";
		Assert.assertEquals("BillingSystemCode is not same", queryBillingSystemCodeV5Response.getBillingSystemCode(),
				queryBillingSystemCodeV5Response2.getBillingSystemCode());
	}

}
