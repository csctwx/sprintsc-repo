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
import com.sprint.integration.eai.services.addressmanagementservice.v1.addressmanagementservice.AddressManagementServicePortType;
import com.sprint.integration.eai.services.addressmanagementservice.v1.addressmanagementservice.SoapFault;
import com.sprint.integration.interfaces.validateaddress.v1.validateaddress.RequestAddressType;
import com.sprint.integration.interfaces.validateaddress.v1.validateaddress.ResponseAddressListType;
import com.sprint.integration.interfaces.validateaddress.v1.validateaddress.ResponseAddressType;
import com.sprint.integration.interfaces.validateaddress.v1.validateaddress.ValidateAddressResponseType;
import com.sprint.integration.interfaces.validateaddress.v1.validateaddress.ValidateAddressType;


/**
 * Created by esvwxal on 21/01/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class AddressManagementProxyServiceTest {
    private Logger logger = LoggerFactory.getLogger(AddressManagementProxyServiceTest.class);
    
    @Autowired
    private AddressManagementProxyService service;

    @Autowired
    private AddressManagementServicePortType mockPort;
    
    private ValidateAddressResponseType validateAddressResponse = null;


    WsMessageHeaderType successHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> successHeader = new Holder<WsMessageHeaderType>(
			successHead);
	
    WsMessageHeaderType failHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> failureHeader = new Holder<WsMessageHeaderType>(
			failHead);
	
	@Value("${AddressManagementService.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${AddressManagementService.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${AddressManagementService.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${AddressManagementService.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${AddressManagementService.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${AddressManagementService.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${AddressManagementService.trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${AddressManagementService.trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${AddressManagementService.trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${AddressManagementService.trackingHeadFailTimeToLive}")
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
       
	validateAddressResponse = new ValidateAddressResponseType();
	
	ResponseAddressType responseAddress1 = new ResponseAddressType();
	responseAddress1.setAddressLine1("2109 Fox Dr");
	responseAddress1.setCity("Champaign");
	responseAddress1.setState("IL");
	responseAddress1.setZipCode("61820");
	
	ResponseAddressListType responseAddressList = new ResponseAddressListType();
	responseAddressList.getValidatedAddressInfo().add(responseAddress1);
	
	validateAddressResponse.setValidatedAddressList(responseAddressList);
	
	}

    @Test
    public void testValidateAddress() throws SoapFault {
    	
    	ValidateAddressType validateAddressRequest = new ValidateAddressType();
    	RequestAddressType requestAddress = new RequestAddressType();
    	requestAddress.setAddressLine1("2109, Fox Drive");
    	requestAddress.setCity("Champaign");
    	requestAddress.setState("IL");
    	requestAddress.setZipCode("61820");
    	validateAddressRequest.setAddressInfo(requestAddress);
    	
    	ValidateAddressResponseType validateAddressResponse2 = service.validateAddress(validateAddressRequest, successHeader);
    	Assert.assertEquals(validateAddressResponse.getValidatedAddressList().getValidatedAddressInfo().get(0).getCity(), validateAddressResponse2.getValidatedAddressList().getValidatedAddressInfo().get(0).getCity());
    } 	  
    
}
