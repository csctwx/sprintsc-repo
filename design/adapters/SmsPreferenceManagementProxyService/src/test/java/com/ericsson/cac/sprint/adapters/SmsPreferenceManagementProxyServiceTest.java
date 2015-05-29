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
import com.sprint.integration.eai.services.smspreferencemanagementservice.v1.smspreferencemanagementservice.SmsPreferenceManagementServicePortType;
import com.sprint.integration.eai.services.smspreferencemanagementservice.v1.smspreferencemanagementservice.SoapFault;
import com.sprint.integration.interfaces.querysmspreferenceinfo.v1.querysmspreferenceinfo.QuerySmsPreferenceInfoRequestType;
import com.sprint.integration.interfaces.querysmspreferenceinfo.v1.querysmspreferenceinfo.QuerySmsPreferenceInfoResponseType;
import com.sprint.integration.interfaces.updatesmspreferenceinfo.v1.updatesmspreferenceinfo.ResourceInfoType;
import com.sprint.integration.interfaces.updatesmspreferenceinfo.v1.updatesmspreferenceinfo.ResourceListType;
import com.sprint.integration.interfaces.updatesmspreferenceinfo.v1.updatesmspreferenceinfo.UpdateSmsPreferenceInfoRequestType;
import com.sprint.integration.interfaces.updatesmspreferenceinfo.v1.updatesmspreferenceinfo.UpdateSmsPreferenceInfoResponseType;

/**
 * Created by echasis on 22/01/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class SmsPreferenceManagementProxyServiceTest {
	private Logger logger = LoggerFactory
			.getLogger(SmsPreferenceManagementProxyServiceTest.class);
			
	@Autowired
	private SmsPreferenceManagementProxyService service;

	@Autowired
	private SmsPreferenceManagementServicePortType mockPort;

	WsMessageHeaderType successHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> successHeader = new Holder<WsMessageHeaderType>(
			successHead);
	
	WsMessageHeaderType failHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> failureHeader = new Holder<WsMessageHeaderType>(
			failHead);
	
	@Value("${SmsPreferenceManagementService.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${SmsPreferenceManagementService.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${SmsPreferenceManagementService.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${SmsPreferenceManagementService.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${SmsPreferenceManagementService.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${SmsPreferenceManagementService.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${SmsPreferenceManagementService.trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${SmsPreferenceManagementService.trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${SmsPreferenceManagementService.trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${SmsPreferenceManagementService.trackingHeadFailTimeToLive}")
	private String trackingHeadFailTimeToLive;

	@Before
	public void instructMock() throws SoapFault,
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
	public void testQuerySmsPreferenceInfo() throws SoapFault {
		QuerySmsPreferenceInfoRequestType querySmsPreferenceInfoRequest = new QuerySmsPreferenceInfoRequestType();
		com.sprint.integration.interfaces.querysmspreferenceinfo.v1.querysmspreferenceinfo.InfoType info = new com.sprint.integration.interfaces.querysmspreferenceinfo.v1.querysmspreferenceinfo.InfoType();
		info.setMdn("1234567890");
		querySmsPreferenceInfoRequest.setInfo(info);
		
		QuerySmsPreferenceInfoResponseType querySmsPreferenceInfoResponse = service
				.querySmsPreferenceInfo(querySmsPreferenceInfoRequest,
						successHeader);
		com.sprint.integration.interfaces.querysmspreferenceinfo.v1.querysmspreferenceinfo.InfoType result = new com.sprint.integration.interfaces.querysmspreferenceinfo.v1.querysmspreferenceinfo.InfoType();
		String resultMdn = "1234567890";
		Assert.assertTrue("mdn not equal", querySmsPreferenceInfoResponse.getInfo().getMdn().equals(resultMdn));
	}
	
	@Test
	public void testUpdateSmsPreferenceInfo() throws SoapFault {
		UpdateSmsPreferenceInfoRequestType updateSmsPreferenceInfoRequest = new UpdateSmsPreferenceInfoRequestType();
		com.sprint.integration.interfaces.updatesmspreferenceinfo.v1.updatesmspreferenceinfo.InfoType info = new com.sprint.integration.interfaces.updatesmspreferenceinfo.v1.updatesmspreferenceinfo.InfoType();
		info.setMdn("12334567890");
		updateSmsPreferenceInfoRequest.setInfo(info);
		
		ResourceListType blockList = new ResourceListType();
		ResourceInfoType resourceInfo = new ResourceInfoType();
		resourceInfo.setResourceId("111");
		blockList.getResourceInfo().add(resourceInfo);
		updateSmsPreferenceInfoRequest.setBlockList(blockList);
		
		
		ResourceListType whiteList = new ResourceListType();
		ResourceInfoType resourceInfo1 = new ResourceInfoType();
		resourceInfo1.setResourceId("1111");
		whiteList.getResourceInfo().add(resourceInfo1);
		updateSmsPreferenceInfoRequest.setWhiteList(whiteList);
		
		UpdateSmsPreferenceInfoResponseType updateSmsPreferenceInfoResponse = service.updateSmsPreferenceInfo(updateSmsPreferenceInfoRequest,successHeader);
		String confirmationMessage = "Confirmed!";
		Assert.assertTrue("Confirmation not equal",updateSmsPreferenceInfoResponse.getConfirmationMessage().equals(confirmationMessage));
		
	}

}
