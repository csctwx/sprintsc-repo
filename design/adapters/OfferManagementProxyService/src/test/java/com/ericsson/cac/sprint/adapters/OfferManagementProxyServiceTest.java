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

import com.sprint.integration.common.errordetailsv2.ErrorDetailsType;
import com.sprint.integration.common.header.wsmessageheader.v2.TrackingMessageHeaderType;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.interfaces.offermanagementservice.v1.offermanagementservice.OfferManagementServicePortType;
import com.sprint.integration.interfaces.offermanagementservice.v1.offermanagementservice.SoapFaultv2;
import com.sprint.integration.interfaces.querytargetedoffers.v1.querytargetedoffers.QueryTargetedOffersResponseType;
import com.sprint.integration.interfaces.querytargetedoffers.v1.querytargetedoffers.QueryTargetedOffersType;
import com.sprint.integration.interfaces.querytargetedoffers.v1.querytargetedoffers.SearchCriteraType;
import com.sprint.integration.interfaces.setofferdisposition.v1.setofferdisposition.OfferInfoType;
import com.sprint.integration.interfaces.setofferdisposition.v1.setofferdisposition.OfferListType;
import com.sprint.integration.interfaces.setofferdisposition.v1.setofferdisposition.SetOfferDispositionResponseType;
import com.sprint.integration.interfaces.setofferdisposition.v1.setofferdisposition.SetOfferDispositionType;

/**
 * Created by esvwxal on 03/02/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class OfferManagementProxyServiceTest {
	private Logger logger = LoggerFactory.getLogger(OfferManagementProxyServiceTest.class);

	/*
	 * @Autowired private PropertiesConfiguration configuration;
	 */

	@Autowired
	private OfferManagementProxyService service;

	@Autowired
	private OfferManagementServicePortType mockPort;

	private QueryTargetedOffersResponseType queryTargetedOffersResponse = null;
	private SetOfferDispositionResponseType setOfferDispositionResponse = null;

	WsMessageHeaderType successHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> successHeader = new Holder<WsMessageHeaderType>(successHead);

	WsMessageHeaderType failHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> failureHeader = new Holder<WsMessageHeaderType>(failHead);

	@Value("${OfferManagementService.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${OfferManagementService.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${OfferManagementService.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${OfferManagementService.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${OfferManagementService.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${OfferManagementService.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${OfferManagementService.trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${OfferManagementService.trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${OfferManagementService.trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${OfferManagementService.trackingHeadFailTimeToLive}")
	private String trackingHeadFailTimeToLive;

	@Before
	public void instructMock() throws SoapFaultv2, DatatypeConfigurationException {

		TrackingMessageHeaderType trackingHead = new TrackingMessageHeaderType();
		trackingHead.setApplicationId(trackingHeadAppId);
		trackingHead.setApplicationUserId(trackingHeadAppUsrId);
		trackingHead.setConsumerId(trackingHeadConsumerId);
		trackingHead.setTimeToLive(new BigInteger(trackingHeadTimeToLive));
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
		ErrorDetailsType details = new ErrorDetailsType();

		queryTargetedOffersResponse = new QueryTargetedOffersResponseType();
		queryTargetedOffersResponse.setBan(5);

		/*
		 * when(mockPort.queryTargetedOffers(any(QueryTargetedOffersType.class),
		 * eq(successHeader))).thenReturn( queryTargetedOffersResponse);
		 * when(mockPort.queryTargetedOffers(any(QueryTargetedOffersType.class),
		 * eq(failureHeader))).thenThrow( new SoapFaultv2("test fail",
		 * details));
		 * 
		 * setOfferDispositionResponse = new SetOfferDispositionResponseType();
		 * setOfferDispositionResponse.setMessage("abcdef");
		 * 
		 * when(mockPort.setOfferDisposition(any(SetOfferDispositionType.class),
		 * eq(successHeader))).thenReturn( setOfferDispositionResponse);
		 * when(mockPort.setOfferDisposition(any(SetOfferDispositionType.class),
		 * eq(failureHeader))).thenThrow( new SoapFaultv2("test fail",
		 * details));
		 */

	}

	@Test
	public void testQueryTargetedOffers() throws SoapFaultv2 {

		QueryTargetedOffersType queryTargetedOffersRequest = new QueryTargetedOffersType();
		SearchCriteraType searchCritera = new SearchCriteraType();
		searchCritera.setBan(5);
		queryTargetedOffersRequest.setInfo(searchCritera);

		QueryTargetedOffersResponseType queryTargetedOffersResponse2 = service.queryTargetedOffers(
				queryTargetedOffersRequest, successHeader);
		Assert.assertEquals("Responses are not same",
				queryTargetedOffersResponse2.getBan().equals(queryTargetedOffersResponse.getBan()));
	}

	@Test
	public void testSetOfferDisposition() throws SoapFaultv2, DatatypeConfigurationException {

		SetOfferDispositionType setOfferDispositionRequest = new SetOfferDispositionType();

		OfferListType offerList = new OfferListType();
		OfferInfoType OfferInfo = new OfferInfoType();
		OfferInfo.setLevel("3");
		OfferInfo.setInteractionId("5");
		OfferInfo.setUserId("abcd5");
		offerList.getOfferInfo().add(OfferInfo);

		setOfferDispositionRequest.setOfferList(offerList);
		GregorianCalendar c = new GregorianCalendar();
		setOfferDispositionRequest.setDispositionDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));

		SetOfferDispositionResponseType setOfferDispositionResponse2 = service.setOfferDisposition(
				setOfferDispositionRequest, successHeader);
		Assert.assertEquals("Responses are not same",
				setOfferDispositionResponse2.getMessage().equals(setOfferDispositionResponse.getMessage()));
	}

}
