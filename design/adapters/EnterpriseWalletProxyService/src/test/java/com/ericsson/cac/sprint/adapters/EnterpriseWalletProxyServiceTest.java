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

import com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage;
import com.amdocs.mfs.api.epp.v1.sprint.encryption.OperationResponse;
import com.sprint.integration.common.header.wsmessageheader.v2.TrackingMessageHeaderType;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.enterprisewalletservice.v1.enterprisewalletservice.EnterpriseWalletPortType;
import com.sprint.integration.eai.services.enterprisewalletservice.v1.enterprisewalletservice.Faultmessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class EnterpriseWalletProxyServiceTest {
	private Logger logger = LoggerFactory.getLogger(EnterpriseWalletProxyServiceTest.class);
	@Autowired
    private EnterpriseWalletProxyService service;
	@Autowired
	private EnterpriseWalletPortType stub;
	WsMessageHeaderType successHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> successHeader = new Holder<WsMessageHeaderType>(
			successHead);
	
    WsMessageHeaderType failHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> failureHeader = new Holder<WsMessageHeaderType>(
			failHead);
	private OperationResponse response;
	
	@Value("${EnterpriseWalletService.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${EnterpriseWalletService.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${EnterpriseWalletService.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${EnterpriseWalletService.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${EnterpriseWalletService.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${EnterpriseWalletService.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${EnterpriseWalletService.trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${EnterpriseWalletService.trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${EnterpriseWalletService.trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${EnterpriseWalletService.trackingHeadFailTimeToLive}")
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
		
		response=new OperationResponse();
		response.setEncryptedMessage(new EncryptedMessage());
		
	}
	
	@Test
	public void testProcessWallet() throws Faultmessage{
		EncryptedMessage processWallet=new EncryptedMessage();
		processWallet.setId("Id11");
		processWallet.setCreated(11111);
		processWallet.setType("type1");
		response=service.processWallet(processWallet, successHeader);
		Assert.assertEquals("type1",response.getEncryptedMessage().getType());
		
	}

}
