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
import com.sprint.integration.eai.services.appledevicemanagementservice.v1.appledevicemanagementservice.AppleDeviceManagementServicePortType;
import com.sprint.integration.eai.services.appledevicemanagementservice.v1.appledevicemanagementservice.Faultmessage;
import com.sprint.integration.interfaces.queryappledevicestatus.v1.queryappledevicestatus.QueryAppleDeviceStatusRequestType;
import com.sprint.integration.interfaces.queryappledevicestatus.v1.queryappledevicestatus.QueryAppleDeviceStatusResponseType;
import com.sprint.integration.interfaces.queryappledevicestatus.v1.queryappledevicestatus.SearchCriteriaType;

/**
 * Created by esvwxal on 03/02/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class AppleDeviceManagementProxyServiceTest {
	private Logger logger = LoggerFactory.getLogger(AppleDeviceManagementProxyServiceTest.class);

	@Autowired
	private AppleDeviceManagementProxyService service;

	@Autowired
	private AppleDeviceManagementServicePortType mockPort;

	private QueryAppleDeviceStatusResponseType queryAppleDeviceStatusResponse = null;

	WsMessageHeaderType successHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> successHeader = new Holder<WsMessageHeaderType>(successHead);

	WsMessageHeaderType failHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> failureHeader = new Holder<WsMessageHeaderType>(failHead);

	@Value("${AppleDeviceManagementService.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${AppleDeviceManagementService.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${AppleDeviceManagementService.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${AppleDeviceManagementService.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${AppleDeviceManagementService.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${AppleDeviceManagementService.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${AppleDeviceManagementService.trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${AppleDeviceManagementService.trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${AppleDeviceManagementService.trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${AppleDeviceManagementService.trackingHeadFailTimeToLive}")
	private String trackingHeadFailTimeToLive;

	@Before
	public void instructMock() throws Faultmessage, DatatypeConfigurationException {

		TrackingMessageHeaderType trackingHead = new TrackingMessageHeaderType();
		trackingHead.setApplicationId(trackingHeadAppId);
		trackingHead.setApplicationUserId(trackingHeadAppUsrId);
		trackingHead.setConsumerId(trackingHeadConsumerId);
		trackingHead.setTimeToLive(new BigInteger(trackingHeadFailTimeToLive));
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

		queryAppleDeviceStatusResponse = new QueryAppleDeviceStatusResponseType();
		/*DeviceListType deviceList = new DeviceListType();
		DeviceInfoType deviceInfo = new DeviceInfoType();
		deviceInfo.setDeviceSerialNumber("12345");
		deviceList.getDeviceInfo().add(deviceInfo);
		queryAppleDeviceStatusResponse.setDeviceList(deviceList);*/

		/*
		 * when(mockPort.queryAppleDeviceStatus(any(
		 * QueryAppleDeviceStatusRequestType.class),
		 * eq(successHeader))).thenReturn(queryAppleDeviceStatusResponse);
		 * 
		 * ErrorDetailsType details = new ErrorDetailsType();
		 * 
		 * when( mockPort.queryAppleDeviceStatus(
		 * any(QueryAppleDeviceStatusRequestType.class),
		 * eq(failureHeader))).thenThrow(new Faultmessage("test fail",
		 * details));
		 */

	}

	@Test
	public void testQueryAppleDeviceStatus() throws Faultmessage {

		QueryAppleDeviceStatusRequestType queryAppleDeviceStatusRequest = new QueryAppleDeviceStatusRequestType();
		SearchCriteriaType searchCriteria = new SearchCriteriaType();
		searchCriteria.setDeviceSerialNumber("12345");
		queryAppleDeviceStatusRequest.setInfo(searchCriteria);
		queryAppleDeviceStatusResponse = service.queryAppleDeviceStatus(queryAppleDeviceStatusRequest, successHeader);
		Assert.assertEquals(
				"Apple",
				queryAppleDeviceStatusResponse.getDeviceList().getDeviceInfo().get(0));
						
	}

}
