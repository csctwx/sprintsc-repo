package com.ericsson.cac.sprint.adapters;

import java.math.BigInteger;
import java.util.GregorianCalendar;
import java.util.List;

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
import com.sprint.integration.eai.services.enterprisepaymentservice.v1.enterprisepaymentservice.EnterprisePaymentPortType;
import com.sprint.integration.eai.services.enterprisepaymentservice.v1.enterprisepaymentservice.Faultmessage;
import com.sprint.integration.interfaces.eppkeyexchange.v1.eppkeyexchange.EppKeyExchangeRequestType;
import com.sprint.integration.interfaces.eppkeyexchange.v1.eppkeyexchange.EppKeyExchangeResponseType;
import com.sprint.integration.interfaces.eppkeyexchange.v1.eppkeyexchange.SignatureCertificateInfoType;
import com.sprint.integration.interfaces.eppkeyexchange.v1.eppkeyexchange.SignatureCertificateListType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class EnterprisePaymentProxyServiceTest {
	private Logger logger = LoggerFactory.getLogger(EnterprisePaymentProxyServiceTest.class);
	@Autowired
    private EnterprisePaymentProxyService service;
	@Autowired
	private EnterprisePaymentPortType stub;
	
	WsMessageHeaderType successHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> successHeader = new Holder<WsMessageHeaderType>(
			successHead);
	
    WsMessageHeaderType failHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> failureHeader = new Holder<WsMessageHeaderType>(
			failHead);
	private EppKeyExchangeResponseType eppKeyResponse;
	private OperationResponse operationResponse;
	
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
		
		eppKeyResponse=new EppKeyExchangeResponseType();
		operationResponse=new OperationResponse();
				
		
	}
	
	@Test
	public void testEppKeyExchange() throws Faultmessage{
		EppKeyExchangeRequestType eppKeyExchangeRequest=new EppKeyExchangeRequestType();
		SignatureCertificateListType signatureCertificateList=new SignatureCertificateListType();
		List<SignatureCertificateInfoType> signatureCertificateInfo=null;
		SignatureCertificateInfoType cert=new SignatureCertificateInfoType();
		byte[] ephemeralPublicKey={001,002};
		byte[] signature={001,002};
		byte[] certificate={0003,0005};
		cert.setCertificate(certificate);
		signatureCertificateInfo.add(cert);
		signatureCertificateList.getSignatureCertificateInfo();
		eppKeyExchangeRequest.setSignature(signature);
		eppKeyExchangeRequest.setSignatureCertificateList(signatureCertificateList);
		eppKeyExchangeRequest.setKeyId("Key11");
		eppKeyExchangeRequest.setRecipient("Rec11");
		eppKeyExchangeRequest.setEphemeralPublicKey(ephemeralPublicKey);
		eppKeyResponse=service.eppKeyExchange(eppKeyExchangeRequest, successHeader);
		Assert.assertEquals("Key11", eppKeyResponse.getKeyExchangeResponse().getKeyId());
	}
	
	@Test
	public void testProcessPayment() throws Faultmessage{
		EncryptedMessage processPayment=new EncryptedMessage();
		processPayment.setId("Id11");
		processPayment.setCreated(11111);
		processPayment.setType("type1");
		operationResponse=service.processPayment(processPayment, successHeader);
		Assert.assertEquals("Id11", operationResponse.getEncryptedMessage().getId());
	}

}
